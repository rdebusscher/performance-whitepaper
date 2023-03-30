package be.rubus.microstream.performance.exposed.utils

import org.javamoney.moneta.RoundedMoney
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import javax.money.CurrencyUnit
import javax.money.Monetary
import javax.money.MonetaryAmount

object MoneyUtil {
    /**
     * [CurrencyUnit] for this demo, US Dollar is used as only currency.
     */
    val CURRENCY_UNIT: CurrencyUnit = Monetary.getCurrency(Locale.US)

    /**
     * Multiplicant used to calculate retail prices, adds an 11% margin.
     */
    private val RETAIL_MULTIPLICANT = scale(BigDecimal.valueOf(1.11))
    private fun scale(number: BigDecimal): BigDecimal {
        return number.setScale(2, RoundingMode.HALF_UP)
    }

    /**
     * Converts a double into a [MonetaryAmount]
     *
     * @param number the number to convert
     * @return the converted [MonetaryAmount]
     */
    fun money(number: Double?): MonetaryAmount? {
        return money(number?.let { BigDecimal(it) })
    }

    /**
     * Converts a [BigDecimal] into a [MonetaryAmount]
     *
     * @param number the number to convert
     * @return the converted [MonetaryAmount]
     */
    fun money(number: BigDecimal?): MonetaryAmount? {
        return if (number == null) null else RoundedMoney.of(scale(number), CURRENCY_UNIT)
    }

    /**
     * Converts a [MonetaryAmount] into a [Double]
     *
     * @param money amount to convert
     * @return the converted [MonetaryAmount]
     */
    fun convertToDouble(money: MonetaryAmount?): Double? {
        return money?.number?.toDouble()
    }

    /**
     * Calculates the retail price based on a purchase price by adding a margin.
     *
     * @param purchasePrice the purchase price
     * @return the calculated retail price
     * @see .RETAIL_MULTIPLICANT
     */
    fun retailPrice(purchasePrice: MonetaryAmount?): MonetaryAmount? {
        return purchasePrice?.multiply(RETAIL_MULTIPLICANT)
    }
}
