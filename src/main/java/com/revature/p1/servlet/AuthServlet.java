package com.revature.p1.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.p1.dtos.LoginDTO;
import com.revature.p1.entities.Customer;
import com.revature.p1.exceptions.InvalidLoginException;
import com.revature.p1.services.HtmlService;
import com.revature.p1.services.WebUserService;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class AuthServlet extends HttpServlet
{
    private final WebUserService webUserService;
    private final ObjectMapper mapper;
    private final Logger logger;
    private final HtmlService htmlService;

    public AuthServlet(WebUserService webUserService, ObjectMapper mapper, Logger logger, HtmlService htmlService)
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
        writer.println("<h1 style=\"color:red\"> Please provide your username and password in the body using raw json and do POST</h1>");
        String message = "{<br>\"username\":\"your username here\",<br>\"password\":\"your password here<br>\"}";
        writer.println("<h2 style=\"color:blue\">" + message + "</h2>");
        logger.info("Prompting user to log in");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        logger.info(String.format("Receiving request from %s", req.getRemoteAddr()));
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try
        {
            LoginDTO login = mapper.readValue(req.getInputStream(), LoginDTO.class);
//            writer.println("<h1> I received: " + login + " </h1>");
            Customer customer = webUserService.authenticate(login, req, resp);
            logger.info(customer);
            //writer.println("<h1> " + customer + " </h1>");
            if (customer != null)
            {
                req.getSession()
                   .setAttribute("user", customer);
                String first_name = customer.getFirstName().substring(0,1).toUpperCase () + customer.getFirstName().substring(1).toLowerCase();
                writer.printf("<h1 style=\"color:red;\"> Welcome '%s' to your friendly neighborhood bank. </h1>", first_name);
                writer.println("<h2 style=\"color:blue;\"> To view your accounts do:\t GET /user/accounts </h2>");
                writer.println("<h2 style=\"color:blue;\"> To open a new account use:\t GET /user/accounts/new </h2>");
                writer.println("<h2 style=\"color:blue;\"> To make a transaction use:\t GET /user/transactions/new </h2>");

            } else
            {
                if (req.getSession() != null) req.getSession().setAttribute("", null);
            }

        } catch (MismatchedInputException e)
        {
            e.printStackTrace();
            resp.setStatus(400);
        } catch (InvalidLoginException e)
        {
            resp.setStatus(401);
        } catch (Exception e)
        {
            resp.setStatus(500);
        }

    }


}















