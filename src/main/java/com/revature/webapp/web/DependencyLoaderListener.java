package com.revature.webapp.web;

import com.revature.orm.repos.ConnectionPool;
import com.revature.orm.services.ObjectService;
import com.revature.webapp.web.servlet.UserServlet;
import com.revature.webapp.service.UserService;
import com.revature.webapp.web.servlet.AuthServlet;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;




public class DependencyLoaderListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
    
    
        ObjectService objectService = ObjectService.getInstance();
        UserService userService = new UserService(objectService);
        AuthServlet authServlet = new AuthServlet(userService);
        UserServlet userServlet = new UserServlet(userService);

        ServletContext context = sce.getServletContext();
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
        context.addServlet("CustomerServlet", userServlet).addMapping("/user/*");
    }

}
