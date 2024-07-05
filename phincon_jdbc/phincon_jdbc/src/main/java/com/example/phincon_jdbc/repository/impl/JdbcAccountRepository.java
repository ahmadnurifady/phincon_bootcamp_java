package com.example.phincon_jdbc.repository.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.phincon_jdbc.controller.AccountController;
import com.example.phincon_jdbc.model.AccounWithBank;
import com.example.phincon_jdbc.model.Account;
import com.example.phincon_jdbc.model.AccountInfo;
import com.example.phincon_jdbc.model.BucketAccount;
import com.example.phincon_jdbc.repository.AccountRepository;

@Repository
public class JdbcAccountRepository implements AccountRepository {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Account account) {

        return jdbcTemplate.update("INSERT INTO accounts (account_number, balance) VALUES(?,?)",
                new Object[]{account.getAccountNumber(), account.getBalance()});
    }

    @Override
    public int saveAccountInfoRepository(AccountInfo accountInfo, Account account) {
        return jdbcTemplate.update("INSERT INTO account_info(id, accounts_id, account_name) VALUES(?,?,?)", new Object[]{
            accountInfo.getId(), account.getId(), accountInfo.getAccountName()
        });

    }

    @Override
    public int update(Account account) {

        return jdbcTemplate.update("UPDATE accounts SET account_number=?, balance=? WHERE id=?",
                new Object[]{account.getAccountNumber(), account.getBalance(), account.getId()});
    }

    @Override
    public Account findById(Integer id) {
        logger.debug("masuk ke find by id");

        return jdbcTemplate.queryForObject("SELECT * FROM accounts WHERE id=?",
                BeanPropertyRowMapper.newInstance(Account.class), id);
    }

    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query("SELECT * from accounts", BeanPropertyRowMapper.newInstance(Account.class));
    }

    @Override
    public List<BucketAccount> findJoin() {
        return jdbcTemplate.query("SELECT a.account_number, ai.account_name, a.balance  FROM accounts as a JOIN account_info as ai ON ai.accounts_id = a.id", BeanPropertyRowMapper.newInstance(BucketAccount.class));
    }

    @Override
    public List<AccounWithBank> findAccountWithBank() {
        return jdbcTemplate.query("SELECT a.account_number, ai.account_name, a.balance, bi.bank_name FROM accounts as a JOIN account_info as ai ON ai.accounts_id = a.id JOIN bank_info as bi ON bi.accounts_id = a.id", BeanPropertyRowMapper.newInstance(AccounWithBank.class));
    }

    @Override
    public boolean existsById(Integer id) {
        Integer count = this.jdbcTemplate.queryForObject("select count(*) from accounts WHERE id=?", Integer.class, id);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int deleteById(Integer id) {
        return jdbcTemplate.update("DELETE FROM accounts WHERE id=?", id);
    }

    @Override
    public int deposit(float amount, Integer id) {
        return jdbcTemplate.update("UPDATE accounts SET balance=balance + ? WHERE id=?",
                new Object[]{amount, id});
    }

    @Override
    public int withdraw(float amount, Integer id) {
        return jdbcTemplate.update("UPDATE accounts SET balance=balance - ? WHERE id=?",
                new Object[]{amount, id});
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM accounts", new Object[]{});
    }

}
