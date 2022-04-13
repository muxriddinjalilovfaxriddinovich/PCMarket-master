package com.example.pcmarket.repository;

import com.example.pcmarket.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Integer> {


}
