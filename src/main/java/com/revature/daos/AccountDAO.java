package com.revature.daos;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.p1.utils.EntityManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                throw new SQLException("was unable to link the accounts");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Account> getAccountsByUserID(User user) {

        try  {
            Connection conn = em.getConnection();
            List<Account> accounts = new ArrayList<>();

            String query = "select accountid, account_name, balance from project0.users_account\n" +
                    "join project0.users\n" +
                    "using (userid)\n" +
                    "join project0.account\n" +
                    "using (accountid)\n" +
                    "where project0.users.userid = ?;";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, user.getUserID());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("accountid"));
                account.setName(rs.getString("account_name"));
                account.setBalance(rs.getDouble("balance"));
                accounts.add(account);
            }
            return accounts;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
