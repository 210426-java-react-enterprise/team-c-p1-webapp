package com.revature.p1.persistance;

import java.sql.Connection;


public class ConnectionManager {

    static{
        try
        {
            Class.forName("org.postgresql.Driver");
            DbConnectionPool.initialize(System.getenv("host-url"),
                                         System.getenv("username"),
                                         System.getenv("password"),
                                         5);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
          return DbConnectionPool.getInstance().getConnection();
    }

    public static boolean releaseConnection(Connection connection)
    {
        return DbConnectionPool.getInstance().releaseConnection(connection);
    }
}
