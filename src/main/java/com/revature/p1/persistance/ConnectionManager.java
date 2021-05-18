package com.revature.p1.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    public ConnectionManager(Properties properties)
    {

    }

    public Connection getConnection()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(
                    System.getenv("host-url"),
                    System.getenv("username"),
                    System.getenv("Sean-Java-React-Course")
                    );
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return connection;
    }
}
