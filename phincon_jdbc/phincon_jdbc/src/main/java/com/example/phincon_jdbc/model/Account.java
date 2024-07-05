package com.example.phincon_jdbc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {
    private Integer id;
    private String accountNumber;
    private float balance;

    public Account(Integer id,String accountNumber, float balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}
