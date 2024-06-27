package com.example.urlshortener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.urlshortener.controller.UrlShortenerController;
import com.example.urlshortener.entity.ShortUrl;
import com.example.urlshortener.service.UrlShortenerService;

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(UrlShortenerController.class)
class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService urlShortenerService;

    @Test
    void shouldRedirectToOriginalUrl() throws Exception {
        String shortCode = "abc123";
        String originalUrl = "https://www.example.com/very/long/url/that/needs/to/be/shortened";

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortCode(shortCode);
        shortUrl.setOriginalUrl(originalUrl);
        when(urlShortenerService.getShortUrlByCode(shortCode)).thenReturn(Optional.of(shortUrl));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/short-urls/{shortCode}", shortCode))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.header().string("Location", originalUrl));
    }

    @Test
    void shouldReturn404WhenShortUrlNotFound() throws Exception {
        String shortCode = "abc123";
        when(urlShortenerService.getShortUrlByCode(shortCode)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/short-urls/{shortCode}", shortCode))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldCreateShortUrl() throws Exception {
        String originalUrl = "https://www.example.com/very/long/url/that/needs/to/be/shortened";
        String shortCode = "abc123";

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortCode(shortCode);
        shortUrl.setOriginalUrl(originalUrl);
        when(urlShortenerService.createShortUrl(originalUrl)).thenReturn(shortUrl);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/short-urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"originalUrl\":\"" + originalUrl + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortCode").value(shortCode))
                .andExpect(MockMvcResultMatchers.jsonPath("$.originalUrl").value(originalUrl));
    }
}