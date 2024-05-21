package com.moneypay.xprice.controller;

import com.moneypay.xprice.data.model.RedisProduct;
import com.moneypay.xprice.data.request.RefreshProductPriceRequest;
import com.moneypay.xprice.data.response.ProductPriceListResponse;
import com.moneypay.xprice.exception.ProductNotFoundException;
import com.moneypay.xprice.service.ProductPriceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.UUID;

import static com.moneypay.xprice.util.ProductPriceUtil.getPageableProductList;

@RestController
@RequestMapping("/prices")
@Tag(name = "Price Controller", description = "Operations related to product prices")
public class PriceController {

   @Autowired
   private ProductPriceService productPriceService;

    @GetMapping("/{productId}/{page}")
    @Operation(summary = "Get product prices", description = "Retrieves a paginated list of prices for a given product with default page size(15)")
    public ResponseEntity<ProductPriceListResponse> getProductPrices(@PathVariable UUID productId, @PathVariable int page) throws ProductNotFoundException {
        RedisProduct result;
        if (!productPriceService.checkCollectionRequired(productId)) {
            result = productPriceService.getProductPrices(productId);
        } else {
            result = productPriceService.collectPrices(productId);
        }
        return ResponseEntity.ok(
                ProductPriceListResponse.builder()
                        .totalCount(result.getPriceList().size())
                        .prices(getPageableProductList(page, result.getPriceList()))
                        .page(page)
                        .lastUpdatedTime(result.getLastUpdatedTime())
                        .build()
        );
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh product prices", description = "Refreshes prices for a given product by collecting new prices from websites")
    public ResponseEntity<ProductPriceListResponse> refreshPrices(@RequestBody RefreshProductPriceRequest productPriceRequest,
                                                                  @RequestParam(defaultValue = "0") int page) throws ProductNotFoundException {
        RedisProduct result = productPriceService.refreshProductPrices(productPriceRequest.getProductId());
        return ResponseEntity.ok(
                ProductPriceListResponse.builder()
                        .totalCount(result.getPriceList().size())
                        .prices(getPageableProductList(page, result.getPriceList()))
                        .page(page)
                        .lastUpdatedTime(result.getLastUpdatedTime())
                        .build()
        );
    }
}
