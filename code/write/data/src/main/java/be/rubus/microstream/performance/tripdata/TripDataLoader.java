package be.rubus.microstream.performance.tripdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Consumer;


public class TripDataLoader {

    public static boolean linesLoadedFeedback = true;
    private static final Logger LOG = LoggerFactory.getLogger(TripDataLoader.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Consumer<TripDetailsData> recordConsumer;
    private final int lastLine;
    private final int firstLine;

    public TripDataLoader(Consumer<TripDetailsData> recordConsumer) {
        this(recordConsumer, Integer.MAX_VALUE);
    }

    public TripDataLoader(Consumer<TripDetailsData> recordConsumer, int lastLine) {
        this(recordConsumer, 2, lastLine);
    }

    public TripDataLoader(Consumer<TripDetailsData> recordConsumer, int firstLine, int lastLine) {
        this.recordConsumer = recordConsumer;
        this.firstLine = firstLine;
        this.lastLine = lastLine;
    }


    public void loadTripDetails() {
        InputStream inputStream = TripDataLoader.class.getResourceAsStream("/yellow_tripdata_2021-07.csv");
        if (inputStream == null) {
            throw new IllegalArgumentException("Could not found file 'yellow_tripdata_2021-07.csv' within root of project");
        }
        try (Scanner scanner = new Scanner(inputStream)) {
            scanner.nextLine();  // skip first line
            int lineCount = 2;
            while (scanner.hasNextLine() && lineCount < lastLine) {
                String[] tripData = scanner.nextLine().split(",");
                if (lineCount < firstLine) {
                    lineCount++;
                    continue;  // skip until we reached first line to read.
                }
                try {
                    // @formatter:off
                    TripDetailsData tripDetailsData = new TripDetailsBuilder()
                            .tripId(UUID.randomUUID().toString())
                            .vendorId(tripData[0])
                            .pickupDateTime(LocalDateTime.parse(tripData[1], formatter))
                            .dropoffDateTime(LocalDateTime.parse(tripData[2], formatter))
                            .passengerCount(getByte(tripData[3]))
                            .distance(getFloat(tripData[4]))
                            .rateCodeId(getByte(tripData[5]))
                            .storeAndFwdFlag(tripData[6].isBlank() ? ' ' : tripData[6].charAt(0))
                            .pickupLocationId(tripData[7])
                            .dropoffLocationId(tripData[8])
                            .paymentType(getByte(tripData[9]))
                            .fareAmount(getFloat(tripData[10]))
                            .extraAmount(getFloat(tripData[11]))
                            .mtaTax(getFloat(tripData[12]))
                            .tipAmount(getFloat(tripData[13]))
                            .tollAmount(getFloat(tripData[14]))
                            .improvementSurcharge(getFloat(tripData[15]))
                            .totalAmount(getFloat(tripData[16]))
                            .congestionSurcharge(getFloat(tripData[17]))
                            .build();
                    // @formatter:on

                    recordConsumer.accept(tripDetailsData);

                    lineCount++;
                } catch (Exception e) {
                    throw new RuntimeException("An error occurred while processing line " + lineCount
                            + ": trip data: " + Arrays.toString(tripData), e);
                }
                if (linesLoadedFeedback && lineCount % 10000 == 0) {
                    LOG.info(String.format("%s lines loaded", lineCount));
                }
            }


        }
        LOG.info("Trip data loaded successfully");
    }


    private byte getByte(String str) {
        if (str == null || str.isBlank()) {
            return 0;
        } else {
            return Byte.parseByte(str);
        }
    }

    private float getFloat(String str) {
        if (str == null || str.isBlank()) {
            return 0;
        } else {
            return Float.parseFloat(str);
        }
    }

}
