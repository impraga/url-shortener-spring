package com.praga.urlshortener.postgres.mapper;

import com.praga.urlshortener.dto.CreateUrlResult;
import com.praga.urlshortener.postgres.UrlShortenerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlShortenerEntityMapper {

    UrlShortenerEntity toEntity(CreateUrlResult model);

    CreateUrlResult toDomain(UrlShortenerEntity entity);
}
