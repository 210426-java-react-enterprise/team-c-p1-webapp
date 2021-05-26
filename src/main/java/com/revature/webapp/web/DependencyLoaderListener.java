package com.revature.webapp.web;

import com.revature.webapp.daos.UserDAO;
import com.revature.webapp.web.servlet.UserServlet;
import com.revature.webapp.repos.ConnectionPool;
import com.revature.webapp.service.UserService;
import com.revature.webapp.web.servlet.AuthServlet;


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
