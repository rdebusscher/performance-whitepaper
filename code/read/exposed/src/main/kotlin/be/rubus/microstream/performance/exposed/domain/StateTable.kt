package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.exposed.getColumnValue
import be.rubus.microstream.performance.model.State
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.ResultRow

object StateTable : NamedTable("STATE") {

    val countryId = long("country_id").references(CountryTable.id)

}

fun mapToState(row: ResultRow, getAlias: ((String) -> Alias<*>?)? = null): State {
    val alias = getAlias?.invoke(StateTable.tableName)
    return State(
        //  No named parameters since State is a Java class. But should be clear enough
        getColumnValue(row, StateTable.id, alias),
        getColumnValue(row, StateTable.name, alias),
        mapToCountry(row, getAlias)
    )
}