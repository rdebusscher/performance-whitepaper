package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.model.Customer
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.ResultRow

object CustomerTable : NamedWithAddressTable("CUSTOMER")

fun mapToCustomer(row: ResultRow, aliases: List<Alias<*>>? = null): Customer {
    val aliasesMap = aliases?.associateBy { it.delegate.tableName }

    val aliasProvider: (String?) -> Alias<*>? = { tableName ->
        aliasesMap?.get(tableName)
    }
    return Customer(
        //  No named parameters since Customer is a Java class. But should be clear enough
        row[CustomerTable.id],
        0,  // This customerId is not stored in database
        row[CustomerTable.name],
        mapToAddress(row, aliasProvider)
    )
}
