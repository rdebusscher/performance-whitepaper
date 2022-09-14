package be.rubus.microstream.performance.hibernate;

import be.rubus.microstream.performance.hibernate.util.HibernateUtil;
import be.rubus.microstream.performance.tripdata.StopWatch;
import be.rubus.microstream.performance.tripdata.TripDataLoader;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PerformanceLoad {

    public static void main(String[] args) {
        // Comment the following line of you want to see some progress (but it is slower)
        TripDataLoader.linesLoadedFeedback = false;

        Logger logger = LoggerFactory.getLogger(PerformanceLoad.class);

        StopWatch stopWatch = StopWatch.start();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            DataRecordLoaded loaded = new DataRecordLoaded(session);

            TripDataLoader loader = new TripDataLoader(loaded);
            loader.loadTripDetails();

            transaction.commit();

        }
        HibernateUtil.shutdown();

        Duration loadingTime = Duration.ofNanos(stopWatch.stop());

        logger.info("Data Loading AND storing took {} minutes {} Second {} Millisecond {} Nanosecond",
                loadingTime.toMinutesPart(),
                loadingTime.toSecondsPart(),
                loadingTime.toMillisPart(),
                loadingTime.toNanosPart() % 1_000_000L);

    }
}
