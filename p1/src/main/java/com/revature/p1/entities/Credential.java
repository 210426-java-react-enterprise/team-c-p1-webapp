package com.revature.p1.entities;

import com.revature.p1.annotations.ColumnType;
import com.revature.p1.annotations.MyColumn;

public class Credential
{

    @MyColumn(  table = "credentials",name = "user_name",type = ColumnType.VARCHAR,length = 15,
            nullable = false,pk = true,fk = false,reference = "",unique = true,delete = "cascade")
    private String username;

    @MyColumn(  table = "credentials",name = "password",type = ColumnType.VARCHAR,length = 50,
            nullable = false,pk = false,fk = false,reference = "",unique = false,delete = "cascade")
    private String password;

    @MyColumn(  table = "credentials",name = "customer_ssn",type = ColumnType.VARCHAR,length = 12,
            nullable = false,pk = false,fk = true,reference = "customer.ssn",unique = true,delete = "cascade")
    private String ssn;
}
