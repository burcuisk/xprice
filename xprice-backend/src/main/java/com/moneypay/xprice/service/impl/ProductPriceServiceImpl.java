package com.moneypay.xprice.service.impl;

import com.moneypay.xprice.data.ProductPrice;
import com.moneypay.xprice.data.model.CategoryPage;
import com.moneypay.xprice.data.model.RedisProduct;
import com.moneypay.xprice.enums.ThirdPartyService;
import com.moneypay.xprice.exception.ProductNotFoundException;
import com.moneypay.xprice.factory.ThirdPartyPriceCheckerFactory;
import com.moneypay.xprice.service.CategoryPagesService;
import com.moneypay.xprice.service.ProductPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductPriceServiceImpl implements ProductPriceService {

    private final CategoryPagesService categoryPagesService;
    private final ThirdPartyPriceCheckerFactory thirdPartyPriceCheckerFactory;
    private final RedisProductService redisProductService;

    @Autowired
    public ProductPriceServiceImpl(CategoryPagesService categoryPagesService, ThirdPartyPriceCheckerFactory thirdPartyPriceCheckerFactory, RedisProductService redisProductService) {
        this.categoryPagesService = categoryPagesService;
        this.thirdPartyPriceCheckerFactory = thirdPartyPriceCheckerFactory;
        this.redisProductService = redisProductService;
    }

    @Override
    public RedisProduct refreshProductPrices(UUID productId) {
        Optional<RedisProduct> existingProduct = redisProductService.findRedisProduct(productId);
        if (existingProduct.isPresent()) {
            redisProductService.deleteById(productId);
        } else {
            throw new ProductNotFoundException("Product with ID " + productId + " not found. Please contact support team.");
        }
        return collectPrices(productId);
    }

    @Override
    public RedisProduct getProductPrices(UUID productId) {
        Optional<RedisProduct> redisProduct = redisProductService.findRedisProduct(productId);
        if (redisProduct.isPresent()) {
            return redisProduct.get();
        } else {
            throw new ProductNotFoundException("Product with ID " + productId + " not found. Please contact support team.");
        }
    }

    @Override
    public boolean checkCollectionRequired(UUID productId) {
        Optional<RedisProduct> redisProduct = redisProductService.findRedisProduct(productId);
        return redisProduct.map(product -> product.getPriceList().isEmpty()).orElse(true);
    }

    @Override
    public RedisProduct collectPrices(UUID productId) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Callable<Void>> tasks = categoryPagesService.findCategoryPages(productId)
                .stream()
                .map(categoryPage -> (Callable<Void>) () -> {
                    ThirdPartyService thirdPartyService = categoryPage.getThirdPartyService();
                    collectPrices(thirdPartyService, categoryPage);
                    return null;
                })
                .collect(Collectors.toList());
        try {
            executor.invokeAll(tasks);
            Optional<RedisProduct> redisProduct = redisProductService.findRedisProduct(productId);
            return redisProduct.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted while collecting prices for product ID: {}", productId, e);
        } finally {
            executor.shutdown();
        }
        return null;
    }

    @Override
    public void collectPrices(ThirdPartyService thirdPartyService, CategoryPage categoryPage) {
        thirdPartyPriceCheckerFactory.getService(thirdPartyService)
                .checkLatestPrices(categoryPage);
    }

}
