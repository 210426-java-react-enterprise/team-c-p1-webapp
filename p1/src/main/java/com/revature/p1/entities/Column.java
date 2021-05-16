package com.revature.p1.entities;

import com.revature.p1.annotations.ColumnType;

public class Column
{
    private String table;
    private String name;
    private boolean nullable;
    private boolean unique;
    private ColumnType type;
    private int length;
    private boolean pk;
    private boolean fk;
    private String reference;

    public Column(String table, String name, boolean nullable, boolean unique, ColumnType type, int length, boolean pk, boolean fk, String reference)
    {
        this.table = table;
        this.name = name;
        this.nullable = nullable;
        this.unique = unique;
        this.type = type;
        this.length = length;
        this.pk = pk;
        this.fk = fk;
        this.reference = reference;
    }

    public String getTable()
    {
        return table;
    }

    public void setTable(String table)
    {
        this.table = table;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isNullable()
    {
        return nullable;
    }

    public void setNullable(boolean nullable)
    {
        this.nullable = nullable;
    }

    public boolean isUnique()
    {
        return unique;
    }

    public void setUnique(boolean unique)
    {
        this.unique = unique;
    }

    public ColumnType getType()
    {
        return type;
    }

    public void setType(ColumnType type)
    {
        this.type = type;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public boolean isPk()
    {
        return pk;
    }

    public void setPk(boolean pk)
    {
        this.pk = pk;
    }

    public boolean isFk()
    {
        return fk;
    }

    public void setFk(boolean fk)
    {
        this.fk = fk;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }
}
