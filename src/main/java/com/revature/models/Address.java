package com.revature.models;

import com.revature.orm.annotations.ColumnType;
import com.revature.orm.annotations.MyColumn;

public class Address
{

    @MyColumn(  name = "unit",type = ColumnType.VARCHAR,length = 3,
            nullable = true,pk = false,fk = false,reference = "",unique = false)
    private String unit;

    @MyColumn(  name = "street",type = ColumnType.VARCHAR,length = 30,
            nullable = false,pk = false,fk = false,reference = "",unique = false)
    private String street;

    @MyColumn(  name = "city",type = ColumnType.VARCHAR,length = 15,
            nullable = false,pk = false,fk = false,reference = "",unique = false)
    private String city;

    @MyColumn(  name = "state",type = ColumnType.VARCHAR,length = 15,
            nullable = false,pk = false,fk = false,reference = "",unique = false)
    private String state;

    @MyColumn(  name = "zip",type = ColumnType.VARCHAR,length = 5,
            nullable = false,pk = false,fk = false,reference = "",unique = false)
    private String zip;

    public Address () {

    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
