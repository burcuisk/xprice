package com.moneypay.xprice.service;

import com.moneypay.xprice.data.model.CategoryPage;
import com.moneypay.xprice.data.model.RedisProduct;
import com.moneypay.xprice.enums.ThirdPartyService;

import java.util.Optional;
import java.util.UUID;

public interface ProductPriceService {

    /**
     * Refreshes product prices by deleting existing prices from Redis and collecting new prices.
     *
     * @param productId the ID of the product
     * @return a list of updated product prices
     */
    RedisProduct refreshProductPrices(UUID productId);

    /**
     * Retrieves product prices from Redis.
     * @param productId the ID of the product
     * @return a list of product prices
     */
    RedisProduct getProductPrices(UUID productId);

    /**
     * Checks if the price collection is required for a given product.
     * @param productId the ID of the product
     * @return true if collection is required, false otherwise
     */
    boolean checkCollectionRequired(UUID productId);

    /**
     * Collects prices for a given product by fetching prices from third-party services asynchronously.
     *
     * @param productId the ID of the product
     * @return a list of collected product prices
     */
    Optional<RedisProduct> collectPrices(UUID productId);

    void collectPrices(ThirdPartyService thirdPartyService, CategoryPage categoryPage);
}
