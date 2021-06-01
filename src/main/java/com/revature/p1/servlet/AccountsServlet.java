package com.revature.p1.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.p1.entities.Customer;
import com.revature.p1.services.HtmlService;
import com.revature.p1.services.WebUserService;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class AccountsServlet extends HttpServlet
{
    private final WebUserService webUserService;
    private final ObjectMapper mapper;
    private final Logger logger;
    private final HtmlService htmlService;

    public AccountsServlet(WebUserService webUserService, ObjectMapper mapper, Logger logger, HtmlService htmlService)
    {
        this.webUserService = webUserService;
        this.mapper = mapper;
        this.logger = logger;
        this.htmlService = htmlService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        logger.info(String.format("%s Handling the request", this.getServletName()));
        logger.info(String.format("Request details: %s - %s - %s", req.getRequestedSessionId(), req.getRequestURI(),
                                  req.getServletPath()));

        PrintWriter writer = resp.getWriter();
        if (req.getSession().getAttribute("user") != null)
        {
            logger.info(String.format("'req.getSession().getAttribute(\"user\")' is not null"));
            try
            {
                Customer customer = (Customer) req.getSession(false)
                                                  .getAttribute("user");
                String first_name = customer.getFirstName()
                                            .substring(0, 1)
                                            .toUpperCase() + customer.getFirstName()
                                                                     .substring(1)
                                                                     .toLowerCase();
                writer.printf("<h1 style=\"color:red;\"> Hello %s, here are your accounts:", first_name);
                customer.getAccounts()
                        .forEach(account ->
                                         writer.println("<h4 style=\"color:blue\"> " + account + "</h4>"));
                writer.println("<h2 style=\"color:blue;\"> To open a new account do: GET /user/accounts/new");
                writer.println("<h2 style=\"color:blue;\"> To make a transaction use: GET /user/transaction/new");
                writer.println("<h2 style=\"color:blue;\"> To go back do: GET /user");

            } catch (Exception e)
            {
                logger.error("Inside the catch block!");
                Arrays.stream(e.getStackTrace())
                             .forEach(StackTraceElement::toString);

            }
        } else
        {
            logger.info(String.format("'req.getSession().getAttribute(\"user\")' is null"));
            writer.println("<h1 style=\"color:red;\"> You are not logged in.</h1>");
            writer.println("<h2 style=\"color:red;\"> To login do: GET /user/login");
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.getWriter().println("<h1> Nothing interesting here yet! </h1>");
    }
}
