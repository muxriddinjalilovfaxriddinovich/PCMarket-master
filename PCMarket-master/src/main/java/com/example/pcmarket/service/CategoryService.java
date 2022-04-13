package com.example.pcmarket.service;

import com.example.pcmarket.dto.ApiResponse;
import com.example.pcmarket.entity.Category;
import com.example.pcmarket.entity.Product;
import com.example.pcmarket.repository.CategoryRepository;
import com.example.pcmarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    final CategoryRepository categoryRepository;
    final ProductRepository productRepository;

    public ApiResponse add(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            return new ApiResponse("Xatolik", false);
        }
        Category save = categoryRepository.save(category);
        return new ApiResponse("Added", true, save);
    }

    public ApiResponse edit(Integer id, Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            return new ApiResponse("Bunday nom bor", false);
        }
        Category byId = categoryRepository.getById(id);
        byId.setName(category.getName());
        Category save = categoryRepository.save(byId);
        return new ApiResponse("Edit", true, save);
    }

    public ApiResponse getCategoryProduct(Integer id) {
        Optional<Product> byId = productRepository.findById(id);
        return byId.map(product -> new ApiResponse("Mana", true, product.getCategory())).orElseGet(() -> new ApiResponse("Not found", false));
    }

    public ApiResponse delete(Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        Category category = byId.get();
        category.setActive(false);
        categoryRepository.save(category);
        return new ApiResponse("deleted", true);
    }
}
