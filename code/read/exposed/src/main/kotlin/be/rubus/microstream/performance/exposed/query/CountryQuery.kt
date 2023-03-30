package be.rubus.microstream.performance.exposed.query

import be.rubus.microstream.performance.exposed.domain.CountryTable
import be.rubus.microstream.performance.exposed.domain.mapToCountry
import be.rubus.microstream.performance.model.Country
import org.jetbrains.exposed.sql.selectAll

class CountryQuery {
    fun performQuery(): List<Country> {
        return CountryTable
            .selectAll()
            .map { mapToCountry(it) }

    }
}