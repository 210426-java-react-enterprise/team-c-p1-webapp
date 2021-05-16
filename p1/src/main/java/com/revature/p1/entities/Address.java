package com.revature.p1.entities;

import com.revature.p1.annotations.ColumnType;
import com.revature.p1.annotations.MyColumn;

public class Address
{

    @MyColumn(  table = "addresses",name = "unit",type = ColumnType.VARCHAR,length = 3,
            nullable = true,pk = false,fk = false,reference = "",unique = false)
    private String unit;

    @MyColumn(  table = "addresses",name = "street",type = ColumnType.VARCHAR,length = 30,
            nullable = false,pk = false,fk = false,reference = "",unique = false)
    private String street;

    @MyColumn(  table = "addresses",name = "city",type = ColumnType.VARCHAR,length = 15,
            nullable = false,pk = false,fk = false,reference = "",unique = false)
    private String city;

    @MyColumn(  table = "addresses",name = "state",type = ColumnType.VARCHAR,length = 15,
            nullable = false,pk = false,fk = false,reference = "",unique = false)
    private String state;

    @MyColumn(  table = "addresses",name = "zip",type = ColumnType.VARCHAR,length = 5,
            nullable = false,pk = false,fk = false,reference = "",unique = false)
    private String zip;


}
