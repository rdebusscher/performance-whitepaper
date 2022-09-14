package be.rubus.microstream.performance.jdbc;

import be.rubus.microstream.performance.QueryInformation;
import be.rubus.microstream.performance.Range;
import be.rubus.microstream.performance.StopWatch;
import be.rubus.microstream.performance.jdbc.model.Purchase;
import be.rubus.microstream.performance.jdbc.query.CountryQuery;
import be.rubus.microstream.performance.jdbc.query.CustomerQuery;
import be.rubus.microstream.performance.jdbc.query.PurchaseQuery;
import be.rubus.microstream.performance.jdbc.query.PurchaseTimeRangeQuery;
import be.rubus.microstream.performance.model.Country;
import be.rubus.microstream.performance.model.Customer;
import be.rubus.microstream.performance.utils.DurationUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Service {

    private final static Random RANDOM = new Random();

    private final Connection connection;

    public Service(Connection connection) {
        this.connection = connection;
    }

    public QueryInformation<List<Customer>> allCustomersPaged() {
        StopWatch stopWatch = StopWatch.StartNanoTime();

        List<List<Customer>> results = new ArrayList<>();

        // 3 sets of data, page 0, 1 and 2.
        IntStream.rangeClosed(0, 2).forEach(page ->
                results.add(new CustomerQuery().performQuery(connection, page))
        );
        long elapsed = stopWatch.stop();
        return new QueryInformation<>(results, elapsed);
    }

    public QueryInformation<List<Purchase>> purchaseOfForeigners() {
        Range<Integer> randomYears = randomYears(3);
        List<Country> countries = randomCountries(3);

        List<List<Purchase>> results = new ArrayList<>();

        StopWatch stopWatch = StopWatch.StartNanoTime();

        IntStream.rangeClosed(randomYears.getLowerBound(), randomYears.getUpperbound()).forEach(year ->
                countries.forEach(country ->
                        results.add(new PurchaseQuery().performQuery(connection, country, year))
                )
        );

        long elapsed = stopWatch.stop();
        purchaseOfForeignersHotRun(randomYears, countries);
        return new QueryInformation<>(results, elapsed);

    }

    private void purchaseOfForeignersHotRun(Range<Integer> randomYears, List<Country> countries) {
        List<List<Purchase>> results = new ArrayList<>();
        StopWatch stopWatch = StopWatch.StartNanoTime();

        IntStream.rangeClosed(randomYears.getLowerBound(), randomYears.getUpperbound()).forEach(year ->
                countries.forEach(country ->
                        results.add(new PurchaseQuery().performQuery(connection, country, year))
                )
        );
        DurationUtil.printDuration("Hot Run Query Execution", stopWatch.stop());

    }

    private List<Country> randomCountries(int cnt) {
        List<Country> countries = new CountryQuery().performQuery(connection);
        Collections.shuffle(countries);
        return countries.subList(0, cnt);
    }

    private Range<Integer> randomYears(int yearSpan) {
        Range<Integer> years = new PurchaseTimeRangeQuery().performQuery(connection);

        int minYear = years.getLowerBound();
        int maxYear = years.getUpperbound();
        int startYear = minYear + RANDOM.nextInt(maxYear - minYear - yearSpan + 1);
        return new Range(startYear, startYear + yearSpan);

    }

}
