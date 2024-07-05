package com.example.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByPrice(double price, Pageable pageable);
    Page<Product> findAllProducts(Pageable pageable);

}
