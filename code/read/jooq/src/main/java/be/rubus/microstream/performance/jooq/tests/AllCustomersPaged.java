package be.rubus.microstream.performance.jooq.tests;

import be.rubus.microstream.performance.QueryInformation;
import be.rubus.microstream.performance.StopWatch;
import be.rubus.microstream.performance.jooq.Service;
import be.rubus.microstream.performance.model.Customer;
import be.rubus.microstream.performance.utils.DurationUtil;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

public class AllCustomersPaged {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Logger logger = LoggerFactory.getLogger(AllCustomersPaged.class);

        logger.info("Performance run JOOQ");
        StopWatch stopWatch = StopWatch.StartNanoTime();

        Class.forName("org.postgresql.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "mysecretpassword")) {

            DSLContext context = DSL.using(connection, SQLDialect.POSTGRES);

            DurationUtil.printDuration(logger, "Database Access", stopWatch.stop());

            Service service = new Service(context);

            logger.info("Execute Query - all customers Paged");
            QueryInformation<List<Customer>> listQueryInformation = service.allCustomersPaged();

            DurationUtil.printDuration(logger, "Query Execution", listQueryInformation.getElapsedNanoSeconds());
            DurationUtil.printDuration(logger, "Mapping (part of total execution)", listQueryInformation.getMappingNanoSeconds());

        }
    }
}
