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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HepsiburadaPriceCheckerServiceTest {

    @InjectMocks
    HepsiburadaPriceCheckerService hepsiburadaPriceCheckerService;

    @Mock
    private ScraperService scraperService;

    @Mock
    private RedisProductService redisProductService;

    private ArgumentCaptor<List<ProductPrice>> pricesCapture = ArgumentCaptor.forClass(List.class);;

    @Test
    @DisplayName("Supports method should return true for Hepsiburada service")
    public void testSupports() {
        // PREPARATION
        ThirdPartyService thirdPartyService = ThirdPartyService.HEPSIBURADA;

        // ACTION
        boolean supports = hepsiburadaPriceCheckerService.supports(thirdPartyService);

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

        String responseBody = "{\n" +
                "  \"products\": [\n" +
                "    {\n" +
                "      \"productId\": \"HBC00002GNKO0\",\n" +
                "      \"brand\": \"Apple\",\n" +
                "      \"customerReviewCount\": 133,\n" +
                "      \"customerReviewScore\": 5,\n" +
                "      \"customerReviewRating\": 4.9,\n" +
                "      \"boostingFactors\": [],\n" +
                "      \"variantList\": [\n" +
                "        {\n" +
                "          \"sku\": \"HBCV00002GNKO1\",\n" +
                "          \"name\": \"Apple MacBook Air M2 Çip 8GB 256GB SSD macOS 13\\\" Taşınabilir Bilgisayar Yıldız Işığı MLY13TU/A\",\n" +
                "          \"url\": \"/apple-macbook-air-m2-cip-8gb-256gb-ssd-macos-13-tasinabilir-bilgisayar-yildiz-isigi-mly13tu-a-pm-HBC00002GNKO0\",\n" +
                "          \"isDefault\": true,\n" +
                "          \"procurable\": true,\n" +
                "          \"adInfo\": \"\",\n" +
                "          \"adShowId\": \"\",\n" +
                "          \"listing\": {\n" +
                "            \"priceInfo\": {\n" +
                "              \"price\": 35965.02,\n" +
                "              \"originalPrice\": 35965.02,\n" +
                "              \"discountRate\": 0,\n" +
                "              \"discountType\": \"NoDiscount\"\n" +
                "            },\n" +
                "            \"isPreOrder\": false,\n" +
                "            \"inStockDate\": null,\n" +
                "            \"merchantName\": \"BTH E-ticaret\",\n" +
                "            \"merchantId\": \"90f573a3-248c-4935-bc6e-b738464164c4\",\n" +
                "            \"listingId\": \"85e354e0-bd98-4c30-ba04-bc939bbfdc4d\",\n" +
                "            \"tagList\": null,\n" +
                "            \"labels\": [\n" +
                "              {\n" +
                "                \"text\": \"Kartsız 12 taksit\",\n" +
                "                \"color\": \"F2E8FF\",\n" +
                "                \"textColor\": \"7723DB\",\n" +
                "                \"endDate\": \"0001-01-01T00:00:00Z\",\n" +
                "                \"order\": 2\n" +
                "              },\n" +
                "              {\n" +
                "                \"text\": \"Kuponlu Ürün\",\n" +
                "                \"color\": \"FF6000\",\n" +
                "                \"textColor\": \"FFFFFF\",\n" +
                "                \"endDate\": \"0001-01-01T00:00:00Z\",\n" +
                "                \"order\": 1\n" +
                "              }\n" +
                "            ],\n" +
                "            \"categorizedLabels\": {\n" +
                "              \"incentiveToBuy\": [\n" +
                "                {\n" +
                "                  \"tagName\": \"27907614-200-tl-ye-10-tl-indirim\",\n" +
                "                  \"textColor\": \"FFFFFF\",\n" +
                "                  \"imageUrl\": \"https://images.hepsiburada.net/banners/s/0/104-104/kuponlu_104x104133117024665936870.png\",\n" +
                "                  \"badgeImageUrl\": \"https://images.hepsiburada.net/banners/s/0/196-157/kupnlu_urun133428851993891315.png\",\n" +
                "                  \"order\": 102,\n" +
                "                  \"type\": \"incentiveToBuy\",\n" +
                "                  \"typeGroup\": \"\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"valueAddedService\": [\n" +
                "                {\n" +
                "                  \"text\": \"Kartsız 12 taksit\",\n" +
                "                  \"color\": \"F2E8FF\",\n" +
                "                  \"textColor\": \"7723DB\",\n" +
                "                  \"tagName\": \"kartsiz-taksit-alisveris-kredisi-12\",\n" +
                "                  \"order\": 1,\n" +
                "                  \"type\": \"valueAddedService\",\n" +
                "                  \"typeGroup\": \"LoanOffer\"\n" +
                "                },\n" +
                "                {\n" +
                "                  \"text\": \"Yarın Kapında\",\n" +
                "                  \"color\": \"FF6000\",\n" +
                "                  \"textColor\": \"FFFFFF\",\n" +
                "                  \"tagName\": \"nextday-delivery\",\n" +
                "                  \"order\": 4,\n" +
                "                  \"type\": \"valueAddedService\",\n" +
                "                  \"typeGroup\": \"\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"campaign\": null\n" +
                "            },\n" +
                "            \"jetDeliveryCities\": null,\n" +
                "            \"jetDelivery\": false,\n" +
                "            \"expressDeliveryWarehouses\": null,\n" +
                "            \"expressDelivery\": false,\n" +
                "            \"cityBasedCutOffEnabled\": false,\n" +
                "            \"campaignText\": \"\"\n" +
                "          },\n" +
                "          \"images\": [\n" +
                "            {\n" +
                "              \"link\": \"https://productimages.hepsiburada.net/s/237/{size}/110000220868843.jpg\",\n" +
                "              \"isDefault\": true,\n" +
                "              \"height\": 712,\n" +
                "              \"width\": 1174\n" +
                "            },\n" +
                "            {\n" +
                "              \"link\": \"https://productimages.hepsiburada.net/s/237/{size}/110000220868844.jpg\",\n" +
                "              \"isDefault\": false,\n" +
                "              \"height\": 901,\n" +
                "              \"width\": 944\n" +
                "            },\n" +
                "            {\n" +
                "              \"link\": \"https://productimages.hepsiburada.net/s/237/{size}/110000220868845.jpg\",\n" +
                "              \"isDefault\": false,\n" +
                "              \"height\": 850,\n" +
                "              \"width\": 888\n" +
                "            },\n" +
                "            {\n" +
                "              \"link\": \"https://productimages.hepsiburada.net/s/237/{size}/110000220868846.jpg\",\n" +
                "              \"isDefault\": false,\n" +
                "              \"height\": 1194,\n" +
                "              \"width\": 962\n" +
                "            }\n" +
                "          ],\n" +
                "          \"properties\": {\n" +
                "            \"Renk\": {\n" +
                "              \"displayValue\": \"Belirtilmemiş\",\n" +
                "              \"value\": \"Belirtilmemiş\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"energyClassInfo\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"displayOptions\": {\n" +
                "        \"isAdultProduct\": false,\n" +
                "        \"hasColorVariant\": false\n" +
                "      },\n" +
                "      \"hasVariant\": false,\n" +
                "      \"userContentInfo\": null,\n" +
                "      \"isSemantic\": false,\n" +
                "      \"mainCategory\": {\n" +
                "        \"id\": 98,\n" +
                "        \"name\": \"Dizüstü Bilgisayar Laptop\"\n" +
                "      },\n" +
                "      \"cardSize\": \"L\",\n" +
                "      \"al_p\": \"dyYTLXlF/cl3acvZMMp+Ug==\",\n" +
                "      \"isSplitted\": false\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        when(scraperService.scrape(anyString(), any(ThirdPartyService.class))).thenReturn(responseBody);

        // ACTION
        hepsiburadaPriceCheckerService.checkLatestPrices(categoryPage);

        // VERIFICATION
        verify(redisProductService).addPricesToProduct(any(), pricesCapture.capture());
        assertEquals(1, pricesCapture.getValue().size());
    }

    @Test
    @DisplayName("Check latest prices for HEPSIBURADA should not add prices to product if no products exist")
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

        String responseBody = "{\n" +
                "  \"products\": []\n" +
                "}\n";
        when(scraperService.scrape(anyString(), any(ThirdPartyService.class))).thenReturn(responseBody);

        // ACTION
        hepsiburadaPriceCheckerService.checkLatestPrices(categoryPage);

        // VERIFICATION
        verify(redisProductService, never()).addPricesToProduct(any(), any());
    }
}