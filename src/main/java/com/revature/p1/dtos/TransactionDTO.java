package com.revature.p1.dtos;

public class TransactionDTO
{
    private int number;
    private String type;
    private double amount;

    public TransactionDTO()
    {
    }

    public TransactionDTO(int number, String type, double amount)
    {
        this.number = number;
        this.type = type;
        this.amount = amount;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public int getNumber()
    {
        return number;

    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    @Override
    public String toString()
    {
        return "TransactionDTO{" +
                       "number=" + number +
                       ", type='" + type + '\'' +
                       ", amount=" + amount +
                       '}';
    }
}
