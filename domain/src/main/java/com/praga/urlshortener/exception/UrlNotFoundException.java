package com.praga.urlshortener.exception;

public class UrlNotFoundException extends UrlShortenerException {

    public UrlNotFoundException(String shortCode) {
        super("URL_NOT_FOUND", "Short URL not found : " + shortCode);
    }
}
