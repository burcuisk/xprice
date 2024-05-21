package com.moneypay.xprice.data.thirdparty.hepsiburada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HepsiburadaResponse {

    private List<HepsiburadaProduct> products;
}
