package com.example.AssignmentBrief.mapper;

import com.example.AssignmentBrief.dto.productDTO;
import com.example.AssignmentBrief.model.product;

public class productMapper {
    public static productDTO toDTO(product product){
        return new productDTO(
                product.getId(),
                product.getName(),
                product.getPopularityScore(),
                product.getWeight(),
                product.getPrice(),
                product.getImages()
        );
    }
}
