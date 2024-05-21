package com.moneypay.xprice.service;

import com.moneypay.xprice.enums.ThirdPartyService;

public interface ScraperService {

    String scrape(String url, ThirdPartyService thirdPartyService);
}
