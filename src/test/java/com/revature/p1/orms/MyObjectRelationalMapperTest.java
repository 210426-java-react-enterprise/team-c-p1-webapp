package com.revature.p1.orms;

import com.revature.orm.MyObjectRelationalMapper;
import com.revature.p1.entities.Credential;
import com.revature.p1.persistance.ConnectionManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class MyObjectRelationalMapperTest
{
    private MyObjectRelationalMapper orm;
    private Connection connection;
    @Before
    public void setup()
    {
        connection = ConnectionManager.getInstance()
                                      .getConnection();
        this.orm = new MyObjectRelationalMapper(connection);
    }

    @After
    public void teardown()
    {

        orm = null;
    }

    @Test
    public void OrmRead_test()
    {
        Credential credential = new Credential("seantaba", "", "");
        Credential returnedSavable = (Credential) orm.readRow(credential);

        Assert.assertEquals(returnedSavable.getPassword(), "password");
        Assert.assertEquals(returnedSavable.getSsn(), "999999999");
    }

}
