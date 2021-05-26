package com.revature.webapp.models;

import com.revature.orm.annotations.ColumnType;
import com.revature.orm.annotations.MyColumn;
import com.revature.orm.annotations.MyEntity;


import java.util.List;
import java.util.ArrayList;


@MyEntity(name = "account")
public abstract class Account
{
    @MyColumn(  name = "balance",nullable = false,unique = false,type = ColumnType.DECIMAL,
            length = 0,pk = false,fk = false,reference = "",delete = "cascade")
    protected double balance;

    @MyColumn(  name = "number",nullable = false,unique = true,type = ColumnType.VARCHAR,
            length = 10,pk = false,fk = false,reference = "",delete = "cascade")
    protected String number;

    @MyColumn(  name = "type",nullable = false,unique = false,type = ColumnType.VARCHAR,
            length = 10,pk = false,fk = false,reference = "",delete = "cascade")
    protected String type;


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
