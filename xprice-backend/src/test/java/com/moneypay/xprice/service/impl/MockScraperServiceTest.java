package com.moneypay.xprice.service.impl;

import com.moneypay.xprice.enums.ThirdPartyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class MockScraperServiceTest {

    private static final String DUMMY_URL = "https://www.xprice.com";

    @InjectMocks
    private MockScraperService mockScraperService;

    @Test
    @DisplayName("Test Trendyol data scraping")
    public void scrapeTrendyolData() throws IOException {
        // PREPARATION
        ThirdPartyService thirdPartyService = ThirdPartyService.TRENDYOL;
        String expectedData = StreamUtils.copyToString(
                new ClassPathResource("trendyol-data.txt").getInputStream(), StandardCharsets.UTF_8);

        // ACTION
        String scrapedData = mockScraperService.scrape(DUMMY_URL, thirdPartyService);

        // VERIFICATION
        assertEquals(expectedData, scrapedData);
    }

    @Test
    @DisplayName("Test Hepsiburada data scraping")
    public void scrapeHepsiburadaData() throws IOException {
        // PREPARATION
        ThirdPartyService thirdPartyService = ThirdPartyService.HEPSIBURADA;
        String expectedData = StreamUtils.copyToString(
                new ClassPathResource("hepsiburada-data.txt").getInputStream(), StandardCharsets.UTF_8);

        // ACTION
        String scrapedData = mockScraperService.scrape(DUMMY_URL, thirdPartyService);

        // VERIFICATION
        assertEquals(expectedData, scrapedData);
    }

}
