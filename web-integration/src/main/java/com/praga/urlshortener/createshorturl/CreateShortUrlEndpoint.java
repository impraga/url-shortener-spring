package com.praga.urlshortener.createshorturl;

import com.praga.urlshortener.createshorturl.model.CreateShortUrlRequest;
import com.praga.urlshortener.createshorturl.model.CreateShortUrlResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CreateShortUrlEndpoint {

    @PostMapping(path = "/short-url")
    CreateShortUrlResponse createShortUrl(
            @RequestBody CreateShortUrlRequest request
    );
}
