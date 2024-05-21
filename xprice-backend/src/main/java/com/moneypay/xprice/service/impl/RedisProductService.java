package com.moneypay.xprice.service.impl;

import com.moneypay.xprice.data.ProductPrice;
import com.moneypay.xprice.data.model.RedisProduct;
import com.moneypay.xprice.repository.redis.RedisProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class RedisProductService {

    private final RedisProductRepository redisProductRepository;

    @Autowired
    public RedisProductService(RedisProductRepository redisProductRepository) {
        this.redisProductRepository = redisProductRepository;
    }

    public void deleteById(UUID productId) {
        redisProductRepository.deleteById(productId);
    }

    public Optional<RedisProduct> findRedisProduct(UUID productId) {
        Optional<RedisProduct> product = redisProductRepository.findById(productId);
        if (product.isPresent()) {
            log.info("Found RedisProduct with ID: {}", productId);
        } else {
            log.warn("RedisProduct with ID {} not found", productId);
        }
        return product;
    }

    public synchronized void addPricesToProduct(UUID productId, List<ProductPrice> newPrices) {
        Optional<RedisProduct> optionalProduct = redisProductRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            RedisProduct redisProduct = optionalProduct.get();
            List<ProductPrice> priceList = redisProduct.getPriceList();
            priceList.addAll(newPrices);

            Collections.sort(priceList, Comparator.comparing(ProductPrice::getPrice));

            redisProduct.setPriceList(priceList);
            redisProductRepository.save(redisProduct);
            log.info("Added prices to RedisProduct with ID: {}", productId);
        } else {
            RedisProduct redisProduct = RedisProduct.builder()
                    .productId(productId)
                    .priceList(newPrices)
                    .build();
            redisProductRepository.save(redisProduct);
            log.info("Created new RedisProduct with ID: {}", productId);
        }
    }
}
