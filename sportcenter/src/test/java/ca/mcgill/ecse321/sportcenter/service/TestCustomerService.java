package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;

public class TestCustomerService {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {
        String username = "testUsername";
        when(accountRepository.findAccountByUsername(username)).thenReturn(new Account());// if account exists, then it
                                                                                          // will be returned, if not,
                                                                                          // null will be returned
        when(customerRepository.save(any(Customer.class))).thenAnswer((invocation) -> invocation.getArgument(0));
        Customer customer = null;
        try {
            customer = customerService.createCustomer(username);
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
            customer = customerService.createCustomer(username);
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
            customer = customerService.createCustomer(username);
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
            customer = customerService.createCustomer(username);
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
            customerService.deleteCustomerByUsername(username);
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
            customerService.deleteCustomerByUsername(username);
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
            customerService.deleteCustomerByUsername(username);
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
            customerService.deleteCustomerByUsername(username);
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
            customerService.getCustomerByUsername(username);
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
            customerService.getCustomerByUsername(username);
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
            customerService.getCustomerByUsername(username);
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
            customerService.getCustomerByUsername(username);
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
            customerService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Customer does not exist!", error);
    }

    @Test
    public void testDeleteAllCustomers() {
        customerService.deleteAllCustomers();
        verify(customerRepository, times(1)).deleteAll();
    }

    @Test
    public void testGetAllCustomers() {
        customerService.getAllCustomers();
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void getCustomerByRoleId() {
        int roleId = 1;
        when(customerRepository.findAccountRoleByAccountRoleId(roleId)).thenReturn(new Customer());
        try {
            customerService.getCustomerByRoleId(roleId);
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
            customerService.getCustomerByRoleId(roleId);
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
            customerService.getCustomerByRoleId(roleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("AccountRoleId is not valid!", error);
    }

    @Test
    public void getAllCustomers() {
        customerService.getAllCustomers();
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void deleteAllCustomers() {
        customerService.deleteAllCustomers();
        verify(customerRepository, times(1)).deleteAll();
    }
}
