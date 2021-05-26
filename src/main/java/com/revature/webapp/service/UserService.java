package com.revature.webapp.service;

import com.revature.webapp.daos.UserDAO;
import com.revature.webapp.dtos.LoginMapper;
import com.revature.webapp.exceptions.CustomerNotFound;
import com.revature.webapp.models.Credential;
import com.revature.webapp.models.Customer;
import com.revature.webapp.repos.ConnectionPool;
import com.revature.orm.MyObjectRelationalMapper;

import javax.naming.AuthenticationException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
                connectionPool.addToConnectionPool(conn);
                return returnedCreds;
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Customer bringCustomerData(String ssn){
        try(Connection conn = connectionPool.pollFromConnectionPool()){
            MyObjectRelationalMapper myObjectRelationalMapper = new MyObjectRelationalMapper(conn);

            Customer customer = new Customer(ssn);
            Customer returnedCustomer = (Customer) myObjectRelationalMapper.readRow(customer);
            if(returnedCustomer!=null){
                ArrayList accts = new ArrayList();

                //Account acct = (Account) myObjectRelationalMapper.readRows()

                connectionPool.addToConnectionPool(conn);
                return returnedCustomer;
            }else{
                connectionPool.addToConnectionPool(conn);
                throw new CustomerNotFound();
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


}
