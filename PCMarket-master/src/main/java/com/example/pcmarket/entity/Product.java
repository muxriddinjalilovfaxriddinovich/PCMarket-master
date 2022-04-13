package com.example.pcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(length = 50)
    private String description;
    private boolean active;
    private Double price;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Attachment attachment;
}
