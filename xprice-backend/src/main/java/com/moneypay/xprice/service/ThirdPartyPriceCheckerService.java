package com.moneypay.xprice.service;

import com.moneypay.xprice.data.model.CategoryPage;
import com.moneypay.xprice.enums.ThirdPartyService;

public interface ThirdPartyPriceCheckerService {

    /**
     * Checks for finding appropriate service in ThirdPartyPriceCheckerFactory
     * @param thirdPartyService The third-party service to check.
     * @return true if the service is supported, false otherwise.
     */
    boolean supports(ThirdPartyService thirdPartyService);

    /**
     * Checks the latest prices for products on the provided category page.
     * @param categoryPage The category page containing the products to check.
     */
    void checkLatestPrices(CategoryPage categoryPage);

}
