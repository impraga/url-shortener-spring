package com.praga.urlshortener;

import com.praga.urlshortener.model.ApiResponse;
import com.praga.urlshortener.model.CreateUrlRequest;
import com.praga.urlshortener.model.CreateUrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/urls")
public interface UrlShortenerEndpoint {

    @PostMapping
    ResponseEntity<ApiResponse<CreateUrlResponse>> createShortUrl(
            @Validated @RequestBody CreateUrlRequest request);

    @GetMapping("/{shortUrl}")
    ResponseEntity<Void> redirect(@PathVariable String shortUrl);
}
