package com.revature.p1.screens;

import com.revature.p1.entities.CurrentAccount;
import com.revature.p1.orms.MyObjectRelationalMapper;
import com.revature.p1.utilities.InputValidator;

import java.sql.SQLException;
import java.util.Scanner;

public class DepositScreen extends Screen
{
    private Scanner scanner;
    private InputValidator inputValidator;
    private MyObjectRelationalMapper orm;

    public DepositScreen(Scanner scanner, InputValidator inputValidator, MyObjectRelationalMapper orm)
    {
        super("/deposit");
        this.scanner = scanner;

        this.inputValidator = inputValidator;
        this.orm = orm;
    }

    @Override
    public void render()
    {
        System.out.print("Enter amount to deposit: ");
        String input = scanner.nextLine();
        try
        {
            if (inputValidator.validate(input, "/deposit") == null)
                return;
            String identifier = "";
           CurrentAccount.getInstance().getAccount().deposit(Double.parseDouble(input));

            //dao.updateAccount(CurrentAccount.getInstance().getAccount());

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
