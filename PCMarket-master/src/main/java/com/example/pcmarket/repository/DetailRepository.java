package com.example.pcmarket.repository;

import com.example.pcmarket.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DetailRepository extends JpaRepository<Detail, Integer> {

   List<Detail> findAllByProduct_Id(Integer id);

   List<Detail> findAllByOrderId(Integer id);
}
