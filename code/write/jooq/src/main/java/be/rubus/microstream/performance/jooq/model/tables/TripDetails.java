/*
 * This file is generated by jOOQ.
 */
package be.rubus.microstream.performance.jooq.model.tables;


import be.rubus.microstream.performance.jooq.model.Keys;
import be.rubus.microstream.performance.jooq.model.Public;
import be.rubus.microstream.performance.jooq.model.tables.records.TripDetailsRecord;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row19;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TripDetails extends TableImpl<TripDetailsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.trip_details</code>
     */
    public static final TripDetails TRIP_DETAILS = new TripDetails();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TripDetailsRecord> getRecordType() {
        return TripDetailsRecord.class;
    }

    /**
     * The column <code>public.trip_details.tripid</code>.
     */
    public final TableField<TripDetailsRecord, String> TRIPID = createField(DSL.name("tripid"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.trip_details.congestionsurcharge</code>.
     */
    public final TableField<TripDetailsRecord, Float> CONGESTIONSURCHARGE = createField(DSL.name("congestionsurcharge"), SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>public.trip_details.distance</code>.
     */
    public final TableField<TripDetailsRecord, Float> DISTANCE = createField(DSL.name("distance"), SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>public.trip_details.dropoffdatetime</code>.
     */
    public final TableField<TripDetailsRecord, LocalDateTime> DROPOFFDATETIME = createField(DSL.name("dropoffdatetime"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>public.trip_details.dropofflocationid</code>.
     */
    public final TableField<TripDetailsRecord, String> DROPOFFLOCATIONID = createField(DSL.name("dropofflocationid"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.trip_details.extraamount</code>.
     */
    public final TableField<TripDetailsRecord, Float> EXTRAAMOUNT = createField(DSL.name("extraamount"), SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>public.trip_details.fareamount</code>.
     */
    public final TableField<TripDetailsRecord, Float> FAREAMOUNT = createField(DSL.name("fareamount"), SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>public.trip_details.improvementsurcharge</code>.
     */
    public final TableField<TripDetailsRecord, Float> IMPROVEMENTSURCHARGE = createField(DSL.name("improvementsurcharge"), SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>public.trip_details.mtatax</code>.
     */
    public final TableField<TripDetailsRecord, Float> MTATAX = createField(DSL.name("mtatax"), SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>public.trip_details.passengercount</code>.
     */
    public final TableField<TripDetailsRecord, Short> PASSENGERCOUNT = createField(DSL.name("passengercount"), SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.trip_details.paymenttype</code>.
     */
    public final TableField<TripDetailsRecord, Short> PAYMENTTYPE = createField(DSL.name("paymenttype"), SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.trip_details.pickupdatetime</code>.
     */
    public final TableField<TripDetailsRecord, LocalDateTime> PICKUPDATETIME = createField(DSL.name("pickupdatetime"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>public.trip_details.pickuplocationid</code>.
     */
    public final TableField<TripDetailsRecord, String> PICKUPLOCATIONID = createField(DSL.name("pickuplocationid"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.trip_details.ratecodeid</code>.
     */
    public final TableField<TripDetailsRecord, Short> RATECODEID = createField(DSL.name("ratecodeid"), SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.trip_details.storeandfwdflag</code>.
     */
    public final TableField<TripDetailsRecord, String> STOREANDFWDFLAG = createField(DSL.name("storeandfwdflag"), SQLDataType.CHAR(1).nullable(false), this, "");

    /**
     * The column <code>public.trip_details.tipamount</code>.
     */
    public final TableField<TripDetailsRecord, Float> TIPAMOUNT = createField(DSL.name("tipamount"), SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>public.trip_details.tollamount</code>.
     */
    public final TableField<TripDetailsRecord, Float> TOLLAMOUNT = createField(DSL.name("tollamount"), SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>public.trip_details.totalamount</code>.
     */
    public final TableField<TripDetailsRecord, Float> TOTALAMOUNT = createField(DSL.name("totalamount"), SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>public.trip_details.vendorid</code>.
     */
    public final TableField<TripDetailsRecord, String> VENDORID = createField(DSL.name("vendorid"), SQLDataType.VARCHAR(255), this, "");

    private TripDetails(Name alias, Table<TripDetailsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TripDetails(Name alias, Table<TripDetailsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.trip_details</code> table reference
     */
    public TripDetails(String alias) {
        this(DSL.name(alias), TRIP_DETAILS);
    }

    /**
     * Create an aliased <code>public.trip_details</code> table reference
     */
    public TripDetails(Name alias) {
        this(alias, TRIP_DETAILS);
    }

    /**
     * Create a <code>public.trip_details</code> table reference
     */
    public TripDetails() {
        this(DSL.name("trip_details"), null);
    }

    public <O extends Record> TripDetails(Table<O> child, ForeignKey<O, TripDetailsRecord> key) {
        super(child, key, TRIP_DETAILS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<TripDetailsRecord> getPrimaryKey() {
        return Keys.TRIP_DETAILS_PKEY;
    }

    @Override
    public TripDetails as(String alias) {
        return new TripDetails(DSL.name(alias), this);
    }

    @Override
    public TripDetails as(Name alias) {
        return new TripDetails(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TripDetails rename(String name) {
        return new TripDetails(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TripDetails rename(Name name) {
        return new TripDetails(name, null);
    }

    // -------------------------------------------------------------------------
    // Row19 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row19<String, Float, Float, LocalDateTime, String, Float, Float, Float, Float, Short, Short, LocalDateTime, String, Short, String, Float, Float, Float, String> fieldsRow() {
        return (Row19) super.fieldsRow();
    }
}
