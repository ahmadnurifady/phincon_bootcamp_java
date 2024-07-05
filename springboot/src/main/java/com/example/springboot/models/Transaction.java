package com.example.springboot.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

    private User user;
    
    private Integer jumlah;

    private Product product;

    private Integer hasil;

    public Transaction(){
        super();
    }

    public Transaction(UUID id, User user, Integer jumlah, Product product, Integer hasil){
        super();
        this.id = id;
        this.user = user;
        this.jumlah = jumlah;
        this.product = product;
        this.hasil = hasil;
    }

    public UUID getId(){
        return id;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Integer getJumlah(){
        return  jumlah;
    }

    public void setJumlah(Integer jumlah){
        this.jumlah = jumlah;
    }

    public Product getProduct(){
        return product;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public Integer getHasil(){
        return hasil;
    }

    public void setHasil(Integer hasil){
        this.hasil = hasil;
    }
}
