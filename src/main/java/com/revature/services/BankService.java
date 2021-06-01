package com.revature.services;

import com.revature.daos.AccountDAO;
import com.revature.dtos.AccountDTO;
import com.revature.dtos.CredentialDTO;
import com.revature.dtos.TransactionDTO;
import com.revature.dtos.newUserDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.p1.utils.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BankService {

    private EntityManager em;
    private AccountDAO accountDAO;

    public BankService(EntityManager em, AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
        this.em = em;
    }

    public User validateUser(CredentialDTO creds) {
        User soughtUser = em.getOneOnCondition(User.class, "username", creds.getUsername());

        if (soughtUser != null && soughtUser.getPassword().equals(creds.getPassword())) {
            return soughtUser;
        } else {
            throw new AuthenticationException("The username or password was invalid");
        }
    }

    public List<Transaction> getUserTransactions(User loggedInUser) {
        List<Transaction> list1 = em.getAllOnCondition(Transaction.class, "sender_name", loggedInUser.getUsername());
        List<Transaction> list2 = em.getAllOnCondition(Transaction.class, "recipient_name", loggedInUser.getUsername());
        List<Transaction> result = new ArrayList<>();

        result.addAll(list1);
        //do not re-list transactions where recipient name is the same as sender name
        result.addAll(list2.stream().filter(transaction -> !transaction.getRecipient().equals(loggedInUser.getUsername()))
        .collect(Collectors.toList()));
        return result;
    }

    public User registerUser(newUserDTO user) {

        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty() || user.getFirstName().length() > 50)
            throw new InvalidRequestException("The first name was invalid");

        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getEmail().length() > 255)
            throw new InvalidRequestException("The email was invalid");

        if (user.getPassword() == null || user.getPassword().trim().isEmpty())
            throw new InvalidRequestException("The password was invalid");

        if (user.getLastName() == null || user.getLastName().trim().isEmpty() || user.getLastName().length() > 50)
            throw new InvalidRequestException("The last name was invalid");

        if (user.getUsername() == null || user.getUsername().trim().isEmpty() || user.getUsername().length() > 50)
            throw new InvalidRequestException("The username was invalid");

        try {
            int age = Integer.parseInt(user.getAge());
            if (age < 0 || age >= 200)
                throw new InvalidRequestException("The age was invalid");

            User userToBeSaved = new User(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(),
                    user.getLastName(), age);
            return em.save(userToBeSaved);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("The age was invalid");
        }

    }

    public Account validateAccount(AccountDTO account, int userID) {
        Account result = new Account();

        if (account.getName().trim().isEmpty() || account.getName().length() <= 2 || account.getName().length() >= 20)
            throw new InvalidRequestException("The account name was invalid");

        try {
            double balance = Double.parseDouble(account.getBalance());
            result.setName(account.getName());
            result.setBalance(balance);
            Account newAccount = em.save(result);

            accountDAO.linkAccount(userID, newAccount.getAccountID());

            return newAccount;
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("The balance was invalid");
        }

    }

    public Account handleTransaction(TransactionDTO transactionDTO, List<Account> accounts, User user) {

        double amount;
        int recipientID;
        Transaction transaction;

        try {
            recipientID = Integer.parseInt(transactionDTO.getRecipient());
            amount = Double.parseDouble(transactionDTO.getAmount());
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("An invalid number was supplied");
        }

        //Get the account number specified in the transaction JSON
        Account userAccount = accounts.stream()
                .filter(account -> String.valueOf(account.getAccountID()).equals(transactionDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("The user account did not exist"));

        switch (transactionDTO.getAction().toLowerCase(Locale.ROOT).trim()) {
            case "deposit":
                transaction = new Transaction(user.getUsername(), userAccount.getAccountID(),
                        user.getUsername(), userAccount.getAccountID(), amount, "deposit");

                userAccount.setBalance(userAccount.getBalance() + amount);
                em.update(userAccount);
                em.save(transaction);
                return userAccount;
            case "withdraw":
                transaction = new Transaction(user.getUsername(), userAccount.getAccountID(),
                        user.getUsername(), userAccount.getAccountID(), amount, "withdrawal");

                if (amount > userAccount.getBalance()) {
                    throw new InvalidRequestException("You cannot withdraw more than what you have!");
                }
                userAccount.setBalance(userAccount.getBalance() - amount);
                em.update(userAccount);
                em.save(transaction);
                return userAccount;
            case "transfer":

                if (amount > userAccount.getBalance()) {
                    throw new InvalidRequestException("You cannot transfer more than what you have!");
                }
                Account recipient = (Account) em.get(Account.class, recipientID);
                String recipientName = accountDAO.getUserNameFromAccount(recipientID);

                userAccount.setBalance(userAccount.getBalance() - amount);
                recipient.setBalance(recipient.getBalance() + amount);

                transaction = new Transaction(user.getUsername(), userAccount.getAccountID(),
                        recipientName, recipientID, amount, "transfer");

                em.save(recipient);
                em.update(userAccount);
                em.save(transaction);
                return userAccount;
            default:
                throw new InvalidRequestException("The action specified is not supported.");
        }

    }

}

