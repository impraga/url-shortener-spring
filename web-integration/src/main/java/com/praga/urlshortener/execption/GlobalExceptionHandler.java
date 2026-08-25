package com.praga.urlshortener.execption;

import com.praga.urlshortener.exception.UrlShortenerException;
import org.springframework.http.ResponseEntity;
import com.praga.urlshortener.model.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlShortenerException.class)
    public ResponseEntity<ErrorResponse> handleUrlShortenerException(UrlShortenerException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .success(false)
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}
