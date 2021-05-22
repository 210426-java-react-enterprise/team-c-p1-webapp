package com.revature.p1.orms;

import com.revature.p1.entities.Credential;
import com.revature.p1.entities.MySavable;
import com.revature.p1.persistance.ConnectionManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class MyObjectRelationalMapperTest
{
    private MyObjectRelationalMapper orm;
    private Connection connection;
    @Before
    public void setup()
    {
//        connection = Mockito.mock(Connection.class);
        connection = ConnectionManager.getConnection();
        orm = new MyObjectRelationalMapper(connection);
    }

    @After
    public void teardown()
    {

        orm = null;
    }

    @Test
    public void OrmRead_test() throws SQLException, ExecutionException, InterruptedException
    {
        MySavable savable = new Credential("seantaba", "", "");
        Credential returnedSavable = (Credential) orm.readRow(savable);

        Assert.assertEquals(returnedSavable.getPassword(), "password");
        Assert.assertEquals(returnedSavable.getSsn(), "999999999");
    }

}
