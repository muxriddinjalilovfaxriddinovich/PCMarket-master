package com.example.pcmarket.service;

import com.example.pcmarket.dto.ApiResponse;
import com.example.pcmarket.dto.DetailDto;
import com.example.pcmarket.dto.DetailOrder;
import com.example.pcmarket.dto.OrderDto;
import com.example.pcmarket.entity.*;
import com.example.pcmarket.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    final OrderRepository orderRepository;
    final UserRepository userRepository;
    final DetailRepository detailRepository;
    final ProductRepository productRepository;
    final InvoiceRepository invoiceRepository;
    public ApiResponse add(OrderDto dto) {
        Optional<User> byId = userRepository.findById(dto.getUserId());
        if (!byId.isPresent()){
            return new ApiResponse("This Customer Not found!",false);
        }
        Order order=new Order();
        order.setUser(byId.get());
        Order save = orderRepository.save(order);
        double totalSum=0;
        for (DetailDto detailDto:dto.getDetailDtoList()){
            Optional<Product> byId1 = productRepository.findById(detailDto.getProductId());
            if (!byId1.isPresent()) {
                continue;
            }
            Product product = byId1.get();
           Detail detail=new Detail();
           detail.setOrder(save);
           detail.setProduct(product);
           detail.setQuantity(detailDto.getQuantity());
           totalSum+=product.getPrice()*detail.getQuantity();
           detailRepository.save(detail);
        }
        Date date = new Date(LocalDate.now().plusDays(3).getLong(ChronoField.DAY_OF_MONTH));
        Invoice invoice = new Invoice();
        invoice.setOrder(save);
        invoice.setAmount(totalSum);
        invoice.setDue(date);
        Invoice invoice1 = invoiceRepository.save(invoice);
        return new ApiResponse("Add order", true, invoice1.getId());
    }

    public ApiResponse edit(Integer id, OrderDto dto) {
        Optional<User> byId = userRepository.findById(dto.getUserId());
        if (!byId.isPresent()) {
            return new ApiResponse("Not found",false);
        }
        Optional<Order> byId1 = orderRepository.findById(id);
        Order order = byId1.get();
        List<Detail> allByOrderId = detailRepository.findAllByOrderId(order.getId());
        detailRepository.deleteAll(allByOrderId);
        Optional<Invoice> byOrderId = invoiceRepository.findByOrderId(order.getId());
        Invoice invoice = byOrderId.get();
        double totalSum=0;
        for (DetailDto detailDto:dto.getDetailDtoList()){
            Optional<Product> byId2 = productRepository.findById(detailDto.getProductId());
            Product product = byId2.get();
            Detail detail=new Detail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity( detailDto.getQuantity());
            totalSum+=product.getPrice()*detail.getQuantity();
        }
        invoice.setAmount(totalSum);
        invoice.setIssue( new java.sql.Date(LocalDate.now().get(ChronoField.EPOCH_DAY)));
        invoice.setDue(new Date(LocalDate.now().plusDays(3).getLong(ChronoField.EPOCH_DAY)));
        Invoice save = invoiceRepository.save(invoice);
        return new ApiResponse("Edit",true,save);
    }

    public ApiResponse delete(Integer id) {
        Optional<Order> byId = orderRepository.findById(id);
        if (!byId.isPresent()) {
           return new ApiResponse("Not found",false);
        }
        Order order = byId.get();
        order.setActive(false);
        Order save = orderRepository.save(order);
        return new ApiResponse("Deleted",true,save);
    }

    public ApiResponse getOrderProduct(Integer id) {

        List<DetailOrder> collect=detailRepository.findAllByOrderId(id).stream().map(this::getDetailOrder).collect(Collectors.toList());
        return new ApiResponse("Mana",true,collect);
    }

    private DetailOrder getDetailOrder(Detail detail) {
           return new DetailOrder(
                   detail.getProduct().getName(),detail.getQuantity()
           );
    }


}
