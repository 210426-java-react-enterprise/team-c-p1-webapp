package com.revature.web;

import com.revature.daos.AccountDAO;
import com.revature.p1.repos.DataSource;
import com.revature.p1.utils.EntityManager;
import com.revature.services.BankService;
import com.revature.util.TableBuilder;
import com.revature.web.servlets.AccountServlet;
import com.revature.web.servlets.AuthServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DependencyLoaderListener implements ServletContextListener {

    private Properties props = new Properties();

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            Class.forName("org.postgresql.Driver");
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream("application.properties");
            props.load(input);
            DataSource.setUrl(props.getProperty("host_url"));
            DataSource.setLogin(props.getProperty("db_login"));
            DataSource.setPassword(props.getProperty("db_password"));
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        EntityManager em = new EntityManager();
        TableBuilder builder = new TableBuilder();

        AccountDAO accountDAO = new AccountDAO(em);
        BankService bankService = new BankService(em, accountDAO);


        AccountServlet accountServlet = new AccountServlet(bankService, accountDAO, builder);
        AuthServlet authServlet = new AuthServlet(bankService, accountDAO);

        ServletContext context = sce.getServletContext();
        context.addServlet("BankServlet", accountServlet).addMapping("/account/*");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth/*");
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
