package com.revature.p1.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbConnectionPool implements ConnectionPool
{
    private static final List<Connection> availableConnections;
    private static final List<Connection> usedConnections;
    private static DbConnectionPool instance;
    private static final String url;
    private static final String username;
    private static final String password;

    static
    {
        availableConnections = new ArrayList<>();
        usedConnections = new ArrayList<>();
        url = System.getProperty("host-url");
        username = System.getProperty("username");
        password = System.getProperty("password");
        try
        {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    private DbConnectionPool()
    {
    }
    public static DbConnectionPool getInstance()
    {
        if (instance == null)
        {
            instance = new DbConnectionPool();
            try
            {
                for (int i = 0; i < 5; i++)
                {
                    availableConnections.add(DriverManager.getConnection(url,username,password));
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return instance;
    }
    @Override
    public Connection getConnection()
    {
        Connection connection = availableConnections.remove(availableConnections.size() - 1);
        usedConnections.add(connection);
        System.out.println("Available connections: " + availableConnections.size());
        System.out.println("Used connections: " + usedConnections.size());
        return connection;
    }
    @Override
    public boolean releaseConnection(Connection connection)
    {
        availableConnections.add(connection);
        return usedConnections.remove(connection);
    }
    public int getUsedConnectionSize()
    {
        return usedConnections.size();
    }
    public int getAvailableConnectionSize()
    {
        return availableConnections.size();
    }
}
