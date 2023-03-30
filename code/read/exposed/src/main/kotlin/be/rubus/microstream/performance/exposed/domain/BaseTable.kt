package be.rubus.microstream.performance.exposed.domain

import org.jetbrains.exposed.sql.Table

open class BaseTable(name: String) : Table(name) {

    val id = long("id").uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}