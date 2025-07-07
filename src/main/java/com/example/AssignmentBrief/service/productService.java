package com.example.AssignmentBrief.service;

import com.example.AssignmentBrief.dto.productDTO;
import com.example.AssignmentBrief.mapper.productMapper;
import com.example.AssignmentBrief.model.product;
import com.example.AssignmentBrief.repository.productRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class productService {
    private final productRepository  productRepository;

    public productService(com.example.AssignmentBrief.repository.productRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<productDTO> getAllProducts(){
        List<product> products = productRepository.findAll();

        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }
}
