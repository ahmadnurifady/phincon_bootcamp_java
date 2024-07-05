package com.example.bootcamp.spring_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bootcamp.spring_rest_api.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("UPDATE Account a SET a.balance = a.balance + ?1 WHERE a.id = ?2")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    public void deposit(float amount, Integer id);

    @Query("UPDATE Account a SET a.balance = a.balance - ?1 WHERE a.id = ?2")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    public void withdraw(float amount, Integer id);
}
