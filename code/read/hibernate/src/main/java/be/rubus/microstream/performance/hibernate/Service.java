package be.rubus.microstream.performance.hibernate;

import be.rubus.microstream.performance.QueryInformation;
import be.rubus.microstream.performance.Range;
import be.rubus.microstream.performance.StopWatch;
import be.rubus.microstream.performance.hibernate.domain.*;
import be.rubus.microstream.performance.utils.DurationUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

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

        String sql = "SELECT C from CustomerEntity C JOIN FETCH C.address a JOIN FETCH a.city ci JOIN FETCH ci.state st JOIN FETCH st.country";
        List<List<CustomerEntity>> results = new ArrayList<>();
        int pageSize = 100;

        StopWatch stopWatch = StopWatch.start();

        IntStream.rangeClosed(0, 2).forEach(page ->
                {
                    Query<CustomerEntity> query = session.createQuery(sql, CustomerEntity.class);
                    query.setFirstResult(page * pageSize);
                    query.setMaxResults(pageSize);

                    results.add(query.getResultList());
                }
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

        StopWatch stopWatch = StopWatch.start();

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

        StopWatch stopWatch = StopWatch.start();

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

        // This method is just to get some countries, but we should not influence the Session cache.
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
