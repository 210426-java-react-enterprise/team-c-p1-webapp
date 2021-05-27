package com.revature.p1.persistance;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DbConnectionPool implements ConnectionPool
{
    private List<Connection> availableConnections;
    private List<Connection> usedConnections;
    private static DbConnectionPool instance;

    private Properties properties= new Properties();;

    static {
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

        try
        {
            Map<String,String> map = System.getenv();
            map.keySet().forEach(key -> System.out.println(key + ":\t" + map.get(key)));
            System.out.println("*****************************************************************************");
            Properties props = System.getProperties();

            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            properties.load(input);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        this.availableConnections = new ArrayList<>();
        this.usedConnections = new ArrayList<>();
    }

    public void initialize()
    {
        if (instance != null) return;


        List<Connection> connections = new ArrayList<>();
        try
        {
            for (int i = 0; i < 5; i++)
            {
                connections.add(DriverManager.getConnection(properties.getProperty("host-url"),properties.getProperty("username"),properties.getProperty("password")  ));
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
        instance = new DbConnectionPool();
    }
    public static DbConnectionPool getInstance()
    {
        if (instance == null)
        {
            instance = new DbConnectionPool();
        }
        return instance;
    }

    @Override
    public Connection getConnection()
    {
        initialize();
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
        return null;
    }

    @Override
    public String getUsername()
    {
        return null;
    }

    @Override
    public String getPassword()
    {
        return null;
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
