package com.example.pcmarket.controller;

import com.example.pcmarket.dto.ApiResponse;
import com.example.pcmarket.dto.PaymentDto;
import com.example.pcmarket.entity.Payment;
import com.example.pcmarket.repository.PaymentRepository;
import com.example.pcmarket.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    final PaymentRepository paymentRepository;
    final PaymentService paymentService;

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Payment> byId = paymentRepository.findById(id);
        return byId.map(payment -> ResponseEntity.ok().body(payment)).orElseGet(() -> ResponseEntity.ok().body(new Payment()));
    }

    @GetMapping("/list")
    public HttpEntity<?> getList() {
        return ResponseEntity.ok().body(paymentRepository.findAll());
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody PaymentDto dto) {
        ApiResponse apiResponse = paymentService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/details")
    public HttpEntity<?> getDetails(@RequestParam("payment_details_id") Integer id) {
        Optional<Payment> byId = paymentRepository.findById(id);
        return byId.map(payment -> ResponseEntity.ok().body(payment)).orElseGet(() -> ResponseEntity.ok().body(new Payment()));
    }
}
