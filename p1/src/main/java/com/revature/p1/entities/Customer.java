package com.revature.p1.entities;


import com.revature.p1.annotations.ColumnType;
import com.revature.p1.annotations.MyColumn;

import java.util.ArrayList;
import java.util.List;

public class Customer
{
    @MyColumn(  table = "customers",name = "first_name",type = ColumnType.VARCHAR,length = 20,
                nullable = false,pk = false,fk = false,reference = "",unique = false,delete = "cascade")
    private String firstName;

    @MyColumn(  table = "customers",name = "last_name",type = ColumnType.VARCHAR,length = 20,
                nullable = false,pk = false,fk = false,reference = "",unique = false,delete = "cascade")
    private String lastName;

    @MyColumn(  table = "customers",name = "ssn",type = ColumnType.VARCHAR,length = 12,
                nullable = false,pk = true,fk = false,reference = "",unique = true,delete = "cascade")
    private String ssn;

    @MyColumn(  table = "customers",name = "email",type = ColumnType.VARCHAR,length = 20,
                nullable = false,pk = false,fk = false,reference = "",unique = false,delete = "cascade")
    private String email;

    @MyColumn(  table = "customers",name = "phone",type = ColumnType.VARCHAR,length = 15,
                nullable = false,pk = false,fk = false,reference = "",unique = false,delete = "cascade")
    private String phone;


    private List<Account> accounts;

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


    public List<Account> getAccounts()
    {
        return accounts;
    }

    public Customer(String firstName, String lastName, String ssn, String email, String phone, String username, String password,
            String unit, String street, String city, String state, String zip)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.email = email;
        this.phone = phone;

        accounts = new ArrayList<>();
    }

    public Customer()
    {
        accounts = new ArrayList<>();
    }
}
