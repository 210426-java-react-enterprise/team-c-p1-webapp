package com.revature.p1.utilities.datasource;

import com.revature.p1.entities.*;
import com.revature.p1.orms.MyObjectRelationalMapper;
import com.revature.p1.persistance.ConnectionManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Session
{
    private Customer customer;
    private Transaction transactions;
    private Credential credentials;
    private Account account;
    private Connection connection;
    private MyObjectRelationalMapper orm;

    public Session(Customer customer, Transaction transactions, Credential credentials, Account account, MyObjectRelationalMapper orm)
    {
        connection = ConnectionManager.getConnection();
        this.orm = orm;
        if (transactions != null)
        {
            this.transactions = transactions;
        }
        if (credentials != null)
        {
            this.credentials = credentials;
        }
        if (customer != null)
        {
            this.customer = customer;
        }
        if (account != null)
        {
            this.account = account;
        }
    }

    public void loadCustomer(Credential credential)
    {
        List<MySavable> savables = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        List<Account> customerAccounts = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();

        customer = (Customer) orm.readRow(new Customer(null, null,
                                                       credential.getSsn(), null, null, credential));
        savables.addAll(orm.readRows(new Account(credential.getSsn())));

        savables.forEach(savable -> accounts.add((Account) savable));

        accounts.forEach((account ->
            {
            switch (account.getType())
            {
                case "checking":
                    customerAccounts.add(new CheckingAccount(account));
                    break;
                case "savings":
                    customerAccounts.add(new SavingsAccount(account));
                    break;
                case "trust":
                    customerAccounts.add(new TrustAccount(account));
            }
            }));
        savables.clear();

        customerAccounts.forEach(account ->
                                     {
                                     savables.addAll(orm.readRows(new Transaction("", 0, 0, account.getNumber())));
                                     savables.forEach(savable -> transactions.add((Transaction) savable));
                                     savables.clear();
                                     account.setTransactions(transactions);
                                     transactions.clear();
                                     });


        customer.setAccounts(customerAccounts);
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
