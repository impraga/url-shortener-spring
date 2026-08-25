package com.praga.urlshortener.service;

import com.praga.urlshortener.dto.CreateUrlResult;

public interface UrlShortenerServiceImpl {

    CreateUrlResult createShortUrl(String originalUrl);

    CreateUrlResult getOriginalUrl(String shortCode);
}
