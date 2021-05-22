package com.revature.p1.entities;

import java.util.List;

public class CheckingAccount extends Account{

    public CheckingAccount(String number)
    {
        super(number);
    }

    public CheckingAccount(String number, double balance)
    {
        super(number, balance);
    }

    public CheckingAccount(String number, double balance, List<Transaction> transactions)
    {
        super(number, balance, transactions);
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %s%.2f", "Checking Account - account number: ",number," - balance: $",this.getBalance());
    }

    @Override
    public double deposit(double amount)
    {
        this.balance += amount;
        transactions.add(new Transaction("deposit", amount, balance,number));
        System.out.println("Deposit successful.");
        return balance;
    }

    @Override
    public double withdraw(double amount)
    {
        if (amount > balance)
        {
            System.out.println("Amount specified is greater than the balance. Please try again.");
            return -1;
        }
        this.balance -= amount;
        transactions.add(new Transaction("withdraw", amount, balance,number));
        System.out.println("Withdrawal successful.");
        return balance;
    }

    public CheckingAccount(Account account)
    {
        super(account.customerSsn);
        this.balance = account.balance;
        this.customerSsn = account.customerSsn;
        this.number = account.number;
        this.type = account.type;
        this.transactions = account.transactions;
    }
}
