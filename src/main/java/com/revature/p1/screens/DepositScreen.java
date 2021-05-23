package com.revature.p1.screens;

import com.revature.p1.orms.MyObjectRelationalMapper;
import com.revature.p1.utilities.InputValidator;
import com.revature.p1.utilities.datasource.Session;

import java.util.Scanner;

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
        if (inputValidator.validate(input, "/deposit") == null)
                    return;

        session.getAccount().deposit(Double.parseDouble(input));
        orm.updateData(session.getAccount());
        orm.saveNewData(session.getAccount().getTransactions().get(session.getAccount().getTransactions().size() - 1));
        session.loadCustomer(session.getCredentials());
    }
}
