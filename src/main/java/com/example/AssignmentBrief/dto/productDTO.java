package com.example.AssignmentBrief.dto;

import java.math.BigDecimal;
import java.util.Map;

public record productDTO (
        String id,
        String name,
        BigDecimal popularityScore,
        int weight,
        BigDecimal price,
        Map<String, String> images
){
}
