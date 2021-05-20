package com.revature.p1.screens;

import com.revature.p1.orms.MyObjectRelationalMapper;
import com.revature.p1.utilities.InputValidator;
import com.revature.p1.utilities.datasource.Session;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class DepositScreen extends Screen
{
    private Scanner scanner;
    private InputValidator inputValidator;
    private MyObjectRelationalMapper orm;
    private Session session;

    public DepositScreen(Scanner scanner, InputValidator inputValidator, MyObjectRelationalMapper orm, Session session)
    {
        super("/deposit");
        this.scanner = scanner;
        this.session = session;
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
           session.getAccounts().deposit(Double.parseDouble(input));

            //dao.updateAccount(CurrentAccount.getInstance().getAccount());

        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
