package com.revature.p1.entities;


import java.util.ArrayList;
import java.util.List;

public abstract class Account
{

    protected double balance;
    protected String number;
    protected List<Transaction> transactions;

    protected Account(String number)
    {
        this.number = number;
        balance = 0;
        transactions = new ArrayList<>();
    }
    protected Account(String number, double balance)
    {
        this.number = number;
        this.balance = balance;
        transactions = new ArrayList<>();
    }

    public Account(String number, double balance, List<Transaction> transactions)
    {
        this.balance = balance;
        this.number = number;
        this.transactions = transactions;
    }

    public double getBalance()
    {
        return balance;
    }

    public String getNumber()
    {
        return number;
    }

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public void updateBalance()
    {
        if (!transactions.isEmpty())
        {
            balance = transactions.get(transactions.size()- 1).getBalance();
        }
    }
    public abstract double deposit(double amount);
    public abstract double withdraw(double amount);
}
