package com.moneypay.xprice.service.impl;

import com.moneypay.xprice.enums.ThirdPartyService;
import com.moneypay.xprice.service.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * The MockScraperService class implements the ScraperService interface
 * and mimics a third-party API response by reading data from a file.
 */
@Slf4j
@Service
public class MockScraperService implements ScraperService {

    @Override
    public String scrape(String url, ThirdPartyService thirdPartyService) {
        if (thirdPartyService == null) {
            throw new IllegalArgumentException("thirdPartyService cannot be null");
        }
        String file = switch (thirdPartyService) {
            case TRENDYOL -> "trendyol-data.txt";
            case AMAZON -> "amazon-data.txt";
            case HEPSIBURADA -> "hepsiburada-data.txt";
            default -> throw new IllegalArgumentException("third party service mock data not exist: " + thirdPartyService);
        };

        String content = null;
        try {
            content = StreamUtils.copyToString(
                    new ClassPathResource(file).getInputStream(), StandardCharsets.UTF_8);
            log.info("Scraped data from file for {}: {}", thirdPartyService, file);
        } catch (IOException e) {
            log.error("Failed to read file for {}", thirdPartyService, e);
            throw new RuntimeException(e);
        }
        return content;
    }
}
