package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.model.Language
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

object LanguageTable : BaseTable("LANGUAGE") {
    val languageTag = varchar("LANGUAGETAG", 255)
}

fun mapToLanguage(row: ResultRow) = Language(
    row[LanguageTable.id],
    Locale.forLanguageTag(row[LanguageTable.languageTag])
)
