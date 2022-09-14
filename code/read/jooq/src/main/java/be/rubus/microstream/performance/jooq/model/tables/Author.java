/*
 * This file is generated by jOOQ.
 */
package be.rubus.microstream.performance.jooq.model.tables;


import be.rubus.microstream.performance.jooq.model.Keys;
import be.rubus.microstream.performance.jooq.model.Public;
import be.rubus.microstream.performance.jooq.model.tables.records.AuthorRecord;
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
public class Author extends TableImpl<AuthorRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.author</code>
     */
    public static final Author AUTHOR = new Author();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AuthorRecord> getRecordType() {
        return AuthorRecord.class;
    }

    /**
     * The column <code>public.author.id</code>.
     */
    public final TableField<AuthorRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.author.name</code>.
     */
    public final TableField<AuthorRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.author.address_id</code>.
     */
    public final TableField<AuthorRecord, Long> ADDRESS_ID = createField(DSL.name("address_id"), SQLDataType.BIGINT, this, "");

    private Author(Name alias, Table<AuthorRecord> aliased) {
        this(alias, aliased, null);
    }

    private Author(Name alias, Table<AuthorRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.author</code> table reference
     */
    public Author(String alias) {
        this(DSL.name(alias), AUTHOR);
    }

    /**
     * Create an aliased <code>public.author</code> table reference
     */
    public Author(Name alias) {
        this(alias, AUTHOR);
    }

    /**
     * Create a <code>public.author</code> table reference
     */
    public Author() {
        this(DSL.name("author"), null);
    }

    public <O extends Record> Author(Table<O> child, ForeignKey<O, AuthorRecord> key) {
        super(child, key, AUTHOR);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<AuthorRecord, Long> getIdentity() {
        return (Identity<AuthorRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<AuthorRecord> getPrimaryKey() {
        return Keys.AUTHOR_PKEY;
    }

    @Override
    public List<ForeignKey<AuthorRecord, ?>> getReferences() {
        return Arrays.asList(Keys.AUTHOR__FK_AUTHOR_ADDRESS);
    }

    private transient Address _address;

    /**
     * Get the implicit join path to the <code>public.address</code> table.
     */
    public Address address() {
        if (_address == null)
            _address = new Address(this, Keys.AUTHOR__FK_AUTHOR_ADDRESS);

        return _address;
    }

    @Override
    public Author as(String alias) {
        return new Author(DSL.name(alias), this);
    }

    @Override
    public Author as(Name alias) {
        return new Author(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Author rename(String name) {
        return new Author(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Author rename(Name name) {
        return new Author(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, String, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
