package com.revature.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.CredentialDTO;
import com.revature.dtos.newUserDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.Credential;
import com.revature.models.Customer;
import com.revature.services.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.MalformedInputException;

public class AuthServlet extends HttpServlet {

    private CustomerService customerService;

    public AuthServlet(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
            resp.setStatus(202);
            resp.getWriter().write("Logged out successfully!");
        } else {
            resp.setStatus(404);
            resp.getWriter().write("There was no one logged in :(");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        String action = req.getParameter("action");

        switch (action) {
            case "login":
                try {
                    CredentialDTO creds = mapper.readValue(req.getInputStream(), CredentialDTO.class);
                    Customer customer = customerService.validateCredentials(creds);

                    req.getSession().setAttribute("this_user", customer);
                    resp.setStatus(202);
                    writer.write("Logged in successfully! your user is: " + customer.toString());
                } catch (AuthenticationException e) {
                    resp.setStatus(401);
                    writer.write(e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    resp.setStatus(500);
                }
                break;
            case "register":
                try {
                    newUserDTO newUser = mapper.readValue(req.getInputStream(), newUserDTO.class);
                    Customer customer = new Customer(newUser.getFirstName(), newUser.getLastName(), newUser.getSsn(),
                            newUser.getEmail(), newUser.getPhone());
                    customer = customerService.validateCustomer(customer);
                    Credential credential = customerService.checkCredentials(new CredentialDTO(newUser.getUsername(),
                            newUser.getPassword()));

                    //TODO: Addresses
                    customerService.registerCustomer(customer, credential);
                    resp.getWriter().write("Registration Successful!");
                } catch (MalformedInputException e) {
                    resp.setStatus(400);
                    e.printStackTrace();
                    writer.write(e.getMessage());
                } catch (InvalidRequestException e) {
                    resp.setStatus(400);
                    writer.write(e.getMessage());
                } catch (Exception e) {
                    resp.setStatus(500);
                    e.printStackTrace();
                    resp.getWriter().write("Something went wrong internally");
                }
                break;
            default:
                resp.setStatus(404);
                resp.getWriter().write("The requested resource was not found.");
        }
    }

}
