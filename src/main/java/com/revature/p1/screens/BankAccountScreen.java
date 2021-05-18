package com.revature.p1.screens;

import com.revature.p1.entities.CurrentAccount;
import com.revature.p1.entities.Transaction;

public class BankAccountScreen extends Screen{

    public BankAccountScreen()
    {
        super("transactions");
    }

    @Override
    public void render()
    {
        System.out.println("\n" + CurrentAccount.getInstance().getAccount() + "\n");

        for (Transaction transaction : CurrentAccount.getInstance().getAccount().getTransactions())
        {
            System.out.println(transaction);
        }
    }
}
