package com.example.urlshortener.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.urlshortener.entity.ShortUrl;
import com.example.urlshortener.service.UrlShortenerService;
import com.example.urlshortener.dto.CreateShortUrlRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/short-urls")
@RequiredArgsConstructor
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;

    @PostMapping
    public ResponseEntity<ShortUrl> createShortUrl(@RequestBody CreateShortUrlRequest request) {
        ShortUrl shortUrl = urlShortenerService.createShortUrl(request.getOriginalUrl());
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<String> redirectToOriginalUrl(@PathVariable String shortCode) {
        Optional<ShortUrl> shortUrl = urlShortenerService.getShortUrlByCode(shortCode);
        if (shortUrl.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, shortUrl.get().getOriginalUrl())
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Short URL with code '" + shortCode + "' not found.");
        }
    }
}