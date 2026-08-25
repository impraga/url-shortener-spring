package com.praga.urlshortener.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    boolean success;
    String errorCode;
    String message;
    String traceId;

}
