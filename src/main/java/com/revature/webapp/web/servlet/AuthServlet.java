package com.revature.webapp.web.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.webapp.dtos.LoginMapper;
import com.revature.webapp.models.Credential;
import com.revature.webapp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            HttpSession session = req.getSession(true);
            Credential crd = userService.authenticate(login);
            if(crd==null){
                resp.setStatus(401);
            }else{
                resp.setStatus(200);
                write.write(mapper.writeValueAsString(crd.toString()));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
