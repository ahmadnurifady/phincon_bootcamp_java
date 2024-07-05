package com.example.phincon_jdbc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.phincon_jdbc.model.AccounWithBank;
import com.example.phincon_jdbc.model.Account;
import com.example.phincon_jdbc.model.Amount;
import com.example.phincon_jdbc.model.BucketAccount;
import com.example.phincon_jdbc.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService service;

    @GetMapping("/join")
    public HttpEntity<List<BucketAccount>> listAllJoin(){
        return new ResponseEntity<>(service.findAllJoin(), HttpStatus.OK);
    }

    @GetMapping("/AccountWithBank")
    public HttpEntity<List<AccounWithBank>> findAllAccountWithBankController(){
        return new ResponseEntity<>(service.findAllAccountWithBankService(), HttpStatus.OK);
    }

    @GetMapping
    public HttpEntity<List<Account>> listAll() {
        return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public HttpEntity<Account> getOne(@PathVariable("id") Integer id) {

        Account account = service.get(id);

        return new ResponseEntity<>(account, HttpStatus.OK);

    }

    @PostMapping
    public HttpEntity<Account> add(@RequestBody Account account) {
        logger.debug("masuk post");

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
    public HttpEntity<?> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
