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
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
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

public class TestAccountCustomerService {
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

    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testCreateCustomer() {
        Account account = new Account();
        account.setUsername("Person1");
        account.setPassword("Password1");
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Customer customer = accountService.createCustomer("Person1");

        assertNotNull(customer);
        assertEquals("Person1", customer.getAccount().getUsername());
        assertEquals("Password1", customer.getAccount().getPassword());
    }

    @Test
    public void testCreateCustomerNull() {
        String username = null;
        Customer customer = null;
        try {
            customer = accountService.createCustomer(username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(null, customer);
        }

    }

    @Test
    public void testCreateCustomerEmpty() {
        String username = "";
        Customer customer = null;
        try {
            customer = accountService.createCustomer(username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(null, customer);
        }

    }

    @Test
    public void testCreateCustomerSpaces() {
        String username = " ";
        Customer customer = null;
        try {
            customer = accountService.createCustomer(username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(null, customer);
        }

    }

    @Test
    public void testDeleteCustomer() {
        Account account = new Account();
        account.setUsername("Person1");
        account.setPassword("Password1");
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Customer customer = accountService.createCustomer("Person1");
        accountService.deleteCustomerByUsername("Person1");
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    public void testDeleteCustomerNull() {
        Account account = new Account();
        account.setUsername("Person1");
        account.setPassword("Password1");
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        Customer customer = accountService.createCustomer("Person1");
        String username = null;
        try {
            accountService.deleteCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(customer, customerRepository.findAccountRoleByUsername("Person1"));
        }
    }

    @Test
    public void testDeleteCustomerEmpty() {
        Account account = new Account();
        account.setUsername("Person1");
        account.setPassword("Password1");
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        Customer customer = accountService.createCustomer("Person1");
        String username = "";
        try {
            accountService.deleteCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(customer, customerRepository.findAccountRoleByUsername("Person1"));

        }

    }

    @Test
    public void testDeleteCustomerSpaces() {
        Account account = new Account();
        account.setUsername("Person1");
        account.setPassword("Password1");
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        Customer customer = accountService.createCustomer("Person1");
        String username = " ";
        try {
            accountService.deleteCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(customer, customerRepository.findAccountRoleByUsername("Person1"));
        }
    }

    @Test
    public void testGetCustomer() {
        Account account = new Account();
        account.setUsername("Person1");
        account.setPassword("Password1");
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Customer customer = accountService.createCustomer("Person1");

        when(customerRepository.findAccountRoleByUsername("Person1")).thenReturn(customer);
        accountService.getCustomerByUsername("Person1");
        verify(customerRepository, times(1)).findAccountRoleByUsername("Person1");
    }

    @Test
    public void testGetCustomerNull() {
        String username = null;
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void testGetCustomerEmpty() {
        String username = "";
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void testGetCustomerSpaces() {
        String username = " ";
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void testGetCustomerDoesNotExist() {
        String username = "testUsername";
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            assertEquals("Customer does not exist!", e.getMessage());
        }
    }

    @Test
    public void testDeleteAllCustomers() {
        Account account1 = new Account();
        account1.setUsername("Person1");
        account1.setPassword("Password1");
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        Account account2 = new Account();
        account2.setUsername("Person2");
        account2.setPassword("Password2");
        when(accountRepository.save(any(Account.class))).thenReturn(account2);
        Customer customer1 = accountService.createCustomer("Person1");
        Customer customer2 = accountService.createCustomer("Person2");
        accountService.deleteAllCustomers();
        verify(customerRepository, times(1)).deleteAll();
    }

    @Test
    public void testGetAllCustomers() {
        Account account1 = new Account();
        account1.setUsername("Person1");
        account1.setPassword("Password1");
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        Account account2 = new Account();
        account2.setUsername("Person2");
        account2.setPassword("Password2");
        when(accountRepository.save(any(Account.class))).thenReturn(account2);
        Customer customer1 = accountService.createCustomer("Person1");
        Customer customer2 = accountService.createCustomer("Person2");
        accountService.getAllCustomers();
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void getCustomerByRoleId() {
        Account account = new Account();
        account.setUsername("Person1");
        account.setPassword("Password1");
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Customer customer = accountService.createCustomer("Person1");
        int roleId = 1;
        when(customerRepository.findAccountRoleByAccountRoleId(roleId))
                .thenReturn(customerRepository.findAccountRoleByUsername("Person1"));

        assertEquals(customer, accountService.getCustomerByAccountRoleId(roleId));
    }

    @Test
    public void getCustomerByRoleIdDoesNotExist() {
        Account account = new Account();
        account.setUsername("Person1");
        account.setPassword("Password1");
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Customer customer = accountService.createCustomer("Person1");
        int roleId = 1;
        when(customerRepository.findAccountRoleByAccountRoleId(2))
                .thenReturn(customerRepository.findAccountRoleByUsername("Person1"));
        try {
            accountService.getCustomerByAccountRoleId(roleId);
        } catch (IllegalArgumentException e) {
            assertEquals("Customer does not exist!", e.getMessage());
            assertEquals(customer, accountService.getCustomerByAccountRoleId(2));
        }
    }

    @Test ///////////////
    public void getCustomerByRoleIdNull() {
        Account account = new Account();
        account.setUsername("Person1");
        account.setPassword("Password1");
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Customer customer = accountService.createCustomer("Person1");
        int roleId = -1;
        when(customerRepository.findAccountRoleByAccountRoleId(2))
                .thenReturn(customerRepository.findAccountRoleByUsername("Person1"));
        try {
            accountService.getCustomerByAccountRoleId(roleId);
        } catch (IllegalArgumentException e) {
            assertEquals("Customer does not exist!", e.getMessage());
            assertEquals(customer, accountService.getCustomerByAccountRoleId(2));
        }
    }
}