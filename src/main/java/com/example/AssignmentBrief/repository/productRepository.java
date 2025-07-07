package com.example.AssignmentBrief.repository;

import com.example.AssignmentBrief.model.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productRepository extends JpaRepository<product,String> {

}
