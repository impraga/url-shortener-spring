package com.praga.urlshortener.exception;

import lombok.Getter;

@Getter
public class UrlShortenerException extends RuntimeException {

    private final String errorCode;

    public UrlShortenerException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
