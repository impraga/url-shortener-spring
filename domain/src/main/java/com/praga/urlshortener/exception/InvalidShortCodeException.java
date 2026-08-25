package com.praga.urlshortener.exception;

public class InvalidShortCodeException extends UrlShortenerException {

    public InvalidShortCodeException(String shortCode) {
        super("INVALID_SHORT_CODE", "Invalid short code: " + shortCode);
    }
}
