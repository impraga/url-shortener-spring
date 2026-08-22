package com.praga.urlshortener.model;

import lombok.Builder;

@Builder
public class ErrorResponse {

    boolean success;
    String errorCode;
    String message;
    String traceId;

}
