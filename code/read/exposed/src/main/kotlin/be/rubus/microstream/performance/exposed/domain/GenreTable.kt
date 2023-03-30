package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.model.Genre
import org.jetbrains.exposed.sql.ResultRow

object GenreTable : NamedTable("GENRE")

fun mapToGenre(row: ResultRow) = Genre(
    row[GenreTable.id],
    row[GenreTable.name]
)