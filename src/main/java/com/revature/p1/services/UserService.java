package com.revature.p1.services;

import com.revature.p1.entities.Customer;
import com.revature.p1.orms.MyObjectRelationalMapper;
import com.revature.p1.utilities.datasource.Session;

public class UserService
{
    private MyObjectRelationalMapper orm;
    private Customer customer;
    private Session session;

    public UserService(MyObjectRelationalMapper orm, Customer customer, Session session)
    {
        this.orm = orm;
        this.customer = customer;
        this.session = session;
    }

    public void authenticate(String username, String password)
    {

    }

    public void register(Customer customer)
    {

    }
}
