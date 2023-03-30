package be.rubus.microstream.performance.exposed.model

import be.rubus.microstream.performance.model.Address
import be.rubus.microstream.performance.model.Employee
import be.rubus.microstream.performance.model.Inventory
import be.rubus.microstream.performance.model.NamedWithAddress
import java.util.stream.Stream

/**
 * Shop entity which holds a name, [Address], [Employee]s and an [Inventory].
 *
 *
 * This type is immutable and therefor inherently thread safe.
 */
data class Shop (
    val id: Long,
    val name: String,
    val address: Address,
    val employees: List<Employee>? = null,
    val inventory: Inventory? = null
) : NamedWithAddress(id, name, address) {


    /**
     * Get the employees.
     *
     * @return a [Stream] of [Employee]s
     */
    fun employees(): Stream<Employee>? {
        return employees?.stream()
    }
}