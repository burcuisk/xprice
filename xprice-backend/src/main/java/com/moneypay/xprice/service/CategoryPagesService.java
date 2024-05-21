package com.moneypay.xprice.service;

import com.moneypay.xprice.data.model.CategoryPage;

import java.util.List;
import java.util.UUID;

public interface CategoryPagesService {

    /**
     * Finds and collects category pages associated with a given product.
     *
     * @param productId the ID of the product
     * @return a list of category pages related to the product
     */
    List<CategoryPage> findCategoryPages(UUID productId);
}
