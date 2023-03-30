package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.model.Author
import org.jetbrains.exposed.sql.ResultRow

object AuthorTable : NamedWithAddressTable("AUTHOR")

fun mapToAuthor(row: ResultRow) = Author(
    row[AuthorTable.id],
    row[AuthorTable.name],
    mapToAddress(row)  // TODO No support for address alias when using Author for the moment
)