package be.rubus.microstream.performance.microstream.tests;

import be.rubus.microstream.performance.QueryInformation;
import be.rubus.microstream.performance.StopWatch;
import be.rubus.microstream.performance.microstream.Service;
import be.rubus.microstream.performance.microstream.StorageManagerFactory;
import be.rubus.microstream.performance.microstream.database.Data;
import be.rubus.microstream.performance.microstream.database.model.Purchase;
import be.rubus.microstream.performance.utils.DurationUtil;
import one.microstream.storage.types.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

public class PurchaseOfForeigners {

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("one.microstream").setLevel(Level.OFF);

        Logger logger = LoggerFactory.getLogger(PurchaseOfForeigners.class);
        logger.info("Performance run MicroStream");

        int channels = Integer.highestOneBit(Runtime.getRuntime().availableProcessors() - 1);

        Data root = new Data();

        StopWatch stopWatch = StopWatch.StartNanoTime();

        try (StorageManager storageManager = StorageManagerFactory.create("bookstore", channels, root)) {

            DurationUtil.printDuration(logger, "Database loading", stopWatch.stop());

            Service service = new Service(root);

            logger.info("Execute Query - purchases of Foreigners");
            QueryInformation<List<Purchase>> listQueryInformation = service.purchasesOfForeigners();

            DurationUtil.printDuration(logger, "Query Execution", listQueryInformation.getElapsedNanoSeconds());

        }
    }
}
