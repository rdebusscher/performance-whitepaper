package be.rubus.microstream.performance.exposed.domain

open class NamedWithCodeTable(name: String ) : NamedTable(name) {

    val code = varchar("code", 255)
}