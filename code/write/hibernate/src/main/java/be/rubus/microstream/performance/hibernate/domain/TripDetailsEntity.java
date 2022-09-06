package be.rubus.microstream.performance.hibernate.domain;

import be.rubus.microstream.performance.tripdata.TripDetailsData;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "trip_details")
public class TripDetailsEntity implements Serializable {

    @Id
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

    /**
     * Required for Hibernate
     */
    public TripDetailsEntity() {
    }

    public TripDetailsEntity(TripDetailsData tripDetailsData) {
        this.tripId = tripDetailsData.getTripId();
        this.vendorId = tripDetailsData.getVendorId();
        this.pickupDateTime = tripDetailsData.getPickupDateTime();
        this.pickupLocationId = tripDetailsData.getPickupLocationId();
        this.dropoffDateTime = tripDetailsData.getDropoffDateTime();
        this.dropoffLocationId = tripDetailsData.getDropoffLocationId();
        this.passengerCount = tripDetailsData.getPassengerCount();
        this.distance = tripDetailsData.getDistance();
        this.rateCodeId = tripDetailsData.getRateCodeId();
        this.storeAndFwdFlag = tripDetailsData.getStoreAndFwdFlag();
        this.paymentType = tripDetailsData.getPaymentType();
        this.fareAmount = tripDetailsData.getFareAmount();
        this.extraAmount = tripDetailsData.getExtraAmount();
        this.mtaTax = tripDetailsData.getMtaTax();
        this.tipAmount = tripDetailsData.getTipAmount();
        this.tollAmount = tripDetailsData.getTollAmount();
        this.improvementSurcharge = tripDetailsData.getImprovementSurcharge();
        this.totalAmount = tripDetailsData.getTotalAmount();
        this.congestionSurcharge = tripDetailsData.getCongestionSurcharge();
    }


    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public LocalDateTime getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(LocalDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public String getPickupLocationId() {
        return pickupLocationId;
    }

    public void setPickupLocationId(String pickupLocationId) {
        this.pickupLocationId = pickupLocationId;
    }

    public LocalDateTime getDropoffDateTime() {
        return dropoffDateTime;
    }

    public void setDropoffDateTime(LocalDateTime dropoffDateTime) {
        this.dropoffDateTime = dropoffDateTime;
    }

    public String getDropoffLocationId() {
        return dropoffLocationId;
    }

    public void setDropoffLocationId(String dropoffLocationId) {
        this.dropoffLocationId = dropoffLocationId;
    }

    public byte getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(byte passengerCount) {
        this.passengerCount = passengerCount;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public byte getRateCodeId() {
        return rateCodeId;
    }

    public void setRateCodeId(byte rateCodeId) {
        this.rateCodeId = rateCodeId;
    }

    public char getStoreAndFwdFlag() {
        return storeAndFwdFlag;
    }

    public void setStoreAndFwdFlag(char storeAndFwdFlag) {
        this.storeAndFwdFlag = storeAndFwdFlag;
    }

    public byte getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(byte paymentType) {
        this.paymentType = paymentType;
    }

    public float getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(float fareAmount) {
        this.fareAmount = fareAmount;
    }

    public float getExtraAmount() {
        return extraAmount;
    }

    public void setExtraAmount(float extraAmount) {
        this.extraAmount = extraAmount;
    }

    public float getMtaTax() {
        return mtaTax;
    }

    public void setMtaTax(float mtaTax) {
        this.mtaTax = mtaTax;
    }

    public float getTipAmount() {
        return tipAmount;
    }

    public void setTipAmount(float tipAmount) {
        this.tipAmount = tipAmount;
    }

    public float getTollAmount() {
        return tollAmount;
    }

    public void setTollAmount(float tollAmount) {
        this.tollAmount = tollAmount;
    }

    public float getImprovementSurcharge() {
        return improvementSurcharge;
    }

    public void setImprovementSurcharge(float improvementSurcharge) {
        this.improvementSurcharge = improvementSurcharge;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getCongestionSurcharge() {
        return congestionSurcharge;
    }

    public void setCongestionSurcharge(float congestionSurcharge) {
        this.congestionSurcharge = congestionSurcharge;
    }
}
