package com.revature.p1.orms;

import com.revature.p1.entities.Credential;
import com.revature.p1.entities.MySavable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;

public class MyObjectRelationalMapperTest
{
    private MyObjectRelationalMapper orm;
    private Connection connection;
    @Before
    public void setup()
    {
        connection = Mockito.mock(Connection.class);
        orm = new MyObjectRelationalMapper(connection);
    }

    @After
    public void teardown()
    {
        connection = null;
        orm = null;
    }

    @Test
    public void OrmRead_test()
    {
        MySavable savable = new Credential("seantaba", "", "");
        Credential returnedSavable = (Credential) orm.read(savable);
        System.out.println(returnedSavable.getPassword());
    }

}
