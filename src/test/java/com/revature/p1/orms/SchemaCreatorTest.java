package com.revature.p1.orms;

import com.revature.p1.entities.Customer;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

import java.util.List;

public class SchemaCreatorTest
{
    private SchemaCreator sut;
    private List<Class<?>> classes;

    @Before
    public void setup()
    {
        sut = new SchemaCreator(classes);
        classes.add(Customer.class);
    }

    @After
    public void teardown()
    {

    }
}
