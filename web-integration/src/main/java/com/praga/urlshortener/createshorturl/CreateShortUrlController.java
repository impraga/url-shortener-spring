package com.praga.urlshortener.createshorturl;

import com.praga.urlshortener.createshorturl.model.CreateShortUrlRequest;
import com.praga.urlshortener.createshorturl.model.CreateShortUrlResponse;
import com.praga.urlshortener.createshorturl.model.ShortUrl;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateShortUrlController implements CreateShortUrlEndpoint{

    public final CreateShortUrlDomainService createShortUrlDomainService;

    @Override
    public CreateShortUrlResponse createShortUrl(CreateShortUrlRequest request) {
        var uniqueId = createShortUrlDomainService.getShortUrl(CreateShortUrlMapper.INSTANCE.toDomain(request));
        return CreateShortUrlResponse.builder().shortUrl(uniqueId).build();
    }

    @Mapper
    interface CreateShortUrlMapper {
        CreateShortUrlMapper INSTANCE = Mappers.getMapper(CreateShortUrlMapper.class);

        ShortUrl toDomain(CreateShortUrlRequest request);
    }

}
