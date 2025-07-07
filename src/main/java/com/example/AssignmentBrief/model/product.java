package com.example.AssignmentBrief.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "products")
public class product {
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal popularityScore;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "color")
    @Column(name = "images")
    private Map<String, String> images = new HashMap<>();

    public product(String id, BigDecimal price,Map<String, String> images, int weight, BigDecimal popularityScore, String name) {
        this.id = id;
        this.price = price;
        this.images = images;
        this.weight = weight;
        this.popularityScore = popularityScore;
        this.name = name;
    }

    public product() {

    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }

    public BigDecimal getPopularityScore() {
        return popularityScore;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Map<String, String> getImages() {
        return images;
    }
}
