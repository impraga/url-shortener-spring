package com.praga.urlshortener.exception;

public class InvalidUrlException extends UrlShortenerException {

    public InvalidUrlException(String originalUrl) {
        super("INVALID_URL", "Invalid URL: " + originalUrl);
    }
}
