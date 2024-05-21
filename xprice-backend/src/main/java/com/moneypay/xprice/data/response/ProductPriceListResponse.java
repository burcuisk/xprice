package com.moneypay.xprice.data.response;

import com.moneypay.xprice.data.ProductPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ProductPriceListResponse {

    private LocalDateTime lastUpdatedTime;
    private int totalCount;
    private List<ProductPrice> prices;
    private int page;
}
