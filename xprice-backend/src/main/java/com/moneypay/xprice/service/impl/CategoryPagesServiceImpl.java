package com.moneypay.xprice.service.impl;

import com.moneypay.xprice.data.model.CategoryPage;
import com.moneypay.xprice.repository.CategoryPageRepository;
import com.moneypay.xprice.service.CategoryPagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryPagesServiceImpl implements CategoryPagesService {

    private final CategoryPageRepository categoryPageRepository;

    @Autowired
    public CategoryPagesServiceImpl(CategoryPageRepository categoryPageRepository) {
        this.categoryPageRepository = categoryPageRepository;
    }

    @Override
    public List<CategoryPage> findCategoryPages(UUID productId) {
        return categoryPageRepository.findByProductId(productId);
    }
}
