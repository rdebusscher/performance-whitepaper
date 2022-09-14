package be.rubus.microstream.performance.tripdata;

import one.microstream.reference.Lazy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRoot {

    private final Map<Integer, Lazy<List<TripDetailsData>>> tripDetailsByDay;

    public DataRoot() {
        tripDetailsByDay = new HashMap<>();
        for (int i = 1; i < 32; i++) {
            tripDetailsByDay.put(i, Lazy.Reference(new ArrayList<>()));
        }

    }

    public List<TripDetailsData> getTripDetails(int day) {
        return tripDetailsByDay.get(day).get();
    }

    public void addDetails(TripDetailsData tripDetailsData) {
        LocalDateTime pickupDateTime = tripDetailsData.getPickupDateTime();
        tripDetailsByDay.get(pickupDateTime.getDayOfMonth()).get().add(tripDetailsData);
    }

    public void clearReferenceFor(int day) {
        tripDetailsByDay.get(day).clear();
    }
}
