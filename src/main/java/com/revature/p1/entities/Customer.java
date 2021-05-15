package com.revature.p1.entities;


import java.util.ArrayList;
import java.util.List;

public class Customer
{

    private String firstName;
    private String lastName;
    private String ssn;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String unit;
    private String street;
    private String city;
    private String state;
    private String zip;
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

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
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
        this.username = username;
        this.password = password;
        this.unit = unit;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        accounts = new ArrayList<>();
    }

    public Customer()
    {
        accounts = new ArrayList<>();
    }
}
