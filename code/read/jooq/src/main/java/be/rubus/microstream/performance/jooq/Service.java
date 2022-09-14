package be.rubus.microstream.performance.jooq;

import be.rubus.microstream.performance.QueryInformation;
import be.rubus.microstream.performance.StopWatch;
import be.rubus.microstream.performance.jooq.mapper.ResultMapper;
import be.rubus.microstream.performance.jooq.model.tables.Purchaseitem;
import be.rubus.microstream.performance.model.*;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Service {

    private final static Random RANDOM = new Random();

    private final DSLContext context;

    public Service(DSLContext context) {
        this.context = context;
    }

    public QueryInformation<List<Customer>> allCustomersPaged() {

        List<List<Customer>> results = new ArrayList<>();
        long pageSize = 100;

        long mappingTime = 0;

        StopWatch stopWatch = StopWatch.start();
        // 3 sets of data, page 0, 1 and 2.
        for (int page = 0; page < 3; page++) {

            Result<Record> records = context.select().from(be.rubus.microstream.performance.jooq.model.tables.Customer.CUSTOMER).join(be.rubus.microstream.performance.jooq.model.tables.Address.ADDRESS).on(be.rubus.microstream.performance.jooq.model.tables.Customer.CUSTOMER.ADDRESS_ID.eq(be.rubus.microstream.performance.jooq.model.tables.Address.ADDRESS.ID)).join(be.rubus.microstream.performance.jooq.model.tables.City.CITY).on(be.rubus.microstream.performance.jooq.model.tables.Address.ADDRESS.CITY_ID.eq(be.rubus.microstream.performance.jooq.model.tables.City.CITY.ID)).join(be.rubus.microstream.performance.jooq.model.tables.State.STATE).on(be.rubus.microstream.performance.jooq.model.tables.City.CITY.STATE_ID.eq(be.rubus.microstream.performance.jooq.model.tables.State.STATE.ID)).join(be.rubus.microstream.performance.jooq.model.tables.Country.COUNTRY).on(be.rubus.microstream.performance.jooq.model.tables.State.STATE.COUNTRY_ID.eq(be.rubus.microstream.performance.jooq.model.tables.Country.COUNTRY.ID)).limit(page * pageSize, pageSize).fetch();

            StopWatch mapping = StopWatch.start();
            results.add(records.map(this::mapFromForCustomer));
            mappingTime += mapping.stop();
        }

        long elapsed = stopWatch.stop();
        return new QueryInformation<>(results, elapsed, mappingTime);
    }

    public QueryInformation<List<Book>> booksByPrice() {
        StopWatch stopWatch = StopWatch.start();

        List<List<Book>> results = new ArrayList<>();

        long mappingTime = 0;

        int priceRange = 5;
        for (int priceStep = 1; priceStep <= 3; priceStep++) {


            double minPrice = priceStep * priceRange;
            double maxPrice = minPrice + priceRange;

            Result<Record> records = context.select().from(be.rubus.microstream.performance.jooq.model.tables.Book.BOOK).join(be.rubus.microstream.performance.jooq.model.tables.Author.AUTHOR).on(be.rubus.microstream.performance.jooq.model.tables.Book.BOOK.AUTHOR_ID.eq(be.rubus.microstream.performance.jooq.model.tables.Author.AUTHOR.ID)).where(be.rubus.microstream.performance.jooq.model.tables.Book.BOOK.RETAIL_PRICE.between(minPrice, maxPrice)).fetch();

            StopWatch mapping = StopWatch.start();
            results.add(records.map(this::mapFromForBook));
            mappingTime += mapping.stop();
        }
        long elapsed = stopWatch.stop();

        return new QueryInformation<>(results, elapsed, mappingTime);
    }

    public QueryInformation<List<BookSales>> bestsellerList() {

        List<Country> countries = randomCountries(3);
        int[] years = randomYears(3).toArray();

        List<List<BookSales>> results = new ArrayList<>();

        long mappingTime = 0;

        StopWatch stopWatch = StopWatch.start();

        for (int year : years) {
            for (Country country : countries) {

                Result<Record> records = context.select(be.rubus.microstream.performance.jooq.model.tables.Book.BOOK.fields()).select(DSL.sum(Purchaseitem.PURCHASEITEM.AMOUNT)).from(be.rubus.microstream.performance.jooq.model.tables.Purchase.PURCHASE).join(be.rubus.microstream.performance.jooq.model.tables.Shop.SHOP).on(be.rubus.microstream.performance.jooq.model.tables.Purchase.PURCHASE.SHOP_ID.eq(be.rubus.microstream.performance.jooq.model.tables.Shop.SHOP.ID)).join(be.rubus.microstream.performance.jooq.model.tables.Address.ADDRESS).on(be.rubus.microstream.performance.jooq.model.tables.Shop.SHOP.ADDRESS_ID.eq(be.rubus.microstream.performance.jooq.model.tables.Address.ADDRESS.ID)).join(be.rubus.microstream.performance.jooq.model.tables.City.CITY).on(be.rubus.microstream.performance.jooq.model.tables.Address.ADDRESS.CITY_ID.eq(be.rubus.microstream.performance.jooq.model.tables.City.CITY.ID)).join(be.rubus.microstream.performance.jooq.model.tables.State.STATE).on(be.rubus.microstream.performance.jooq.model.tables.City.CITY.STATE_ID.eq(be.rubus.microstream.performance.jooq.model.tables.State.STATE.ID)).join(be.rubus.microstream.performance.jooq.model.tables.Country.COUNTRY).on(be.rubus.microstream.performance.jooq.model.tables.State.STATE.COUNTRY_ID.eq(be.rubus.microstream.performance.jooq.model.tables.Country.COUNTRY.ID)).join(Purchaseitem.PURCHASEITEM).on(Purchaseitem.PURCHASEITEM.PURCHASE_ID.eq(be.rubus.microstream.performance.jooq.model.tables.Purchase.PURCHASE.ID)).join(be.rubus.microstream.performance.jooq.model.tables.Book.BOOK).on(Purchaseitem.PURCHASEITEM.BOOK_ID.eq(be.rubus.microstream.performance.jooq.model.tables.Book.BOOK.ID)).where(DSL.year(be.rubus.microstream.performance.jooq.model.tables.Purchase.PURCHASE.TIME_STAMP).eq(year)).and(be.rubus.microstream.performance.jooq.model.tables.Country.COUNTRY.ID.eq(country.getId())).groupBy(be.rubus.microstream.performance.jooq.model.tables.Book.BOOK.fields()).fetch();


                StopWatch mapping = StopWatch.start();

                List<BookSales> sales = records.map(this::mapFromForBookSales);
                List<BookSales> bestsellers = sales.stream().sorted(Comparator.comparing(BookSales::amount).reversed()).limit(10).collect(Collectors.toList());

                mappingTime += mapping.stop();

                results.add(bestsellers);
            }
        }
        long elapsed = stopWatch.stop();
        return new QueryInformation<>(results, elapsed, mappingTime);
    }

    private IntStream randomYears(int yearSpan) {
        Record2<LocalDateTime, LocalDateTime> yearRange = context.select(DSL.min(be.rubus.microstream.performance.jooq.model.tables.Purchase.PURCHASE.TIME_STAMP), DSL.max(be.rubus.microstream.performance.jooq.model.tables.Purchase.PURCHASE.TIME_STAMP)).from(be.rubus.microstream.performance.jooq.model.tables.Purchase.PURCHASE).fetchSingle();


        int minYear = extractYear(yearRange.value1());
        int maxYear = extractYear(yearRange.value2());
        int startYear = minYear + RANDOM.nextInt(maxYear - minYear - yearSpan + 1);
        return IntStream.range(startYear, startYear + yearSpan);

    }

    private int extractYear(LocalDateTime timestamp) {
        return timestamp.getYear();
    }

    private List<Country> randomCountries(int cnt) {
        Result<Record> records = context.select().from(be.rubus.microstream.performance.jooq.model.tables.Country.COUNTRY).fetch();
        List<Country> countries = records.map(this::mapFromForCountry);

        Collections.shuffle(countries);
        return countries.subList(0, cnt);
    }

    public QueryInformation<Employee> employeeOfTheYear() {

        return null;
    }

    public QueryInformation<List<Book>> booksByTitle() {


        List<Country> countries = randomCountries(3);

        List<String> patterns = Arrays.asList("the", "light", "black", "hero", "of");

        List<List<Book>> results = new ArrayList<>();


        long mappingTime = 0;

        StopWatch stopWatch = StopWatch.start();
        // 3 sets of data, page 0, 1 and 2.
        for (String pattern : patterns) {
            for (Country country : countries) {

                Result<Record> records = context.select().from(be.rubus.microstream.performance.jooq.model.tables.Book.BOOK).join(be.rubus.microstream.performance.jooq.model.tables.Author.AUTHOR).on(be.rubus.microstream.performance.jooq.model.tables.Book.BOOK.AUTHOR_ID.eq(be.rubus.microstream.performance.jooq.model.tables.Author.AUTHOR.ID)).join(be.rubus.microstream.performance.jooq.model.tables.Address.ADDRESS).on(be.rubus.microstream.performance.jooq.model.tables.Author.AUTHOR.ADDRESS_ID.eq(be.rubus.microstream.performance.jooq.model.tables.Address.ADDRESS.ID)).join(be.rubus.microstream.performance.jooq.model.tables.City.CITY).on(be.rubus.microstream.performance.jooq.model.tables.Address.ADDRESS.CITY_ID.eq(be.rubus.microstream.performance.jooq.model.tables.City.CITY.ID)).join(be.rubus.microstream.performance.jooq.model.tables.State.STATE).on(be.rubus.microstream.performance.jooq.model.tables.City.CITY.STATE_ID.eq(be.rubus.microstream.performance.jooq.model.tables.State.STATE.ID)).join(be.rubus.microstream.performance.jooq.model.tables.Country.COUNTRY).on(be.rubus.microstream.performance.jooq.model.tables.State.STATE.COUNTRY_ID.eq(be.rubus.microstream.performance.jooq.model.tables.Country.COUNTRY.ID)).where(be.rubus.microstream.performance.jooq.model.tables.Country.COUNTRY.ID.eq(country.getId())).and(be.rubus.microstream.performance.jooq.model.tables.Book.BOOK.TITLE.like("%" + pattern + "%")).fetch();

                StopWatch mapping = StopWatch.start();
                results.add(records.map(this::mapFromForBook));
                mappingTime += mapping.stop();
            }
        }

        long elapsed = stopWatch.stop();
        return new QueryInformation<>(results, elapsed, mappingTime);
    }

    private Customer mapFromForCustomer(Record record) {
        return ResultMapper.getInstance().map(record, Customer.class);
    }

    private Book mapFromForBook(Record record) {
        return ResultMapper.getInstance().map(record, Book.class);
    }

    private Country mapFromForCountry(Record record) {
        return ResultMapper.getInstance().map(record, Country.class);
    }

    private BookSales mapFromForBookSales(Record record) {
        return ResultMapper.getInstance().map(record, BookSales.class);
    }

}
