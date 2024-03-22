package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.t;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.invocation.InvocationOnMock;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.service.AccountManagementService;

public class TestAccountService {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private AccountManagementService accountService;

    @BeforeEach
    public void setAccount() {
        Account account = new Account();
        account.setUsername("Person1");
        account.setPassword("Password1");
        accountRepository.save(account);
    }

    @Test
    public void testCreateAccount() {
        String username = "username";
        String password = "password";
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account createdAccount = accountService.createAccount(username, password);

        assertNotNull(createdAccount);
        assertEquals(username, createdAccount.getUsername());
        assertEquals(password, createdAccount.getPassword());
        verify(accountRepository, times(1)).save(account1);
    }

    @Test
    public void testCreateAccountNull() {
        String username = null;
        String password = null;
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        Account createdAccount = null;

        try {
            createdAccount = accountService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(createdAccount, null);
        }
    }

    @Test
    public void testCreateAccountUsernameEmpty() {
        String username = "";
        String password = "";
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        Account createdAccount = null;

        try {
            createdAccount = accountService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(createdAccount, null);
        }
    }

    @Test
    public void testCreateAccountPasswordEmpty() {
        String username = "Person1";
        String password = "";
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        Account createdAccount = null;

        try {
            createdAccount = accountService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            assertEquals("Password cannot be empty!", e.getMessage());
            assertEquals(createdAccount, null);
        }
    }

    @Test
    public void testCreateAccountUsernameSpaces() {
        String username = " ";
        String password = " ";
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        Account createdAccount = null;

        try {
            createdAccount = accountService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(createdAccount, null);
        }
    }

    @Test
    public void testCreateAccountPasswordSpaces() {
        String username = "Person1";
        String password = "";
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        Account createdAccount = null;

        try {
            createdAccount = accountService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            assertEquals("Password cannot be empty!", e.getMessage());
            assertEquals(createdAccount, null);
        }
    }

    @Test
    public void testCreateAccountUsernameExists() {
        String username1 = "username";
        String username2 = "username";
        String password = "password";
        Account account1 = new Account(username1, password);
        Account account2 = new Account(username2, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account2);

        Account createdAccount1 = accountService.createAccount(username1, password);
        Account createdAccount2 = null;

        try {
            createdAccount2 = accountService.createAccount(username2, password);
        } catch (IllegalArgumentException e) {
            assertEquals("Username already exists!", e.getMessage());
            assertEquals(createdAccount1, accountRepository.findAccountByUsername(username1));
            assertEquals(createdAccount2, null);
        }
    }

