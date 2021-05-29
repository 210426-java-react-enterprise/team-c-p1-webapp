package com.revature.p1.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.p1.dtos.AccountTypeDTO;
import com.revature.p1.entities.*;
import com.revature.p1.services.HtmlService;
import com.revature.p1.services.WebUserService;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class NewAccountServlet extends HttpServlet
{
    private final WebUserService webUserService;
    private final ObjectMapper mapper;
    private final Logger logger;
    private final HtmlService htmlService;


    public NewAccountServlet(WebUserService webUserService, ObjectMapper mapper, Logger logger, HtmlService htmlService)
    {
        this.webUserService = webUserService;
        this.mapper = mapper;
        this.logger = logger;
        this.htmlService = htmlService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        PrintWriter writer = resp.getWriter();
        if (req.getSession().getAttribute("user") != null)
        {
            writer.println("<h1 style=\"color:red\"> Please provide your choice of account type in the body using raw json and do POST</h1>");
            String message = "{\n\t\"type\":\"account type you want to open\"}";
            writer.println("<h2 style=\"color:blue\">" + message + "</h2>");
            writer.println("<h3 style=\"color:blue\"> Choices: checking, savings, trust");
            logger.info("Prompting user to choose an account type");
        } else
        {
            logger.info("'req.getSession().getAttribute(\"user\")' is null");
            writer.println("<h1 style=\"color:red;\"> You are not logged in.</h1>");
            writer.println("<h2 style=\"color:red;\"> To login do: GET /user/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        PrintWriter writer = resp.getWriter();
        if (req.getSession().getAttribute("user") != null)
        {
            Customer customer = ((Customer)req.getSession().getAttribute("user"));
            Account newAccount = null;
            logger.info("Attempting to create 'accountDTO' object from");
            AccountTypeDTO accountTypeDTO = mapper.readValue(req.getInputStream(), AccountTypeDTO.class);
            logger.info(accountTypeDTO);

            switch (accountTypeDTO.getType().toLowerCase())
            {
                case "checking":
                    newAccount = new CheckingAccount(((Customer)req.getSession().getAttribute("user")).getSsn());
                    break;
                case "savings":
                    newAccount = new SavingsAccount(((Customer)req.getSession().getAttribute("user")).getSsn());
                    break;
                case "trust":
                    newAccount = new TrustAccount(((Customer)req.getSession().getAttribute("user")).getSsn());
                    break;
                default:
                    logger.info("default case on switch reached - account type was not correct");
                    writer.println("<h2 style=\"color:red;\"> Incorrect account type entered.");
            }
            if (newAccount != null)
            {
                logger.info("Attempting to add the new account to the database");
                logger.info("Current customer before the update: " + customer);
                webUserService.addNewAccount(newAccount);
                Credential credential = customer.getCredential();
                Customer updatedCustomer = webUserService.refreshCustomer(customer.getCredential());
                updatedCustomer.setCredential(credential);
                logger.info("Current customer after the update: " + updatedCustomer);
                req.getSession(false).setAttribute("user", updatedCustomer);
                writer.println(String.format("<h1 style=\"color:red;\"> Your new account: %s was successfully created.",
                                             updatedCustomer.getAccounts().get(updatedCustomer.getAccounts().size() - 1)));
                writer.println("<h2 style=\"color:blue;\"> To view your accounts do: GET /user/accounts </h2>");
                writer.println("<h2 style=\"color:blue;\"> To open a new account do: GET /user/accounts/new");
                writer.println("<h2 style=\"color:blue;\"> To make a transaction use: GET /user/transaction/new");
                writer.println("<h2 style=\"color:blue;\"> To go back do: GET /user");
            }
        } else
        {
            logger.info("'req.getSession().getAttribute(\"user\")' is null");
            writer.println("<h1 style=\"color:red;\"> You are not logged in.</h1>");
            writer.println("<h2 style=\"color:red;\"> To login do: GET /user/login");
        }
    }
}
