package com.example.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.models.Transaction;
import com.example.springboot.repository.TransactionRepository;

@RestController
@RequestMapping(path = "transaction")
public class TransactionController {
    
    @Autowired
    TransactionRepository transactionRepository;

    @PostMapping(path = "/post")
    public Transaction addTransaction(){
        Transaction n = new Transaction();
        
        return n;
    } 
}
