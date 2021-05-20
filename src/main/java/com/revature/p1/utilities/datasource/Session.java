package com.revature.p1.utilities.datasource;

import com.revature.p1.entities.Account;
import com.revature.p1.entities.Credential;
import com.revature.p1.entities.Customer;
import com.revature.p1.entities.Transaction;
import com.revature.p1.persistance.ConnectionManager;

import java.sql.Connection;

public class Session
{
    private Customer customer;
    private Account accounts;
    private Transaction transactions;
    private Credential credentials;
    Connection connection;

    public Session(Customer customer, Account accounts, Transaction transactions, Credential credentials)
    {
        connection = ConnectionManager.getConnection();
        if (transactions != null)
            this.transactions = transactions;
        if (credentials != null)
            this.credentials = credentials;
        if (customer != null)
            this.customer = customer;
        if (accounts != null)
            this.accounts = accounts;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public Account getAccounts()
    {
        return accounts;
    }

    public void setAccounts(Account accounts)
    {
        this.accounts = accounts;
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
