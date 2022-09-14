/*
 * This file is generated by jOOQ.
 */
package be.rubus.microstream.performance.jooq.model;


import be.rubus.microstream.performance.jooq.model.tables.*;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.address</code>.
     */
    public final Address ADDRESS = Address.ADDRESS;

    /**
     * The table <code>public.author</code>.
     */
    public final Author AUTHOR = Author.AUTHOR;

    /**
     * The table <code>public.book</code>.
     */
    public final Book BOOK = Book.BOOK;

    /**
     * The table <code>public.city</code>.
     */
    public final City CITY = City.CITY;

    /**
     * The table <code>public.country</code>.
     */
    public final Country COUNTRY = Country.COUNTRY;

    /**
     * The table <code>public.customer</code>.
     */
    public final Customer CUSTOMER = Customer.CUSTOMER;

    /**
     * The table <code>public.employee</code>.
     */
    public final Employee EMPLOYEE = Employee.EMPLOYEE;

    /**
     * The table <code>public.genre</code>.
     */
    public final Genre GENRE = Genre.GENRE;

    /**
     * The table <code>public.inventoryitem</code>.
     */
    public final Inventoryitem INVENTORYITEM = Inventoryitem.INVENTORYITEM;

    /**
     * The table <code>public.language</code>.
     */
    public final Language LANGUAGE = Language.LANGUAGE;

    /**
     * The table <code>public.publisher</code>.
     */
    public final Publisher PUBLISHER = Publisher.PUBLISHER;

    /**
     * The table <code>public.purchase</code>.
     */
    public final Purchase PURCHASE = Purchase.PURCHASE;

    /**
     * The table <code>public.purchaseitem</code>.
     */
    public final Purchaseitem PURCHASEITEM = Purchaseitem.PURCHASEITEM;

    /**
     * The table <code>public.shop</code>.
     */
    public final Shop SHOP = Shop.SHOP;

    /**
     * The table <code>public.state</code>.
     */
    public final State STATE = State.STATE;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Address.ADDRESS,
            Author.AUTHOR,
            Book.BOOK,
            City.CITY,
            Country.COUNTRY,
            Customer.CUSTOMER,
            Employee.EMPLOYEE,
            Genre.GENRE,
            Inventoryitem.INVENTORYITEM,
            Language.LANGUAGE,
            Publisher.PUBLISHER,
            Purchase.PURCHASE,
            Purchaseitem.PURCHASEITEM,
            Shop.SHOP,
            State.STATE
        );
    }
}
