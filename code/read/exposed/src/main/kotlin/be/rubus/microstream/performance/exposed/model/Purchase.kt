package be.rubus.microstream.performance.exposed.model

/**
 * Purchase entity which holds a [Shop], [Employee],
 * [Customer], timestamp and [PurchaseItem]s.
 *
 *
 * This type is immutable and therefor inherently thread safe.
 */
import be.rubus.microstream.performance.model.Customer
import be.rubus.microstream.performance.model.Employee
import be.rubus.microstream.performance.model.HasId
import java.time.LocalDateTime
import javax.money.MonetaryAmount

data class Purchase(
    val id: Long,
    val shop: Shop,
    val employee: Employee?,
    val customer: Customer,
    val timestamp: LocalDateTime,
    val items: MutableList<PurchaseItem> = mutableListOf(),
    private var total: MonetaryAmount? = null
) : HasId(id) {

    fun itemCount(): Int {
        return items.size
    }

    fun addItem(item: PurchaseItem) {
        items.add(item)
    }

    fun addItems(items: List<PurchaseItem>?) {
        items?.let { this.items.addAll(it) }
    }

    fun total(): MonetaryAmount? {
        if (total == null) {
            var total: MonetaryAmount? = null
            for (item in items) {
                total = if (total == null) item.itemTotal() else total.add(item.itemTotal())
            }
            this.total = total
        }
        return total
    }
}
