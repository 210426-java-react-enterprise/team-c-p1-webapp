package com.revature.services;

import com.revature.dtos.CredentialDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.Credential;
import com.revature.models.Customer;
import com.revature.orm.MyObjectRelationalMapper;

public class CustomerService {

    private MyObjectRelationalMapper orm;

    public CustomerService(MyObjectRelationalMapper orm) {
        this.orm = orm;
    }

    public Customer validateCredentials(CredentialDTO credentials) {
        Credential credential = (Credential) orm.readRow(new Credential(credentials.getUsername(), null, null));

        if (credential.getSsn().isEmpty() || !credential.getPassword().equals(credentials.getPassword())) {
            throw new AuthenticationException("Either the username or password was incorrect");
        } else {
            Customer customer = (Customer) orm.readRow(new Customer(credential.getSsn()));
            return customer;
        }
    }

    public Customer validateCustomer(Customer customer) {
        if (customer.getSsn().length() != 9)
            throw new InvalidRequestException("The SSN was invalid(must be 9 digits)");

        if (customer.getFirstName().isEmpty() || customer.getFirstName().length() < 3 && customer.getFirstName().length() > 20)
            throw new InvalidRequestException("First name was invalid");

        if (customer.getLastName().isEmpty() || customer.getLastName().length() < 3 && customer.getFirstName().length() > 20)
            throw new InvalidRequestException("Last name was invalid");

        if (customer.getEmail().isEmpty() || customer.getEmail().length() < 3 && customer.getEmail().length() > 255)
            throw new InvalidRequestException("Email was invalid");

        if (customer.getPhone().length() >= 15)
            throw new InvalidRequestException("Phone number was invalid");

        return customer;
    }

    public Credential checkCredentials(CredentialDTO credentialDTO) {
        if (credentialDTO.getUsername().isEmpty() || credentialDTO.getUsername().length() < 3 || credentialDTO.getUsername().length() > 15)
            throw new InvalidRequestException("The username was either empty, too long, or too short.");

        if (credentialDTO.getPassword().isEmpty() || credentialDTO.getPassword().length() < 3 || credentialDTO.getPassword().length() > 50)
            throw new InvalidRequestException("The password was either to long or too short.");

        Credential credential = new Credential(credentialDTO.getUsername(), credentialDTO.getPassword(), "");

        return credential;
    }

    public void registerCustomer(Customer customer, Credential credential) {
        try {
            credential.setSsn(customer.getSsn());
            orm.saveNewData(customer);
            orm.saveNewData(credential);
        } catch (Exception e) {
            throw new RuntimeException("something went wrong internally while registering the user");
        }

    }

}
