package com.example.urlshortener.service;

import org.springframework.stereotype.Service;
import com.example.urlshortener.entity.ShortUrl;
import com.example.urlshortener.repository.ShortUrlRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {
    private final ShortUrlRepository shortUrlRepository;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 6;

    public ShortUrl createShortUrl(String originalUrl) {
        // Generate a short code
        String shortCode = generateShortCode();

        // Create a new ShortUrl entity
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortCode(shortCode);
        shortUrl.setOriginalUrl(originalUrl);

        // Save the ShortUrl entity to the in-memory database
        return shortUrlRepository.save(shortUrl);
    }

    public Optional<ShortUrl> getShortUrlByCode(String shortCode) {
        return shortUrlRepository.findByShortCode(shortCode);
    }

    private String generateShortCode() {
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            int randomIndex = (int) (Math.random() * CHARACTERS.length());
            shortCode.append(CHARACTERS.charAt(randomIndex));
        }
        return shortCode.toString();
    }
}