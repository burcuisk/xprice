package com.moneypay.xprice.data.thirdparty.trendyol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrendyolResult {

    private List<TrendyolProduct> products;

}
