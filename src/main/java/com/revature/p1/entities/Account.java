package com.revature.p1.entities;


import com.revature.p1.annotations.ColumnType;
import com.revature.p1.annotations.MyColumn;
import com.revature.p1.annotations.MyEntity;

import java.util.ArrayList;
import java.util.List;


@MyEntity(name = "account")
public abstract class Account
{
    @MyColumn(  name = "balance",nullable = false,unique = false,type = ColumnType.DECIMAL,
                length = 0,pk = false,fk = false,reference = "",delete = "cascade")
    protected double balance;

    @MyColumn(  name = "number",nullable = false,unique = true,type = ColumnType.VARCHAR,
            length = 10,pk = true, fk = false,reference = "",delete = "cascade")
    protected String number;

    @MyColumn(  name = "type",nullable = false,unique = false,type = ColumnType.VARCHAR,
            length = 10,pk = false,fk = false,reference = "",delete = "cascade")
    protected String type;

    @MyColumn(name = "customer_ssn", type = ColumnType.VARCHAR, length = 9, nullable = false,unique = false,
              pk = false, fk = true, reference = "project1.customer(ssn)", delete = "cascade")
    protected String customerSsn;

    protected List<Transaction> transactions;

    protected Account(String number)
    {
        this.number = number;
        this.balance = 0;
        this.customerSsn = "";
        this.type = ColumnType.VARCHAR.name();
        this.transactions = new ArrayList<>();
    }
    protected Account(String number, double balance)
    {
        this.number = number;
        this.balance = balance;
        this.customerSsn = "";
        this.type = ColumnType.VARCHAR.name();
        this.transactions = new ArrayList<>();
    }

    protected Account(String number, double balance, List<Transaction> transactions)
    {
        this.balance = balance;
        this.number = number;
        this.customerSsn = "";
        this.type = ColumnType.VARCHAR.name();
        this.transactions = transactions;
    }

    protected Account(double balance, String number, String type, String customerSsn)
    {
        this.balance = balance;
        this.number = number;
        this.type = type;
        this.customerSsn = customerSsn;
        this.transactions = new ArrayList<>();
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
