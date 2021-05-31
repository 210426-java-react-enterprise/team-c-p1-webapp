package com.revature.p1.persistance;

import java.sql.Connection;



public class ConnectionManager {

    public static ConnectionManager connectionManager;
    public DbConnectionPool connectionPool;

    public ConnectionManager()
    {
        connectionPool = DbConnectionPool.getInstance();
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
        return connectionPool.getConnection();
    }

    public boolean releaseConnection(Connection connection)
    {
        return connectionPool.releaseConnection(connection);
    }
}
