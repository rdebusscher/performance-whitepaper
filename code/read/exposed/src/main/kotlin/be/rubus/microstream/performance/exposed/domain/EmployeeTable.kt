package be.rubus.microstream.performance.exposed.domain

object EmployeeTable: NamedWithAddressTable("EMPLOYEE") {
    val shopId = long("shop_id").references(ShopTable.id)
}