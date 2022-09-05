package be.rubus.microstream.performance.tripdata;

import java.time.LocalDateTime;

public class TripDetailsBuilder {
    private String tripId;
    private String vendorId;
    private LocalDateTime pickupDateTime;
    private String pickupLocationId;
    private LocalDateTime dropoffDateTime;
    private String dropoffLocationId;
    private byte passengerCount;
    private float distance;
    private byte rateCodeId;
    private char storeAndFwdFlag;
    private byte paymentType;
    private float fareAmount;
    private float extraAmount;
    private float mtaTax;
    private float tipAmount;
    private float tollAmount;
    private float improvementSurcharge;
    private float totalAmount;
    private float congestionSurcharge;

    public TripDetailsBuilder tripId(String tripId) {
        this.tripId = tripId;
        return this;
    }

    public TripDetailsBuilder vendorId(String vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public TripDetailsBuilder pickupDateTime(LocalDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
        return this;
    }

    public TripDetailsBuilder pickupLocationId(String pickupLocationId) {
        this.pickupLocationId = pickupLocationId;
        return this;
    }

    public TripDetailsBuilder dropoffDateTime(LocalDateTime dropoffDateTime) {
        this.dropoffDateTime = dropoffDateTime;
        return this;
    }

    public TripDetailsBuilder dropoffLocationId(String dropoffLocationId) {
        this.dropoffLocationId = dropoffLocationId;
        return this;
    }

    public TripDetailsBuilder passengerCount(byte passengerCount) {
        this.passengerCount = passengerCount;
        return this;
    }

    public TripDetailsBuilder distance(float distance) {
        this.distance = distance;
        return this;
    }

    public TripDetailsBuilder rateCodeId(byte rateCodeId) {
        this.rateCodeId = rateCodeId;
        return this;
    }

    public TripDetailsBuilder storeAndFwdFlag(char storeAndFwdFlag) {
        this.storeAndFwdFlag = storeAndFwdFlag;
        return this;
    }

    public TripDetailsBuilder paymentType(byte paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public TripDetailsBuilder fareAmount(float fareAmount) {
        this.fareAmount = fareAmount;
        return this;
    }

    public TripDetailsBuilder extraAmount(float extraAmount) {
        this.extraAmount = extraAmount;
        return this;
    }

    public TripDetailsBuilder mtaTax(float mtaTax) {
        this.mtaTax = mtaTax;
        return this;
    }

    public TripDetailsBuilder tipAmount(float tipAmount) {
        this.tipAmount = tipAmount;
        return this;
    }

    public TripDetailsBuilder tollAmount(float tollAmount) {
        this.tollAmount = tollAmount;
        return this;
    }

    public TripDetailsBuilder improvementSurcharge(float improvementSurcharge) {
        this.improvementSurcharge = improvementSurcharge;
        return this;
    }

    public TripDetailsBuilder totalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public TripDetailsBuilder congestionSurcharge(float congestionSurcharge) {
        this.congestionSurcharge = congestionSurcharge;
        return this;
    }

    public TripDetailsData build() {
        TripDetailsData result = new TripDetailsData();

        result.setTripId(tripId);
        result.setVendorId(vendorId);
        result.setPickupDateTime(pickupDateTime);
        result.setPickupLocationId(pickupLocationId);
        result.setDropoffDateTime(dropoffDateTime);
        result.setDropoffLocationId(dropoffLocationId);
        result.setPassengerCount(passengerCount);
        result.setDistance(distance);
        result.setRateCodeId(rateCodeId);
        result.setStoreAndFwdFlag(storeAndFwdFlag);
        result.setPaymentType(paymentType);
        result.setFareAmount(fareAmount);
        result.setExtraAmount(extraAmount);
        result.setMtaTax(mtaTax);
        result.setTipAmount(tipAmount);
        result.setTollAmount(tollAmount);
        result.setImprovementSurcharge(improvementSurcharge);
        result.setTotalAmount(totalAmount);
        result.setCongestionSurcharge(congestionSurcharge);
        return result;
    }
}