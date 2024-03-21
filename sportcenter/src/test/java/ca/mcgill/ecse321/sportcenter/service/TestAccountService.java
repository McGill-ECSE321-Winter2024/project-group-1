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

        Instructor instructor = new Instructor();
        instructor.setAccount(account);
        instructorRepository.save(instructor);

        Owner owner = new Owner();
        owner.setAccount(account);
        ownerRepository.save(owner);

        Customer customer = new Customer();
        customer.setAccount(account);
        customerRepository.save(customer);
    }

    @Test
    public void testCreateAccount() {
        String username = "username";
        String password = "password";
        Account account1 = new Account();
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
        String error = null;
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account createdAccount = accountService.createAccount(username, password);
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
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test ////////////////////////////
    public void testCreateAccountUsernameExists() {
        String username1 = "username";
        String username2 = "username";
        String password = "password";
        String error = null;
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.findAccountByUsername(username1)).thenReturn(account1);

        try {
            Account createdAccount = accountService.createAccount(username1, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username already exists!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountUsername(String oldUsername, String username) {
        Account account1 = accountRepository.findAccountByUsername(oldUsername);

        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = accountService.updateAccountUsername(oldUsername, username);

        assertNotNull(updatedAccount);
        assertEquals(username, updatedAccount.getUsername());
        verify(accountRepository, times(1)).save(account1);

    }

    @Test
    public void testUpdateAccountPassword(String username, String oldPassword, String newPassword) {
        Account account1 = accountRepository.findAccountByUsername(username);
        when(accountRepository.findAccountByUsername(username)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = accountService.updateAccountPassword(username, oldPassword, newPassword);

        assertNotNull(updatedAccount);
        assertEquals(newPassword, updatedAccount.getPassword());
        verify(accountRepository, times(1)).save(account1);
    }

    @Test
    public void testUpdateAccountUsernameNull() {
        String oldUsername = "Person1";
        String username = null;
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccountUsername(oldUsername, username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountPasswordNull() {
        String oldUsername = "Person1";
        String oldPassword = null;

        String newPassword = null;
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        assertEquals("Username cannot be empty! Password cannot be empty! ", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountUsernameEmpty() {
        String oldUsername = "Person1";
        String username = "";
        String error = null;

        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccountUsername(oldUsername, username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountPasswordEmpty() {
        String oldUsername = "Person1";
        String password = "";
        String newPassword = "";
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccountPassword(oldUsername, password, newPassword);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty! Password cannot be empty! ", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountUsernameSpaces() {
        String oldUsername = "Person1";
        String username = " ";
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccountUsername(oldUsername, username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty! Password cannot be empty! ", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountPasswordSpaces() {
        String oldUsername = "Person1";
        String password = " ";
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccountPassword(oldUsername, password, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty! Password cannot be empty! ", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountUsernameExists() {
        String oldUsername = "oldUsername";
        String username = "Person1";
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.findAccountByUsername(username)).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccountUsername(oldUsername, username);
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

        Account account = accountService.getAccountByAccountId(accountId);

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
            Account account = accountService.getAccountByAccountId(accountId);
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