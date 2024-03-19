package ca.mcgill.ecse321.sportcenter.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.service.AccountService;

@SpringBootTest
public class TestAccountService {
    
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    @SuppressAjWarnings("null")
    
    @Test
    public void testCreateAccount() {
        String username = "username";
        String password = "password";
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account createdAccount = accountService.createAccount(username, password);
        accountService.validateAccount(username, password);

        assertNotNull(createdAccount);
        assertEquals(username, createdAccount.getUsername());
        assertEquals(password, createdAccount.getPassword());
        verify(accountRepository, times(1)).save(account1);
    }

    @Test
    public void testCreateAccountNull() {
        String username = null;
        String password = null;
        String error = null;
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account createdAccount = accountService.createAccount(username, password);
            accountService.validateAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Username cannot be empty!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testCreateAccountEmpty() {
        String username = "";
        String password = "";
        String error = null;
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account createdAccount = accountService.createAccount(username, password);
            accountService.validateAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testCreateAccountSpaces() {
        String username = " ";
        String password = " ";
        String error = null;
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account createdAccount = accountService.createAccount(username, password);
            accountService.validateAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testCreateAccountUsernameExists() {
        String username = "username";
        String password = "password";
        String error = null;
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.findAccountByUsername(username)).thenReturn(account1);

        try {
            Account createdAccount = accountService.createAccount(username, password);
            accountService.validateAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username already exists!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testCreateAccountUsernameExists2() {
        String username = "username";
        String password = "password";
        String error = null;
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.findAccountByUsername(username)).thenReturn(account1);

        try {
            Account createdAccount = accountService.createAccount(username, password);
            accountService.validateAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username already exists!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccount(String oldUsername,String username, String password) {
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = accountService.updateAccount(oldUsername, username, password);

        assertNotNull(updatedAccount);
        assertEquals(username, updatedAccount.getUsername());
        assertEquals(password, updatedAccount.getPassword());
        verify(accountRepository, times(1)).save(account1);
        
    }

    @Test
    public void testUpdateAccountNull() {
        String oldUsername = "oldUsername";
        String username = null;
        String password = null;
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccount(oldUsername, username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty! Password cannot be empty! ", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountEmpty() {
        String oldUsername = "oldUsername";
        String username = "";
        String password = "";
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccount(oldUsername, username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty! Password cannot be empty! ", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountSpaces() {
        String oldUsername = "oldUsername";
        String username = " ";
        String password = " ";
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccount(oldUsername, username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty! Password cannot be empty! ", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountUsernameExists() {
        String oldUsername = "oldUsername";
        String username = "username";
        String password = "password";
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.findAccountByUsername(username)).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccount(oldUsername, username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username already exists!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testDeleteAccount() {
        int accountId = 1;
        Account account1 = new Account();
        when(accountRepository.findAccountByAccountId(accountId)).thenReturn(account1);

        accountService.deleteAccount(accountId);

        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    public void testDeleteAccountNull() {
        int accountId = 1;
        Account account1 = null;
        String error = null;
        when(accountRepository.findAccountByAccountId(accountId)).thenReturn(account1);

        try {
            accountService.deleteAccount(accountId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account does not exist", error);
        verify(accountRepository, times(0)).deleteById(accountId);
    }

    @Test
    public void testGetAccount() {
        int accountId = 1;
        Account account1 = new Account();
        when(accountRepository.findAccountByAccountId(accountId)).thenReturn(account1);

        Account account = accountService.getAccountById(accountId);

        assertNotNull(account);
        assertEquals(account1, account);
    }

    @Test
    public void testGetAccountNull() {
        int accountId = 1;
        Account account1 = null;
        String error = null;
        when(accountRepository.findAccountByAccountId(accountId)).thenReturn(account1);

        try {
            Account account = accountService.getAccountById(accountId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account does not exist", error);
    }

    @Test
    public void testGetAllAccounts() {
        Account account1 = new Account();
        Account account2 = new Account();
        when(accountRepository.findAll()).thenReturn(java.util.Arrays.asList(account1, account2));

        java.util.List<Account> accounts = accountService.getAllAccounts();

        assertNotNull(accounts);
        assertEquals(2, accounts.size());
    }
}
