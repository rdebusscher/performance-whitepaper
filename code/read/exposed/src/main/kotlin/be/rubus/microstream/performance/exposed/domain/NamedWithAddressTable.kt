package be.rubus.microstream.performance.exposed.domain

open class NamedWithAddressTable(name: String) : NamedTable(name) {
    val addressId = long("address_id").references(AddressTable.id)
}