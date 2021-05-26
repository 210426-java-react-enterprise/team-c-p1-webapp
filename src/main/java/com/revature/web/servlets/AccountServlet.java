package com.revature.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.dtos.AccountDTO;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.BankService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AccountServlet extends HttpServlet {

    private BankService bankService;
    private UserDAO userDAO;
    private AccountDAO accountDAO;

    public AccountServlet(BankService bankService, UserDAO userDAO, AccountDAO accountDAO) {
        this.bankService = bankService;
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();

        if(session == null) {
            writer.write("You must be logged in to perform this action.");
            resp.setStatus(404);
            return;
        }

        switch (req.getParameter("action")) {
            case "open" : {
                try {
                    User user = (User) session.getAttribute("this-user");
                    AccountDTO newAccount = mapper.readValue(req.getInputStream(), AccountDTO.class);

                    Account account = bankService.validateAccount(newAccount, user.getUserID());
                    resp.setStatus(201);
                    writer.write("Your new account was created! " + account);
                } catch (InvalidRequestException e) {
                    resp.setStatus(400);
                    writer.write(e.getMessage());
                } catch (Exception e) {
                    resp.setStatus(500);
                    e.printStackTrace();
                    writer.write("Something went wrong internally. (" + e.getMessage() + ")");
                }
            }
        }


    }


}
