package com.moneypay.xprice.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Xprice Rest Api", version = "1.0.0", description = "X-price Documentation"))
public class OpenAPIConfig {
}
