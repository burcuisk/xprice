package com.moneypay.xprice.data.thirdparty.hepsiburada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HepsiburadaProductVariant {

    private HepsiburadaProductVariantListing listing;

    private String url;

}
