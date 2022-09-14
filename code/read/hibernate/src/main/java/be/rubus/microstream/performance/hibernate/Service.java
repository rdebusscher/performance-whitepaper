package be.rubus.microstream.performance.hibernate;

import be.rubus.microstream.performance.QueryInformation;
import be.rubus.microstream.performance.Range;
import be.rubus.microstream.performance.StopWatch;
import be.rubus.microstream.performance.hibernate.domain.*;
import be.rubus.microstream.performance.utils.DurationUtil;
import be.rubus.microstream.performance.utils.MoneyUtil;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.query.Query;

import javax.money.MonetaryAmount;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

public class Service {

    private final static Random RANDOM = new Random();

    private final Session session;

    public Service(Session session) {
        this.session = session;
    }

    public QueryInformation<List<CustomerEntity>> allCustomersPaged() {
        StopWatch stopWatch = StopWatch.StartNanoTime();

        List<List<CustomerEntity>> results = new ArrayList<>();
        int pageSize = 100;
        IntStream.rangeClosed(0, 2).forEach(page ->
                {
                    Query<CustomerEntity> query = session.createQuery("SELECT C from CustomerEntity C JOIN FETCH C.address a JOIN FETCH a.city ci JOIN FETCH ci.state st JOIN FETCH st.country", CustomerEntity.class);
                    query.setFirstResult(page * pageSize);
                    query.setMaxResults(pageSize);

                    results.add(query.getResultList());
                }
        );
        long elapsed = stopWatch.stop();
        return new QueryInformation<>(results, elapsed);
    }


    public QueryInformation<List<BookEntity>> booksByPrice() {
        StopWatch stopWatch = StopWatch.StartNanoTime();

        List<List<BookEntity>> results = new ArrayList<>();

        int priceRange = 5;
        IntStream.rangeClosed(1, 3).forEach(priceStep -> {

            final double minPrice = priceStep * priceRange;
            final double maxPrice = minPrice + priceRange;
            final MonetaryAmount minPriceMoney = MoneyUtil.money(minPrice);
            final MonetaryAmount maxPriceMoney = MoneyUtil.money(maxPrice);


            Query<BookEntity> query = session.createQuery("SELECT B from BookEntity B WHERE B.retailPrice >= :minPrice AND B.retailPrice < :maxPrice", BookEntity.class);
            query.setParameter("minPrice", minPriceMoney);
            query.setParameter("maxPrice", maxPriceMoney);

            results.add(query.getResultList());
        });
        long elapsed = stopWatch.stop();
        return new QueryInformation<>(results, elapsed);
    }

    public QueryInformation<List<BookEntitySales>> bestsellerList() {

        List<CountryEntity> countries = randomCountries(3);
        Range<Integer> randomYears = randomYears(3);


        StopWatch stopWatch = StopWatch.StartNanoTime();

        List<List<BookEntitySales>> results = new ArrayList<>();

        IntStream.rangeClosed(randomYears.getLowerBound(), randomYears.getUpperbound()).forEach(year ->
                countries.forEach(country -> {

                    String sql = "SELECT new be.rubus.microstream.performance.hibernate.domain.BookEntitySales(" +
                            " p.book, " +
                            " COUNT(p.amount) as bookAmount" +
                            ") " +
                            "        FROM  PurchaseItemEntity p " +
                            " WHERE EXTRACT(YEAR FROM p.purchase.timestamp) = :year " +
                            "          AND p.purchase.shop.address.city.state.country = :country " +
                            "        GROUP BY p.book ";

                    Query<BookEntitySales> query = session.createQuery(sql, BookEntitySales.class);
                    query.setParameter("year", year);
                    query.setParameter("country", country);

                    results.add(query.getResultList());
                }));
        long elapsed = stopWatch.stop();
        return new QueryInformation<>(results, elapsed);
    }


    public QueryInformation<List<BookEntity>> booksByTitle() {

        List<CountryEntity> countries = randomCountries(3);

        StopWatch stopWatch = StopWatch.StartNanoTime();

        List<List<BookEntity>> results = new ArrayList<>();
        Arrays.asList("the", "light", "black", "hero", "of").forEach(pattern ->
                countries.forEach(country -> {

                    Query<BookEntity> query = session.createQuery("SELECT b FROM BookEntity b JOIN FETCH b.author a JOIN FETCH a.address ad JOIN FETCH ad.city c JOIN FETCH c.state s JOIN FETCH s.country c WHERE LOWER(b.title) LIKE :title AND c.code = :countryCode", BookEntity.class);

                    query.setParameter("title", MatchMode.ANYWHERE.toMatchString(pattern));
                    query.setParameter("countryCode", country.getCode());

                    results.add(query.getResultList());
                })
        );

        long elapsed = stopWatch.stop();
        return new QueryInformation<>(results, elapsed);
    }


