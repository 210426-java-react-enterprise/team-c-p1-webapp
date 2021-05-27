package com.revature.p1.persistance;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionManager {

    private Properties props = new Properties();
    public static ConnectionManager connectionManager;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ConnectionManager()
    {
        try
        {
            InputStream inputStream = Thread.currentThread()
                                            .getContextClassLoader()
                                            .getResourceAsStream("application.properties");
            props.load(inputStream);
        }  catch (IOException e)
        {
            e.printStackTrace();
        }
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
            connection = DriverManager.getConnection(props.getProperty("host-url"),
                                                     props.getProperty("username"),
                                                     props.getProperty("password"));
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
