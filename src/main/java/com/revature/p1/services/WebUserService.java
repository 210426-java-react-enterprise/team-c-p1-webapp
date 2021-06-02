package com.revature.p1.services;

import com.revature.orm.MyObjectRelationalMapper;
import com.revature.orm.MySavable;
import com.revature.p1.dtos.LoginDTO;
import com.revature.p1.entities.*;
import com.revature.p1.exceptions.InvalidLoginException;
import com.revature.p1.utilities.InputValidator;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WebUserService
{
    private MyObjectRelationalMapper orm;
    private InputValidator iv;
    private Logger logger;

    public WebUserService(MyObjectRelationalMapper orm, InputValidator iv, Logger logger)
    {
        this.orm = orm;
        this.iv = iv;
        this.logger = logger;
    }

    public Customer authenticate(LoginDTO login, HttpServletRequest request, HttpServletResponse response) throws InvalidLoginException
    {
        PrintWriter writer = null;
        try
        {
            writer = response.getWriter();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        String username = iv.validate(login.getUsername(), "/isUsername");
        String password = iv.validate(login.getPassword(), "/password");

        if (username == null || password == null) throw new InvalidLoginException();

        Credential credential = new Credential(login.getUsername());
        credential = (Credential) orm.readRow(credential);

        Customer customer = null;

        if (password.equals(credential.getPassword()))
        {
            customer = loadCustomer(credential);
            customer.setCredential(credential);
            logger.info(customer);
            //writer.println(customer);
        }
        else
        {
            writer.println("<h1> Incorrect password was used. Please try again.");
        }
        return customer;
    }

    private Customer loadCustomer(Credential credential)
    {
        List<Account> accounts = new ArrayList<>();
        List<Account> customerAccounts = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();

        Customer customer = (Customer) orm.readRow(new Customer(credential.getSsn()));
        List<MySavable> savables = new ArrayList<>(orm.readRows(new Account(credential.getSsn())));

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
                    break;
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
        customer.setCredential(credential);
        customer.setAccounts(customerAccounts);
        return customer;
    }

    public void addNewAccount(Account account)
    {
        orm.saveNewData(account);
    }

    public Customer refreshCustomer(Credential credential)
    {
        return loadCustomer(credential);
    }


    public void update(Account account)
    {
        orm.updateData(account);
    }

    public void save(Transaction transaction)
    {
        orm.saveNewData(transaction);
    }
}