    @Test
    public void testUpdateAccountUsername() {
        String oldUsername = "oldUsername";
        String username = "username";
        String password = "password";
        Account account1 = new Account(oldUsername, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = accountService.updateAccountUsername(oldUsername, username);

        assertNotNull(updatedAccount);
        assertEquals(username, updatedAccount.getUsername());
        assertEquals(password, updatedAccount.getPassword());
        verify(accountRepository, times(1)).save(account1);

    }

    @Test
    public void testUpdateAccountPassword() {
        String oldUsername = "Person1";
        String password = "password";
        String newPassword = "newPassword";
        Account account1 = new Account(oldUsername, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = accountService.updateAccountPassword(oldUsername, password, newPassword);

        assertNotNull(updatedAccount);
        assertEquals(oldUsername, updatedAccount.getUsername());
        assertEquals(newPassword, updatedAccount.getPassword());
        verify(accountRepository, times(1)).save(account1);
    }

    @Test
    public void testUpdateAccountUsernameNull() {
        String oldUsername = "Person1";
        String password = "password";
        String username = null;
        Account account1 = new Account(oldUsername, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = null;

        try {
            updatedAccount = accountService.updateAccountUsername(oldUsername, username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(updatedAccount, null);
            assertEquals(oldUsername, account1.getUsername());
        }
    }

    @Test
    public void testUpdateAccountPasswordNull() {
        String oldUsername = "Person1";
        String password = "password";
        String password2 = null;
        Account account1 = new Account(oldUsername, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = null;

        try {
            updatedAccount = accountService.updateAccountPassword(oldUsername, password, password2);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(updatedAccount, null);
            assertEquals(password, account1.getPassword());
        }
    }

    @Test
    public void testUpdateAccountUsernameEmpty() {
        String oldUsername = "Person1";
        String password = "password";
        String username = "";
        Account account1 = new Account(oldUsername, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = null;

        try {
            updatedAccount = accountService.updateAccountUsername(oldUsername, username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(updatedAccount, null);
            assertEquals(oldUsername, account1.getUsername());
        }
    }

    @Test
    public void testUpdateAccountPasswordEmpty() {
        String oldUsername = "Person1";
        String password = "password";
        String password2 = "";
        Account account1 = new Account(oldUsername, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = null;

        try {
            updatedAccount = accountService.updateAccountPassword(oldUsername, password, password2);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(updatedAccount, null);
            assertEquals(password, account1.getPassword());
        }
    }

    @Test
    public void testUpdateAccountUsernameSpaces() {
        String oldUsername = "Person1";
        String password = "password";
        String username = " ";
        Account account1 = new Account(oldUsername, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = null;

        try {
            updatedAccount = accountService.updateAccountUsername(oldUsername, username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(updatedAccount, null);
            assertEquals(oldUsername, account1.getUsername());
        }
    }

    @Test
    public void testUpdateAccountPasswordSpaces() {
        String oldUsername = "Person1";
        String password = "password";
        String password2 = " ";
        Account account1 = new Account(oldUsername, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = null;

        try {
            updatedAccount = accountService.updateAccountPassword(oldUsername, password, password2);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(updatedAccount, null);
            assertEquals(password, account1.getPassword());
        }
    }

    @Test
    public void testUpdateAccountUsernameExists() {
        String oldUsername = "oldUsername";
        String password = "password";
        String username = "Person1";
        Account account1 = new Account(oldUsername, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = null;
        try {
            updatedAccount = accountService.updateAccountUsername(oldUsername, username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username already exists!", e.getMessage());
            assertEquals(updatedAccount, null);
            assertEquals(oldUsername, account1.getUsername());
        }

    }

    @Test
    public void testDeleteAccount() {
        String username = "Person1";
        String password = "password";
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        accountService.deleteAccount(account1.getAccountId());

        verify(accountRepository, times(1)).deleteById(account1.getAccountId());
    }

    @Test
    public void testDeleteAccountNull() {
        String username = "Person1";
        String password = "password";
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            accountService.deleteAccount(0);
        } catch (IllegalArgumentException e) {
            assertEquals("Account does not exist", e.getMessage());
            assertEquals(account1, accountRepository.findAccountByUsername(username));
        }
    }

    @Test
    public void testGetAccountByAccountId() {
        String username = "Person1";
        String password = "password";
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.findAccountByAccountId(0)).thenReturn(account1);

        Account account = accountService.getAccountByAccountId(0);

        assertNotNull(account);
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
    }

    @Test
    public void testGetAccountByUsername() {
        String username = "Person1";
        String password = "password";
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account account = accountService.getAccountByUsername(username);

        assertNotNull(account);
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
    }

    @Test
    public void testGetAccountByUsernameNull() {
        String username = "Person1";
        String password = "password";
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.findAccountByUsername("")).thenReturn(null);

        Account account = accountService.getAccountByUsername("");

        assertEquals(null, account);
    }

    @Test
    public void testGetAccountByAccountIdNull() {
        String username = "Person1";
        String password = "password";
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.findAccountByAccountId(0)).thenReturn(null);

        Account account = accountService.getAccountByAccountId(0);

        assertEquals(null, account);
    }

    @Test
    public void testGetAllAccounts() {
        String username = "Person1";
        String password = "password";
        Account account1 = new Account(username, password);
        String username2 = "Person2";
        String password2 = "password2";
        Account account2 = new Account(username2, password2);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account2);

        accountService.getAllAccounts();

        verify(accountRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllAccountsEmpty() {
        when(accountRepository.findAll()).thenReturn(null);

        accountService.getAllAccounts();

        verify(accountRepository, times(1)).findAll();
    }
}