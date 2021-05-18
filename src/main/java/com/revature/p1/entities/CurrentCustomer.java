package com.revature.p1.entities;


public class CurrentCustomer
{
    private Customer customer;
    private static CurrentCustomer instance;

    private CurrentCustomer()
    {
    }

    public static CurrentCustomer getInstance()
    {
        if (instance == null)
        {
            instance = new CurrentCustomer();
        }
        return instance;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public Customer getCustomer()
    {
        return customer;
    }
}
