package com.revature.util;


import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;

import java.util.*;

/**
 * TableBuilder to encapsulate Ozzy's HtmlBuilder, all credits to writing the builder go to him.
 */
public class TableBuilder {


    public TableBuilder () {

    }

    public String buildAccountTable(List<Account> accounts) {
        List<String> tables = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        accounts.forEach(account -> {
            List<Pair<String, Object>> pairs = new ArrayList<>();
            pairs.add(new Pair<>("Id", account.getAccountID()));
            pairs.add(new Pair<>("Name", account.getName()));
            pairs.add(new Pair<>("Balance", account.getBalanceFormatted()));
            tables.add(buildHtmlTable("Account", pairs));
        });

        tables.forEach(sb::append);

        return sb.toString();
    }

    public String buildTransactionTable(List<Transaction> transactions) {
        List<String> tables = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        transactions.forEach(transaction -> {
            List<Pair<String, Object>> pairs = new ArrayList<>();
            pairs.add(new Pair<>("Id", transaction.getTransactionID()));
            pairs.add(new Pair<>("Sender", transaction.getSender()));
            pairs.add(new Pair<>("Recipient", transaction.getRecipient()));
            pairs.add(new Pair<>("Amount", transaction.getAmountFormatted()));
            pairs.add(new Pair<>("Type", transaction.getTransactionType()));
            tables.add(buildHtmlTable("Transaction", pairs));
        });

        tables.forEach(sb::append);

        return sb.toString();
    }

    public String buildUserTable(User user) {
        List<Pair<String, Object>> pairs = new ArrayList<>();
        pairs.add(new Pair<>("Username", user.getUsername()));
        pairs.add(new Pair<>("Email", user.getEmail()));
        pairs.add(new Pair<>("First Name", user.getFirstName()));
        pairs.add(new Pair<>("Last Name", user.getLastName()));
        pairs.add(new Pair<>("Age", user.getAge()));

        return buildHtmlTable("User", pairs);
    }


    /**
     * Programmatically creates an HTML table given an object. Full credits go to Ozzy for writing this; I just made
     * the code a bit cleaner.
     *
     * @param tableName The name of the table
     * @param tableMap The Object as a map
     * @return A String representing the HTML table
     * @author Oswaldo Castillo
     */
    private String buildHtmlTable(String tableName, List<Pair<String, Object>> tableMap){
        StringBuilder htmlString = new StringBuilder();
        //Insert font's links
        htmlString.append("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\">\n" +
                "<link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@100;400&display=swap\" rel=\"stylesheet\">\n");

        //Insert inline css style
        htmlString.append(  "<style>\n" +
                "    h1{\n" +
                "        font-family: 'Roboto', sans-serif;\n" +
                "    }\n" +
                "    table.customTable {\n" +
                "\n" +
                "      font-family: 'Roboto', sans-serif;\n" +
                "      width: 100%;\n" +
                "      background-color: #FFFFFF;\n" +
                "      border-collapse: collapse;\n" +
                "      border-width: 3px;\n" +
                "      border-color: white;\n" +
                "      border-style: solid;\n" +
                "      color: #000000;\n" +
                "    }\n" +
                "\n" +
                "    table.customTable td, table.customTable th {\n" +
                "      font-family: 'Roboto', sans-serif;\n" +
                "      border-width: 2px;\n" +
                "      border-color:white;\n" +
                "      border-style: solid;\n" +
                "      padding: 5px;\n" +
                "    }\n" +
                "\n" +
                "    table.customTable .header-text{\n" +
                "        font-weight: bolder;\n" +
                "    }\n" +
                "\n" +
                "    table.customTable thead {\n" +
                "      text-align: left;\n" +
                "      font-weight: 400;\n" +
                "      background-color: #fea82f;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "</style>\n");

        //Building the table
        htmlString.append("<table class=\"customTable\">\n").append("  <h1>").append(tableName).append("</h1>\n");
        //Inserting Header
        htmlString.append("  <thead>\n");
        tableMap.forEach(pair -> htmlString.append("      <th class = header-text>").append(pair.getFirst()).append("</th>\n"));
        htmlString.append("  </thead>\n");
        //Insert row
        htmlString.append("<tr>\n");
        tableMap.forEach(pair -> htmlString.append("      <td>").append(pair.getSecond()).append("</td>\n"));
        htmlString.append("</tr>\n");
        htmlString.append("</table>");

        //Return htmlString
        return String.valueOf(htmlString);
    }


}
