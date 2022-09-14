package be.rubus.microstream.performance.microstream;

import be.rubus.microstream.performance.QueryInformation;
import be.rubus.microstream.performance.Range;
import be.rubus.microstream.performance.StopWatch;
import be.rubus.microstream.performance.microstream.database.Data;
import be.rubus.microstream.performance.microstream.database.model.Purchase;
import be.rubus.microstream.performance.model.*;
import be.rubus.microstream.performance.utils.DurationUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Service {

    private final static Random RANDOM = new Random();

    private final Data data;

    public Service(Data data) {
        this.data = data;
    }

    public QueryInformation<List<Customer>> allCustomersPaged() {
        StopWatch stopWatch = StopWatch.start();

        List<List<Customer>> results = new ArrayList<>();
        long pageSize = 100;
        // 3 sets of data, page 0, 1 and 2.
        IntStream.rangeClosed(0, 2).forEach(page ->
                results.add(data.customers().compute(customers ->
                        customers.skip(page * pageSize).limit(pageSize).collect(Collectors.toList())))
        );
        long elapsed = stopWatch.stop();
        return new QueryInformation<>(results, elapsed);
    }

    public QueryInformation<List<Purchase>> purchasesOfForeigners() {
        Range<Integer> randomYears = randomYears(3);
        List<Country> countries = randomCountries(3);

        StopWatch stopWatch = StopWatch.start();

        List<List<Purchase>> results = new ArrayList<>();

        IntStream.rangeClosed(randomYears.getLowerBound(), randomYears.getUpperbound()).forEach(year ->
                countries.forEach(country ->
                        results.add(data.purchases().purchasesOfForeigners(year, country))
                )
        );

        long elapsed = stopWatch.stop();

        purchaseOfForeignersHotRun(randomYears, countries);

        return new QueryInformation<>(results, elapsed);
    }

    private void purchaseOfForeignersHotRun(Range<Integer> randomYears, List<Country> countries) {
        StopWatch stopWatch = StopWatch.start();

        List<List<Purchase>> results = new ArrayList<>();

        IntStream.rangeClosed(randomYears.getLowerBound(), randomYears.getUpperbound()).forEach(year ->
                countries.forEach(country ->
                        results.add(data.purchases().purchasesOfForeigners(year, country))
                )
        );

        DurationUtil.printDuration("Hot Run Query Execution", stopWatch.stop());

    }


    private Range<Integer> randomYears(int yearSpan) {
        Range<Integer> years = data.purchases().years();
        int minYear = years.getLowerBound();
        int maxYear = years.getUpperbound();
        int startYear = minYear + RANDOM.nextInt(maxYear - minYear - yearSpan + 1);
        return new Range(startYear, startYear + yearSpan);
    }

    private List<Country> randomCountries(int cnt) {
        List<Country> countries = data.shops().compute(shops ->
                shops.map(s -> s.address().city().state().country())
                        .distinct()
                        .collect(Collectors.toList())
        );
        Collections.shuffle(countries);
        return countries.subList(0, cnt);
    }
}
