package be.rubus.microstream.performance.tripdata;

import java.util.function.Consumer;

public class DataRecordLoaded implements Consumer<TripDetailsData> {

    private final DataRoot dataRoot;

    public DataRecordLoaded(DataRoot dataRoot) {
        this.dataRoot = dataRoot;
    }

    @Override
    public void accept(TripDetailsData tripDetailsData) {
        dataRoot.addDetails(tripDetailsData);
    }


}
