package be.rubus.microstream.performance.hibernate.tests;

import be.rubus.microstream.performance.QueryInformation;
import be.rubus.microstream.performance.StopWatch;
import be.rubus.microstream.performance.hibernate.Service;
import be.rubus.microstream.performance.hibernate.domain.CustomerEntity;
import be.rubus.microstream.performance.hibernate.util.HibernateUtil;
import be.rubus.microstream.performance.utils.DurationUtil;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class AllCustomersPaged {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(AllCustomersPaged.class);
        logger.info("Performance run Hibernate");

        StopWatch stopWatch = StopWatch.StartNanoTime();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            DurationUtil.printDuration(logger, "Database Access", stopWatch.stop());

            Service service = new Service(session);

            logger.info("Execute Query - all customers Paged");
            QueryInformation<List<CustomerEntity>> listQueryInformation = service.allCustomersPaged();

            DurationUtil.printDuration(logger, "Query Execution", listQueryInformation.getElapsedNanoSeconds());

        }

    }
}
