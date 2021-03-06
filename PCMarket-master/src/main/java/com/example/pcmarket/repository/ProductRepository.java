package com.example.pcmarket.repository;

import com.example.pcmarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    boolean existsByNameIgnoreCase(String name);
}
