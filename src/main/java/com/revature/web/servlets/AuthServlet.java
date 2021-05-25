package com.revature.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.CredentialDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.models.Customer;
import com.revature.services.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthServlet extends HttpServlet {

    private CustomerService customerService;

    public AuthServlet(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if(session != null) {
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
    }

}
