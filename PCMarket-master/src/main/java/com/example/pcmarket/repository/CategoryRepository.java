package com.example.pcmarket.repository;

import com.example.pcmarket.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer>{

    boolean existsByNameIgnoreCase(String name);
}
