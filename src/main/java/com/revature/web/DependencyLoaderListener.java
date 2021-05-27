package com.revature.web;

import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
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
    private String url;
    private String login;
    private String password;

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



        UserDAO userDAO = new UserDAO(em);
        AccountDAO accountDAO = new AccountDAO(em);
        BankService bankService = new BankService(em, accountDAO);
        TableBuilder builder = new TableBuilder();

        AccountServlet accountServlet = new AccountServlet(bankService, userDAO, accountDAO, builder);
        AuthServlet authServlet = new AuthServlet(bankService, userDAO, accountDAO);

        ServletContext context = sce.getServletContext();
        context.addServlet("BankServlet", accountServlet).addMapping("/account/*");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth/*");
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
