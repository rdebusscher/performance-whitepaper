package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.exposed.getColumnValue
import be.rubus.microstream.performance.model.Country
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.ResultRow

object CountryTable : NamedWithCodeTable("COUNTRY")

fun mapToCountry(row: ResultRow, getAlias: ((String) -> Alias<*>?)? = null): Country {
    val alias = getAlias?.invoke(CountryTable.tableName)
    return Country(
        //  No named parameters since Country is a Java class. But should be clear enough
        getColumnValue(row, CountryTable.id, alias),
        getColumnValue(row, CountryTable.name, alias),
        getColumnValue(row, CountryTable.code, alias)
    )
}