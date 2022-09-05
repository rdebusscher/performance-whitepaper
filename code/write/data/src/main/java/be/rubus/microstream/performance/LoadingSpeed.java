package be.rubus.microstream.performance;

import be.rubus.microstream.performance.tripdata.StopWatch;
import be.rubus.microstream.performance.tripdata.TripDataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class LoadingSpeed {

    public static void main(String[] args) {
        // Comment the following line of you want to see some progress (but it is slower)
        TripDataLoader.linesLoadedFeedback = false;

        // The consumer does nothing to get an idea about the actual loading speed.
        TripDataLoader loader = new TripDataLoader(tripDetails -> {
        });

        StopWatch stopWatch = StopWatch.StartNanoTime();
        loader.loadTripDetails();

        Duration loadingTime = Duration.ofNanos(stopWatch.stop());

        Logger logger = LoggerFactory.getLogger(LoadingSpeed.class);
        // Default format
        logger.info("Data Loading took {} Second {} Millisecond {} Nanosecond", loadingTime.toSecondsPart(), loadingTime.toMillisPart(), loadingTime.toNanosPart() % 1_000_000L);

    }
}
