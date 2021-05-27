package com.revature.p1.dtos;

public class AccountTypeDTO
{
    private String type;

    public AccountTypeDTO()
    {
    }

    public AccountTypeDTO(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "AccountDTO{" +
                       "type='" + type + '\'' +
                       '}';
    }
}
