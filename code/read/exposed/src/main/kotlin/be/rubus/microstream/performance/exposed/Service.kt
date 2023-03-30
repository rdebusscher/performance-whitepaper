package be.rubus.microstream.performance.exposed

import be.rubus.microstream.performance.QueryInformation
import be.rubus.microstream.performance.Range
import be.rubus.microstream.performance.StopWatch
import be.rubus.microstream.performance.exposed.model.Purchase
import be.rubus.microstream.performance.exposed.query.CountryQuery
import be.rubus.microstream.performance.exposed.query.CustomerQuery
import be.rubus.microstream.performance.exposed.query.PurchaseQuery
import be.rubus.microstream.performance.exposed.query.PurchaseTimeRangeQuery
import be.rubus.microstream.performance.model.Country
import be.rubus.microstream.performance.model.Customer
import be.rubus.microstream.performance.utils.DurationUtil
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transactionManager
import kotlin.random.Random

object Service {
    fun allCustomersPaged(): QueryInformation<List<Customer>> {
        val stopWatch = StopWatch.start()
        val results: MutableList<List<Customer>> = mutableListOf()

        // 3 sets of data, page 0, 1 and 2.
        (0..2).forEach { page ->
            results.add(CustomerQuery().performQuery(page))
        }


        val elapsed = stopWatch.stop()
        return QueryInformation<List<Customer>>(results, elapsed)
    }

    fun purchaseOfForeigners(): QueryInformation<List<Purchase>> {
        val randomYears = randomYears(3)
        val countries = randomCountries(3)
        val results: MutableList<List<Purchase>> = mutableListOf()
        val stopWatch = StopWatch.start()

        for (year in randomYears.lowerBound..randomYears.upperbound) {
            countries.forEach { country: Country ->
                results.add(
                    PurchaseQuery().performQuery(
                        country, year
                    )
                )
            }

        }
        val elapsed = stopWatch.stop()
        purchaseOfForeignersHotRun(randomYears, countries)

        return QueryInformation<List<Purchase>>(results, elapsed)

    }


    private fun purchaseOfForeignersHotRun(randomYears: Range<Int>, countries: List<Country>) {
        val results: MutableList<List<Purchase>> = mutableListOf()
        val stopWatch = StopWatch.start()
        for (year in randomYears.lowerBound..randomYears.upperbound) {
            countries.forEach { country: Country ->
                results.add(
                    PurchaseQuery().performQuery(
                        country, year
                    )
                )

            }


        }
        DurationUtil.printDuration("Hot Run Query Execution", stopWatch.stop())
    }


    private fun randomCountries(cnt: Int): List<Country> {
        val countries: List<Country> = CountryQuery().performQuery()
        return countries.shuffled().take(cnt)
    }

    private fun randomYears(yearSpan: Int): Range<Int> {
        val years: Range<Int> = PurchaseTimeRangeQuery().performQuery()
        val minYear = years.lowerBound
        val maxYear = years.upperbound

        val startYear =
            minYear + Random.nextInt(maxYear - minYear - yearSpan + 1)
        return Range(startYear, startYear + yearSpan)

    }

}


fun <T> readonlyTransaction(db: Database? = null, statement: Transaction.() -> T): T =
    org.jetbrains.exposed.sql.transactions.transaction(
        db.transactionManager.defaultIsolationLevel,
        db.transactionManager.defaultRepetitionAttempts,
        true,
        db,
        statement
    )

fun <T : Any> getColumnValue(row: ResultRow, column: Column<*>, alias: Alias<*>?): T? {
    val tableColumn = alias?.get(column) ?: column
    return row[tableColumn] as T?
}