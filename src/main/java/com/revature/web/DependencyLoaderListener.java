package com.revature.web;

import com.revature.daos.UserDAO;
import com.revature.service.UserService;
import com.revature.web.servlet.AuthServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DependencyLoaderListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {

        UserDAO userDao = new UserDAO();
        UserService userService = new UserService(userDao);

        AuthServlet authServlet = new AuthServlet(userService);

        ServletContext context = sce.getServletContext();
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
    }

}
