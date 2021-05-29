package com.revature.p1.entities;


import com.revature.orm.annotations.ColumnType;
import com.revature.orm.annotations.MyColumn;
import com.revature.orm.annotations.MyEntity;

import java.util.ArrayList;
import java.util.List;

@MyEntity(name = "account")
public class SavingsAccount extends Account{

    @MyColumn(name = "balance", nullable = false, unique = false, type = ColumnType.DECIMAL,
              length = 0, pk = false, fk = false, reference = "", delete = "cascade")
    private double balance;

    @MyColumn(  name = "number",nullable = false,unique = true,type = ColumnType.SERIAL,
                length = 10,pk = true, fk = false,reference = "",delete = "cascade")
    private int number;

    @MyColumn(  name = "type",nullable = false,unique = false,type = ColumnType.VARCHAR,
                length = 10,pk = false,fk = false,reference = "",delete = "cascade")
    private String type;

    @MyColumn(name = "customer_ssn", type = ColumnType.VARCHAR, length = 9, nullable = false,unique = false,
              pk = false, fk = true, reference = "project1.customer(ssn)", delete = "cascade")
    private String customerSsn;

    private List<Transaction> transactions;
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
        if (amount > balance) return -1;
        this.balance -= amount;
        transactions.add(new Transaction("withdraw", amount, balance,number));
        return balance;
    }
    public SavingsAccount(Account account)
    {
        super(account.getCustomerSsn());
        this.balance = account.getBalance();
        this.customerSsn = account.getCustomerSsn();
        this.number = account.getNumber();
        this.type = account.getType();
        this.transactions = new ArrayList<>();
        this.transactions.addAll(account.transactions);
    }

    public SavingsAccount(String ssn)
    {
        this.customerSsn = ssn;
        this.type = "savings";
        this.transactions = new ArrayList<>();
    }

    @Override
    public List<Transaction> getTransactions()
    {
        return new ArrayList<>(transactions);
    }

    @Override
    public void setTransactions(List<Transaction> transactions)
    {
        this.transactions.addAll(transactions);
    }

    @Override
    public double getBalance()
    {
        return balance;
    }

    @Override
    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    @Override
    public int getNumber()
    {
        return number;
    }

    @Override
    public void setNumber(int number)
    {
        this.number = number;
    }

    @Override
    public String getType()
    {
        return type;
    }

    @Override
    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public String getCustomerSsn()
    {
        return customerSsn;
    }

    @Override
    public void setCustomerSsn(String customerSsn)
    {
        this.customerSsn = customerSsn;
    }
}
