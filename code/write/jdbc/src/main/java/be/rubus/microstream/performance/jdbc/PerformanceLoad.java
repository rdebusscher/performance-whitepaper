package be.rubus.microstream.performance.jdbc;

import be.rubus.microstream.performance.tripdata.StopWatch;
import be.rubus.microstream.performance.tripdata.TripDataLoader;
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

        Class.forName("org.postgresql.Driver");

        StopWatch stopWatch = StopWatch.StartNanoTime();

        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "mysecretpassword")) {
            connection.setAutoCommit(false);

            DataRecordLoaded loaded = new DataRecordLoaded(connection);

            TripDataLoader loader = new TripDataLoader(loaded);
            loader.loadTripDetails();

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Duration loadingTime = Duration.ofNanos(stopWatch.stop());

        // Default format
        logger.info("Data Loading AND storing took {} minutes {} Second {} Millisecond {} Nanosecond", loadingTime.toMinutesPart(), loadingTime.toSecondsPart(), loadingTime.toMillisPart(), loadingTime.toNanosPart() % 1_000_000L);

    }
}
