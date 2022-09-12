package be.rubus.microstream.performance.generator;

import be.rubus.microstream.performance.generator.config.DataSize;
import be.rubus.microstream.performance.generator.data.DataMetrics;
import be.rubus.microstream.performance.generator.data.RandomDataGenerator;
import be.rubus.microstream.performance.microstream.StorageManagerFactory;
import be.rubus.microstream.performance.microstream.database.Data;
import one.microstream.storage.types.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GenerateData {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(GenerateData.class);

        int channels = 1; // basic
        // When you want to get the maximum out of your machine
        channels = Integer.highestOneBit(Runtime.getRuntime().availableProcessors() - 1);

        Data root = new Data();

        try (StorageManager storageManager = StorageManagerFactory.create("bookstore", channels, root)) {

            DataMetrics metrics = new RandomDataGenerator(
                    root.books(),
                    root.shops(),
                    root.customers(),
                    root.purchases(),
                    DataSize.MEDIUM,  // Change if you need a larger set
                    storageManager)
                    .generate();

            storageManager.setRoot(root);
            storageManager.storeRoot();


            logger.info("Random data generated: " + metrics.toString());
        }

    }
}
