package com.example.phincon_jdbc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccounWithBank {
    private String accountNumber;
    private String accountName;
    private float balance;
    private String bankName;

    public AccounWithBank(String accountNumber, String accountName, float balance, String bankName){
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.bankName = bankName;
    }
}
