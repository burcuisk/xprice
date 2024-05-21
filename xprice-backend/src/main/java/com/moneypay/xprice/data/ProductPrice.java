package com.moneypay.xprice.data;

import com.moneypay.xprice.enums.ThirdPartyService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class ProductPrice {

    private String url;
    private ThirdPartyService thirdPartyService;
    private BigDecimal price;
}
