package com.revature.services;

import com.revature.dtos.CredentialDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.models.Credential;
import com.revature.models.Customer;
import com.revature.orm.MyObjectRelationalMapper;

public class CustomerService {

    private MyObjectRelationalMapper orm;

    public CustomerService (MyObjectRelationalMapper orm) {
        this.orm = orm;
    }

    public Customer validateCredentials (CredentialDTO credentials) {
        Credential credential = (Credential) orm.readRow(new Credential(credentials.getUsername(), null, null));

        if (credential.getSsn().isEmpty() || !credential.getPassword().equals(credentials.getPassword())) {
            throw new AuthenticationException("Either the username or password was incorrect");
        } else {
           Customer customer = (Customer) orm.readRow(new Customer(credential.getSsn()));
           return customer;
        }
    }
}
