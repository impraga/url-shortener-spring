package com.praga.urlshortener.service;

import com.praga.urlshortener.dto.CreateUrlResult;
import com.praga.urlshortener.repository.UrlRepository;
import com.praga.urlshortener.util.Base62Encoder;
import com.praga.urlshortener.util.SnowflakeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlShortenerShortenerService implements UrlShortenerServiceImpl {

    private final Base62Encoder base62Encoder;
    private final SnowflakeGenerator snowflakeGenerator;
    private final UrlRepository repository;

    @Override
    public CreateUrlResult createShortUrl(String originalUrl) {
        var sid = snowflakeGenerator.nextId();
        var shortCode = base62Encoder.encode(sid);

        return repository.save(CreateUrlResult.builder()
                .shortCode(shortCode)
                .snowflakeId(sid)
                .originalUrl(originalUrl)
                .createdAt("Now Time")
                .build());
    }

    @Override
    public CreateUrlResult getOriginalUrl(String shortCode) {
        return repository.findByShortCode(shortCode);
    }
}
