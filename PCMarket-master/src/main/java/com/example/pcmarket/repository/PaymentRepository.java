package com.example.pcmarket.repository;

import com.example.pcmarket.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
