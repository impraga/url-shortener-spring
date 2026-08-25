package com.praga.urlshortener.repository;

import com.praga.urlshortener.dto.CreateUrlResult;

import java.util.Optional;

public interface UrlRepository {

    CreateUrlResult save(CreateUrlResult url);

    CreateUrlResult findByShortCode(String shortCode);

    Optional<CreateUrlResult> findByOriginalUrl(String originalUrl);
}
