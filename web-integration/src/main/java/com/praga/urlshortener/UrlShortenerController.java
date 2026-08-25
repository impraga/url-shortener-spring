package com.praga.urlshortener;

import com.praga.urlshortener.mapper.UrlMapper;
import com.praga.urlshortener.model.ApiResponse;
import com.praga.urlshortener.model.CreateUrlRequest;
import com.praga.urlshortener.model.CreateUrlResponse;
import com.praga.urlshortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlShortenerController implements UrlShortenerEndpoint {

    private final UrlShortenerService urlShortenerService;
    private final UrlMapper urlMapper;

    @Override
    public ResponseEntity<ApiResponse<CreateUrlResponse>> createShortUrl(CreateUrlRequest request) {

        var createUrlResult = urlShortenerService.createShortUrl(request.getOriginalUrl());
        var createUrlResponse = urlMapper.toResponse(createUrlResult);
        return ResponseEntity.ok(ApiResponse.<CreateUrlResponse>builder()
                        .data(createUrlResponse)
                        .success(true)
                        .message("URL has shortened successfully.")
                .build());
    }

    @Override
    public ResponseEntity<Void> redirect(String shortUrl) {
        var redirectUrl = urlShortenerService.getOriginalUrl(shortUrl).getOriginalUrl();
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }

}
