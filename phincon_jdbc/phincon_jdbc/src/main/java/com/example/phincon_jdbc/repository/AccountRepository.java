package com.example.phincon_jdbc.repository;

import java.util.List;

import com.example.phincon_jdbc.model.AccounWithBank;
import com.example.phincon_jdbc.model.Account;
import com.example.phincon_jdbc.model.AccountInfo;
import com.example.phincon_jdbc.model.BucketAccount;

public interface AccountRepository {
    int save(Account account);
    int update(Account account);
    Account findById(Integer id);
    List<Account> findAll();
    boolean existsById(Integer id);
    int deleteById(Integer id);
    int deposit(float amount, Integer id);
    int withdraw(float amount, Integer id);
    int deleteAll();


    List<BucketAccount> findJoin();
    
    List<AccounWithBank> findAccountWithBank();
    int saveAccountInfoRepository(AccountInfo accountInfo, Account account);

}
