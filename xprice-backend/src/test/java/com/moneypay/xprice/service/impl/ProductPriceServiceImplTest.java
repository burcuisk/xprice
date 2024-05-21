package com.moneypay.xprice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import com.moneypay.xprice.data.model.CategoryPage;
import com.moneypay.xprice.data.model.RedisProduct;
import com.moneypay.xprice.enums.ThirdPartyService;
import com.moneypay.xprice.factory.ThirdPartyPriceCheckerFactory;
import com.moneypay.xprice.service.CategoryPagesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductPriceServiceImplTest {

    @InjectMocks
    private ProductPriceServiceImpl productPriceService;

    @Mock
    private CategoryPagesService categoryPagesService;

    @Mock
    private ThirdPartyPriceCheckerFactory thirdPartyPriceCheckerFactory;

    @Mock
    private RedisProductService redisProductService;

    @Test
    @DisplayName("Test Collect Prices")
    public void testCollectPrices() {
        // PREPARATION
        UUID productId = UUID.randomUUID();
        CategoryPage categoryPage = CategoryPage.builder()
                .url("testurl")
                .thirdPartyService(ThirdPartyService.TRENDYOL)
                .build();
        RedisProduct redisProductResponse = RedisProduct.builder()
                        .productId(productId)
                        .priceList(new ArrayList<>())
                        .lastUpdatedTime(LocalDateTime.now())
                        .build();

        when(categoryPagesService.findCategoryPages(productId)).thenReturn(Collections.singletonList(categoryPage));
        when(redisProductService.findRedisProduct(productId)).thenReturn(Optional.ofNullable(redisProductResponse));

        // ACTION
        Optional<RedisProduct> redisProduct = productPriceService.collectPrices(productId);

        // VERIFICATION
        verify(categoryPagesService, times(1)).findCategoryPages(productId);
        verify(thirdPartyPriceCheckerFactory, times(1)).getService(ThirdPartyService.TRENDYOL);
        verify(redisProductService).findRedisProduct(productId);
        assertTrue(redisProduct.isPresent());
    }

}
