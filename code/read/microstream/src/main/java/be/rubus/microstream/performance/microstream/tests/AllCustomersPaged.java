package be.rubus.microstream.performance.microstream.tests;

import be.rubus.microstream.performance.QueryInformation;
import be.rubus.microstream.performance.StopWatch;
import be.rubus.microstream.performance.microstream.Service;
import be.rubus.microstream.performance.microstream.StorageManagerFactory;
import be.rubus.microstream.performance.microstream.database.Data;
import be.rubus.microstream.performance.model.Customer;
import be.rubus.microstream.performance.utils.ChannelUtil;
import be.rubus.microstream.performance.utils.DurationUtil;
import one.microstream.storage.types.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.logging.Level;

public class AllCustomersPaged {

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("one.microstream").setLevel(Level.OFF);

        Logger logger = LoggerFactory.getLogger(AllCustomersPaged.class);
        logger.info("Performance run MicroStream");

        Data root = new Data();

        StopWatch stopWatch = StopWatch.start();

        try (StorageManager storageManager =
                     StorageManagerFactory.create("bookstore", ChannelUtil.channelCount(), root)) {

            DurationUtil.printDuration(logger, "Database loading", stopWatch.stop());

            Service service = new Service(root);

            logger.info("Execute Query - all customers Paged");
            QueryInformation<List<Customer>> listQueryInformation = service.allCustomersPaged();

            DurationUtil.printDuration(logger, "Query Execution", listQueryInformation.getElapsedNanoSeconds());

        }
    }
}
