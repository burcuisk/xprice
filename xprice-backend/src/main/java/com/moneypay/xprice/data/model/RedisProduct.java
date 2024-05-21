package com.moneypay.xprice.data.model;

import com.moneypay.xprice.data.ProductPrice;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RedisHash(value = "RedisProduct", timeToLive = 10800)
@Data
@Builder
public class RedisProduct {

    @Id
    private UUID productId;

    private List<ProductPrice> priceList;

    @Builder.Default
    private LocalDateTime lastUpdatedTime = LocalDateTime.now();

}
