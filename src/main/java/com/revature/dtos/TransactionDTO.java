package com.revature.dtos;

public class TransactionDTO {

    private String id;
    private String amount;
    private String recipient;
    private String action;

    public TransactionDTO() {

    }

    public TransactionDTO(String id, String amount, String recipient, String action) {
        this.id = id;
        this.amount = amount;
        this.recipient = recipient;
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
