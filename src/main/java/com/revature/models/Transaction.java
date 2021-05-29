package com.revature.models;

import com.revature.p1.utils.annotations.*;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity(name = "transactions")
public class Transaction {

    @Key
    @Column
    private int transactionID;

    @Column(name = "sender_name")
    private String sender;

    @Column(name = "sender_account")
    private int senderAccount;

    @Column(name = "recipient_name")
    private String recipient;

    @Column(name = "recipient_account")
    private int recipientAccount;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(isDouble = true)
    private double amount;

    public Transaction () {

    }

    public Transaction (int transactionID, String sender, int senderAccount, String recipient,
                        int recipientAccount, double amount, String transactionType) {
        this.transactionID = transactionID;
        this.sender = sender;
        this.senderAccount = senderAccount;
        this.recipient = recipient;
        this.recipientAccount = recipientAccount;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public Transaction (String sender, int senderAccount, String recipient,
                        int recipientAccount, double amount, String transactionType) {
        this.sender = sender;
        this.senderAccount = senderAccount;
        this.recipient = recipient;
        this.recipientAccount = recipientAccount;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(int senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public int getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(int recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("transactionID=").append(transactionID);
        sb.append(", sender='").append(sender).append('\'');
        sb.append(", senderAccount=").append(senderAccount);
        sb.append(", recipient='").append(recipient).append('\'');
        sb.append(", recipientAccount=").append(recipientAccount);
        sb.append(", transactionType='").append(transactionType).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }

    public String getAmountFormatted() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(amount);
    }
}