package com.revature.servlet;

import controller.UserController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Dispatcher extends HttpServlet {


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        switch (req.getRequestURI()){
            case "/bank/auth.data":
                UserController.authenticate(req,resp);
                break;
            case "/bank/user.data":

                break;
            default:
                resp.setStatus(400);
                resp.getWriter().println(req.getRequestURI());
        }

    }


}
