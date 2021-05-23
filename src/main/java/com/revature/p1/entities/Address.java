package com.revature.p1.entities;

import com.revature.p1.annotations.ColumnType;
import com.revature.p1.annotations.MyColumn;
import com.revature.p1.annotations.MyEntity;

@MyEntity(name = "address")
public class Address extends MySavable
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

    @MyColumn(name = "customer_ssn",type = ColumnType.VARCHAR,length = 9,
              nullable = false,unique = true,pk = true,fk = true,reference = "customer.ssn",delete = "cascade")
    private String customerSsn;

    public Address(String unit, String street, String city, String state, String zip,String customerSsn)
    {
        this.unit = unit;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.customerSsn = customerSsn;
    }

    public Address(MySavable savable)
    {
        this.unit = ((Address) savable).unit;
        this.street = ((Address) savable).street;
        this.city = ((Address) savable).city;
        this.state = ((Address) savable).state;
        this.zip = ((Address) savable).zip;
        this.customerSsn = ((Address) savable).customerSsn;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }
}
