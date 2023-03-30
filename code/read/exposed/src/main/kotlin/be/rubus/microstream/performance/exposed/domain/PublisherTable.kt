package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.model.Publisher
import org.jetbrains.exposed.sql.ResultRow

object PublisherTable: NamedWithAddressTable("PUBLISHER")

fun mapToPublisher(row: ResultRow) = Publisher(
    row[PublisherTable.id],
    row[PublisherTable.name],
    mapToAddress(row)  // TODO No support for address alias when using Publisher for the moment

)