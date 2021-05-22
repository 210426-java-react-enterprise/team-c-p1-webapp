package com.revature.p1.entities;


import com.revature.p1.annotations.ColumnType;
import com.revature.p1.annotations.MyColumn;
import com.revature.p1.annotations.MyEntity;

import java.util.ArrayList;
import java.util.List;


@MyEntity(name = "account")
public class Account extends MySavable
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


    public Account(String ssn)
    {
        this.number = "";
        this.balance = 0.0;
        this.customerSsn = ssn;
        this.type = "";
        this.transactions = new ArrayList<>();
    }
    protected Account(String number, double balance)
    {
        this.number = number;
        this.balance = balance;
        this.customerSsn = "";
        this.type = "";
        this.transactions = new ArrayList<>();
    }

    protected Account(String number, double balance, List<Transaction> transactions)
    {
        this.balance = balance;
        this.number = number;
        this.customerSsn = "";
        this.type = "";
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

    public Account(MySavable savable)
    {
        this.balance = ((Account) savable).balance;
        this.customerSsn = ((Account) savable).customerSsn;
        this.number = ((Account) savable).number;
        this.transactions = ((Account) savable).transactions;
        this.type = ((Account) savable).type;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public void setCustomerSsn(String customerSsn)
    {
        this.customerSsn = customerSsn;
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
    public double deposit(double amount){return 0;};
    public double withdraw(double amount){return 0;};
}
