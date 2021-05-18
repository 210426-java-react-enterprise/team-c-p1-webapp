package com.revature.p1.screens;

import com.revature.p1.orms.MyObjectRelationalMapper;
import com.revature.p1.utilities.InputValidator;

import java.sql.SQLException;
import java.util.Scanner;

public class WithdrawalScreen extends Screen
{
    private Scanner scanner;
    private InputValidator inputValidator;
    private MyObjectRelationalMapper orm;

    public WithdrawalScreen(Scanner scanner, InputValidator inputValidator, MyObjectRelationalMapper orm)
    {
        super("/withdraw");

        this.scanner = scanner;
        this.inputValidator = inputValidator;
        this.orm = orm;
    }

    @Override
    public void render()
    {
        System.out.print("Enter amount to withdraw: ");
        String input = scanner.nextLine();
        try
        {
            if (inputValidator.validate(input, "/withdraw") == null)
                return;

//            if(CurrentAccount.getInstance().getAccount().withdraw(Double.parseDouble(input)) != -1)
//                dao.updateAccount(CurrentAccount.getInstance().getAccount());

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}
