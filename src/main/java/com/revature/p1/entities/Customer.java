package com.revature.p1.entities;


import com.revature.p1.annotations.ColumnType;
import com.revature.p1.annotations.MyColumn;
import com.revature.p1.annotations.MyEntity;

import java.util.ArrayList;
import java.util.List;


@MyEntity(name = "customer")
public class Customer extends MySavable
{
    @MyColumn(  name = "first_name",type = ColumnType.VARCHAR,length = 20,
                nullable = false,pk = false,fk = false,reference = "",unique = false,delete = "cascade")
    private String firstName;

    @MyColumn(  name = "last_name",type = ColumnType.VARCHAR,length = 20,
                nullable = false,pk = false,fk = false,reference = "",unique = false,delete = "cascade")
    private String lastName;

    @MyColumn(  name = "ssn",type = ColumnType.VARCHAR,length = 12,
                nullable = false,pk = true,fk = false,reference = "",unique = true,delete = "cascade")
    private String ssn;

    @MyColumn(  name = "email",type = ColumnType.VARCHAR,length = 20,
                nullable = false,pk = false,fk = false,reference = "",unique = false,delete = "cascade")
    private String email;

    @MyColumn(  name = "phone",type = ColumnType.VARCHAR,length = 15,
                nullable = false,pk = false,fk = false,reference = "",unique = false,delete = "cascade")
    private String phone;


    private List<? extends MySavable> accounts;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public List<? extends MySavable> getAccounts()
    {
        return accounts;
    }

    public void setAccounts(List<? extends MySavable> accounts)
    {
        this.accounts = accounts;
    }

    public Customer(String firstName, String lastName, String ssn, String email, String phone)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.email = email;
        this.phone = phone;
        this.accounts = new ArrayList<>();
    }
    public Customer(MySavable savable)
    {
        this.email = ((Customer) savable).email;
        this.firstName = ((Customer) savable).firstName;
        this.lastName = ((Customer) savable).lastName;
        this.phone = ((Customer) savable).phone;
        this.accounts = ((Customer) savable).accounts;
        this.ssn = ((Customer) savable).ssn;
    }

    public Customer()
    {
        accounts = new ArrayList<>();
    }
}
