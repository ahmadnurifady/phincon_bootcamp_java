package com.example.phincon_jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import com.example.phincon_jdbc.controller.AccountController;
import com.example.phincon_jdbc.model.Account;
import com.example.phincon_jdbc.model.Amount;
import com.example.phincon_jdbc.service.AccountService;

public class AccountControllerTest3 {
       @Mock
    private AccountService accountService;   
    
    @InjectMocks
    AccountController accountController;
    
    @BeforeEach
    void setMockOutput() {
        List<Account> accountList = new ArrayList<Account>();
        accountList.add(new Account(1,"1982094121",10000));
        accountList.add(new Account(2,"1982094122",10000));
        accountList.add(new Account(3,"1982094123",10000));
        accountList.add(new Account(4,"1982094124",10000));
        accountList.add(new Account(5,"1982094125",10000));
        
        lenient().when(accountService.listAll()).thenReturn(accountList);
        lenient().when(accountService.save(any())).thenReturn(new Account(1,"1982094125",10000) );        
        lenient().when(accountService.get(anyInt())).thenReturn(new Account(1,"11111",1000));
        lenient().when(accountService.withdraw(anyFloat(), anyInt())).thenReturn(new Account(1,"11111",1000));
    }

    @Test
    public void whenGetRequestToAccounts_thenCorrectResponse() throws Exception {
        log.info("assert GET /api/accounts is correct response");
        List<Account> accountList = accountController.listAll().getBody();
        
        assertEquals(5, accountList.size());
        verify(accountService, times(1)).listAll();
    }
    
    @Test
    void testCreateAccount() {
        Account account = new Account(1,"1982094125",10000);
        
        assertEquals(accountController.add(account).getBody().getAccountNumber(), "1982094125");
        
        verify(accountService, times(1)).save(account);
    }
    
    @Test
    void testWithdraw() {        
        Amount amount = new Amount();
        amount.setAmount(1000);
        accountController.withdraw(1, amount);
        verify(accountService, times(1)).withdraw(amount.getAmount(), 1);
    }
}
