package com.revature.web.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.LoginMapper;
import com.revature.models.Credential;
import com.revature.models.Customer;
import com.revature.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthServlet extends HttpServlet {

    private final UserService userService;


    public AuthServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter write = resp.getWriter();
        resp.setContentType("application/json");

        try{
            LoginMapper login = mapper.readValue(req.getInputStream(), LoginMapper.class);
            //Customer authCustomer = userService.authenticate(creds);
            Credential crd = userService.authenticate(login);
            resp.getWriter().println(crd.toString());
            // I continue here to create a session

        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
