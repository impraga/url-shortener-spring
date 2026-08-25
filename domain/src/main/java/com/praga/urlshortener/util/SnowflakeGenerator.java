package com.praga.urlshortener.util;

import com.praga.urlshortener.configuration.SnowflakeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SnowflakeGenerator {

    private final SnowflakeProperties properties;
    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public synchronized long nextId() {

        long timestamp = currentTimestamp();

        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence();
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - properties.getEpoch())
                << timestampShift())
                | (properties.getWorkerId()
                << properties.getSequenceIdBit())
                | sequence;
    }

    private long currentTimestamp() {
        return System.currentTimeMillis();
    }

    private long waitNextMillis(long lastTimestamp) {

        long timestamp = currentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimestamp();
        }
        return timestamp;
    }

    private long maxSequence() {
        return (1L << properties.getSequenceIdBit()) - 1;
    }

    private int timestampShift() {
        return properties.getWorkerIdBit()  + properties.getSequenceIdBit();
    }
}