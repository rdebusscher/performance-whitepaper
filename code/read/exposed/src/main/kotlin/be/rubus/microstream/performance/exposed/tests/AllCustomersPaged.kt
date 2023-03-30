package be.rubus.microstream.performance.exposed.tests

import be.rubus.microstream.performance.StopWatch
import be.rubus.microstream.performance.exposed.DBSettings
import be.rubus.microstream.performance.exposed.Service
import be.rubus.microstream.performance.exposed.readonlyTransaction
import be.rubus.microstream.performance.utils.DurationUtil
import org.slf4j.LoggerFactory

fun main() {
    val logger = LoggerFactory.getLogger("AllCustomersPaged")
    logger.info("Performance run Exposed")

    val stopWatch: StopWatch = StopWatch.start()
    DBSettings.db
    DurationUtil.printDuration(logger, "Database Access", stopWatch.stop())

    logger.info("Execute Query - all customers Paged")
    val listQueryInformation = readonlyTransaction(DBSettings.db) {
        Service.allCustomersPaged()
    }

    DurationUtil.printDuration(logger, "Query Execution", listQueryInformation.elapsedNanoSeconds)


}

