package com.revature.p1.entities;


import com.revature.p1.exceptions.IllegalInputException;

import java.util.List;

public class TrustAccount extends Account
{
    public TrustAccount(String number)
    {
        super(number);
    }

    public TrustAccount(String number, double balance)
    {
        super(number, balance);
    }

    public TrustAccount(String number, double balance, List<Transaction> transactions)
    {
        super(number, balance, transactions);
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %s%.2f", "Trust Account - account number: ",number," - balance: $",this.getBalance());
    }

    @Override
    public double deposit(double amount)
    {
        this.balance += amount;
        transactions.add(new Transaction("deposit", amount, balance, number));
        return balance;
    }

    @Override
    public double withdraw(double amount)
    {
        if (amount > balance) return balance;
        this.balance -= amount;
        transactions.add(new Transaction("withdraw", amount, balance, number));
        return balance;
    }
    public TrustAccount(Account account)
    {
        super(account.customerSsn);
        this.balance = account.balance;
        this.customerSsn = account.customerSsn;
        this.number = account.number;
        this.type = account.type;
        this.transactions = account.transactions;
    }
}