    public QueryInformation<EmployeeEntity> employeeOfTheYear() {

        List<CountryEntity> countries = randomCountries(3);
        Range<Integer> randomYears = randomYears(3);

        String sql = "select e.* from employee e where e.id=(" +
                "  select p.employee_id " +
                "  from purchaseitem i " +
                "  cross join purchase p " +
                "  left outer join shop s on p.shop_id=s.id " +
                "  left outer join address a on s.address_id=a.id " +
                "  left outer join city c on a.city_id=c.id " +
                "  left outer join \"state\" st on c.state_id=st.id " +
                "  where st.country_id=?1 " +
                "    and i.purchase_id=p.id " +
                "    and extract(YEAR FROM p.time_stamp)=?2 " +
                "  group by p.employee_id " +
                "  order by SUM(i.amount*i.price) DESC" +
                "  limit 1" +
                ")";

        StopWatch stopWatch = StopWatch.StartNanoTime();

        List<EmployeeEntity> results = new ArrayList<>();
        IntStream.rangeClosed(randomYears.getLowerBound(), randomYears.getUpperbound()).forEach(year ->
                countries.forEach(country ->
                        {
                            Query<EmployeeEntity> query = session.createNativeQuery(sql, EmployeeEntity.class);
                            query.setParameter(2, year);
                            query.setParameter(1, country.getId());

                            EmployeeEntity result = query.getSingleResult();
                            results.add(result);
                        }
                )
        );


        long elapsed = stopWatch.stop();
        return new QueryInformation<>(results, elapsed);
    }

    public QueryInformation<List<PurchaseEntity>> purchasesOfForeigners() {

        List<CountryEntity> countries = randomCountries(3);
        Range<Integer> randomYears = randomYears(3);

        String sql = "select p from PurchaseEntity p " +
                "  JOIN FETCH p.shop s JOIN FETCH s.address ad JOIN FETCH ad.city c JOIN FETCH c.state ss JOIN FETCH ss.country csc " +
                "  JOIN FETCH p.customer pc JOIN FETCH pc.address cad JOIN FETCH cad.city cc JOIN FETCH cc.state cs JOIN FETCH cs.country ccc " +
                "  JOIN FETCH p.items pi JOIN FETCH pi.book b  " +
                "  where p.customer.address.city <> p.shop.address.city " +
                "  and p.shop.address.city.state.country.id = :countryId " +
                "  and EXTRACT(YEAR FROM p.timestamp) = :year";

        List<List<PurchaseEntity>> results = new ArrayList<>();

        StopWatch stopWatch = StopWatch.StartNanoTime();

        IntStream.rangeClosed(randomYears.getLowerBound(), randomYears.getUpperbound()).forEach(year ->
                countries.forEach(country ->
                        {
                            Query<PurchaseEntity> query = session.createQuery(sql, PurchaseEntity.class);
                            query.setParameter("countryId", country.getId());
                            query.setParameter("year", year);

                            results.add(query.getResultList());

                        }
                )
        );

        long elapsed = stopWatch.stop();

        purchaseOfForeignersHotRun(randomYears, countries, sql);
        return new QueryInformation<>(results, elapsed);
    }

    private void purchaseOfForeignersHotRun(Range<Integer> randomYears, List<CountryEntity> countries, String sql) {
        List<List<PurchaseEntity>> results = new ArrayList<>();

        StopWatch stopWatch = StopWatch.StartNanoTime();

        IntStream.rangeClosed(randomYears.getLowerBound(), randomYears.getUpperbound()).forEach(year ->
                countries.forEach(country ->
                        {
                            Query<PurchaseEntity> query = session.createQuery(sql, PurchaseEntity.class);
                            query.setParameter("countryId", country.getId());
                            query.setParameter("year", year);

                            results.add(query.getResultList());

                        }
                )
        );
        DurationUtil.printDuration("Hot Run Query Execution", stopWatch.stop());

    }
    private List<CountryEntity> randomCountries(int cnt) {
        Query<CountryEntity> query = session.createQuery("SELECT C FROM CountryEntity C", CountryEntity.class);
        List<CountryEntity> countries = query.getResultList();
        Collections.shuffle(countries);

        // This method is just to get some countries but we should not influence the Session cache.
        countries.forEach(session::evict);

        return countries.subList(0, cnt);

    }

    private Range<Integer> randomYears(int yearSpan) {
        Query query = session.createQuery("SELECT min(C.timestamp), max(C.timestamp) FROM PurchaseEntity C");
        Object[] minMax = (Object[]) query.getSingleResult();
        int minYear = extractYear((LocalDateTime) minMax[0]);
        int maxYear = extractYear((LocalDateTime) minMax[1]);

        int startYear = minYear + RANDOM.nextInt(maxYear - minYear - yearSpan + 1);
        return new Range(startYear, startYear + yearSpan);

    }

    private int extractYear(LocalDateTime timestamp) {
        return timestamp.getYear();
    }


}
