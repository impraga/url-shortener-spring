package com.praga.urlshortener.mapper;

import com.praga.urlshortener.dto.CreateUrlResult;
import com.praga.urlshortener.model.CreateUrlResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlMapper {

    CreateUrlResponse toResponse(CreateUrlResult result);
}
