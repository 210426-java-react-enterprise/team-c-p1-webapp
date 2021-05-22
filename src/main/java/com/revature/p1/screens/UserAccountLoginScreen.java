package com.revature.p1.screens;

import com.revature.p1.entities.*;
import com.revature.p1.orms.MyObjectRelationalMapper;
import com.revature.p1.utilities.InputValidator;
import com.revature.p1.utilities.ScreenManager;
import com.revature.p1.utilities.datasource.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class UserAccountLoginScreen extends Screen {

    private final Scanner scanner;
    private final InputValidator inputValidator;
    private final ScreenManager screenManager;
    private final MyObjectRelationalMapper orm;
    private final Session session;

    public UserAccountLoginScreen(  Scanner scanner, InputValidator inputValidator, ScreenManager screenManager,
                                    MyObjectRelationalMapper orm, Session session)
    {
        super("/login");
        this.session = session;
        this.scanner = scanner;
        this.inputValidator = inputValidator;
        this.screenManager = screenManager;
        this.orm = orm;
    }

    @Override
    public void render()
    {
        try
        {
            System.out.println("\n\n*** Login to your account ***\n");
            System.out.println("Enter your username: ");
            String input = scanner.nextLine();
            String username = inputValidator.validate(input, "/isUsername");
            if (username == null)
                return;

            System.out.println("Enter your password: ");
            input = scanner.nextLine();
            String password = inputValidator.validate(input, "/password");
            if (password == null)
                return;
            Credential credential = ((Credential)orm.readRow(new Credential(username, "", "")));
            if(password.equals(credential.getPassword()))
            {
                session.setCustomer((Customer) orm.readRow(new Customer(null, null,
                                                                        credential.getSsn(), null, null)));
                session.setCredentials(credential);
                List<MySavable> savables = orm.readRows(new Account(credential.getSsn()));
                List<Account> accounts = new ArrayList<>();
                savables.forEach(savable -> accounts.add((Account) savable));
                List<Account> customerAccounts = new ArrayList<>();
                accounts.forEach((account -> {
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
                session.getCustomer().setAccounts(customerAccounts);


                screenManager.navigate("/customer account");
            } else
            {
                System.out.println("Password was not correct. Please try again.");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
