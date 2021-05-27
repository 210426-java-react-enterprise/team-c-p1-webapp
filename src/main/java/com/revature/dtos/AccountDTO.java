package com.revature.dtos;

public class AccountDTO {

    public AccountDTO() {

    }

    private String name;
    private String balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
