package com.praga.urlshortener.postgres;

import com.praga.urlshortener.dto.CreateUrlResult;
import com.praga.urlshortener.exception.UrlNotFoundException;
import com.praga.urlshortener.postgres.mapper.UrlShortenerEntityMapper;
import com.praga.urlshortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UrlShortenerRepositoryAdapter implements UrlRepository {

    private final UrlShortenerRepository repository;
    private final UrlShortenerEntityMapper mapper;

    @Override
    public CreateUrlResult save(CreateUrlResult model) {

        UrlShortenerEntity entity = mapper.toEntity(model);
        UrlShortenerEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public CreateUrlResult findByShortCode(String shortCode) {
        return repository.findByShortCode(shortCode)
                .map(mapper::toDomain)
                .orElseThrow(() -> new UrlNotFoundException(shortCode));
    }

    @Override
    public Optional<CreateUrlResult> findByOriginalUrl(String originalUrl) {
        return repository.findByOriginalUrl(originalUrl)
                .map(mapper::toDomain);
    }
}
