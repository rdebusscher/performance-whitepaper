package be.rubus.microstream.performance.exposed.domain

import be.rubus.microstream.performance.exposed.model.Shop
import org.jetbrains.exposed.sql.Alias
import org.jetbrains.exposed.sql.ResultRow

object ShopTable : NamedWithAddressTable("SHOP")

fun mapToShop(row: ResultRow, aliases: List<Alias<*>>): Shop {
    val aliasesMap = aliases.associateBy { it.delegate.tableName }

    val aliasProvider: (String?) -> Alias<*>? = { tableName ->
        aliasesMap[tableName]
    }
    return Shop(
        row[ShopTable.id],
        row[ShopTable.name],
        mapToAddress(row, aliasProvider)
    )
}
