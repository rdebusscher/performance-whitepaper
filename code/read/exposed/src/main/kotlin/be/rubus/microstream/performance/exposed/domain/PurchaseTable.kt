package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.exposed.model.Purchase
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.LocalDateTime
import java.time.ZoneId

object PurchaseTable : BaseTable("PURCHASE") {
    val employeeId = long("employee_id").references(EmployeeTable.id).index()
    val customerId = long("customer_id").references(CustomerTable.id).index()
    val timeStamp = timestamp("time_stamp").index()
    val shopId = long("shop_id").references(ShopTable.id).index()
}

fun mapToPurchase(row: ResultRow, aliasesGroups: Map<Table, List<Alias<*>>>) = Purchase(
    row[PurchaseTable.id],
    mapToShop(row, aliasesGroups.getValue(ShopTable)),
    null,  // TODO not used in our PurchaseForeign Query but need mapping if fully functional code is required.
    mapToCustomer(row, aliasesGroups.getValue(CustomerTable)),
    LocalDateTime.ofInstant(row[PurchaseTable.timeStamp], ZoneId.systemDefault())
)