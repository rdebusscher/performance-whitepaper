package be.rubus.microstream.performance.jdbc.tests;

import be.rubus.microstream.performance.QueryInformation;
import be.rubus.microstream.performance.StopWatch;
import be.rubus.microstream.performance.jdbc.Service;
import be.rubus.microstream.performance.jdbc.model.Purchase;
import be.rubus.microstream.performance.utils.DurationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class PurchaseOfForeigners {

    public static void main(String[] args) throws ClassNotFoundException {
        Logger logger = LoggerFactory.getLogger(PurchaseOfForeigners.class);

        logger.info("Performance run JDBC");
        StopWatch stopWatch = StopWatch.start();

        Class.forName("org.postgresql.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "mysecretpassword")) {

            DurationUtil.printDuration(logger, "Database Access", stopWatch.stop());

            Service service = new Service(connection);

            logger.info("Execute Query - PurchaseOfForeigners");
            QueryInformation<List<Purchase>> listQueryInformation = service.purchaseOfForeigners();

            DurationUtil.printDuration(logger, "Query Execution", listQueryInformation.getElapsedNanoSeconds());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
