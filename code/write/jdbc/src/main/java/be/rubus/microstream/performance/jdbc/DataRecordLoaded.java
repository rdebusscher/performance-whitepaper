package be.rubus.microstream.performance.jdbc;

import be.rubus.microstream.performance.tripdata.TripDetailsData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;

public class DataRecordLoaded implements Consumer<TripDetailsData> {

    private final Connection connection;

    private PreparedStatement ps;

    private long idx;

    public DataRecordLoaded(Connection connection) throws SQLException {
        this.connection = connection;
        prepareStatement();
    }

    private void prepareStatement() throws SQLException {

        ps = connection.prepareStatement("INSERT INTO trip_details(tripId,congestionSurcharge,distance,dropoffDateTime,dropoffLocationId,extraAmount,fareAmount,improvementSurcharge,mtaTax,passengerCount,paymentType,pickupDateTime,pickupLocationId,rateCodeId,storeAndFwdFlag,tipAmount,tollAmount,totalAmount,vendorId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

    }

    @Override
    public void accept(TripDetailsData tripDetailsData) {
        try {
            ps.setObject(1, tripDetailsData.getTripId());
            ps.setObject(2, tripDetailsData.getCongestionSurcharge());
            ps.setObject(3, tripDetailsData.getDistance());
            ps.setObject(4, tripDetailsData.getDropoffDateTime());
            ps.setObject(5, tripDetailsData.getDropoffLocationId());
            ps.setObject(6, tripDetailsData.getExtraAmount());
            ps.setObject(7, tripDetailsData.getFareAmount());
            ps.setObject(8, tripDetailsData.getImprovementSurcharge());
            ps.setObject(9, tripDetailsData.getMtaTax());
            ps.setObject(10, tripDetailsData.getPassengerCount());
            ps.setObject(11, tripDetailsData.getPaymentType());
            ps.setObject(12, tripDetailsData.getPickupDateTime());
            ps.setObject(13, tripDetailsData.getPickupLocationId());
            ps.setObject(14, tripDetailsData.getRateCodeId());
            ps.setObject(15, tripDetailsData.getStoreAndFwdFlag());
            ps.setObject(16, tripDetailsData.getTipAmount());
            ps.setObject(17, tripDetailsData.getTollAmount());
            ps.setObject(18, tripDetailsData.getTotalAmount());
            ps.setObject(19, tripDetailsData.getVendorId());


            ps.addBatch();

            idx++;
            if (idx % 1000 == 0) {
                ps.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
