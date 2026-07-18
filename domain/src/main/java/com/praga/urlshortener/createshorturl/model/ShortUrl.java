package com.praga.urlshortener.createshorturl.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ShortUrl {

    private Long id;
    private String shortCode;
    private String longUrl;

}
