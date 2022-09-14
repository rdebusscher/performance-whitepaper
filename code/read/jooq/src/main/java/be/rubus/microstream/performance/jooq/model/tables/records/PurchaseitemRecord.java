/*
 * This file is generated by jOOQ.
 */
package be.rubus.microstream.performance.jooq.model.tables.records;


import be.rubus.microstream.performance.jooq.model.tables.Purchaseitem;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PurchaseitemRecord extends UpdatableRecordImpl<PurchaseitemRecord> implements Record5<Long, Integer, Double, Long, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.purchaseitem.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.purchaseitem.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.purchaseitem.amount</code>.
     */
    public void setAmount(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.purchaseitem.amount</code>.
     */
    public Integer getAmount() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.purchaseitem.price</code>.
     */
    public void setPrice(Double value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.purchaseitem.price</code>.
     */
    public Double getPrice() {
        return (Double) get(2);
    }

    /**
     * Setter for <code>public.purchaseitem.book_id</code>.
     */
    public void setBookId(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.purchaseitem.book_id</code>.
     */
    public Long getBookId() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>public.purchaseitem.purchase_id</code>.
     */
    public void setPurchaseId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.purchaseitem.purchase_id</code>.
     */
    public Long getPurchaseId() {
        return (Long) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, Integer, Double, Long, Long> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, Integer, Double, Long, Long> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Purchaseitem.PURCHASEITEM.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Purchaseitem.PURCHASEITEM.AMOUNT;
    }

    @Override
    public Field<Double> field3() {
        return Purchaseitem.PURCHASEITEM.PRICE;
    }

    @Override
    public Field<Long> field4() {
        return Purchaseitem.PURCHASEITEM.BOOK_ID;
    }

    @Override
    public Field<Long> field5() {
        return Purchaseitem.PURCHASEITEM.PURCHASE_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getAmount();
    }

    @Override
    public Double component3() {
        return getPrice();
    }

    @Override
    public Long component4() {
        return getBookId();
    }

    @Override
    public Long component5() {
        return getPurchaseId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getAmount();
    }

    @Override
    public Double value3() {
        return getPrice();
    }

    @Override
    public Long value4() {
        return getBookId();
    }

    @Override
    public Long value5() {
        return getPurchaseId();
    }

    @Override
    public PurchaseitemRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public PurchaseitemRecord value2(Integer value) {
        setAmount(value);
        return this;
    }

    @Override
    public PurchaseitemRecord value3(Double value) {
        setPrice(value);
        return this;
    }

    @Override
    public PurchaseitemRecord value4(Long value) {
        setBookId(value);
        return this;
    }

    @Override
    public PurchaseitemRecord value5(Long value) {
        setPurchaseId(value);
        return this;
    }

    @Override
    public PurchaseitemRecord values(Long value1, Integer value2, Double value3, Long value4, Long value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PurchaseitemRecord
     */
    public PurchaseitemRecord() {
        super(Purchaseitem.PURCHASEITEM);
    }

    /**
     * Create a detached, initialised PurchaseitemRecord
     */
    public PurchaseitemRecord(Long id, Integer amount, Double price, Long bookId, Long purchaseId) {
        super(Purchaseitem.PURCHASEITEM);

        setId(id);
        setAmount(amount);
        setPrice(price);
        setBookId(bookId);
        setPurchaseId(purchaseId);
    }
}