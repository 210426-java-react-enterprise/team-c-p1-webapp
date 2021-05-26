package com.revature.webapp.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.webapp.models.Customer;
import com.revature.webapp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {

    private final UserService userService;

    public UserServlet(UserService userService){
        this.userService = userService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter write = resp.getWriter();
        resp.setContentType("application/son");

        /*HttpSession session = req.getSession(true);
        Customer requestingCust = (session == null) ? null : (Customer) session.getAttribute("this-user");

        if(requestingCust == null) {
            resp.setStatus(401);
            return;
        }*/

        String custSSN = req.getParameter("ssn");

        try{
            if (custSSN != null) {
              Customer customer = userService.bringCustomerData(custSSN);
                if(customer==null){
                    resp.setStatus(401);
                }else{
                    resp.setStatus(200);
                    write.write(mapper.writeValueAsString(customer.toString()));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

