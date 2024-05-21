package com.moneypay.xprice.repository.redis;

import org.springframework.data.repository.CrudRepository;
import com.moneypay.xprice.data.model.RedisProduct;

import java.util.UUID;

public interface RedisProductRepository extends CrudRepository<RedisProduct, UUID> {


}
