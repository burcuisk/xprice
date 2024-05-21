package com.moneypay.xprice.controller;

import com.moneypay.xprice.data.model.Product;
import com.moneypay.xprice.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Price Controller", description = "Operations related to products")
public class ProductController {

   @Autowired
   private ProductService productService;

    @GetMapping()
    @Operation(summary = "Get all products", description = "Retrieves a list of all products with ids")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

}
