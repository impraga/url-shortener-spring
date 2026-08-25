package com.praga.urlshortener.service;

import com.praga.urlshortener.dto.CreateUrlResult;
import com.praga.urlshortener.exception.InvalidUrlException;
import com.praga.urlshortener.repository.UrlRepository;
import com.praga.urlshortener.util.Base62Encoder;
import com.praga.urlshortener.util.SnowflakeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlShortenerService implements UrlShortenerServiceImpl {

    private final Base62Encoder base62Encoder;
    private final SnowflakeGenerator snowflakeGenerator;
    private final UrlRepository repository;

    @Override
    public CreateUrlResult createShortUrl(String originalUrl) {

        return repository.findByOriginalUrl(originalUrl)
                .orElseGet(() -> createAndSave(originalUrl));
    }

    @Override
    public CreateUrlResult getOriginalUrl(String shortCode) {
        return repository.findByShortCode(shortCode);
    }

    private CreateUrlResult createAndSave(String originalUrl) {

        if (!originalUrl.startsWith("http")) {
            throw new InvalidUrlException(originalUrl);
        }

        var sid = snowflakeGenerator.nextId();
        var shortCode = base62Encoder.encode(sid);

        return repository.save(CreateUrlResult.builder()
                .shortCode(shortCode)
                .snowflakeId(sid)
                .originalUrl(originalUrl)
                .createdAt(String.valueOf(System.currentTimeMillis()))
                .build());
    }

}
