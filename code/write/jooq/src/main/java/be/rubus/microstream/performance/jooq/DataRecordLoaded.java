package be.rubus.microstream.performance.jooq;

import be.rubus.microstream.performance.jooq.model.tables.TripDetails;
import be.rubus.microstream.performance.tripdata.TripDetailsData;
import org.jooq.DSLContext;
import org.jooq.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static be.rubus.microstream.performance.jooq.model.Tables.TRIP_DETAILS;

public class DataRecordLoaded implements Consumer<TripDetailsData> {
    private final DSLContext context;

    private final List<Query> statements;

    public DataRecordLoaded(DSLContext context) {

        this.context = context;
        this.statements = new ArrayList<>();
    }

    @Override
    public void accept(TripDetailsData tripDetailsData) {
        statements.add(context.insertInto(TRIP_DETAILS
                        , TripDetails.TRIP_DETAILS.TRIPID
                        , TripDetails.TRIP_DETAILS.CONGESTIONSURCHARGE
                        , TripDetails.TRIP_DETAILS.DISTANCE
                        , TripDetails.TRIP_DETAILS.DROPOFFDATETIME
                        , TripDetails.TRIP_DETAILS.DROPOFFLOCATIONID
                        , TripDetails.TRIP_DETAILS.EXTRAAMOUNT
                        , TripDetails.TRIP_DETAILS.FAREAMOUNT
                        , TripDetails.TRIP_DETAILS.IMPROVEMENTSURCHARGE
                        , TripDetails.TRIP_DETAILS.MTATAX
                        , TripDetails.TRIP_DETAILS.PASSENGERCOUNT
                        , TripDetails.TRIP_DETAILS.PAYMENTTYPE
                        , TripDetails.TRIP_DETAILS.PICKUPDATETIME
                        , TripDetails.TRIP_DETAILS.PICKUPLOCATIONID
                        , TripDetails.TRIP_DETAILS.RATECODEID
                        , TripDetails.TRIP_DETAILS.STOREANDFWDFLAG
                        , TripDetails.TRIP_DETAILS.TIPAMOUNT
                        , TripDetails.TRIP_DETAILS.TOLLAMOUNT
                        , TripDetails.TRIP_DETAILS.TOTALAMOUNT
                        , TripDetails.TRIP_DETAILS.VENDORID
                )
                .values(
                        tripDetailsData.getTripId()
                        , tripDetailsData.getCongestionSurcharge()
                        , tripDetailsData.getDistance()
                        , tripDetailsData.getDropoffDateTime()
                        , tripDetailsData.getDropoffLocationId()
                        , tripDetailsData.getExtraAmount()
                        , tripDetailsData.getFareAmount()
                        , tripDetailsData.getImprovementSurcharge()
                        , tripDetailsData.getMtaTax()
                        , (short) tripDetailsData.getPassengerCount()
                        , (short) tripDetailsData.getPaymentType()
                        , tripDetailsData.getPickupDateTime()
                        , tripDetailsData.getPickupLocationId()
                        , (short) tripDetailsData.getRateCodeId()
                        , Character.toString(tripDetailsData.getStoreAndFwdFlag())
                        , tripDetailsData.getTipAmount()
                        , tripDetailsData.getTollAmount()
                        , tripDetailsData.getTotalAmount()
                        , tripDetailsData.getVendorId()

                ));

        if (statements.size() == 10000) {
            sendBatch();
        }
        // Transactions handled in main class.
    }

    public void sendBatch() {
        context.batch(statements).execute();
        statements.clear();
    }
}
