package com.revature.p1.entities;


import com.revature.p1.annotations.ColumnType;
import com.revature.p1.annotations.MyColumn;
import com.revature.p1.annotations.MyEntity;

@MyEntity(name = "transaction")
public class Transaction extends MySavable
{
    @MyColumn(  name = "type",nullable = false,unique = false,type = ColumnType.VARCHAR,length = 10,pk = false,
                fk = false,reference = "",delete = "cascade")
    private String type;

    @MyColumn(  name = "amount",nullable = false,unique = false,type = ColumnType.DECIMAL,length = 0,pk = false,
                fk = false,reference = "",delete = "cascade")
    private double amount;

    @MyColumn(  name = "balance",nullable = false,unique = false,type = ColumnType.DECIMAL,length = 0,pk = false,
            fk = false,reference = "",delete = "cascade")
    private double balance;

    @MyColumn(  name = "account_number",nullable = false,unique = false,type = ColumnType.INT,length = 10,pk = false,
            fk = true,reference = "account(number)",delete = "cascade")
    private int accountNumber;

    public Transaction(String type, double amount, double balance, int accountNumber)
    {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public Transaction(MySavable savable)
    {
        this.type = ((Transaction) savable).type;
        this.amount = ((Transaction) savable).amount;
        this.balance = ((Transaction) savable).balance;
        this.accountNumber = ((Transaction) savable).accountNumber;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public void setAccountNumber(int accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public int getAccountNumber()
    {
        return accountNumber;
    }

    public String getType()
    {
        return type;
    }

    public double getAmount()
    {
        return amount;
    }

    public double getBalance()
    {
        return balance;
    }

    @Override
    public String toString()
    {
        return "{Transaction - " +
                "type = '" + type + '\'' +
                ", amount = $" + amount +
                ", balance = $" + balance +
                       ", number = $" + accountNumber +
                '}';
    }
}
