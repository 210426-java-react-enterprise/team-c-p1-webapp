package com.revature.p1.services;

import com.revature.orm.MyObjectRelationalMapper;
import com.revature.orm.MySavable;
import com.revature.p1.dtos.LoginDTO;
import com.revature.p1.entities.*;
import com.revature.p1.exceptions.InvalidLoginException;
import com.revature.p1.utilities.InputValidator;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

;

public class WebUserServiceTest
{
    private MyObjectRelationalMapper orm;
    private Logger logger;
    private InputValidator inputValidator;
    private WebUserService sut;

    @Before
    public void setup()
    {
        orm = mock(MyObjectRelationalMapper.class);
        logger = mock(Logger.class);
        inputValidator = mock(InputValidator.class);

        sut = new WebUserService(orm, inputValidator, logger);
    }

    @After
    public void teardown()
    {
        sut = null;
    }

    @Test
    public void authenticateLoginWithSavingsTest() throws InvalidLoginException
    {
        Credential credential = new Credential("sean", "password", "999999999");
        LoginDTO loginDTO = new LoginDTO("sean", "password");

        when(inputValidator.validate(anyString(), anyString())).thenReturn("password");
        when(orm.readRow(any(Credential.class))).thenReturn(new com.revature.p1.entities.Credential("sean", "password", "999999999"));
        when(orm.readRow(any(Customer.class))).thenReturn(new com.revature.p1.entities.Customer("sean", "taba", "999999999","email","354354445434",credential));
        when(orm.readRow(any(Account.class))).thenReturn(new com.revature.p1.entities.CheckingAccount("999999999"));
        when(orm.readRows(any(Transaction.class))).thenReturn(new ArrayList<MySavable>(Collections.singletonList(new Transaction("deposit",100.0,100.0,
                                                                                                                                 100125))));
        when(orm.readRows(any(Account.class))).thenReturn(new ArrayList<MySavable>(Collections.singletonList(new SavingsAccount("999999999"))));

        Customer customer = sut.authenticate(loginDTO, mock(HttpServletRequest.class), mock(HttpServletResponse.class));

        Assert.assertEquals(customer.getFirstName(), "sean");
    }

    @Test
    public void authenticateLoginWithCheckingTest() throws InvalidLoginException
    {
        Credential credential = new Credential("sean", "password", "999999999");
        LoginDTO loginDTO = new LoginDTO("sean", "password");

        when(inputValidator.validate(anyString(), anyString())).thenReturn("password");
        when(orm.readRow(any(Credential.class))).thenReturn(new com.revature.p1.entities.Credential("sean", "password", "999999999"));
        when(orm.readRow(any(Customer.class))).thenReturn(new com.revature.p1.entities.Customer("sean", "taba", "999999999","email","354354445434",credential));
        when(orm.readRow(any(Account.class))).thenReturn(new com.revature.p1.entities.CheckingAccount("999999999"));
        when(orm.readRows(any(Transaction.class))).thenReturn(new ArrayList<MySavable>(Collections.singletonList(new Transaction("deposit",100.0,100.0,
                                                                                                                                 100125))));
        when(orm.readRows(any(Account.class))).thenReturn(new ArrayList<MySavable>(Collections.singletonList(new CheckingAccount("999999999"))));

        Customer customer = sut.authenticate(loginDTO, mock(HttpServletRequest.class), mock(HttpServletResponse.class));

        Assert.assertEquals(customer.getFirstName(), "sean");
    }

    @Test
    public void authenticateLoginWithTrustTest() throws InvalidLoginException
    {
        Credential credential = new Credential("sean", "password", "999999999");
        LoginDTO loginDTO = new LoginDTO("sean", "password");

        when(inputValidator.validate(anyString(), anyString())).thenReturn("password");
        when(orm.readRow(any(Credential.class))).thenReturn(new com.revature.p1.entities.Credential("sean", "password", "999999999"));
        when(orm.readRow(any(Customer.class))).thenReturn(new com.revature.p1.entities.Customer("sean", "taba", "999999999","email","354354445434",credential));
        when(orm.readRow(any(Account.class))).thenReturn(new com.revature.p1.entities.CheckingAccount("999999999"));
        when(orm.readRows(any(Transaction.class))).thenReturn(new ArrayList<MySavable>(Collections.singletonList(new Transaction("deposit",100.0,100.0,
                                                                                                                                 100125))));
        when(orm.readRows(any(Account.class))).thenReturn(new ArrayList<MySavable>(Collections.singletonList(new TrustAccount("999999999"))));

        Customer customer = sut.authenticate(loginDTO, mock(HttpServletRequest.class), mock(HttpServletResponse.class));

        Assert.assertEquals(customer.getFirstName(), "sean");
    }

    @Test
    public void addNewAccountTest()
    {
        Account account = new Account("999999999");

        when(orm.saveNewData(account)).thenReturn(0);

        sut.addNewAccount(account);
    }

    @Test
    public void updateTest()
    {
        Account account = new Account("999999999");

        doNothing().when(orm).updateData(account);

        sut.update(account);
    }
    @Test
    public void saveTest()
    {

    }
}
