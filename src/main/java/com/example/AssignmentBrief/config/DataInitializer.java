package com.example.AssignmentBrief.config;

import com.example.AssignmentBrief.model.product;
import com.example.AssignmentBrief.repository.productRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    private static final String GOLD_PRICE_API_URL = "https://www.goldapi.io/api/XAU/USD";
    private static final String API_KEY = "goldapi-1jlsbk17mct6tn2c-io";

    @Bean
    CommandLineRunner runner(productRepository productRepository) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<product>> typeReference = new TypeReference<>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/products.json");

            if (inputStream != null) {
                List<product> products = mapper.readValue(inputStream, typeReference);

                BigDecimal goldPrice = fetchGoldPrice(); // USD per gram
                if (goldPrice == null) {
                    System.err.println("Gold price could not be fetched. Aborting data load.");
                    return;
                }

                for (product p : products) {
                    BigDecimal popularity = p.getPopularityScore().add(BigDecimal.ONE);
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
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-access-token", API_KEY);
            headers.set("Content-Type", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String url = GOLD_PRICE_API_URL;
            record GoldPriceResponse(BigDecimal price) {
            }

            ResponseEntity<GoldPriceResponse> response = restTemplate.exchange(
                    GOLD_PRICE_API_URL,
                    HttpMethod.GET,
                    entity,
                    GoldPriceResponse.class
            );

            return response.getBody() != null ? response.getBody().price() : null;

        } catch (Exception e) {
            System.err.println("Failed to fetch gold price: " + e.getMessage());
            return null;
        }
    }

}
