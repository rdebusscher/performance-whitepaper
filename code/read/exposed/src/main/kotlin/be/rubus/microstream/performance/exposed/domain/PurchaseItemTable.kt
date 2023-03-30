package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.exposed.model.PurchaseItem
import be.rubus.microstream.performance.exposed.utils.MoneyUtil
import org.jetbrains.exposed.sql.ResultRow

object PurchaseItemTable : BaseTable("PURCHASEITEM") {
    val bookId = long("book_id").references(BookTable.id)
    val amount = integer("AMOUNT")
    val price = double("PRICE")
    val purchaseId = long("PURCHASE_ID").references(PurchaseTable.id).index()
}

fun mapToPurchaseItem(row: ResultRow) = PurchaseItem(
    row[PurchaseItemTable.id],
    mapToBook(row, true),
    row[PurchaseItemTable.amount],
    MoneyUtil.money(row[PurchaseItemTable.price])!!,  // price is required field so we always have value
    row[PurchaseItemTable.purchaseId]
)