package com.target.myretail.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.target.myretail.dao.CurrentPriceRepository;
import com.target.myretail.dao.ProductDetailsRepository;
import com.target.myretail.dto.CurrentPrice;
import com.target.myretail.dto.ProductDetails;
import com.target.myretail.models.Price;
import com.target.myretail.models.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private RestService restService;
    private CurrentPriceRepository currentPriceRepository;
    private ProductDetailsRepository productDetailsRepository;


    @Autowired
    public ProductServiceImpl(RestService restService, CurrentPriceRepository currentPriceRepository, ProductDetailsRepository productDetailsRepository) {
        this.restService = restService;
        this.currentPriceRepository = currentPriceRepository;
        this.productDetailsRepository = productDetailsRepository;
    }

    @Override
    public ProductInfo getProductInfo(Long productId) {
        ProductDetails productDetails;
        CurrentPrice currentPrice;
        try {
            productDetails = restService.getProductDetails(productId);
        } catch (JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product ID not found");
        }
        if(productDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product ID not found");
        } else if(productDetails.getId() == null) {
            /**
             *  Backup code to read product data from DB as Redsky URL provided is not working
             */
            Optional<ProductDetails> productDetail = productDetailsRepository.findById(productId);
            if(productDetail.isPresent()){
                productDetails = productDetail.get();
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product ID not found");
            }
        }

        try {
            currentPrice = restService.getCurrentPrice(productId);
        } catch (Exception ex) {
            currentPrice = getPriceFromDataStore(productId);
        }
        if(currentPrice == null) {
            currentPrice = getPriceFromDataStore(productId);
        }
        Price price = currentPrice.getCurrencyCode() != null ? new Price(currentPrice.getValue(), currentPrice.getCurrencyCode()) : new Price();
        return new ProductInfo(productId, productDetails.getName(), price);
    }

    @Override
    public ProductInfo updateCurrentPrice(ProductInfo productInfo) {
        CurrentPrice currentPrice = new CurrentPrice(productInfo.getId(), productInfo.getCurrentPrice().getValue(), productInfo.getCurrentPrice().getCurrencyCode());
        currentPriceRepository.save(currentPrice);
        return productInfo;
    }

    private CurrentPrice getPriceFromDataStore(Long productId) {
        /**
         *  Backup code to read current price from DB as price API does not exist
         */
        Optional<CurrentPrice> currentPrice = currentPriceRepository.findById(productId);
        return currentPrice.isPresent() ? currentPrice.get() : new CurrentPrice();
    }
}
