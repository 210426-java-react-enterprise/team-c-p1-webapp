package com.revature.p1.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.revature.orm.MyObjectRelationalMapper;
import com.revature.p1.persistance.ConnectionManager;
import com.revature.p1.services.HtmlService;
import com.revature.p1.services.WebUserService;
import com.revature.p1.utilities.InputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This class is tied to the startup and shutdown of tomcat. Just implement
 * the ServletContextListener and put whatever logic into the overridden
 * methods. Make sure you inform tomcat of this class by including it
 * in your deployment descriptor (web.xml) under the listener tag.
 */
public class DependencyLoaderListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        System.out.println("*********************************Starting contextInitialized****************************************");

        try
        {
            System.out.println("*********************************Starting try block****************************************");

            Logger logger = LogManager.getLogger();
            System.out.println("*********************************After logger****************************************");

            ObjectMapper objectMapper = new JsonMapper();
            System.out.println("*********************************After objectMapper****************************************");

            HtmlService htmlService = new HtmlService();
            System.out.println("*********************************After htmlService****************************************");

            MyObjectRelationalMapper orm = new MyObjectRelationalMapper(ConnectionManager.getInstance()
                                                                                         .getConnection());
            System.out.println("*********************************After orm****************************************");


            InputValidator iv = new InputValidator(orm);
            System.out.println("*********************************After iv****************************************");

            WebUserService webUserService = new WebUserService(orm, iv, logger);
            System.out.println("*********************************After webUserService****************************************");


            WelcomeServlet welcomeServlet = new WelcomeServlet(objectMapper, htmlService);
            AuthServlet authServlet = new AuthServlet(webUserService, objectMapper, logger, htmlService);
            AccountsServlet accountsServlet = new AccountsServlet(webUserService, objectMapper, logger, htmlService);
            NewAccountServlet newAccountServlet = new NewAccountServlet(webUserService, objectMapper, logger, htmlService);
            AccountServlet accountServlet = new AccountServlet(webUserService, objectMapper, logger, iv, htmlService);

            System.out.println("*********************************After servlet instantiation****************************************");


            ServletContext context = sce.getServletContext();

            System.out.println("*********************************After context****************************************");


            context.addServlet("welcomeServlet", welcomeServlet)
                   .addMapping("/");
            context.addServlet("authServlet", authServlet)
                   .addMapping("/login");
            context.addServlet("accountsServlet", accountsServlet)
                   .addMapping("/user/accounts");
            context.addServlet("newAccountServlet", newAccountServlet)
                   .addMapping("/user/accounts/new");
            context.addServlet("accountServlet", accountServlet)
                   .addMapping("/user/account");

            System.out.println("*********************************End of listener****************************************");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {

    }
}
