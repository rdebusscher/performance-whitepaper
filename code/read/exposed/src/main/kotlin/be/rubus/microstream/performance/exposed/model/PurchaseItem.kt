package be.rubus.microstream.performance.exposed.model

import be.rubus.microstream.performance.model.Book
import be.rubus.microstream.performance.model.HasId
import javax.money.MonetaryAmount

/**
 * Purchase item entity, which holds a [Book], an amount and a price.
 */
data class PurchaseItem(val id: Long, val book: Book, val amount: Int, val price: MonetaryAmount, val purchaseId: Long) :
    HasId(id) {

    /**
     * Computes the total amount of the purchase item (price * amount)
     *
     * @return the total amount of this item
     */
    fun itemTotal(): MonetaryAmount {
        return price.multiply(amount.toLong())
    }
}