package com.moneypay.xprice.configuration;

import com.moneypay.xprice.data.model.Product;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@EnableCaching
@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {

    @Bean
    public RedisTemplate<Long, Product> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Long, Product> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

}