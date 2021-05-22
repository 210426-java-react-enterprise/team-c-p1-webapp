package com.revature.p1.utilities.datasource;

import com.revature.p1.entities.Account;
import com.revature.p1.entities.Credential;
import com.revature.p1.entities.Customer;
import com.revature.p1.entities.Transaction;
import com.revature.p1.persistance.ConnectionManager;

import java.sql.Connection;
import java.util.List;

public class Session
{
    private Customer customer;
    private Transaction transactions;
    private Credential credentials;
    private Account account;
    Connection connection;

    public Session(Customer customer, Transaction transactions, Credential credentials, Account account)
    {
        connection = ConnectionManager.getConnection();
        if (transactions != null)
            this.transactions = transactions;
        if (credentials != null)
            this.credentials = credentials;
        if (customer != null)
            this.customer = customer;
        if (account != null)
            this.account = account;
    }

    public Account getAccount()
    {
        return account;
    }

    public void setAccount(Account account)
    {
        this.account = account;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }


    public Transaction getTransactions()
    {
        return transactions;
    }

    public void setTransactions(Transaction transactions)
    {
        this.transactions = transactions;
    }

    public Credential getCredentials()
    {
        return credentials;
    }

    public void setCredentials(Credential credentials)
    {
        this.credentials = credentials;
    }
}
