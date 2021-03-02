package com.target.myretail.controllers;

import com.target.myretail.models.Price;
import com.target.myretail.models.ProductInfo;
import com.target.myretail.services.ProductService;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class, ProductController.class})
@WebMvcTest(value = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    ProductInfo productInfo = new ProductInfo( 12433232L, "Product1", new Price(12.75, "USD"));

    @Test
    public void getProductDetailWithCurrentPrice() throws Exception {
        Mockito.when(
                productService.getProductInfo(Mockito.anyLong())
        ).thenReturn(productInfo);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/12433232").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":12433232,\"name\":\"Product1\",\"current_price\":{\"value\":12.75,\"currency_code\":\"USD\"}}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getProductDetailWithCurrentPriceWithShortId() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/1243232").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
    }

    @Test
    public void updateCurrentPrice() throws Exception {
        Mockito.when(
                productService.updateCurrentPrice(Mockito.any())
        ).thenReturn(productInfo);
        String requestData = "{\"id\":12433232,\"name\":\"Product1\",\"current_price\":{\"value\":12.75,\"currency_code\":\"USD\"}}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/12433232").content(requestData).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    public void updateCurrentPriceNonMatchingId() throws Exception {
        Mockito.when(
                productService.updateCurrentPrice(Mockito.any())
        ).thenReturn(productInfo);
        String requestData = "{\"id\":1243323,\"name\":\"Product1\",\"current_price\":{\"value\":12.75,\"currency_code\":\"USD\"}}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/12433232").content(requestData).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
    }

    @Test
    public void updateCurrentPriceNoCurrencyCode() throws Exception {
        Mockito.when(
                productService.updateCurrentPrice(Mockito.any())
        ).thenReturn(productInfo);
        String requestData = "{\"id\":12433232,\"name\":\"Product1\",\"current_price\":{\"value\":12.75}}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/12433232").content(requestData).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
    }

}

