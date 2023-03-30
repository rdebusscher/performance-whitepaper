package be.rubus.microstream.performance.exposed.query

import be.rubus.microstream.performance.exposed.domain.*
import be.rubus.microstream.performance.exposed.model.Purchase
import be.rubus.microstream.performance.model.Country
import org.jetbrains.exposed.sql.*

class PurchaseQuery {
    fun performQuery(country: Country, year: Int): List<Purchase> {

        val psa = AddressTable.alias("psa")
        val psc = CityTable.alias("psc")
        val pss = StateTable.alias("pss")
        val pscc = CountryTable.alias("pscc")
        val pca = AddressTable.alias("pca")
        val pcc = CityTable.alias("pcc")
        val pcs = StateTable.alias("pcs")
        val pccc = CountryTable.alias("pccc")

        val aliasesGroups: MutableMap<Table, List<Alias<*>>> = mutableMapOf()
        aliasesGroups[ShopTable] = listOf(psa, psc, pss, pscc)
        aliasesGroups[CustomerTable] = listOf(pca, pcc, pcs, pccc)

        val data = PurchaseTable
            .innerJoin(ShopTable)
            .join(psa, JoinType.INNER) {
                ShopTable.addressId eq psa[AddressTable.id]
            }
            .join(psc, JoinType.INNER) {
                psa[AddressTable.cityId] eq psc[CityTable.id]
            }
            .join(pss, JoinType.INNER) {
                psc[CityTable.stateId] eq pss[StateTable.id]
            }
            .join(pscc, JoinType.INNER) {
                pss[StateTable.countryId] eq pscc[CountryTable.id]
            }
            .innerJoin(CustomerTable)
            .join(pca, JoinType.INNER) {
                CustomerTable.addressId eq pca[AddressTable.id]
            }
            .join(pcc, JoinType.INNER) {
                pca[AddressTable.cityId] eq pcc[CityTable.id]
            }
            .join(pcs, JoinType.INNER) {
                pcc[CityTable.stateId] eq pcs[StateTable.id]
            }
            .join(pccc, JoinType.INNER) {
                pcs[StateTable.countryId] eq pccc[CountryTable.id]
            }
            .select {
                (psa[AddressTable.cityId] neq pca[AddressTable.cityId]) and
                        (pscc[CountryTable.id] eq country.id) and
                        (ExtractYear(PurchaseTable.timeStamp) eq year)
            }.map { mapToPurchase(it, aliasesGroups) }


        val purchaseIds = data.map { it.id }

        val purchaseItems = PurchaseItemTable.innerJoin(BookTable)
            .select {
                PurchaseItemTable.purchaseId inList purchaseIds
            }
            .map { mapToPurchaseItem(it) }
            .groupBy { it.purchaseId }

        data.forEach { it.addItems(purchaseItems[it.id]) }

        return data
    }

    // Custom function
    class ExtractYear(expr: Expression<*>) : CustomFunction<Int>("", IntegerColumnType(), expr) {
        override fun toQueryBuilder(queryBuilder: QueryBuilder): Unit = queryBuilder {
            append("extract(year from ")
            expr.appendTo { +it }
            append(')')
        }
    }


}