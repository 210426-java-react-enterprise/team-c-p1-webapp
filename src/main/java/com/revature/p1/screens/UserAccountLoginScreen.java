package com.revature.p1.screens;

import com.revature.p1.orms.MyObjectRelationalMapper;
import com.revature.p1.utilities.InputValidator;
import com.revature.p1.utilities.ScreenManager;

import java.util.Scanner;

public class UserAccountLoginScreen extends Screen {

    private Scanner scanner;
    private InputValidator inputValidator;
    private ScreenManager screenManager;
    private MyObjectRelationalMapper orm;

    public UserAccountLoginScreen(Scanner scanner, InputValidator inputValidator, ScreenManager screenManager, MyObjectRelationalMapper orm)
    {
        super("/login");

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
//            if(password.equals(dao.isCorrectPassword(username)))
//            {
//                CurrentCustomer.getInstance().setCustomer(dao.getCustomer(username));
//                screenManager.navigate("/customer account");
//            } else
//            {
//                System.out.println("Password was not correct. Please try again.");
//            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
