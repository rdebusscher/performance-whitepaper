package be.rubus.microstream.performance.hibernate.tests;

import be.rubus.microstream.performance.QueryInformation;
import be.rubus.microstream.performance.StopWatch;
import be.rubus.microstream.performance.hibernate.Service;
import be.rubus.microstream.performance.hibernate.domain.PurchaseEntity;
import be.rubus.microstream.performance.hibernate.util.HibernateUtil;
import be.rubus.microstream.performance.utils.DurationUtil;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PurchaseOfForeigners {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(PurchaseOfForeigners.class);
        logger.info("Performance run Hibernate");

        StopWatch stopWatch = StopWatch.start();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            DurationUtil.printDuration(logger, "Database Access", stopWatch.stop());

            Service service = new Service(session);

            logger.info("Execute Query - purchases of Foreigners");
            QueryInformation<List<PurchaseEntity>> listQueryInformation = service.purchasesOfForeigners();

            DurationUtil.printDuration(logger, "Query Execution", listQueryInformation.getElapsedNanoSeconds());

        }

    }
}
