package com.revature.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.dtos.CredentialDTO;
import com.revature.dtos.newUserDTO;
import com.revature.exceptions.AuthenticationException;
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
import java.util.List;

public class AuthServlet extends HttpServlet {

    private BankService bankService;
    private UserDAO userDAO;
    private AccountDAO accountDAO;

    public AuthServlet(BankService bankService, UserDAO userDAO, AccountDAO accountDAO) {
        this.bankService = bankService;
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
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
                    User user = bankService.validateUser(creds);
                    resp.setStatus(202);
                    req.getSession().setAttribute("this-user", user);
                    List<Account> accounts = accountDAO.getAccountsByUserID(user);
                    req.getSession().setAttribute("user-accounts", accounts);
                    writer.write(user.toString() + " with accounts: " + accounts);
                } catch (AuthenticationException e) {
                    resp.setStatus(401);
                    writer.write(e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    writer.write(e.getMessage());
                    resp.setStatus(500);
                }
                break;
            case "register":
                try {
                    newUserDTO newUser = mapper.readValue(req.getInputStream(), newUserDTO.class);
                    User user = bankService.validateUser(newUser);
                    req.setAttribute("this-user", user);
                    resp.setStatus(201);
                    writer.write(user.toString());
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
