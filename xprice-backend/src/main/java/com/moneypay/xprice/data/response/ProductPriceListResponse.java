package com.moneypay.xprice.data.response;

import com.moneypay.xprice.data.ProductPrice;

import java.time.LocalDateTime;
import java.util.List;

public record ProductPriceListResponse(
        LocalDateTime lastUpdatedTime,
        int totalCount,
        List<ProductPrice> prices,
        int page
) {}
