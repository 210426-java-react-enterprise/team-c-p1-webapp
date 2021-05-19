package com.revature.p1.persistance;

import java.sql.Connection;

public interface ConnectionPool
{
    Connection getConnection();
    boolean releaseConnection(Connection connection);
    String getConnectionURL();
    String getUsername();
    String getPassword();
}
