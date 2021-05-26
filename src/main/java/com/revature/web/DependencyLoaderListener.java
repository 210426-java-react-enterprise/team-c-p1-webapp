package com.revature.web;

import com.revature.daos.UserDAO;
import com.revature.repos.ConnectionPool;
import com.revature.service.UserService;
import com.revature.web.servlet.AuthServlet;
import com.revature.web.servlet.UserServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DependencyLoaderListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {


        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        UserDAO userDao = new UserDAO();
        UserService userService = new UserService(connectionPool,userDao);
        AuthServlet authServlet = new AuthServlet(userService);
        UserServlet userServlet = new UserServlet(userService);

        ServletContext context = sce.getServletContext();
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
        context.addServlet("CustomerServlet", userServlet).addMapping("/user/*");
    }

}
