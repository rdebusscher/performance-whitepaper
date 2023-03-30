package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.exposed.utils.MoneyUtil
import be.rubus.microstream.performance.model.Book
import org.jetbrains.exposed.sql.ResultRow

object BookTable : BaseTable("BOOK") {

    val isbn13 = varchar("ISBN13", 255)
    val title = varchar("TITLE", 255)
    val purchasePrice = double("PURCHASE_PRICE")
    val retailPrice = double("RETAIL_PRICE")
    val authorId = long("AUTHOR_ID").references(AuthorTable.id)
    val genreId = long("GENRE_ID").references(GenreTable.id)
    val languageId = long("LANGUAGE_ID").references(LanguageTable.id)
    val publisherId = long("PUBLISHER_ID").references(PublisherTable.id)

    // Alternative is to define MonetaryAmount (implements IColumnType)
    // and registerColumn<MonetaryAmount>("amount", MonetaryAmountColumnType)
    // instead of keeping a double column mapping.
}

fun mapToBook(row: ResultRow, minimal: Boolean) = Book(
    row[BookTable.id],
    row[BookTable.isbn13],
    row[BookTable.title],
    if (minimal) null else mapToAuthor(row),
    if (minimal) null else mapToGenre(row),
    if (minimal) null else mapToPublisher(row),
    if (minimal) null else mapToLanguage(row),
    MoneyUtil.money(row[BookTable.purchasePrice]),
    MoneyUtil.money(row[BookTable.retailPrice])

)