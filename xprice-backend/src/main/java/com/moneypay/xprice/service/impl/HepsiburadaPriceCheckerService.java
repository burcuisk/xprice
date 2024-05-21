package com.moneypay.xprice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneypay.xprice.data.ProductPrice;
import com.moneypay.xprice.data.model.CategoryPage;
import com.moneypay.xprice.data.thirdparty.hepsiburada.HepsiburadaResponse;
import com.moneypay.xprice.enums.ThirdPartyService;
import com.moneypay.xprice.repository.CategoryPageRepository;
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
public class HepsiburadaPriceCheckerService implements ThirdPartyPriceCheckerService {

    private final CategoryPageRepository categoryPageRepository;
    private final RestTemplate restTemplate;
    private final ScraperService scraperService;
    private final RedisProductService redisProductService;

    @Autowired
    public HepsiburadaPriceCheckerService(CategoryPageRepository categoryPageRepository, RestTemplate restTemplate,
                                          ScraperService scraperService, RedisProductService redisProductService) {
        this.categoryPageRepository = categoryPageRepository;
        this.restTemplate = restTemplate;
        this.scraperService = scraperService;
        this.redisProductService = redisProductService;
    }

    @Override
    public boolean supports(ThirdPartyService thirdPartyService) {
        return ThirdPartyService.HEPSIBURADA.equals(thirdPartyService);
    }

    @Override
    public void checkLatestPrices(CategoryPage categoryPage) {

        String responseBody = scraperService.scrape(categoryPage.getUrl(), ThirdPartyService.HEPSIBURADA);

        ObjectMapper objectMapper = new ObjectMapper();
        HepsiburadaResponse hepsiburadaResponse;
        try {
            hepsiburadaResponse = objectMapper.readValue(responseBody, HepsiburadaResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Error while processing JSON response: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        List<ProductPrice> prices = new ArrayList<>();

        hepsiburadaResponse.getProducts().stream().forEach(hepsiburadaProduct -> {
                    prices.add(ProductPrice.builder()
                            .url(ThirdPartyService.HEPSIBURADA.getUrl() + hepsiburadaProduct.getVariantList().get(0).getUrl())
                            .price(hepsiburadaProduct.getVariantList().get(0).getListing().getPriceInfo().getPrice())
                            .thirdPartyService(ThirdPartyService.HEPSIBURADA)
                            .build());
        });

        log.debug("Found {} prices for category page: {}", prices.size(), categoryPage.getUrl());

        if(prices.size() > 0) {
            redisProductService.addPricesToProduct(categoryPage.getProduct().getId(), prices);
            log.info("Added {} prices to {} for service: {}", prices.size(), categoryPage.getProduct().getDisplayName(), ThirdPartyService.HEPSIBURADA);
        }
    }
}
