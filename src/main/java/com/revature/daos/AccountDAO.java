package com.revature.daos;

import com.revature.exceptions.ResourcePersistenceException;
import com.revature.p1.utils.EntityManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class will contain all the JDBC boilerplate code that my ORM could not abstract away from me
 */
public class AccountDAO {

    private EntityManager em;

    public AccountDAO (EntityManager em) {
        this.em = em;
    }

    public String getUserNameFromAccount(int accountID) {

        try {
            Connection conn = em.getConnection();

            String query = "select username\n" +
                    "from project0.users_account ua\n" +
                    "join project0.users\n" +
                    "using(userid)\n" +
                    "join project0.account\n" +
                    "using(accountid) where accountid = ?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void linkAccount(int userID, int accountID) {

        try {
            Connection conn = em.getConnection();

            String query = "insert into users_account (accountid, userid)\n" +
                    "values (?, ?);";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, accountID);
            stmt.setInt(2, userID);

            int result = stmt.executeUpdate();

            if(result == 0)
                throw new ResourcePersistenceException("was unable to link the accounts");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
