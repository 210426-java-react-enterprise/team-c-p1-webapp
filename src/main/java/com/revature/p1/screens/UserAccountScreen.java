package com.revature.p1.screens;


import com.revature.p1.entities.*;
import com.revature.p1.exceptions.IllegalInputException;
import com.revature.p1.orms.MyObjectRelationalMapper;
import com.revature.p1.utilities.InputValidator;
import com.revature.p1.utilities.ScreenManager;
import com.revature.p1.utilities.datasource.Session;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

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
        try
        {
            if (inputValidator.validate(input, "/account number") == null)
                return null;

            Account account = null;
            for (MySavable acc : session.getCustomer().getAccounts())
            {
                if (((Account)acc).getNumber().equals(input))
                {
                    account = (Account) acc;
                    return account;
                }
            }
            return null;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void render() throws IllegalInputException
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
                        try
                        {
                            String newAccountNumber = "";
                            switch (choice)
                            {
                                case 1:
                                    newAccountNumber = dao.addAccount("checking");
                                    break;
                                case 2:
                                    newAccountNumber = dao.addAccount("savings");
                                    break;
                                case 3:
                                    newAccountNumber = dao.addAccount("trust");
                            }
                            if (newAccountNumber != null)
                                System.out.println("\nAccount was created successfully. Your new account number is " + newAccountNumber);
                        } catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        if (session.getCustomer().getAccounts().size() == 0)
                        {
                            System.out.println("You have no accounts to make transactions. Please create an account first.");
                            break;
                        }
                        Account account = getCustomerAccount();
                        if (account == null) break;

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
                                session.setAccount(account);
                                screenManager.navigate("/deposit");
                                break;
                            case 2:
                                session.setAccount(account);
                                screenManager.navigate("/withdraw");
                            case 3:
                                break;
                        }

                        break;
                    case 3:
                        Account account1 = getCustomerAccount();
                        if (account1 == null)
                        {
                            break;
                        }

                        System.out.println("\n" + account1 + "\n");
                        for (Transaction transaction : account1.getTransactions())
                        {
                            System.out.println(transaction);
                        }

                        break;

                    case 4:
                        session.setCustomer(null);
                        session.setAccount(null);
//                        customer = null;
                        break main;

                }
            }
        }
    }


}
