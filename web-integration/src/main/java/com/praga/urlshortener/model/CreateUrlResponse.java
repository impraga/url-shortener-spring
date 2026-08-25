package com.praga.urlshortener.model;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUrlResponse {

    long snowflakeId;
    String shortCode;
    String shortUrl;
    String originalUrl;
    String createdAt;

}
