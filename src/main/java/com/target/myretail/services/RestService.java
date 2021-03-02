package com.target.myretail.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myretail.dto.CurrentPrice;
import com.target.myretail.dto.ProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    RestTemplate restTemplate;
    ObjectMapper mapper;

    @Value("${app.redskyurl}")
    String redskyUrl;

    @Value("${app.redskyparams}")
    String redskyParams;

    @Value("${app.priceApiUrl}")
    String priceApiUrl;

    @Value("${app.priceApiUrlSuffix}")
    String priceApiUrlSuffix;

    @Autowired
    public RestService(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    public ProductDetails getProductDetails(Long productId) throws JsonProcessingException {
        String url = redskyUrl + productId.toString() + "?" + redskyParams;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return mapper.readValue(response.getBody(), ProductDetails.class);
        }
        else if(response.getStatusCode().is3xxRedirection()) {
            return new ProductDetails();
        }
        return null;
    }

    public CurrentPrice getCurrentPrice(Long productId) throws JsonProcessingException {
        String url = priceApiUrl + productId.toString() + priceApiUrlSuffix;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return mapper.readValue(response.getBody(), CurrentPrice.class);
        }
        return null;
    }

}
