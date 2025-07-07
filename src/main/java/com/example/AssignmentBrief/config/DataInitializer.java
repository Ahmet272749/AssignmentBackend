package com.example.AssignmentBrief.config;

import com.example.AssignmentBrief.model.product;
import com.example.AssignmentBrief.repository.productRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    private static final String GOLD_PRICE_API_URL = "https://api.example.com/gold-price";

    @Bean
    CommandLineRunner runner(productRepository productRepository) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<product>> typeReference = new TypeReference<>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/products.json");

            if (inputStream != null) {
                List<product> products = mapper.readValue(inputStream, typeReference);

                BigDecimal goldPrice = fetchGoldPrice(); // USD per gram
                if (goldPrice == null) {
                    System.err.println("Gold price could not be fetched. Aborting data load.");
                    return;
                }

                for (product p : products) {
                    BigDecimal popularity = BigDecimal.valueOf(p.getPopularityScore() + 1);
                    BigDecimal weight = BigDecimal.valueOf(p.getWeight());
                    BigDecimal price = popularity.multiply(weight).multiply(goldPrice);
                    p.setPrice(price);
                }

                productRepository.saveAll(products);
                System.out.println("Products loaded successfully with calculated prices.");
            } else {
                System.err.println("products.json file not found!");
            }
        };
    }

    private BigDecimal fetchGoldPrice() {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = GOLD_PRICE_API_URL;
            record GoldPriceResponse(BigDecimal price) {}

            GoldPriceResponse response = restTemplate.getForObject(url, GoldPriceResponse.class);
            return response != null ? response.price() : null;

        } catch (Exception e) {
            System.err.println("Failed to fetch gold price: " + e.getMessage());
            return null;
        }
    }
}
