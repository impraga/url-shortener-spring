package com.praga.urlshortener.createshorturl.service;

import org.springframework.stereotype.Service;

@Service
public class Base62Encoder {

    private static final String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String encode(long value) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(CHARSET.charAt((int) (value % 62)));
            value /= 62;
        } while (value > 0);

        return sb.reverse().toString();
    }

}
