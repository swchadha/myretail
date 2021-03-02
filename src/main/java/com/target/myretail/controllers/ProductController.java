package com.target.myretail.controllers;

import com.target.myretail.models.ProductInfo;
import com.target.myretail.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductInfo> getProductInfo(@PathVariable Long id) {
        if(String.valueOf(id).length() != 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID should be 8 digit number");
        }
        return new ResponseEntity<>(productService.getProductInfo(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductInfo> setProductPrice(@RequestBody ProductInfo productInfo, @PathVariable Long id) {
        if(String.valueOf(id).length() != 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID should be 8 digit number");
        }
        if(id.longValue() != productInfo.getId().longValue() || productInfo.getCurrentPrice() == null || productInfo.getCurrentPrice().getCurrencyCode() == null || productInfo.getCurrentPrice().getCurrencyCode().isEmpty() || productInfo.getCurrentPrice().getValue() == null || productInfo.getCurrentPrice().getValue().doubleValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Data");
        }
        return new ResponseEntity<>(productService.updateCurrentPrice(productInfo), HttpStatus.OK);
    }


}
