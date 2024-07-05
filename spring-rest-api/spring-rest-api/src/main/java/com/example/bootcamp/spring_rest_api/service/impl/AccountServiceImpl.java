package com.example.bootcamp.spring_rest_api.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.bootcamp.spring_rest_api.exeption.AccountAlreadyExistsException;
import com.example.bootcamp.spring_rest_api.exeption.NoSuchAccountExistsException;
import com.example.bootcamp.spring_rest_api.model.Account;
import com.example.bootcamp.spring_rest_api.repository.AccountRepository;
import com.example.bootcamp.spring_rest_api.service.AccountService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository repo;

    public List<Account> listAll() {
        return repo.findAll();
    }

    public Account get(Integer id) {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchAccountExistsException("No Such Account exists!");
        }
    }

    public Account save(Account account) {
        try {
            return repo.save(account);
        } catch (DataIntegrityViolationException e) {
            throw new AccountAlreadyExistsException("Account already exists!", e);
        } catch (ConstraintViolationException e) {
            throw new AccountAlreadyExistsException("Account already exists!", e);
        }
    }

    public Account deposit(float amount, Integer id) {
        boolean isExists = repo.existsById(id);

        if (isExists) {
            repo.deposit(amount, id);
            return repo.findById(id).get();
        } else {
            throw new NoSuchAccountExistsException("No Such Account exists!");
        }
    }

    public Account withdraw(float amount, Integer id) {
        boolean isExists = repo.existsById(id);

        if (isExists) {
            repo.withdraw(amount, id);
            return repo.findById(id).get();
        } else {
            throw new NoSuchAccountExistsException("No Such Account exists!");
        }
    }

    public void delete(Integer id) {
        boolean isExists = repo.existsById(id);

        if (isExists) {
            repo.deleteById(id);
        } else {
            throw new NoSuchAccountExistsException("No Such Account exists!");
        }
    }
}
