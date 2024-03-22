package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;

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

        String username = "Person1";
        String password = "Password1";
        Account account = new Account(username, password);
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
        String username = "Person1";
        String password = "Password1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Customer customer = accountService.createCustomer("Person1");
        accountService.deleteCustomerByUsername("Person1");
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    public void testDeleteCustomerNull() {
        String username = "Person1";
        String password = "Password1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        Customer customer = accountService.createCustomer("Person1");
        String username_empty = null;
        try {
            accountService.deleteCustomerByUsername(username_empty);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(customer, customerRepository.findCustomerByAccountUsername("Person1"));
        }
    }

    @Test
    public void testDeleteCustomerEmpty() {
        String username = "Person1";
        String password = "Password1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        Customer customer = accountService.createCustomer("Person1");
        String username_empty = "";
        try {
            accountService.deleteCustomerByUsername(username_empty);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(customer, customerRepository.findCustomerByAccountUsername("Person1"));

        }

    }

    @Test
    public void testDeleteCustomerSpaces() {
        String username = "Person1";
        String password = "Password1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        Customer customer = accountService.createCustomer("Person1");
        String username_empty = " ";
        try {
            accountService.deleteCustomerByUsername(username_empty);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertEquals(customer, customerRepository.findCustomerByAccountUsername("Person1"));
        }
    }

    @Test
    public void testGetCustomer() {
        String username = "Person1";
        String password = "Password1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Customer customer = accountService.createCustomer("Person1");

        when(customerRepository.findCustomerByAccountUsername("Person1")).thenReturn(customer);
        accountService.getCustomerByUsername("Person1");
        verify(customerRepository, times(1)).findCustomerByAccountUsername("Person1");
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
        String username = "Person1";
        String password = "Password1";
        Account account1 = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        String username2 = "Person2";
        String password2 = "Password2";
        Account account2 = new Account(username2, password2);
        when(accountRepository.save(any(Account.class))).thenReturn(account2);
        Customer customer1 = accountService.createCustomer("Person1");
        Customer customer2 = accountService.createCustomer("Person2");
        accountService.getAllCustomers();
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void getCustomerByRoleId() {
        String username = "Person1";
        String password = "Password1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Customer customer = accountService.createCustomer("Person1");
        int roleId = 1;
        when(customerRepository.findCustomerByAccountRoleId(roleId))
                .thenReturn(customerRepository.findCustomerByAccountUsername("Person1"));

        Customer customer2 = accountService.getCustomerByAccountRoleId(roleId);

        assertEquals(customer, customer2);
        assertEquals("Person1", customer2.getAccount().getUsername());
        assertEquals("Password1", customer2.getAccount().getPassword());
    }

    @Test
    public void getCustomerByRoleIdDoesNotExist() {
        String username = "Person1";
        String password = "Password1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Customer customer = accountService.createCustomer("Person1");
        int roleId = 1;
        when(customerRepository.findCustomerByAccountRoleId(2))
                .thenReturn(customerRepository.findCustomerByAccountUsername("Person1"));
        try {
            accountService.getCustomerByAccountRoleId(roleId);
        } catch (IllegalArgumentException e) {
            assertEquals("Customer does not exist!", e.getMessage());
            assertEquals(customer, accountService.getCustomerByAccountRoleId(2));
        }
    }

    @Test ///////////////
    public void getCustomerByRoleIdNull() {
        String username = "Person1";
        String password = "Password1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Customer customer = accountService.createCustomer("Person1");
        int roleId = -1;
        when(customerRepository.findCustomerByAccountRoleId(2))
                .thenReturn(customerRepository.findCustomerByAccountUsername("Person1"));
        try {
            accountService.getCustomerByAccountRoleId(roleId);
        } catch (IllegalArgumentException e) {
            assertEquals("Customer does not exist!", e.getMessage());
            assertEquals(customer, accountService.getCustomerByAccountRoleId(2));
        }
    }
}