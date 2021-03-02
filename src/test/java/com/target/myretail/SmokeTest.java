package com.target.myretail;

import com.target.myretail.controllers.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private ProductController productController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(productController).isNotNull();
    }

}
