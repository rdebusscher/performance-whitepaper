package be.rubus.microstream.performance.write

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object TripDetailsTable : Table("trip_details") {
    val tripId = varchar("tripId", 255)
    val congestionSurcharge = float("congestionSurcharge")
    val distance = float("distance")
    val dropoffDateTime = datetime("dropoffDateTime").nullable()
    val dropoffLocationId = varchar("dropoffLocationId", 255).nullable()
    val extraAmount = float("extraAmount")
    val fareAmount = float("fareAmount")
    val improvementSurcharge = float("improvementSurcharge")
    val mtaTax = float("mtaTax")
    val passengerCount = byte("passengerCount")
    val paymentType = byte("paymentType")
    val pickupDateTime = datetime("pickupDateTime").nullable()
    val pickupLocationId = varchar("pickupLocationId", 255).nullable()
    val rateCodeId = byte("rateCodeId")
    //val storeAndFwdFlag = char("storeAndFwdFlag", 1)
    val tipAmount = float("tipAmount")
    val tollAmount = float("tollAmount")
    val totalAmount = float("totalAmount")
    val vendorId = varchar("vendorId", 255).nullable()

    override val primaryKey = PrimaryKey(tripId, name = "PK_Cities_ID")
}