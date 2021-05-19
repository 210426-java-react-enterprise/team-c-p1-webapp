package com.revature.p1.utilities.datasource;

import com.revature.p1.entities.Account;
import com.revature.p1.entities.Customer;
import com.revature.p1.persistance.ConnectionManager;

import java.sql.Connection;

public class Session
{
    private Customer customer;
    private Account account;
    Connection connection;

    public Session(Customer customer, Account account)
    {
        connection = ConnectionManager.getConnection();
        if (customer != null)
            this.customer = customer;
        if (account != null)
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

    public Account getAccount()
    {
        return account;
    }

    public void setAccount(Account account)
    {
        this.account = account;
    }
}
