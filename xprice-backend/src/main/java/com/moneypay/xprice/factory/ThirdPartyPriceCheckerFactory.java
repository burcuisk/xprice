package com.moneypay.xprice.factory;

import com.moneypay.xprice.enums.ThirdPartyService;
import com.moneypay.xprice.service.ThirdPartyPriceCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ThirdPartyPriceCheckerFactory {

    // Autowire a list of ThirdPartyPriceCheckerService implementations
    @Autowired
    private List<ThirdPartyPriceCheckerService> thirdPartyPriceCheckerServiceList;

    /**
     * This method finds and returns the appropriate implementation of ThirdPartyPriceCheckerService
     * that supports the given ThirdPartyService.
     *
     * @param thirdPartyService the third-party service for which to find the price checker service
     * @return the implementation of ThirdPartyPriceCheckerService that supports the given service
     */
    public ThirdPartyPriceCheckerService getService(ThirdPartyService thirdPartyService) {
        Optional<ThirdPartyPriceCheckerService> service = thirdPartyPriceCheckerServiceList
                .stream()
                .filter(s -> s.supports(thirdPartyService))
                .findFirst();
        return service.orElseThrow(() -> new RuntimeException("No suitable ThirdPartyPriceCheckerService found"));
    }
}
