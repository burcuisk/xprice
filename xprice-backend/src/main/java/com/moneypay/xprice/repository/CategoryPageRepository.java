package com.moneypay.xprice.repository;

import com.moneypay.xprice.data.model.CategoryPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryPageRepository extends JpaRepository<CategoryPage, Long> {

    List<CategoryPage> findByProductId(UUID productId);

}
