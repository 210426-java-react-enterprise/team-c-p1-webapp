package com.revature.p1.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionManager {

    private Properties props = new Properties();
    public static ConnectionManager connectionManager;
    private String url;
    private String username;
    private String password;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ConnectionManager()
    {
            url = System.getProperty("host-url");
            username = System.getProperty("username");
            password = System.getProperty("password");

    }

    public static ConnectionManager getInstance()
    {
        if (connectionManager == null)
        {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    public Connection getConnection()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
          return connection;
    }

    public static boolean releaseConnection(Connection connection)
    {
        return DbConnectionPool.getInstance().releaseConnection(connection);
    }
}
