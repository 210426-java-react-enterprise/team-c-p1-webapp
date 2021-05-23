package com.revature.p1.screens;

import com.revature.p1.orms.MyObjectRelationalMapper;
import com.revature.p1.utilities.InputValidator;
import com.revature.p1.utilities.datasource.Session;

import java.util.Scanner;

public class WithdrawalScreen extends Screen
{
    private Scanner scanner;
    private InputValidator inputValidator;
    private MyObjectRelationalMapper orm;
    private Session session;

    public WithdrawalScreen(Scanner scanner, InputValidator inputValidator, MyObjectRelationalMapper orm, Session session)
    {
        super("/withdraw");
        this.session = session;
        this.scanner = scanner;
        this.inputValidator = inputValidator;
        this.orm = orm;
    }

    @Override
    public void render()
    {
        System.out.print("Enter amount to withdraw: ");
        String input = scanner.nextLine();
        if (inputValidator.validate(input, "/deposit") == null)
            return;

        session.getAccount().withdraw(Double.parseDouble(input));
        orm.updateData(session.getAccount());
        orm.saveNewData(session.getAccount().getTransactions().get(session.getAccount().getTransactions().size() - 1));
    }
}
