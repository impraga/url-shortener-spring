package com.praga.urlshortener.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateUrlResult {

    long snowflakeId;
    String shortCode;
    String originalUrl;
    String createdAt;

}
