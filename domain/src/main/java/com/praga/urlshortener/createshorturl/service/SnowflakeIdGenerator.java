package com.praga.urlshortener.createshorturl.service;

import com.praga.urlshortener.createshorturl.SnowflakeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SnowflakeIdGenerator {

    private final SnowflakeProperties snowflakeProperties;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public synchronized long nextId() {

        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards");
        }
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & ((1L << snowflakeProperties.getSequenceIdBit()) - 1);
            if (sequence == 0) {
                while (currentTimestamp <= lastTimestamp) {
                    currentTimestamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - snowflakeProperties.getEpoch())
                << (snowflakeProperties.getWorkerIdBit() + snowflakeProperties.getSequenceIdBit()))
                | (snowflakeProperties.getWorkerId() << snowflakeProperties.getSequenceIdBit())
                | sequence;
    }
}
