package be.rubus.microstream.performance.jooq;

import be.rubus.microstream.performance.tripdata.StopWatch;
import be.rubus.microstream.performance.tripdata.TripDataLoader;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;

public class PerformanceLoad {

    public static void main(String[] args) throws ClassNotFoundException {
        // Comment the following line of you want to see some progress (but it is slower)
        TripDataLoader.linesLoadedFeedback = false;

        Logger logger = LoggerFactory.getLogger(PerformanceLoad.class);

        StopWatch stopWatch = StopWatch.StartNanoTime();

        Class.forName("org.postgresql.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "mysecretpassword")) {

            DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            connection.setAutoCommit(false);

            DataRecordLoaded loaded = new DataRecordLoaded(context);

            TripDataLoader loader = new TripDataLoader(loaded);
            loader.loadTripDetails();

            // send the last statements to database
            loaded.sendBatch();

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Duration loadingTime = Duration.ofNanos(stopWatch.stop());

        // Default format
        logger.info("Data Loading AND storing took {} minutes {} Second {} Millisecond {} Nanosecond", loadingTime.toMinutesPart(), loadingTime.toSecondsPart(), loadingTime.toMillisPart(), loadingTime.toNanosPart() % 1_000_000L);

    }
}
