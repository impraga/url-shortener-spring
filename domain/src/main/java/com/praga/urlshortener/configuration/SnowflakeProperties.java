package com.praga.urlshortener.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "services.snowflake")
public class SnowflakeProperties {

    private long epoch;
    private long workerId;
    private int workerIdBit;
    private int sequenceIdBit;

}
