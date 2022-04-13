package com.example.pcmarket.repository;

import com.example.pcmarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByFullNameIgnoreCase(String name);
}
