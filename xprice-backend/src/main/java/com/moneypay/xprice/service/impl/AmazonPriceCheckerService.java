package com.moneypay.xprice.service.impl;

import com.moneypay.xprice.data.model.CategoryPage;
import com.moneypay.xprice.enums.ThirdPartyService;
import com.moneypay.xprice.service.ThirdPartyPriceCheckerService;
import org.springframework.stereotype.Service;

@Service
public class AmazonPriceCheckerService implements ThirdPartyPriceCheckerService {

    @Override
    public boolean supports(ThirdPartyService thirdPartyService) {
        return ThirdPartyService.AMAZON.equals(thirdPartyService);
    }

    @Override
    public void checkLatestPrices(CategoryPage categoryPage) {

    }
}
