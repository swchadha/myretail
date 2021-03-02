package com.target.myretail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myretail.dao.CurrentPriceRepository;
import com.target.myretail.dao.ProductDetailsRepository;
import com.target.myretail.dto.CurrentPrice;
import com.target.myretail.dto.ProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.text.NumberFormat;
import java.util.stream.Stream;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.target.myretail.dao")
@EntityScan(basePackages = "com.target.myretail.dto")
public class MyretailApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyretailApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() { return new RestTemplate(); }

	@Bean
	public ObjectMapper objectMapper() { return new ObjectMapper(); }

	@Bean
	ApplicationRunner init(CurrentPriceRepository currentPriceRepository, ProductDetailsRepository productDetailsRepository) {

		currentPriceRepository.deleteAll();
		productDetailsRepository.deleteAll();

		String[][] currentPriceData = {
				{"13860428", "104.22", "USD"},
				{"15117729", "101.12", "USD"},
				{"16483589", "34.73", "USD"},
				{"16696652", "234.10", "USD"},
				{"16752456", "42.09", "USD"},
				{"15643793", "122.90", "USD"}
		};

		String[][] productDetailsData = {
				{"13860428", "Tennis Raquet"},
				{"15117729", "Baseball Bat"},
				{"16483589", "Cross Mark Soda"},
				{"16696652", "Orange Juice"},
				{"16752456", "Cranberry"},
				{"15643793", "Sprite"}
		};

		return args -> {
			Stream.of(currentPriceData).forEach(priceObj -> {
				try {
					CurrentPrice currentPrice = new CurrentPrice(
							Long.parseLong(priceObj[0]),
							Double.parseDouble(priceObj[1]),
							priceObj[2]
					);
					currentPriceRepository.save(currentPrice);
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			});
			Stream.of(productDetailsData).forEach(productDetailData -> {
				try {
					ProductDetails productDetails = new ProductDetails(
							Long.parseLong(productDetailData[0]),
							productDetailData[1]
					);
					productDetailsRepository.save(productDetails);
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			});
		};
	}

}
