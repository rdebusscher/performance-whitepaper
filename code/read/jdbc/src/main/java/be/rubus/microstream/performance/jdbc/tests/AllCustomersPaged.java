package be.rubus.microstream.performance.jdbc.tests;

import be.rubus.microstream.performance.QueryInformation;
import be.rubus.microstream.performance.StopWatch;
import be.rubus.microstream.performance.jdbc.Service;
import be.rubus.microstream.performance.model.Customer;
import be.rubus.microstream.performance.utils.DurationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class AllCustomersPaged {

    public static void main(String[] args) throws ClassNotFoundException {
        Logger logger = LoggerFactory.getLogger(AllCustomersPaged.class);

        logger.info("Performance run JDBC");
        StopWatch stopWatch = StopWatch.StartNanoTime();

        Class.forName("org.postgresql.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "mysecretpassword")) {

            DurationUtil.printDuration(logger, "Database Access", stopWatch.stop());

            Service service = new Service(connection);

            logger.info("Execute Query - all customers Paged");
            QueryInformation<List<Customer>> listQueryInformation = service.allCustomersPaged();

            DurationUtil.printDuration(logger, "Query Execution", listQueryInformation.getElapsedNanoSeconds());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
