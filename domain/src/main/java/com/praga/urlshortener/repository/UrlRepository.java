package com.praga.urlshortener.repository;

import com.praga.urlshortener.dto.CreateUrlResult;

public interface UrlRepository {

    CreateUrlResult save(CreateUrlResult url);

    CreateUrlResult findByShortCode(String shortCode);
}
