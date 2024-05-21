package com.moneypay.xprice.data.thirdparty.trendyol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrendyolPrice {

    private BigDecimal sellingPrice;

    private BigDecimal originalPrice;

    private BigDecimal discountedPrice;
}
