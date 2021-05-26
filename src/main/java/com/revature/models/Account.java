package com.revature.models;

import com.revature.p1.utils.annotations.*;

/**
 * Account
 * <p>
 * POJO to represent a bank account within the application.
 */
@Entity()
public class Account {

    @Key
    @Column
    private int accountID;

    @Column(name = "account_name")
    private String name;

    @Column
    private double balance;



    public Account() {

    }

    public Account(int accountID, double balance, String name) {
        this.accountID = accountID;
        this.balance = balance;
        this.name = name;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("accountID=").append(accountID);
        sb.append(", name='").append(name).append('\'');
        sb.append(", balance=").append(balance);
        sb.append('}');
        return sb.toString();
    }
}
