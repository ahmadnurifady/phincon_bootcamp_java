package com.example.bootcamp.spring_rest_api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bootcamp.spring_rest_api.model.Account;
import com.example.bootcamp.spring_rest_api.model.Amount;
import com.example.bootcamp.spring_rest_api.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping
    public List<Account> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<Account> getOne(@PathVariable("id") Integer id) {
        try {
            Account account = service.get(id);

            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public HttpEntity<Account> add(@RequestBody Account account) {
        Account savedAccount = service.save(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public HttpEntity<Account> replace(@PathVariable("id") Integer id, @RequestBody Account account) {
        account.setId(id);
        Account updatedAccount = service.save(account);

        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deposits")
    public HttpEntity<Account> deposit(@PathVariable("id") Integer id, @RequestBody Amount amount) {
        Account updatedAccount = service.deposit(amount.getAmount(), id);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PatchMapping("/{id}/withdrawal")
    public HttpEntity<Account> withdraw(@PathVariable("id") Integer id, @RequestBody Amount amount) {
        Account updatedAccount = service.withdraw(amount.getAmount(), id);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
