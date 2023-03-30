package be.rubus.microstream.performance.exposed.query

import be.rubus.microstream.performance.exposed.DBSettings
import be.rubus.microstream.performance.exposed.domain.*
import be.rubus.microstream.performance.model.Customer
import org.jetbrains.exposed.sql.selectAll

class CustomerQuery {
    fun performQuery(page: Int): List<Customer> {

        return CustomerTable.innerJoin(AddressTable)
            .innerJoin(CityTable)
            .innerJoin(StateTable)
            .innerJoin(CountryTable)
            .selectAll()
            //.orderBy(CustomerTable.id) // We should, but didn't do this for JDBC either.
            .limit(DBSettings.PAGE_SIZE, offset = (page * DBSettings.PAGE_SIZE).toLong())
            .map { mapToCustomer(it) }
    }
}