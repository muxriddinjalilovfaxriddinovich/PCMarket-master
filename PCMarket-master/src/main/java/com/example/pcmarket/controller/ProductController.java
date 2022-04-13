package com.example.pcmarket.controller;

import com.example.pcmarket.dto.ApiResponse;
import com.example.pcmarket.dto.ProductDto;
import com.example.pcmarket.entity.Product;
import com.example.pcmarket.repository.DetailRepository;
import com.example.pcmarket.repository.ProductRepository;
import com.example.pcmarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    final ProductRepository productRepository;
    final ProductService productService;
    final DetailRepository detailRepository;

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Product> byId = productRepository.findById(id);
        return byId.map(product -> ResponseEntity.ok().body(product)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Product()));
    }

    @GetMapping("/list")
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(productRepository.findAll());
    }

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody ProductDto dto) {
        ApiResponse apiResponse = productService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/details")
    public HttpEntity<?> getProduct(@RequestParam("product_id") Integer id) {
        return ResponseEntity.ok().body(detailRepository.findAllByProduct_Id(id));
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody ProductDto dto) {
        ApiResponse apiResponse = productService.edit(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = productService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 404).body(apiResponse);
    }
}
