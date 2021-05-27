package com.revature.p1.servlet;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.p1.dtos.TransactionDTO;
import com.revature.p1.entities.Account;
import com.revature.p1.entities.Customer;
import com.revature.p1.entities.Transaction;
import com.revature.p1.exceptions.IllegalInputException;
import com.revature.p1.exceptions.OverDraftException;
import com.revature.p1.services.HtmlService;
import com.revature.p1.services.WebUserService;
import com.revature.p1.utilities.InputValidator;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class AccountServlet extends HttpServlet
{
    private final WebUserService webUserService;
    private final ObjectMapper mapper;
    private final Logger logger;
    private final InputValidator iv;
    private final HtmlService htmlService;


    public AccountServlet(WebUserService webUserService, ObjectMapper mapper, Logger logger, InputValidator iv,
            HtmlService htmlService)
    {
        this.webUserService = webUserService;
        this.mapper = mapper;
        this.logger = logger;
        this.iv = iv;
        this.htmlService = htmlService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        PrintWriter writer = resp.getWriter();

        if (req.getSession()
               .getAttribute("user") != null)
        {
            Customer customer = ((Customer) req.getSession()
                                               .getAttribute("user"));
            logger.info(String.format("Number of accounts for this customer: %s", customer.getAccounts()
                                                                                          .size()));
            //writer.println("<h4 style=\"color:red\"> " + account + "</h4>");
            customer.getAccounts()
                    .forEach(savable -> writer.println("<h3 style=\"color:red\"> " + savable));
            writer.println("<h2 style=\"color:blue\"> Do POST ");
            writer.println("<h2 style=\"color:blue\">  {\"number\":\"your account number to use\",");
            writer.println("<h2 style=\"color:blue\">  {\"type\":\"deposit/withdraw\",");
            writer.println("<h2 style=\"color:blue\">  {\"amount\":\"amount to deposit/withdraw\",");
        }
        else
        {
            logger.info("'req.getSession().getAttribute(\"user\")' is null");
            writer.println("<h1 style=\"color:red;\"> You are not logged in.</h1>");
            writer.println("<h2 style=\"color:red;\"> To login do: GET /user/login");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        try
        {
            PrintWriter writer = resp.getWriter();
            Customer customer = (Customer) req.getSession()
                                              .getAttribute("user");
            if (customer != null)
            {
                TransactionDTO transactionDTO = mapper.readValue(req.getInputStream(), TransactionDTO.class);
                logger.info(transactionDTO);
                iv.validate(String.valueOf(transactionDTO.getNumber()), "/account number");
                iv.validate(String.valueOf(transactionDTO.getAmount()), "/deposit");
                if (!(transactionDTO.getType()
                                    .equalsIgnoreCase("deposit") || transactionDTO.getType()
                                                                                  .equalsIgnoreCase("withdraw")))
                {
                    throw new IllegalInputException();
                }

                Optional<Account> optional = (Optional<Account>) customer.getAccounts()
                                                                         .stream()
                                                                         .filter(account -> ((Account) account).getNumber() == transactionDTO.getNumber())
                                                                         .findAny();
                optional.ifPresent(account ->
                                   {
                                       try
                                       {
                                           req.getSession(false)
                                              .setAttribute("account", account);
                                           if (transactionDTO.getType()
                                                             .equals("deposit"))
                                           {
                                               account.deposit(transactionDTO.getAmount());
                                           }
                                           else
                                           {
                                               logger.info(transactionDTO.getAmount());
                                               if (account.withdraw(transactionDTO.getAmount()) == -1)
                                                   throw new OverDraftException();
                                           }
                                           webUserService.update(account);
                                           Transaction transaction = new Transaction(transactionDTO.getType(), transactionDTO.getAmount(), account.getBalance(),
                                                                                     account.getNumber());
                                           webUserService.save(transaction);
                                           Customer currentCustomer = (Customer) req.getSession(false)
                                                                                    .getAttribute("user");
                                           logger.info(currentCustomer);
                                           currentCustomer = webUserService.refreshCustomer(currentCustomer.getCredential());

                                           logger.info(currentCustomer);
                                           req.getSession()
                                              .setAttribute("user", currentCustomer);
                                           writer.println(htmlService.singleRow(String.format("%s on Account: %s was completed.", transaction + "<br>",
                                                                                              optional.get()), "red", "#303030"));
                                       } catch (OverDraftException e)
                                       {
                                           writer.println(htmlService.singleRow("There was not enough funds in this account to complete the transaction.",
                                                                                "red","#303030"));
                                           logger.info(e);
                                       }
                                   });
            }
            else
            {
                logger.info("'req.getSession().getAttribute(\"user\")' is null");
                writer.println("<h1 style=\"color:red;\"> You are not logged in.</h1>");
                writer.println("<h2 style=\"color:red;\"> To login do: GET /user/login");
            }
        } catch (IllegalInputException e)
        {
            logger.warn(e.getMessage());
            logger.warn("Exception in accountServlet.doPost()", e);
            resp.setStatus(400);
        } catch (JsonMappingException e)
        {
            logger.warn(e.getMessage());
            logger.warn("Exception in accountServlet.doPost()", e);
            resp.setStatus(400);
        } catch (JsonParseException e)
        {
            logger.warn(e.getMessage());
            logger.warn("Exception in accountServlet.doPost()", e);
            resp.setStatus(400);
        } catch (NullPointerException | IOException e)
        {
            logger.warn(e.getMessage());
            logger.warn("Exception in accountServlet.doPost()", e);
            resp.setStatus(500);
        }
    }
}
