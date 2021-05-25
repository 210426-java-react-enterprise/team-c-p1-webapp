package com.revature.web;

import com.revature.orm.MyObjectRelationalMapper;
import com.revature.repos.DataSource;
import com.revature.services.CustomerService;
import com.revature.web.servlets.AuthServlet;
import com.revature.web.servlets.BankServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class DependencyLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        MyObjectRelationalMapper orm = null;

        try {
            orm = new MyObjectRelationalMapper(DataSource.getInstance().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CustomerService customerService = new CustomerService(orm);

        BankServlet bankServlet = new BankServlet(customerService);
        AuthServlet authServlet = new AuthServlet(customerService);

        ServletContext context = sce.getServletContext();
        context.addServlet("BankServlet", bankServlet).addMapping("/bank");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth/*");
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
