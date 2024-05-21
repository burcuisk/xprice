package com.moneypay.xprice.data.thirdparty.trendyol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrendyolResponse {

    private boolean isSuccess;

    private TrendyolResult result;

}
