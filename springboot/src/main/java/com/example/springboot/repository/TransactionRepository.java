package com.example.springboot.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.models.Transaction;

public interface  TransactionRepository extends JpaRepository<Transaction, UUID>{
    
}
