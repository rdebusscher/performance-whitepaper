package be.rubus.microstream.performance.tripdata;

import java.util.function.LongSupplier;

public interface StopWatch {

    long stop();

    static StopWatch StartNanoTime() {
        return new Default(System::nanoTime);
    }

    class Default implements StopWatch {
        private final LongSupplier timeSupplier;
        private final long start;

        public Default(LongSupplier timeSupplier) {
            this.start = (this.timeSupplier = timeSupplier).getAsLong();
        }

        @Override
        public long stop() {
            return this.timeSupplier.getAsLong() - this.start;
        }

    }

}
