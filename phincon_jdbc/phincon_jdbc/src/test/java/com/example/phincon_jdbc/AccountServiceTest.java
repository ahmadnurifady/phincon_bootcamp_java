package com.example.phincon_jdbc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.phincon_jdbc.model.AccounWithBank;
import com.example.phincon_jdbc.model.Account;
import com.example.phincon_jdbc.model.BucketAccount;
import com.example.phincon_jdbc.repository.AccountRepository;
import com.example.phincon_jdbc.service.AccountService;
import com.example.phincon_jdbc.service.impl.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepo;

    @InjectMocks
    AccountService accountService = new AccountServiceImpl();

    @BeforeEach
    void setMockOutput() {

        List<Account> accountList = new ArrayList<Account>();
        accountList.add(new Account(1,"1982094121", 10000));
        accountList.add(new Account(2,"1982094122", 10000));
        accountList.add(new Account(3,"1982094123", 10000));
        accountList.add(new Account(4,"1982094124", 10000));
        accountList.add(new Account(5,"1982094125", 10000));

        List<BucketAccount> bucketAccountList = new ArrayList<BucketAccount>();
        bucketAccountList.add(new BucketAccount("10101027393020", "accountName1", 1900));
        bucketAccountList.add(new BucketAccount("101010298420", "accountName2", 1001));
        bucketAccountList.add(new BucketAccount("1012930202020", "accountName3", 122));
        bucketAccountList.add(new BucketAccount("10831202020", "accountName4", 120));
        bucketAccountList.add(new BucketAccount("011010202020", "accountName5", 101));

        List<AccounWithBank> accountWithBankList = new ArrayList<AccounWithBank>();
        accountWithBankList.add(new AccounWithBank("10101027393020", "accountName1", 1900, "bank satu"));
        accountWithBankList.add(new AccounWithBank("101010298420", "accountName2", 1001, "bank satu"));
        accountWithBankList.add(new AccounWithBank("1012930202020", "accountName3", 122, "bank tiga"));
        accountWithBankList.add(new AccounWithBank("10831202020", "accountName4", 120, "bank 2"));
        accountWithBankList.add(new AccounWithBank("011010202020", "accountName5", 101, "bank empat"));

        lenient().when(accountRepo.findAll()).thenReturn(accountList);
        lenient().when(accountRepo.findJoin()).thenReturn(bucketAccountList);
        lenient().when(accountRepo.findAccountWithBank()).thenReturn(accountWithBankList);

    }

    @Test
    void testFindJoin() {
        List<BucketAccount> bucketAccountList = accountService.findAllJoin();
        try {
            System.out.println("UNIT TEST testFindJoin METHOD");
            assertEquals(5, bucketAccountList.size());
            System.out.println("expected: 5");
            System.out.println("actual : " + bucketAccountList.size());
        } catch (Exception e) {
            System.out.println("UNIT TEST testFindJoin METHOD FAILURE");
            System.out.println("expected: 5");
            System.out.println("actual :" + bucketAccountList.size());
        }

        verify(accountRepo, times(1)).findJoin();
    }

    @Test
    void testGet() {
        Integer userId = 1;
        List<Account> accountList = accountService.listAll();
        
        accountList.forEach((a) -> {
            if(a.getId() == userId){
                assertEquals(1, a.getId());
                
            }
        });
    }

    @Test
    void testFindAllAccountWithBankService() {
        List<AccounWithBank> accountWithBankList = accountService.findAllAccountWithBankService();

        assertEquals(5, accountWithBankList.size());
        verify(accountRepo, times(1)).findAccountWithBank();
    }

    @Test
    void testCreateAccount() {
        Account account = new Account(1,"1982094125", 10000);

        accountService.save(account);

        verify(accountRepo, times(1)).save(account);
    }

}
