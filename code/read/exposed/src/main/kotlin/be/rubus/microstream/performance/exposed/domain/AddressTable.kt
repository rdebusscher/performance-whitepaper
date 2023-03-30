package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.exposed.getColumnValue
import be.rubus.microstream.performance.model.Address
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.ResultRow

object AddressTable : BaseTable("ADDRESS") {
    val address = varchar("address", 255)
    val address2 = varchar("address2", 255)
    val zipCode = varchar("zipcode", 255)
    val cityId = long("city_id").references(CityTable.id)

}


fun mapToAddress(row: ResultRow, getAlias: ((String?) -> Alias<*>?)? = null): Address {
    val alias = getAlias?.invoke(AddressTable.tableName)
    return Address(
        getColumnValue(row, AddressTable.id, alias),
        getColumnValue(row, AddressTable.address, alias),
        getColumnValue(row, AddressTable.address2, alias),
        getColumnValue(row, AddressTable.zipCode, alias),
        mapToCity(row, getAlias)
    )
}
