package com.revature.service;

import com.revature.daos.UserDAO;
import com.revature.dtos.LoginMapper;
import com.revature.models.Credential;
import com.revature.models.Customer;
import com.revature.orm.MyObjectRelationalMapper;
import com.revature.repos.ConnectionPool;

import javax.naming.AuthenticationException;
import java.sql.Connection;
import java.sql.SQLException;

public class UserService {
    private ConnectionPool connectionPool;
    private UserDAO userDao;

    public UserService(ConnectionPool connectionPool,UserDAO userDao) {
        this.connectionPool = connectionPool;
        this.userDao = userDao;
    }

    public Credential authenticate(LoginMapper login) throws AuthenticationException {



        try(Connection conn = connectionPool.pollFromConnectionPool()){
            MyObjectRelationalMapper myObjectRelationalMapper = new MyObjectRelationalMapper(conn);

            Credential creds = new Credential(login.getUsername());
            Credential returnedCreds = (Credential) myObjectRelationalMapper.readRow(creds);
            if(returnedCreds.getPassword().equals(login.getPassword())){
                return returnedCreds;
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
