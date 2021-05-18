package com.revature.p1.screens;

import com.revature.p1.entities.Customer;
import com.revature.p1.orms.MyObjectRelationalMapper;
import com.revature.p1.utilities.InputValidator;

import java.sql.SQLException;
import java.util.Scanner;

public class CreateUserAccountScreen extends Screen
{

    private Scanner scanner;
    private InputValidator inputValidator;
    private MyObjectRelationalMapper orm;

    public CreateUserAccountScreen(Scanner scanner, InputValidator inputValidator, MyObjectRelationalMapper orm)
    {
        super("/create user");
        this.scanner = scanner;

        this.inputValidator = inputValidator;
        this.orm = orm;
    }

    @Override
    public void render()
    {

        System.out.println("\n\n\n*** Create a new user account ***\n\nEnter your desired username (5 - 15 alphanumeric): "); // edit to accept just
        // alphabetic usernames
        String readLine = scanner.nextLine();
        try
        {
            String username = inputValidator.validate(readLine, "/username");
            if (username == null) return;

            System.out.println("\nEnter your desired password (8 - 50): ");
            readLine = scanner.nextLine();
            String password = inputValidator.validate(readLine, "/password");
            if (password == null) return;

            System.out.println("Enter your first name (2 - 20 alphabetic):");
            readLine = scanner.nextLine();
            String firstName = inputValidator.validate(readLine, "/name");
            if (firstName == null) return;

            System.out.println("Enter your last name (2 - 20 alphabetic):");
            readLine = scanner.nextLine();
            String lastName = inputValidator.validate(readLine, "/name");
            if (lastName == null) return;

            System.out.println("Enter your social security  (9 numeric):");
            readLine = scanner.nextLine();
            String ssn = inputValidator.validate(readLine, "/ssn");
            if (ssn == null) return;

            System.out.println("Enter your email  (valid email address):");
            readLine = scanner.nextLine();
            String email = inputValidator.validate(readLine, "/email");
            if (email == null) return;

            System.out.println("Enter your phone  (9-13 digits):");
            readLine = scanner.nextLine();
            String phone = inputValidator.validate(readLine, "/phone");
            if (phone == null) return;

            System.out.println("*** Enter your home address ***\n\nunit number (if applies 1-3 digits):");
            readLine = scanner.nextLine();
            String unit = inputValidator.validate(readLine, "/unit");


            System.out.println("Street (10 - 30 alphanumeric): ");
            readLine = scanner.nextLine();
            String street = inputValidator.validate(readLine, "/street");
            if (street == null) return;

            System.out.println("City (2 - 15 alphabetic):");
            readLine = scanner.nextLine();
            String city = inputValidator.validate(readLine, "/city");
            if (city == null) return;

            System.out.println("State (2 - 15 alphabetic):");
            readLine = scanner.nextLine();
            String state = inputValidator.validate(readLine, "/state");
            if (state == null) return;

            System.out.println("Enter your zip code  (5 digits):");
            readLine = scanner.nextLine();
            String zip = inputValidator.validate(readLine, "/zip");
            if (zip == null) return;

            Customer newCustomer = new Customer(firstName, lastName, ssn, email, phone, username, password, unit, street, city, state, zip);

            //dao.addCustomer(newCustomer);

            System.out.println("Account created successfully.");

        } catch (SQLException e)

        {
            System.out.println("Account creation failed");
            e.printStackTrace();
        }

    }


}
