package be.rubus.microstream.performance.write

import be.rubus.microstream.performance.LoadingSpeed
import be.rubus.microstream.performance.tripdata.StopWatch
import be.rubus.microstream.performance.tripdata.TripDataLoader
import be.rubus.microstream.performance.tripdata.TripDetailsData
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.time.Duration

fun main() {
    val logger = LoggerFactory.getLogger(LoadingSpeed::class.java)

    // Comment the following line of you want to see some progress (but it is slower)
    TripDataLoader.linesLoadedFeedback = false

    val dataRecordLoaded = DataRecordLoaded()

    val loader = TripDataLoader(dataRecordLoaded)
    logger.info("Start")
    val stopWatch = StopWatch.start()

    DBSettings.db  // Connect to DB
    logger.info("start Transaction")
    transaction {
        logger.info("start Loading ")
        //SchemaUtils.create(TripDetailsTable)
        loader.loadTripDetails()  // Load data
    }
    logger.info("end Loading ")

    val loadingTime = Duration.ofNanos(stopWatch.stop())

    logger.info(
        "Data Loading took {} Second {} Millisecond {} Nanosecond",
        loadingTime.toSecondsPart(),
        loadingTime.toMillisPart(),
        loadingTime.toNanosPart() % 1000000L
    )

}