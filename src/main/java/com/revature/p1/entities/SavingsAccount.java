package com.revature.p1.entities;


import java.util.List;

public class SavingsAccount extends Account{

    public SavingsAccount(String number)
    {
        super(number);
    }

    public SavingsAccount(String number, double balance)
    {
        super(number, balance);
    }

    public SavingsAccount(String number, double balance, List<Transaction> transactions)
    {
        super(number, balance, transactions);
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %s%.2f", "Savings Account - account number: ",number," - balance: $",this.getBalance());
    }
    @Override
    public double deposit(double amount)
    {
        this.balance += amount;
        transactions.add(new Transaction("deposit", amount, balance,number));
        return balance;
    }

    @Override
    public double withdraw(double amount)
    {
        if (amount > balance) return balance;
        this.balance -= amount;
        transactions.add(new Transaction("withdraw", amount, balance,number));
        return balance;
    }
}
