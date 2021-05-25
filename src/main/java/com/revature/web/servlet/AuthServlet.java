package com.revature.web.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
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
            //Credential creds = mapper.readValue()

        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
