package com.example.phincon_jdbc.model;

import lombok.Data;

@Data
public class AccountInfo {
    private Integer id;
    private Integer accountsId;
    private String accountName;

    public AccountInfo(Integer accountsId, String accountName){
        this.accountsId = accountsId;
        this.accountName = accountName;
    }
}
