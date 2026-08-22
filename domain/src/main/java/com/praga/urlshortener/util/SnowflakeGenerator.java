package com.praga.urlshortener.util;

import org.springframework.stereotype.Component;

@Component
public class SnowflakeGenerator {

    public long nextId() {
        return System.currentTimeMillis();
    }
}
