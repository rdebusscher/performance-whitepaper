package be.rubus.microstream.performance.hibernate;

import be.rubus.microstream.performance.hibernate.domain.TripDetailsEntity;
import be.rubus.microstream.performance.tripdata.TripDetailsData;
import org.hibernate.Session;

import java.util.function.Consumer;

public class DataRecordLoaded implements Consumer<TripDetailsData> {

    private final Session session;

    public DataRecordLoaded(Session session) {
        this.session = session;
    }

    @Override
    public void accept(TripDetailsData tripDetailsData) {
        session.persist(new TripDetailsEntity(tripDetailsData));
    }


}
