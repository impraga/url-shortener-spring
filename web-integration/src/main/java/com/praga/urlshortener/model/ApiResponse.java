package com.praga.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private Boolean success;
    private String message;
    private T data;

}
