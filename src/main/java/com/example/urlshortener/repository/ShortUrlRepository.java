package com.example.urlshortener.repository;

import com.example.urlshortener.entity.ShortUrl;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface ShortUrlRepository extends CrudRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByShortCode(String shortCode);
}