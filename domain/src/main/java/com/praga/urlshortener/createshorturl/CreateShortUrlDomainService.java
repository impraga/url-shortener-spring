package com.praga.urlshortener.createshorturl;

import com.praga.urlshortener.createshorturl.model.ShortUrl;
import com.praga.urlshortener.createshorturl.service.Base62Encoder;
import com.praga.urlshortener.createshorturl.service.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateShortUrlDomainService {

    private final SnowflakeIdGenerator snowflakeIdGenerator;
    private final Base62Encoder base62Encoder;

    public String getShortUrl(ShortUrl url) {

        var snowId = snowflakeIdGenerator.nextId();
        var shortUrl = base62Encoder.encode(snowId);
        var shortObj = ShortUrl.builder()
                .longUrl(url.getLongUrl())
                .id(snowId)
                .shortCode(shortUrl)
                .build();

        return shortObj.getShortCode();
    }
}
