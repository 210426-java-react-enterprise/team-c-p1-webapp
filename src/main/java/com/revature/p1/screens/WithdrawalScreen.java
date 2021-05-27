package com.revature.p1.screens;

import com.revature.orm.MyObjectRelationalMapper;
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
        if (session.getAccount().withdraw(Double.parseDouble(input)) != -1)
        {
            orm.updateData(session.getAccount());
            orm.saveNewData(session.getAccount()
                                   .getTransactions()
                                   .get(session.getAccount()
                                               .getTransactions()
                                               .size() - 1));
            session.loadCustomer(session.getCredentials());
        } else System.out.println("Amount specified is greater than the balance. Please try again.");
    }
}
