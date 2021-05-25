package com.revature.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.CredentialDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.models.Credential;
import com.revature.models.Customer;
import com.revature.orm.MyObjectRelationalMapper;
import com.revature.repos.DataSource;
import com.revature.services.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class BankServlet extends HttpServlet {

    private CustomerService customerService;

    public BankServlet(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }


}
