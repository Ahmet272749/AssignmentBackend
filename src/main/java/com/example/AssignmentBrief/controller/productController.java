package com.example.AssignmentBrief.controller;

import com.example.AssignmentBrief.dto.productDTO;
import com.example.AssignmentBrief.service.productService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
@Validated
public class productController {
    private final productService productService;

    public productController(productService service) {
        this.productService = service;
    }

    @GetMapping("/list")
    public ResponseEntity<List<productDTO>> getAllProducts() {
        List<productDTO> productDTOList = productService.getAllProducts();
        return ResponseEntity.ok(productDTOList);
    }
}
