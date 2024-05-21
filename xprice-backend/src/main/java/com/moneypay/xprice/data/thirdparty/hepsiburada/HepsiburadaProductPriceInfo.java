package com.moneypay.xprice.data.thirdparty.hepsiburada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HepsiburadaProductPriceInfo {

    private BigDecimal price;

    private BigDecimal originalPrice;

    private BigDecimal discountRate;

}
