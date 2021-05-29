package com.revature.services;

import com.revature.daos.AccountDAO;
import com.revature.dtos.AccountDTO;
import com.revature.dtos.CredentialDTO;
import com.revature.dtos.TransactionDTO;
import com.revature.dtos.newUserDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.p1.utils.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BankServiceTest {

    private BankService sut;
    private AccountDAO mockAccountDAO;
    private EntityManager mockEM;

    @Before
    public void setUpTest() {
        mockAccountDAO = mock(AccountDAO.class);
        mockEM = mock(EntityManager.class);

        sut = new BankService(mockEM, mockAccountDAO);
    }

    @After
    public void tearDownTest() {
        mockAccountDAO = null;
        mockEM = null;
        sut = null;
    }

    @Test
    public void whenValidateUserWithValidCredentials_thenReturnUser() {
        //Arrange
        CredentialDTO credentials = new CredentialDTO("username", "password");
        User expected = new User(1, "username", "password", "email@email", "first", "last", 23);

        when(mockEM.getOneOnCondition(any(), anyString(), anyString())).thenReturn(expected);

        //Act
        User actual = sut.validateUser(credentials);

        //Assert
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test(expected = AuthenticationException.class)
    public void whenValidateUserWithInvalidCredentials_ThenThrowException() {
        //Arrange
        CredentialDTO credentials = new CredentialDTO("username", "wrongPassword");
        User user = new User(0, "username", "password", "email@email", "first", "last", 23);

        when(mockEM.getOneOnCondition(any(), anyString(), anyString())).thenReturn(user);

        //Act/Assert
        sut.validateUser(credentials);
    }

    @Test(expected = AuthenticationException.class)
    public void whenValidateUserButUserWasNull_ThenThrowException() {
        //Arrange
        CredentialDTO credentials = new CredentialDTO("username", "password");
        User user = new User(0, "username", "password", "email@email", "first", "last", 23);

        when(mockEM.getOneOnCondition(any(), anyString(), anyString())).thenReturn(null);

        //Act/Assert
        sut.validateUser(credentials);
    }

    @Test
    public void whenRegisterUserWithValidCredentials_thenReturnUser() {
        //Arrange
        newUserDTO newUser = new newUserDTO("username", "password", "email@email", "first", "last", "23");
        User expected = new User(0, "username", "password", "email@email", "first", "last", 23);

        when(mockEM.save(any())).thenReturn(expected);

        //Act
        User actual = sut.registerUser(newUser);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void whenRegisterUserWithInvalidUsername_thenThrowException() {
        //Arrange
        newUserDTO newUser = new newUserDTO("", "password", "email@email", "first", "last", "23");

        when(mockEM.save(any())).thenReturn(null);

        //Act
        try {
            sut.registerUser(newUser);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("The username was invalid", e.getMessage());
        } finally {
            verify(mockEM, times(0)).save(any());
        }
    }

    @Test
    public void whenRegisterUserWithInvalidPassword_thenThrowException() {
        //Arrange
        newUserDTO newUser = new newUserDTO("username", "", "email@email", "first", "last", "23");

        when(mockEM.save(any())).thenReturn(null);

        //Act
        try {
            sut.registerUser(newUser);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("The password was invalid", e.getMessage());
        } finally {
            verify(mockEM, times(0)).save(any());
        }

    }

    @Test
    public void whenRegisterUserWithInvalidEmail_thenThrowException() {
        //Arrange
        newUserDTO newUser = new newUserDTO("username", "password", "", "first", "last", "23");

        when(mockEM.save(any())).thenReturn(null);

        //Act
        try {
            sut.registerUser(newUser);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("The email was invalid", e.getMessage());
        } finally {
            verify(mockEM, times(0)).save(any());
        }
    }

    @Test
    public void whenRegisterUserWithInvalidFirstName_thenThrowException() {
        //Arrange
        newUserDTO newUser = new newUserDTO("username", "password", "email@amail", "", "last", "23");

        when(mockEM.save(any())).thenReturn(null);

        //Act
        try {
            sut.registerUser(newUser);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("The first name was invalid", e.getMessage());
        } finally {
            verify(mockEM, times(0)).save(any());
        }
    }

    @Test
    public void whenRegisterUserWithInvalidLastName_thenThrowException() {
        //Arrange
        newUserDTO newUser = new newUserDTO("username", "password", "email@amail", "first", "", "23");

        when(mockEM.save(any())).thenReturn(null);

        //Act
        try {
            sut.registerUser(newUser);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("The last name was invalid", e.getMessage());
        } finally {
            verify(mockEM, times(0)).save(any());
        }
    }

    @Test
    public void whenRegisterUserWithHighAge_thenThrowException() {
        //Arrange
        newUserDTO newUser = new newUserDTO("username", "password", "email@amail", "first", "last", "230");

        when(mockEM.save(any())).thenReturn(null);

        //Act
        try {
            sut.registerUser(newUser);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("The age was invalid", e.getMessage());
        } finally {
            verify(mockEM, times(0)).save(any());
        }
    }

    @Test
    public void whenRegisterUserWithInvalidAge_thenThrowException() {
        //Arrange
        newUserDTO newUser = new newUserDTO("username", "password", "email@amail", "first", "last", "2asdf");

        when(mockEM.save(any())).thenReturn(null);

        //Act
        try {
            sut.registerUser(newUser);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("The age was invalid", e.getMessage());
        } finally {
            verify(mockEM, times(0)).save(any());
        }
    }

    @Test
    public void whenRegisterUserWithKiwAge_thenThrowException() {
        //Arrange
        newUserDTO newUser = new newUserDTO("username", "password", "email@amail", "first", "last", "-1");

        when(mockEM.save(any())).thenReturn(null);

        //Act
        try {
            sut.registerUser(newUser);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("The age was invalid", e.getMessage());
        } finally {
            verify(mockEM, times(0)).save(any());
        }
    }

    @Test
    public void whenValidateAccountWithValidDetails_thenReturnNewAccount() {
        //Arrange
        AccountDTO accountDTO = new AccountDTO("checking", "100");
        Account expected = new Account(0, 100, "checking");

        when(mockEM.save(any())).thenReturn(expected);

        //Act
        Account actual = sut.validateAccount(accountDTO, 0);

        //Assert
        assertEquals(expected, actual);
        verify(mockAccountDAO, times(1)).linkAccount(anyInt(), anyInt());
    }

    @Test
    public void whenValidateAccountWithBadDetails_thenThrowException() {
        //Arrange
        AccountDTO accountDTO = new AccountDTO("a", "100");

        when(mockEM.save(any())).thenReturn(null);

        //Act
        try {
            sut.validateAccount(accountDTO, 0);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("The account name was invalid", e.getMessage());
        } finally {
            verify(mockAccountDAO, times(0)).linkAccount(anyInt(), anyInt());
        }
    }

    @Test
    public void whenValidateAccountWithInvalidBalance_thenThrowException() {
        //Arrange
        AccountDTO accountDTO = new AccountDTO("account", "asdfadsf");

        when(mockEM.save(any())).thenReturn(null);

        //Act
        try {
            sut.validateAccount(accountDTO, 0);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("The balance was invalid", e.getMessage());
        } finally {
            verify(mockAccountDAO, times(0)).linkAccount(anyInt(), anyInt());
        }
    }

    @Test
    public void whenHandleTransactionDepositWithValidInfo_thenDeposit() {
        //Arrange
        List<Account> accounts = new ArrayList<>();
        User user = new User(1, "username", "password", "email@email", "first", "last", 23);
        accounts.add(new Account(1, 200, "checking"));
        TransactionDTO transaction = new TransactionDTO("1", "200", "2", "deposit");
        newUserDTO newUser = new newUserDTO("username", "password", "email@amail", "first", "last", "23");

        when(mockEM.save(any())).thenReturn(null);
        when(mockEM.update(any())).thenReturn(true);

        //act
        sut.handleTransaction(transaction, accounts, user);

        //Assert
        verify(mockEM, times(1)).save(any());
        verify(mockEM, times(1)).update(any());

    }

    @Test
    public void whenHandleTransactionDepositWithNoMatchingAccounts_thenThrowException() {
        //Arrange
        List<Account> accounts = new ArrayList<>();
        User user = new User(1, "username", "password", "email@email", "first", "last", 23);
        accounts.add(new Account(0, 200, "checking")); //accountID is zero this time
        TransactionDTO transaction = new TransactionDTO("1", "200", "2", "deposit");
        newUserDTO newUser = new newUserDTO("username", "password", "email@amail", "first", "last", "23");

        when(mockEM.save(any())).thenReturn(null);
        when(mockEM.update(any())).thenReturn(true);

        //act
        try {
            sut.handleTransaction(transaction, accounts, user);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("The user account did not exist", e.getMessage());
        } finally {
            //Assert
            verify(mockEM, times(0)).save(any());
            verify(mockEM, times(0)).update(any());
        }

    }

    @Test
    public void whenHandleTransactionDepositWithInvalidAmounts_thenThrowException() {
        //Arrange
        List<Account> accounts = new ArrayList<>();
        User user = new User(1, "username", "password", "email@email", "first", "last", 23);
        accounts.add(new Account(1, 200, "checking")); //accountID is zero this time
        TransactionDTO transaction = new TransactionDTO("1", "2asdf", "2asd", "deposit");
        newUserDTO newUser = new newUserDTO("username", "password", "email@amail", "first", "last", "23");

        when(mockEM.save(any())).thenReturn(null);
        when(mockEM.update(any())).thenReturn(true);

        //act
        try {
            sut.handleTransaction(transaction, accounts, user);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("An invalid number was supplied", e.getMessage());
        } finally {
            //Assert
            verify(mockEM, times(0)).save(any());
            verify(mockEM, times(0)).update(any());
        }

    }

    @Test
    public void whenHandleTransactionWithdrawWithValidInfo_thenWithdraw() {
        //Arrange
        List<Account> accounts = new ArrayList<>();
        User user = new User(1, "username", "password", "email@email", "first", "last", 23);
        accounts.add(new Account(1, 200, "checking"));
        TransactionDTO transaction = new TransactionDTO("1", "200", "2", "withdraw");
        newUserDTO newUser = new newUserDTO("username", "password", "email@amail", "first", "last", "23");

        when(mockEM.save(any())).thenReturn(null);
        when(mockEM.update(any())).thenReturn(true);

        //act
        sut.handleTransaction(transaction, accounts, user);

        //Assert
        verify(mockEM, times(1)).save(any());
        verify(mockEM, times(1)).update(any());

    }

    @Test
    public void whenHandleTransactionTransferWithValidInfo_thenTransfer() {
        //Arrange
        List<Account> accounts = new ArrayList<>();
        User user = new User(1, "username", "password", "email@email", "first", "last", 23);
        accounts.add(new Account(1, 200, "checking"));
        TransactionDTO transaction = new TransactionDTO("1", "200", "2", "transfer");
        newUserDTO newUser = new newUserDTO("username", "password", "email@amail", "first", "last", "23");

        when(mockEM.save(any())).thenReturn(null);
        when(mockEM.update(any())).thenReturn(true);
        when(mockEM.get(any(), anyInt())).thenReturn(new Account(0, 200, "name"));
        when(mockAccountDAO.getUserNameFromAccount(0)).thenReturn("name");

        //act
        sut.handleTransaction(transaction, accounts, user);

        //Assert
        verify(mockEM, times(2)).save(any());
        verify(mockEM, times(1)).update(any());
        verify(mockEM, times(1)).get(any(), anyInt());
    }
}
