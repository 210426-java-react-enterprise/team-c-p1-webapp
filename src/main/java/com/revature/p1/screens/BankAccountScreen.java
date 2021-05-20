package com.revature.p1.screens;

import com.revature.p1.entities.Transaction;
import com.revature.p1.utilities.datasource.Session;

public class BankAccountScreen extends Screen{
    private Session session;

    public BankAccountScreen(Session session)
    {
        super("transactions");
        this.session = session;
    }

    @Override
    public void render()
    {
        System.out.println("\n" + session.getAccounts() + "\n");

        for (Transaction transaction : session.getAccounts().getTransactions())
        {
            System.out.println(transaction);
        }
    }
}
