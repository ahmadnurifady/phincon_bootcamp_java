package com.example.phincon_jdbc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BucketAccount {
    private String accountNumber;
    private String accountName;
    private float balance;

    public BucketAccount(String accountNumber, String accountName, float balance){
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
    }
}
