package com.revature.services;

import com.revature.dtos.CredentialDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.Address;
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
            return (Customer) orm.readRow(new Customer(credential.getSsn()));
        }
    }

    public Customer validateCustomer(Customer customer) {
        if (customer.getSsn().length() != 9)
            throw new InvalidRequestException("The SSN was invalid(must be 9 digits)");

        if (customer.getFirstName().isEmpty() || customer.getFirstName().length() < 3 || customer.getFirstName().length() > 20)
            throw new InvalidRequestException("First name was invalid");

        if (customer.getLastName().isEmpty() || customer.getLastName().length() < 3 || customer.getLastName().length() > 20)
            throw new InvalidRequestException("Last name was invalid");

        if (customer.getEmail().isEmpty() || customer.getEmail().length() < 3 || customer.getEmail().length() > 255)
            throw new InvalidRequestException("Email was invalid");

        if (customer.getPhone().length() >= 15)
            throw new InvalidRequestException("Phone number was invalid");

        return customer;
    }

    public Address validateAddress(Address address) {
        if(address.getStreet().isEmpty() || address.getStreet().length() <= 3 || address.getStreet().length() > 30)
            throw new InvalidRequestException("Street was invalid");

        if(address.getCity().isEmpty() || address.getCity().length() <= 3 || address.getCity().length() > 15)
            throw new InvalidRequestException("City was invalid");

        if(address.getState().isEmpty() || address.getState().length() <= 1 || address.getState().length() > 30)
            throw new InvalidRequestException("State was invalid");

        if(address.getZip().length() != 5)
            throw new InvalidRequestException("Zip was invalid");

        return address;
    }

    public Credential checkCredentials(CredentialDTO credentialDTO) {
        if (credentialDTO.getUsername().isEmpty() || credentialDTO.getUsername().length() < 3 || credentialDTO.getUsername().length() > 15)
            throw new InvalidRequestException("The username was either empty, too long, or too short.");

        if (credentialDTO.getPassword().isEmpty() || credentialDTO.getPassword().length() < 3 || credentialDTO.getPassword().length() > 50)
            throw new InvalidRequestException("The password was either to long or too short.");

        return new Credential(credentialDTO.getUsername(), credentialDTO.getPassword(), "");
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
