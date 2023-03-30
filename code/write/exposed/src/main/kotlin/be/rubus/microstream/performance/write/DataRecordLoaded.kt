package be.rubus.microstream.performance.write

import be.rubus.microstream.performance.tripdata.TripDetailsData
import org.jetbrains.exposed.sql.batchInsert
import java.util.function.Consumer

class DataRecordLoaded : Consumer<TripDetailsData> {

    private var idx: Long = 0

    private val cache: MutableList<TripDetailsData> = mutableListOf()

    override fun accept(tripDetailsData: TripDetailsData) {
        cache.add(tripDetailsData)
        idx++
        if (idx % 1000 == 0L) {
            TripDetailsTable.batchInsert(cache, shouldReturnGeneratedValues = false) {
                this[TripDetailsTable.tripId] = it.tripId
                this[TripDetailsTable.congestionSurcharge] = it.congestionSurcharge
                this[TripDetailsTable.distance] = it.distance
                this[TripDetailsTable.dropoffDateTime] = it.dropoffDateTime
                this[TripDetailsTable.dropoffLocationId] = it.dropoffLocationId
                this[TripDetailsTable.extraAmount] = it.extraAmount
                this[TripDetailsTable.fareAmount] = it.fareAmount
                this[TripDetailsTable.improvementSurcharge] = it.improvementSurcharge
                this[TripDetailsTable.mtaTax] = it.mtaTax
                this[TripDetailsTable.passengerCount] = it.passengerCount
                this[TripDetailsTable.paymentType] = it.paymentType
                this[TripDetailsTable.pickupDateTime] = it.pickupDateTime
                this[TripDetailsTable.pickupLocationId] = it.pickupLocationId
                this[TripDetailsTable.rateCodeId] = it.rateCodeId
                //this[TripDetailsTable.storeAndFwdFlag] = it.storeAndFwdFlag
                this[TripDetailsTable.tipAmount] = it.tipAmount
                this[TripDetailsTable.tollAmount] = it.tollAmount
                this[TripDetailsTable.totalAmount] = it.totalAmount
                this[TripDetailsTable.vendorId] = it.vendorId

            }


            cache.clear()
        }

    }
}