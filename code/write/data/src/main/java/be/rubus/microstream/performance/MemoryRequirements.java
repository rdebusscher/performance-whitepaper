package be.rubus.microstream.performance;

import be.rubus.microstream.performance.tripdata.StopWatch;
import be.rubus.microstream.performance.tripdata.TripDataLoader;
import be.rubus.microstream.performance.tripdata.TripDetailsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class MemoryRequirements {

    private static final Map<String, TripDetailsData> data = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // Comment the following line of you want to see some progress (but it is slower)

        TripDataLoader loader = new TripDataLoader(tripDetails -> {
            data.put(tripDetails.getTripId(), tripDetails);
        });

        StopWatch stopWatch = StopWatch.StartNanoTime();
        loader.loadTripDetails();

        Duration loadingTime = Duration.ofNanos(stopWatch.stop());

        Logger logger = LoggerFactory.getLogger(MemoryRequirements.class);
        // Default format
        logger.info("Data Loading took {} Second {} Millisecond {} Nanosecond", loadingTime.toSecondsPart(), loadingTime.toMillisPart(), loadingTime.toNanosPart() % 1_000_000L);

        HeapDumpUtil.dump("memreq.hprof", true);

        System.out.println("Number of records " + data.keySet().size());

    }

}
