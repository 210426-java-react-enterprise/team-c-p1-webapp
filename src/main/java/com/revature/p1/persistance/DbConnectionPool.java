package com.revature.p1.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbConnectionPool implements ConnectionPool
{
    private List<Connection> availableConnections   = new ArrayList<>();
    private List<Connection> usedConnections        = new ArrayList<>();
    private static DbConnectionPool instance;

    private String url;
    private String username;
    private String password;

    private DbConnectionPool(String url, String username, String password, List<Connection> pool)
    {
        this.url = url;
        this.username = username;
        this.password = password;
        this.availableConnections = pool;
    }

    public static void initialize(String url, String username, String password, int poolSize)
    {
        if (instance != null) return;

        List<Connection> connections = new ArrayList<>();
        try
        {
            for (int i = 0; i < poolSize; i++)
            {
                connections.add(DriverManager.getConnection(url, username, password));
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
        instance = new DbConnectionPool(url, username, password, connections);
    }
    public static DbConnectionPool getInstance()
    {
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

    @Override
    public String getConnectionURL()
    {
        return url;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public String getPassword()
    {
        return password;
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
