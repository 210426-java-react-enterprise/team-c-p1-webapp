package com.revature.p1.entities;



public class CurrentAccount
{
    private Account account;
    private static CurrentAccount instance;

    private CurrentAccount()
    {
        account = null;
    }

    public static CurrentAccount getInstance()
    {
        if (instance == null)
        {
            instance = new CurrentAccount();
        }
        return instance;
    }
    public Account getAccount()
    {
        return account;
    }

    public void setAccount(Account account)
    {
        this.account = account;
    }
}
