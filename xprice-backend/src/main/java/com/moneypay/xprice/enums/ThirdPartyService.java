package com.moneypay.xprice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ThirdPartyService {

    TRENDYOL("https://trendyol.com"),
    AMAZON("https://amazon.com"),
    HEPSIBURADA("https://hepsiburada.com");

    private final String url;
}