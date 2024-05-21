package com.moneypay.xprice.util;

import com.moneypay.xprice.data.ProductPrice;

import java.util.List;

import static com.moneypay.xprice.constant.Constants.PRODUCT_PRICE_PAGE_SIZE;

public class ProductPriceUtil {

    public static List<ProductPrice> getPageableProductList(int page, List<ProductPrice> productPriceList) {
        int start = page * PRODUCT_PRICE_PAGE_SIZE;
        int end = start + PRODUCT_PRICE_PAGE_SIZE - 1;
        return productPriceList.subList(start, Math.min(end, productPriceList.size()));
    }
}
