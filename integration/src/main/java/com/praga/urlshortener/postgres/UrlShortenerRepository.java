package com.praga.urlshortener.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlShortenerRepository extends JpaRepository<UrlShortenerEntity, Long> {

    Optional<UrlShortenerEntity> findByShortCode(String shortCode);

    Optional<UrlShortenerEntity> findByOriginalUrl(String originalUrl);
}
