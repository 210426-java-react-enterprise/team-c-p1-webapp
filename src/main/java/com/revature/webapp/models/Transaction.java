package com.revature.webapp.models;


import com.revature.orm.MySavable;
import com.revature.orm.annotations.ColumnType;
import com.revature.orm.annotations.MyColumn;
import com.revature.orm.annotations.MyEntity;

@MyEntity(name = "transaction")
public class Transaction extends MySavable
{
    @MyColumn(  name = "type",nullable = false,unique = false,type = ColumnType.VARCHAR,length = 10,pk = false,
            fk = false,reference = "",delete = "cascade")
    private final String type;

    @MyColumn(  name = "amount",nullable = false,unique = false,type = ColumnType.DECIMAL,length = 0,pk = false,
            fk = false,reference = "",delete = "cascade")
    private final double amount;

    @MyColumn(  name = "balance",nullable = false,unique = false,type = ColumnType.DECIMAL,length = 0,pk = false,
            fk = false,reference = "",delete = "cascade")
    private final double balance;

    @MyColumn(  name = "account_number",nullable = false,unique = false,type = ColumnType.VARCHAR,length = 10,pk = false,
            fk = true,reference = "account(number)",delete = "cascade")
    private final int accountNumber;

    public Transaction(String type, double amount, double balance, int accountNumber)
    {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
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
                '}';
    }
}