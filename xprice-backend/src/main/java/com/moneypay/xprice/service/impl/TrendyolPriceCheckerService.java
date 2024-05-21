package com.moneypay.xprice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneypay.xprice.data.ProductPrice;
import com.moneypay.xprice.data.model.CategoryPage;
import com.moneypay.xprice.enums.ThirdPartyService;
import com.moneypay.xprice.repository.CategoryPageRepository;
import com.moneypay.xprice.data.thirdparty.trendyol.TrendyolResponse;
import com.moneypay.xprice.repository.redis.RedisProductRepository;
import com.moneypay.xprice.service.ThirdPartyPriceCheckerService;
import com.moneypay.xprice.service.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TrendyolPriceCheckerService implements ThirdPartyPriceCheckerService {

    private final CategoryPageRepository categoryPageRepository;
    private final RestTemplate restTemplate;
    private final ScraperService scraperService;
    private final RedisProductService redisProductService;
    private final RedisProductRepository redisProductRepository;

    @Autowired
    public TrendyolPriceCheckerService(CategoryPageRepository categoryPageRepository, RestTemplate restTemplate,
                                       ScraperService scraperService, RedisProductService redisProductService,
                                       RedisProductRepository redisProductRepository) {
        this.categoryPageRepository = categoryPageRepository;
        this.restTemplate = restTemplate;
        this.scraperService = scraperService;
        this.redisProductService = redisProductService;
        this.redisProductRepository = redisProductRepository;
    }


    @Override
    public boolean supports(ThirdPartyService thirdPartyService) {
        return ThirdPartyService.TRENDYOL.equals(thirdPartyService);
    }

    @Override
    public void checkLatestPrices(CategoryPage categoryPage) {

        String responseBody = scraperService.scrape(categoryPage.getUrl(), ThirdPartyService.TRENDYOL);
        ObjectMapper objectMapper = new ObjectMapper();
        TrendyolResponse trendyolResponse;
        try {
            trendyolResponse = objectMapper.readValue(responseBody, TrendyolResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Error while processing JSON response: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        List<ProductPrice> prices = new ArrayList<>();

        trendyolResponse.getResult().getProducts().stream().forEach(trendyolProduct -> {
            prices.add(ProductPrice.builder()
                    .url(ThirdPartyService.TRENDYOL.getUrl() + trendyolProduct.getUrl())
                    .price(trendyolProduct.getPrice().getSellingPrice())
                    .thirdPartyService(ThirdPartyService.TRENDYOL)
                    .build());
        });

        log.debug("Found {} prices for category page: {}", prices.size(), categoryPage.getUrl());

        if(prices.size() > 0) {
            redisProductService.addPricesToProduct(categoryPage.getProduct().getId(), prices);
            log.info("Added {} prices to {} for service: {}", prices.size(), categoryPage.getProduct().getDisplayName(), ThirdPartyService.TRENDYOL);
        }
    }
}
