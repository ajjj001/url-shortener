package com.example.urlshortener.dto;

import lombok.Data;

@Data
public class CreateShortUrlRequest {
    private String originalUrl;
}