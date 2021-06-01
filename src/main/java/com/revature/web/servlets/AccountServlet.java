package com.revature.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.AccountDAO;
import com.revature.dtos.AccountDTO;
import com.revature.dtos.TransactionDTO;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.services.BankService;
import com.revature.util.TableBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AccountServlet extends HttpServlet {

    private BankService bankService;
    private AccountDAO accountDAO;
    private TableBuilder tableBuilder;

    public AccountServlet(BankService bankService, AccountDAO accountDAO, TableBuilder tableBuilder) {
        this.bankService = bankService;
        this.accountDAO = accountDAO;
        this.tableBuilder = tableBuilder;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();

        if (session == null) {
            writer.write("You must be logged in to perform this action.");
            resp.setStatus(404);
            return;
        }

        User user = (User) session.getAttribute("this-user");
        List<Account> accounts;

        switch (req.getParameter("action")) {
            case "open":
                try {
                    AccountDTO newAccount = mapper.readValue(req.getInputStream(), AccountDTO.class);
                    accounts = (List<Account>) session.getAttribute("user-accounts");
                    Account account = bankService.validateAccount(newAccount, user.getUserID());

                    accounts.add(account);
                    session.setAttribute("user-accounts", accounts);
                    resp.setStatus(201);
                    writer.write("<h1>Your new account was created! </h1>" + tableBuilder.buildAccountTable(Collections.singletonList(account)));
                } catch (InvalidRequestException e) {
                    resp.setStatus(400);
                    writer.write(e.getMessage());
                } catch (Exception e) {
                    resp.setStatus(500);
                    e.printStackTrace();
                    writer.write("Something went wrong internally. (" + e.getMessage() + ")");
                }
                break;
            case "manage":
                try {
                    accounts = (ArrayList<Account>) session.getAttribute("user-accounts");
                    TransactionDTO trans = mapper.readValue(req.getInputStream(), TransactionDTO.class);

                    Account userAccount = bankService.handleTransaction(trans, accounts, user);
                    resp.setStatus(200);
                    writer.write("<h1>Transaction successful! Your account details are as follows: <br> </h1>" + tableBuilder.buildAccountTable(Collections.singletonList(userAccount)));
                } catch (InvalidRequestException e) {
                    resp.setStatus(400);
                    writer.write(e.getMessage());
                } catch (Exception e) {
                    resp.setStatus(500);
                    e.printStackTrace();
                    writer.write("Something went wrong internally. (" + e.getMessage() + ")");
                }
                break;
            default:
                resp.setStatus(404);
                writer.write("Nothing was found by this action. Check the URL and try again.");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();

        if (session == null) {
            writer.write("You must be logged in to perform this action.");
            resp.setStatus(404);
            return;
        }

        List<Account> accounts;
        User user = (User) session.getAttribute("this-user");

        switch (req.getParameter("view")) {
            case "account":
                try {
                    accounts = accountDAO.getAccountsByUserID(user);

                    if (accounts.isEmpty()) {
                        resp.setStatus(404);
                        writer.write("You have no registered accounts.");
                        return;
                    }

                    writer.write(String.valueOf(tableBuilder.buildAccountTable(accounts)));
                    resp.setStatus(200);
                } catch (Exception e) {
                    resp.setStatus(500);
                    e.printStackTrace();
                    writer.write("Something went wrong internally. (" + e.getMessage() + ")");
                }
                break;
            case "transaction":
                try {
                    List<Transaction> transactions = bankService.getUserTransactions(user);

                    if (transactions.isEmpty()) {
                        resp.setStatus(404);
                        writer.write("You have no transactions to list.");
                    }

                    writer.write(String.valueOf(tableBuilder.buildTransactionTable(transactions)));
                } catch (Exception e) {
                    resp.setStatus(500);
                    e.printStackTrace();
                    writer.write("Something went wrong internally. (" + e.getMessage() + ")");
                }
                break;
            default:
                resp.setStatus(404);
                writer.write("Nothing was found by this action. Check the URL and try again.");
        }
    }
}
