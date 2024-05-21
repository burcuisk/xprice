package com.moneypay.xprice.data.thirdparty.trendyol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrendyolProduct {

    private String name;

    private String url;

    private TrendyolPrice price;

}
