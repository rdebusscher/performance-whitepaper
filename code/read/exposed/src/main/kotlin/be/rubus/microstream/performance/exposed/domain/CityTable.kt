package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.exposed.getColumnValue
import be.rubus.microstream.performance.model.City
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.ResultRow

object CityTable : NamedTable("CITY") {

    val stateId = long("state_id").references(StateTable.id)
}

fun mapToCity(row: ResultRow, getAlias: ((String) -> Alias<*>?)? = null): City {
    val alias = getAlias?.invoke(CityTable.tableName)
    return City(
        //  No named parameters since City is a Java class. But should be clear enough
        getColumnValue(row, CityTable.id, alias),
        getColumnValue(row, CityTable.name, alias),
        mapToState(row, getAlias)
    )
}