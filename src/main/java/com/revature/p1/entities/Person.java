package com.revature.p1.entities;


import com.revature.p1.annotations.ColumnType;
import com.revature.p1.annotations.MyColumn;
import com.revature.p1.annotations.MyEntity;
import com.revature.p1.annotations.MyField;

@MyEntity(name = "Person")
public class Person
{
    @MyColumn(table = "person",name = "name",nullable = false,unique = false,type = ColumnType.VARCHAR, length = 20)
    private String name;

    @MyField(name = "age")
    private int age;

    @MyField(name = "balance")
    private double balance;



}
