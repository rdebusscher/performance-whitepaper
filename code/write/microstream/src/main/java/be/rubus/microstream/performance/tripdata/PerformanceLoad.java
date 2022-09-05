package be.rubus.microstream.performance.tripdata;

import one.microstream.storage.types.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PerformanceLoad {

    public static void main(String[] args) {
        // Comment the following line of you want to see some progress (but it is slower)
        TripDataLoader.linesLoadedFeedback = false;

        Logger logger = LoggerFactory.getLogger(PerformanceLoad.class);

        StopWatch stopWatch = StopWatch.StartNanoTime();

        DataRoot root = new DataRoot();

        int channels = 1; // basic
        // When you want to get the maximum out of your machine
        channels = Integer.highestOneBit(Runtime.getRuntime().availableProcessors() - 1);

        StorageManager storageManager = StorageManagerFactory.create(System.getProperty("user.home")+"/microstream-performance/bydays", channels, root);

        DataRecordLoaded loaded = new DataRecordLoaded(root);

        TripDataLoader loader = new TripDataLoader(loaded);
        loader.loadTripDetails();

        for (int day = 1; day <= 31; day++) {
            if (TripDataLoader.linesLoadedFeedback) {
                logger.info("Storing data for day {}", day);
            }
            storageManager.store(root.getTripDetails(day));
            root.clearReferenceFor(day);
        }

        Duration loadingTime = Duration.ofNanos(stopWatch.stop());

        // Default format
        logger.info("Data Loading AND storing took {} minutes {} Second {} Millisecond {} Nanosecond",
                loadingTime.toMinutesPart(),
                loadingTime.toSecondsPart(),
                loadingTime.toMillisPart(),
                loadingTime.toNanosPart() % 1_000_000L);

        storageManager.shutdown();
    }
}
