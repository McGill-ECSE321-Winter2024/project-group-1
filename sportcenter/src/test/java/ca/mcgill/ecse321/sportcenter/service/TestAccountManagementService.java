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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

public class TestAccountManagementService {
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
    public void testCreateAccountUsernameExists2() {
        String username = "username";
        String password = "password";
        String error = null;
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.findAccountByUsername(username)).thenReturn(account1);

        try {
            Account createdAccount = accountService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username already exists!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccount(String oldUsername, String username, String password) {
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

    @Test
    public void testCreateCustomer() {
        String username = "testUsername";
        when(accountRepository.findAccountByUsername(username)).thenReturn(new Account());// if account exists, then it
                                                                                          // will be returned, if not,
                                                                                          // null will be returned
        when(customerRepository.save(any(Customer.class))).thenAnswer((invocation) -> invocation.getArgument(0));
        Customer customer = null;
        try {
            customer = accountService.createCustomer(username);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            assertEquals("Username cannot be empty!", e.getMessage());
        }
        // check model in memory
        assertNotNull(customer);
    }

    @Test
    public void testCreateCustomerNull() {
        String username = null;
        String error = null;
        Customer customer = null;
        try {
            customer = accountService.createCustomer(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
        // check model in memory
        assertEquals(null, customer);
    }

    @Test
    public void testCreateCustomerEmpty() {
        String username = "";
        String error = null;
        Customer customer = null;
        try {
            customer = accountService.createCustomer(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
        // check model in memory
        assertEquals(null, customer);
    }

    @Test
    public void testCreateCustomerSpaces() {
        String username = " ";
        String error = null;
        Customer customer = null;
        try {
            customer = accountService.createCustomer(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
        // check model in memory
        assertEquals(null, customer);
    }

    @Test
    public void testDeleteCustomer() {
        String username = "testUsername";
        when(customerRepository.findAccountRoleByUsername(username)).thenReturn(new Customer());
        when(customerRepository.save(any(Customer.class))).thenAnswer((invocation) -> invocation.getArgument(0));
        try {
            accountService.deleteCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            assertEquals("Username cannot be empty!", e.getMessage());
        }
        verify(customerRepository, times(1)).delete(any(Customer.class));
    }

    @Test
    public void testDeleteCustomerNull() {
        String username = null;
        String error = null;
        try {
            accountService.deleteCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testDeleteCustomerEmpty() {
        String username = "";
        String error = null;
        try {
            accountService.deleteCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testDeleteCustomerSpaces() {
        String username = " ";
        String error = null;
        try {
            accountService.deleteCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testGetCustomer() {
        String username = "testUsername";
        when(customerRepository.findAccountRoleByUsername(username)).thenReturn(new Customer());
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            assertEquals("Username cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void testGetCustomerNull() {
        String username = null;
        String error = null;
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testGetCustomerEmpty() {
        String username = "";
        String error = null;
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testGetCustomerSpaces() {
        String username = " ";
        String error = null;
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testGetCustomerDoesNotExist() {
        String username = "testUsername";
        when(customerRepository.findAccountRoleByUsername(username)).thenReturn(null);
        String error = null;
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Customer does not exist!", error);
    }

    @Test
    public void testDeleteAllCustomers() {
        accountService.deleteAllCustomers();
        verify(customerRepository, times(1)).deleteAll();
    }

    @Test
    public void testGetAllCustomers() {
        accountService.getAllCustomers();
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void getCustomerByRoleId() {
        int roleId = 1;
        when(customerRepository.findAccountRoleByAccountRoleId(roleId)).thenReturn(new Customer());
        try {
            accountService.getCustomerByAccountRoleId(roleId);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            assertEquals("Role ID cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void getCustomerByRoleIdDoesNotExist() {
        int roleId = 1;
        when(customerRepository.findAccountRoleByAccountRoleId(roleId)).thenReturn(null);
        String error = null;
        try {
            accountService.getCustomerByAccountRoleId(roleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Customer does not exist!", error);
    }

    @Test ///////////////
    public void getCustomerByRoleIdNull() {
        int roleId = -1;
        String error = null;
        try {
            accountService.getCustomerByAccountRoleId(roleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("AccountRoleId is not valid!", error);
    }

    @Test
    public void getAllCustomers() {
        accountService.getAllCustomers();
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void deleteAllCustomers() {
        accountService.deleteAllCustomers();
        verify(customerRepository, times(1)).deleteAll();
    }

    @Test
    public void testGetOwnerByAccountRoleId() {
        String username = "testname";
        String password = "testpassword";

        ownerRepository.save(new Owner(new Account(username, password)));
        int id = 1;
        Owner owner = accountService.getOwnerByAccountRoleId(id);

        try {
            owner = accountService.getOwnerByAccountRoleId(id);
        } catch (IllegalArgumentException e) {
            assertEquals("AccountRoleId cannot be found!", e.getMessage()); // TODO: Find the exact message
        }

        assertNotNull(owner);
    }

    @Test
    public void testGetOwnerByAccountRoleIdZero() {
        int accountRoleId = 0;
        String error = null;
        Owner owner = null;

        try {
            owner = accountService.getOwnerByAccountRoleId(accountRoleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("AccountRoleId cannot be negative!", error); // TODO: Modify the message
        assertEquals(null, owner);
    }

    @Test
    public void testGetOwnerByAccountRoleIdNegative() {
        int accountRoleId = -1;
        String error = null;
        Owner owner = null;

        try {
            owner = accountService.getOwnerByAccountRoleId(accountRoleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("AccountRoleId cannot be negative!", error);
        assertEquals(null, owner);
    }

    // TODO 2 - checkAccountOwner
    @Test
    public void testCheckAccountOwner() {
        int id = 1;
        // ownerRepository.Owner o = new Owner();
        when(ownerRepository.findAccountRoleByAccountRoleId(id)).thenReturn(new Owner());
        assertTrue(accountService.checkAccountOwner(id));
    }

    @Test
    public void testCheckAccountOwnerNegative() {
        int id = -1;
        when(ownerRepository.findAccountRoleByAccountRoleId(id)).thenReturn(new Owner());
        assertTrue(accountService.checkAccountOwner(id));
    }

    @Test
    public void testCheckAccountOwnerZero() {
        int id = 0;
        when(ownerRepository.findAccountRoleByAccountRoleId(id)).thenReturn(new Owner());
        assertTrue(accountService.checkAccountOwner(id));
    }

    @Test
    public void testCheckAccountOwnerDoesNotExist() {
        int id = 1;
        when(ownerRepository.findAccountRoleByAccountRoleId(id)).thenReturn(null);
        assertFalse(accountService.checkAccountOwner(id));
    }
}
