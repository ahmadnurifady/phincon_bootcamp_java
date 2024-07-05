package com.example.phincon_jdbc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.phincon_jdbc.model.AccounWithBank;
import com.example.phincon_jdbc.model.Account;
import com.example.phincon_jdbc.model.BucketAccount;


@Service
public interface AccountService {

    List<Account> listAll();

    Account get(Integer id);

    Account save(Account account);

    Account deposit(float amount, Integer id);

    Account withdraw(float amount, Integer id);

    void delete(Integer id);

    List<BucketAccount> findAllJoin();

    List<AccounWithBank> findAllAccountWithBankService();

    // AccountInfo saveAccountInfoService(AccountInfo accountInfo, Account account);
}
