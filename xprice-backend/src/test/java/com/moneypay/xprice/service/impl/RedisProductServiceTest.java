package com.moneypay.xprice.service.impl;

import com.moneypay.xprice.data.ProductPrice;
import com.moneypay.xprice.data.model.RedisProduct;
import com.moneypay.xprice.enums.ThirdPartyService;
import com.moneypay.xprice.repository.redis.RedisProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RedisProductServiceTest {

    @InjectMocks
    private RedisProductService redisProductService;

    @Mock
    private RedisProductRepository redisProductRepository;

    @Test
    @DisplayName("Test Add Prices to Existing Product")
    public void testAddPricesToProduct_existingProduct() {
        // PREPARATION
        UUID productId = UUID.randomUUID();
        List<ProductPrice> prices = new ArrayList<>();
        prices.add(ProductPrice.builder()
                .price(BigDecimal.valueOf(100))
                .thirdPartyService(ThirdPartyService.TRENDYOL)
                .url("test.com")
                .build());
        prices.add(ProductPrice.builder()
                .price(BigDecimal.valueOf(200))
                .thirdPartyService(ThirdPartyService.TRENDYOL)
                .url("test.com")
                .build());

        RedisProduct existingProduct = RedisProduct.builder()
                        .productId(productId)
                        .priceList(prices)
                        .build();

        when(redisProductRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        List<ProductPrice> newPrices = new ArrayList<>();
        newPrices.add(ProductPrice.builder()
                .price(BigDecimal.valueOf(55))
                .thirdPartyService(ThirdPartyService.TRENDYOL)
                .url("test.com")
                .build());

        // ACTION
        redisProductService.addPricesToProduct(productId, newPrices);

        // VERIFICATION
        assertEquals(3, existingProduct.getPriceList().size());
        assertEquals(BigDecimal.valueOf(55), existingProduct.getPriceList().get(0).getPrice());
        assertEquals(BigDecimal.valueOf(200), existingProduct.getPriceList().get(2).getPrice());
        verify(redisProductRepository, times(1)).findById(productId);
        verify(redisProductRepository, times(1)).save(existingProduct);
    }

   @Test
   @DisplayName("Test Add Prices to New Product")
   public void testAddPricesToProduct_newProduct() {
       // PREPARATION
       UUID productId = UUID.randomUUID();

        when(redisProductRepository.findById(productId)).thenReturn(Optional.empty());

        List<ProductPrice> newPrices = new ArrayList<>();
        newPrices.add(ProductPrice.builder()
                .price(BigDecimal.valueOf(20))
                .thirdPartyService(ThirdPartyService.TRENDYOL)
                .url("test.com")
                .build());

       // ACTION
       redisProductService.addPricesToProduct(productId, newPrices);

       // VERIFICATION
       verify(redisProductRepository, times(1)).findById(productId);
       verify(redisProductRepository, times(1)).save(any(RedisProduct.class));
    }

}