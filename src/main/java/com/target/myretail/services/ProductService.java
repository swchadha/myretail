package com.target.myretail.services;

import com.target.myretail.models.ProductInfo;

public interface ProductService {

    ProductInfo getProductInfo(Long productId);

    ProductInfo updateCurrentPrice(ProductInfo productInfo);

}
