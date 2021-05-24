package com.revature.repos;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataSource implements ConnectionPool {

    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private final int POOL_SIZE = 10;
    private static DataSource instance;
    private Properties props = new Properties();

    public static DataSource getInstance() {
        if(instance == null) {
            instance = new DataSource();
        }
        return instance;
    }


    private DataSource () {
        try { //ty based Wezley
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream("application.properties");
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionPool = new ArrayList<>(POOL_SIZE);
        try {
            for (int i = 0; i < POOL_SIZE; i++){
                connectionPool.add(createConnection());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < POOL_SIZE) {
                connectionPool.add(createConnection());
            } else {
                throw new RuntimeException(
                        "Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);

        if(!connection.isValid(1)) {
            connection = createConnection();
        }

        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                props.getProperty("host_url"),
                props.getProperty("db_login"),
                props.getProperty("db_password")
                );
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }


    public void shutdown() throws SQLException {
        for(int i = usedConnections.size() - 1; i == -1; i--) {
            if (usedConnections.get(i).isValid(1)) {
                this.releaseConnection(usedConnections.get(i));
            }
        }
        for (Connection c: connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }

}