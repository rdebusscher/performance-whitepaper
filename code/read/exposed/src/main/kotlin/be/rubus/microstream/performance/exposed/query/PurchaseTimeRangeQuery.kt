package be.rubus.microstream.performance.exposed.query

import be.rubus.microstream.performance.Range
import be.rubus.microstream.performance.exposed.domain.PurchaseTable
import org.jetbrains.exposed.sql.max
import org.jetbrains.exposed.sql.min
import org.jetbrains.exposed.sql.selectAll
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class PurchaseTimeRangeQuery {
    fun performQuery(): Range<Int> {
        val (minTimestamp, maxTimestamp) = PurchaseTable
            .slice(PurchaseTable.timeStamp.min(), PurchaseTable.timeStamp.max())
            .selectAll()
            .map { row ->
                val minTimestamp = row[PurchaseTable.timeStamp.min()]
                val maxTimestamp = row[PurchaseTable.timeStamp.max()]
                Pair(minTimestamp, maxTimestamp)
            }
            .first()

        return Range(getYear(minTimestamp), getYear(maxTimestamp))
    }

    private fun getYear(minTimestamp: Instant?) = LocalDateTime.ofInstant(minTimestamp, ZoneId.systemDefault()).year
}