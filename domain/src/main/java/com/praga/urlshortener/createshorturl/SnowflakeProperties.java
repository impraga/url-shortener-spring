package com.praga.urlshortener.createshorturl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "services.snowflake")
public class SnowflakeProperties {

    public Long workerId;
    public Long epoch;
    public Long sequenceIdBit;
    public Long workerIdBit;
}
