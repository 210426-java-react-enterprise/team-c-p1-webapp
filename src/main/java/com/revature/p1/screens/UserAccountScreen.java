package com.revature.p1.screens;


import com.revature.orm.MyObjectRelationalMapper;
import com.revature.orm.MySavable;
import com.revature.p1.entities.*;
import com.revature.p1.utilities.InputValidator;
import com.revature.p1.utilities.ScreenManager;
import com.revature.p1.utilities.datasource.Session;

import java.util.Scanner;
import java.util.stream.Collectors;

public class UserAccountScreen extends Screen
{
    private Scanner scanner;
    private InputValidator inputValidator;
    private MyObjectRelationalMapper orm;
    private ScreenManager screenManager;
    private Session session;


    public UserAccountScreen(   Scanner scanner, InputValidator inputValidator, MyObjectRelationalMapper orm,
                                ScreenManager screenManager, Session session)
    {
        super("/customer account");
        this.scanner = scanner;
        this.inputValidator = inputValidator;
        this.orm = orm;
        this.screenManager = screenManager;
        this.session = session;
    }

    private Account getCustomerAccount()
    {
        System.out.print("Enter the account number to use: ");
        String input = scanner.nextLine();
        if (inputValidator.validate(input, "/account number") == null)
            return null;

        Account account = null;

        for (MySavable mySavable : session.getCustomer()
                                          .getAccounts())
        {
            if (((Account) mySavable).getNumber() == Integer.getInteger(input))
            {
                account = (Account) mySavable;
                return account;
            }
        }
        return null;
    }

    @Override
    public void render()
    {
        main:
        while (true)
        {
            Customer customer = session.getCustomer();
            System.out.println(String.format("\n*** %s's accounts ***\n", customer.getFirstName()));

            if (customer.getAccounts().size() != 0)
            {
                for (int i = 0; i < customer.getAccounts().size(); i++)
                {
                    System.out.println(String.format("%d - %s", i + 1, customer.getAccounts().get(i)));
                }
            }

            System.out.println("\nPlease choose an option from the menu:\n");
            System.out.println("1 - Add a new account");
            System.out.println("2 - Add a new transaction");
            System.out.println("3 - View account");
            System.out.println("4 - Logout");
            System.out.print("\nchoice: ");
            String input = scanner.nextLine();
            int choice = inputValidator.validate(input, 1, 4);
            if (choice != -1)
            {
                switch (choice)
                {
                    case 1:
                        System.out.println("\nWhat type of account would you like to open?");
                        System.out.println("1 - Checking");
                        System.out.println("2 - Savings");
                        System.out.println("3 - Trust");
                        System.out.print("\nchoice: ");
                        input = scanner.nextLine();
                        choice = inputValidator.validate(input, 1, 3);

                        switch (choice)
                        {
                            case 1:
                                orm.saveNewData(new CheckingAccount(session.getCustomer().getSsn()));
                                break;
                            case 2:
                                orm.saveNewData(new SavingsAccount(session.getCustomer().getSsn()));
                                break;
                            case 3:
                                orm.saveNewData(new TrustAccount(session.getCustomer().getSsn()));
                                break;
                        }
                        session.loadCustomer(session.getCredentials());

                        break;
                    case 2:
                        if (session.getCustomer().getAccounts().size() == 0)
                        {
                            System.out.println("You have no accounts to make transactions. Please open an account first.");
                            break;
                        }
                        session.getCustomer().getAccounts()
                               .forEach(System.out::println);

                        System.out.print("\nWhat account number to use? ");
                        input = scanner.nextLine();
                        input = inputValidator.validate(input, "/account number");
                        if (input != null)
                        {
                            int accountNumber = Integer.parseInt(input);
                            session.setAccount(session.getCustomer().getAccounts().stream()
                                              .filter(account -> ((Account)account).getNumber() == accountNumber)
                                              .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                                              if (list.size() != 1)
                                              {
                                                  System.out.println("Incorrect account number entered. Please try again.");
                                                  return null;
                                              }
                                              return (Account) list.get(0);
                                              })));
                            if (session.getAccount() == null) break ;

                            System.out.println("\nWhat would you like to do?\n");
                            System.out.println("1 - Make a deposit");
                            System.out.println("2 - Make a withdrawal");
                            System.out.println("3 - Go back");
                            System.out.print("\nChoice: ");
                            input = scanner.nextLine();

                            choice = inputValidator.validate(input, 1, 4);
                            if (choice == -1)
                                break;

                            switch (choice)
                            {
                                case 1:
                                    screenManager.navigate("/deposit");
                                    break;
                                case 2:
                                    screenManager.navigate("/withdraw");
                                case 3:
                                    break;
                            }
                        }
                        break;
                    case 3:
                        System.out.print("\nEnter account number to view: ");
                        input = scanner.nextLine();
                        input = inputValidator.validate(input, "/account number");
                        session.setAccount(null);
                        for (MySavable account : session.getCustomer()
                                                        .getAccounts())
                        {
                            if (((Account)account).getNumber() == Integer.parseInt(input))
                            {
                                session.setAccount((Account) account);
                                break;
                            }
                        }
                        if (session.getAccount() == null)
                        {
                            System.out.println("Incorrect account number. Please try again.");
                            break;
                        }
                        if (session.getAccount().getTransactions().size() == 0)
                        {
                            System.out.println("There are no transactions in this account.");
                            break ;
                        }
                        System.out.println("\n" + session.getAccount() + "\n");
                        for (Transaction transaction : session.getAccount().getTransactions())
                        {
                            System.out.println(transaction);
                        }

                        break;

                    case 4:
                        session.setCustomer(null);
                        session.setAccount(null);

                        break main;

                }
            }
        }
    }


}
