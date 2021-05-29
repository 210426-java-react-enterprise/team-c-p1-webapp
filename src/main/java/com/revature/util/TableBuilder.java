package com.revature.util;


import com.revature.models.Account;
import com.revature.models.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * TableBuilder to encapsulate Ozzy's HtmlBuilder, all credits to writing the builder go to him.
 */
public class TableBuilder {


    public TableBuilder () {

    }

    public List<String> buildAccountTable(List<Account> accounts) {
        List<String> tables = new ArrayList<>();

        accounts.forEach(account -> {
            Map<String, Object> accountMap = new HashMap<>();
            accountMap.put("Id", account.getAccountID());
            accountMap.put("Name", account.getName());
            accountMap.put("Balance", account.getBalanceFormatted());
            tables.add(buildHtmlTable("Account", accountMap));
        });
        return tables;
    }

    public List<String> buildTransactionTable(List<Transaction> transactions) {
        List<String> tables = new ArrayList<>();

        transactions.forEach(transaction -> {
            Map<String, Object> transactionMap = new HashMap<>();
            transactionMap.put("Id", transaction.getTransactionID());
            transactionMap.put("Sender", transaction.getSender());
            transactionMap.put("Recipient", transaction.getRecipient());
            transactionMap.put("Amount", transaction.getAmountFormatted());
            transactionMap.put("Type", transaction.getTransactionType());
            tables.add(buildHtmlTable("Transaction", transactionMap));
        });

        return tables;
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
    public String buildHtmlTable(String tableName, Map<?,?> tableMap){
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
        tableMap.forEach((key1, value1) -> htmlString.append("      <th class = header-text>").append(key1).append("</th>\n"));
        htmlString.append("  </thead>\n");
        //Insert row
        htmlString.append("<tr>\n");
        tableMap.forEach((key, value) -> htmlString.append("      <td>").append(value).append("</td>\n"));
        htmlString.append("</tr>\n");
        htmlString.append("</table>");

        //Return htmlString
        return String.valueOf(htmlString);
    }


}
