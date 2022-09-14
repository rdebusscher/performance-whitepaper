/*
 * This file is generated by jOOQ.
 */
package be.rubus.microstream.performance.jooq.model.tables;


import be.rubus.microstream.performance.jooq.model.Keys;
import be.rubus.microstream.performance.jooq.model.Public;
import be.rubus.microstream.performance.jooq.model.tables.records.BookRecord;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Book extends TableImpl<BookRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.book</code>
     */
    public static final Book BOOK = new Book();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BookRecord> getRecordType() {
        return BookRecord.class;
    }

    /**
     * The column <code>public.book.id</code>.
     */
    public final TableField<BookRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.book.isbn13</code>.
     */
    public final TableField<BookRecord, String> ISBN13 = createField(DSL.name("isbn13"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.book.purchase_price</code>.
     */
    public final TableField<BookRecord, Double> PURCHASE_PRICE = createField(DSL.name("purchase_price"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.book.retail_price</code>.
     */
    public final TableField<BookRecord, Double> RETAIL_PRICE = createField(DSL.name("retail_price"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.book.title</code>.
     */
    public final TableField<BookRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.book.author_id</code>.
     */
    public final TableField<BookRecord, Long> AUTHOR_ID = createField(DSL.name("author_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.book.genre_id</code>.
     */
    public final TableField<BookRecord, Long> GENRE_ID = createField(DSL.name("genre_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.book.language_id</code>.
     */
    public final TableField<BookRecord, Long> LANGUAGE_ID = createField(DSL.name("language_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.book.publisher_id</code>.
     */
    public final TableField<BookRecord, Long> PUBLISHER_ID = createField(DSL.name("publisher_id"), SQLDataType.BIGINT, this, "");

    private Book(Name alias, Table<BookRecord> aliased) {
        this(alias, aliased, null);
    }

    private Book(Name alias, Table<BookRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.book</code> table reference
     */
    public Book(String alias) {
        this(DSL.name(alias), BOOK);
    }

    /**
     * Create an aliased <code>public.book</code> table reference
     */
    public Book(Name alias) {
        this(alias, BOOK);
    }

    /**
     * Create a <code>public.book</code> table reference
     */
    public Book() {
        this(DSL.name("book"), null);
    }

    public <O extends Record> Book(Table<O> child, ForeignKey<O, BookRecord> key) {
        super(child, key, BOOK);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<BookRecord, Long> getIdentity() {
        return (Identity<BookRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<BookRecord> getPrimaryKey() {
        return Keys.BOOK_PKEY;
    }

    @Override
    public List<ForeignKey<BookRecord, ?>> getReferences() {
        return Arrays.asList(Keys.BOOK__FK_BOOK_AUTHOR, Keys.BOOK__FK_BOOK_GENRE, Keys.BOOK__FK_BOOK_LANGUAGE, Keys.BOOK__FK_BOOK_PUBLISHER);
    }

    private transient Author _author;
    private transient Genre _genre;
    private transient Language _language;
    private transient Publisher _publisher;

    /**
     * Get the implicit join path to the <code>public.author</code> table.
     */
    public Author author() {
        if (_author == null)
            _author = new Author(this, Keys.BOOK__FK_BOOK_AUTHOR);

        return _author;
    }

    /**
     * Get the implicit join path to the <code>public.genre</code> table.
     */
    public Genre genre() {
        if (_genre == null)
            _genre = new Genre(this, Keys.BOOK__FK_BOOK_GENRE);

        return _genre;
    }

    /**
     * Get the implicit join path to the <code>public.language</code> table.
     */
    public Language language() {
        if (_language == null)
            _language = new Language(this, Keys.BOOK__FK_BOOK_LANGUAGE);

        return _language;
    }

    /**
     * Get the implicit join path to the <code>public.publisher</code> table.
     */
    public Publisher publisher() {
        if (_publisher == null)
            _publisher = new Publisher(this, Keys.BOOK__FK_BOOK_PUBLISHER);

        return _publisher;
    }

    @Override
    public Book as(String alias) {
        return new Book(DSL.name(alias), this);
    }

    @Override
    public Book as(Name alias) {
        return new Book(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Book rename(String name) {
        return new Book(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Book rename(Name name) {
        return new Book(name, null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, String, Double, Double, String, Long, Long, Long, Long> fieldsRow() {
        return (Row9) super.fieldsRow();
    }
}
