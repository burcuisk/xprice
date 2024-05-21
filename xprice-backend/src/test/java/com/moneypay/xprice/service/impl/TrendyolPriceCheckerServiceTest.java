package com.moneypay.xprice.service.impl;

import com.moneypay.xprice.data.ProductPrice;
import com.moneypay.xprice.data.model.CategoryPage;
import com.moneypay.xprice.data.model.Product;
import com.moneypay.xprice.enums.ThirdPartyService;
import com.moneypay.xprice.service.ScraperService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrendyolPriceCheckerServiceTest {

    @InjectMocks
    private TrendyolPriceCheckerService trendyolPriceCheckerService;

    @Mock
    private ScraperService scraperService;

    @Mock
    private RedisProductService redisProductService;

    private ArgumentCaptor<List<ProductPrice>> pricesCapture = ArgumentCaptor.forClass(List.class);;

    @Test
    @DisplayName("Supports method should return true for Trendyol service")
    public void testSupports() {
        // PREPARATION
        ThirdPartyService thirdPartyService = ThirdPartyService.TRENDYOL;

        // ACTION
        boolean supports = trendyolPriceCheckerService.supports(thirdPartyService);

        // VERIFICATION
        assertTrue(supports);
    }

    @Test
    @DisplayName("Check latest prices should add prices to product if products exist")
    public void testCheckLatestPrices() {
        // PREPARATION
        CategoryPage categoryPage = CategoryPage.builder()
                .id(UUID.randomUUID())
                .url("http://example.com")
                .product(Product.builder()
                        .id(UUID.randomUUID())
                        .displayName("Test")
                        .build())
                .build();

        String responseBody = "{\"result\":{\"products\":[{\"url\":\"/product/123\",\"price\":{\"sellingPrice\":100.0}}]}}";
        when(scraperService.scrape(anyString(), any(ThirdPartyService.class))).thenReturn(responseBody);

        // ACTION
        trendyolPriceCheckerService.checkLatestPrices(categoryPage);

        // VERIFICATION
        verify(redisProductService).addPricesToProduct(any(), pricesCapture.capture());
        assertEquals(1, pricesCapture.getValue().size());
    }

    @Test
    @DisplayName("Check latest prices FOR TRENDYOL should not add prices to product if no products exist")
    public void testCheckLatestPrices_NoProducts() {
        // PREPARATION
        CategoryPage categoryPage = CategoryPage.builder()
                .id(UUID.randomUUID())
                .url("http://example.com")
                .product(Product.builder()
                        .id(UUID.randomUUID())
                        .displayName("Test")
                        .build())
                .build();        categoryPage.setUrl("http://example.com");

        String responseBody = "{\"result\":{\"products\":[]}}";
        when(scraperService.scrape(anyString(), any(ThirdPartyService.class))).thenReturn(responseBody);

        // ACTION
        trendyolPriceCheckerService.checkLatestPrices(categoryPage);

        // VERIFICATION
        verify(redisProductService, never()).addPricesToProduct(any(), any());
    }

    @Test
    @DisplayName("Check latest prices should throw RuntimeException if JsonProcessingException occurs")
    public void testCheckLatestPrices_JsonProcessingException() {
        // PREPARATION
        CategoryPage categoryPage = CategoryPage.builder()
                .id(UUID.randomUUID())
                .url("http://example.com")
                .product(Product.builder()
                        .id(UUID.randomUUID())
                        .displayName("Test")
                        .build())
                .build();

        // ACTION
        when(scraperService.scrape(any(), any())).thenThrow(RuntimeException.class);

        // VERIFICATION
        assertThrows(RuntimeException.class, () -> trendyolPriceCheckerService.checkLatestPrices(categoryPage));
    }

}
