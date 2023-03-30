package be.rubus.microstream.performance.exposed.domain

open class NamedTable(name: String) : BaseTable(name) {
    val name = varchar("name", 255)
}