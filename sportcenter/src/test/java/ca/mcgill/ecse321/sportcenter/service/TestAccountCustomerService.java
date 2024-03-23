package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;

/*
 * Test class for Customer Account
 * 
 * @autor Emilie Ruel and Andrew Nemr
 *
 * 
 */
@ExtendWith(MockitoExtension.class)
public class TestAccountCustomerService {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    // account keys
    private static final int CUSTOMER_ACCOUNT_KEY = 1;
    private static final int APPROVED_INSTRUCTOR_ACCOUNT_KEY = 2;
    private static final int DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY = 3;
    private static final int NEW_CUSTOMER_ACCOUNT_KEY = 4;
    // account role keys
    private static final int CUSTOMER_KEY = 1;
    private static final int NEW_CUSTOMER_KEY = 4;
    private static final String CUSTOMER_USERNAME = "customer";

    @InjectMocks
    private AccountManagementService accountManagementService;

    @SuppressWarnings("null")
    @BeforeEach
    void setMockOutput() {
        lenient().when(accountRepository.findAccountByAccountId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(CUSTOMER_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername("customer");
                account.setPassword("password");
                account.setAccountId(CUSTOMER_ACCOUNT_KEY);
                return account;
            } else if (invocation.getArgument(0).equals(APPROVED_INSTRUCTOR_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername("approvedInstructor");
                account.setPassword("password");
                account.setAccountId(APPROVED_INSTRUCTOR_ACCOUNT_KEY);
                return account;
            } else if (invocation.getArgument(0).equals(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername("disapprovedInstructor");
                account.setPassword("password");
                account.setAccountId(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY);
                return account;
            } else if (invocation.getArgument(0).equals(NEW_CUSTOMER_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername("newCustomer");
                account.setPassword("password");
                account.setAccountId(NEW_CUSTOMER_ACCOUNT_KEY);
                return account;
            } else {
                return null;
            }
        });

        lenient().when(customerRepository.findCustomerByAccountRoleId(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(CUSTOMER_KEY)) {
                        Customer customer = new Customer();
                        customer.setAccount(accountRepository.findAccountByAccountId(CUSTOMER_ACCOUNT_KEY));
                        return customer;
                    } else if (invocation.getArgument(0).equals(NEW_CUSTOMER_KEY)) {
                        Customer customer = new Customer();
                        customer.setAccount(accountRepository.findAccountByAccountId(NEW_CUSTOMER_ACCOUNT_KEY));
                        return customer;
                    } else {
                        return null;
                    }
                });

        lenient().when(customerRepository.findCustomerByAccountUsername(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(CUSTOMER_USERNAME)) {
                        Customer customer = new Customer();
                        customer.setAccount(accountRepository.findAccountByUsername(CUSTOMER_USERNAME));
                        return customer;
                    } else {
                        return null;
                    }
                });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(accountRepository.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
    }

    @SuppressAjWarnings("null")

    // test sucessful creation of a customer account
    @Test
    public void testCreateCustomerAccount() {
        Customer customer = null;
        try {
            customer = accountManagementService.createCustomer(CUSTOMER_USERNAME);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            fail();
        }
        assertNotNull(customer);
        assertEquals(CUSTOMER_USERNAME, customer.getAccount().getUsername());
    }

    // test create customer null
    @Test
    public void testCreateCustomerNull() {
        String error = null;
        try {
            accountManagementService.createCustomer(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Username cannot be empty!", error);
    }

    // test create customer empty -> fail
    @Test
    public void testCreateCustomerEmpty() {
        String error = null;
        Customer customer = null;
        try {
            customer = accountManagementService.createCustomer("");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Username cannot be empty!", error);
    }

    // test create customer whitespace -> fail
    @Test
    public void testCreateCustomerWhitespace() {
        String error = null;
        Customer customer = null;
        try {
            customer = accountManagementService.createCustomer(" ");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Username cannot be empty!", error);
    }

    // test get customer account byits accountRoleId -> success
    @Test
    public void testGetCustomerAccountByAccountRoleId() {
        Customer customer = null;
        try {
            customer = accountManagementService.getCustomerByAccountRoleId(CUSTOMER_KEY);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            fail();
        }
        assertNotNull(customer);
        assertEquals(CUSTOMER_KEY, customer.getAccountRoleId());
    }

    // test get customer account by its accountRoleId -> non existing, fail
    @Test
    public void testGetCustomerAccountByInvalidAccountRoleId() {
        String error = null;
        Customer customer = null;
        try {
            customer = accountManagementService.getCustomerByAccountRoleId(0);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Customer not found", error);
    }

    // test get customer account by its accountId -> success
    @Test
    public void testGetCustomerAccountByAccountId() {
        Customer customer = null;
        try {
            customer = accountManagementService.getCustomerByAccountId(CUSTOMER_ACCOUNT_KEY);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            fail();
        }
        assertNotNull(customer);
        assertEquals(CUSTOMER_ACCOUNT_KEY, customer.getAccount().getAccountId());
    }

    // test get customer account by its accountId -> non existing, fail
    @Test
    public void testGetCustomerAccountByInvalidAccountId() {
        String error = null;
        Customer customer = null;
        try {
            customer = accountManagementService.getCustomerByAccountId(0);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Customer not found", error);
    }

    // test get customer account by its username -> success
    @Test
    public void testGetCustomerAccountByUsername() {
        Customer customer = null;
        try {
            customer = accountManagementService.getCustomerByUsername(CUSTOMER_USERNAME);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            fail();
        }
        assertNotNull(customer);
        assertEquals(CUSTOMER_USERNAME, customer.getAccount().getUsername());
    }

    // test get customer account by its username -> non existing, fail
    @Test
    public void testGetCustomerAccountByInvalidUsername() {
        String error = null;
        Customer customer = null;
        try {
            customer = accountManagementService.getCustomerByUsername("invalid");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Customer not found", error);
    }

    // test get customer account by its username -> null, fail
    @Test
    public void testGetCustomerAccountByNullUsername() {
        String error = null;
        Customer customer = null;
        try {
            customer = accountManagementService.getCustomerByUsername(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Username cannot be empty!", error);
    }

    // test get customer account by its username -> empty, fail
    @Test
    public void testGetCustomerAccountByEmptyUsername() {
        String error = null;
        Customer customer = null;
        try {
            customer = accountManagementService.getCustomerByUsername("");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Username cannot be empty!", error);
    }

    // test get customer account by its username -> whitespace, fail
    @Test
    public void testGetCustomerAccountByWhitespaceUsername() {
        String error = null;
        Customer customer = null;
        try {
            customer = accountManagementService.getCustomerByUsername(" ");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(customer);
        assertEquals("Username cannot be empty!", error);
    }

    // Test get all customer accounts
    @Test
    public void testGetAllCustomerAccounts() {
        List<Customer> customers = accountManagementService.getAllCustomers();
        assertNotNull(customers);
        assertEquals(1, customers.size());
    }

    // test delete customer account by accountRoleId -> success
    @Test
    public void testDeleteCustomerAccountByAccountRoleId() {
        List<Customer> customers = accountManagementService.getAllCustomers();
        int size = customers.size();
        try {
            accountManagementService.deleteCustomerByAccountRoleId(CUSTOMER_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        customers = toList(accountManagementService.getAllCustomers());
        assertEquals(size - 1, customers.size());
        assertNull(accountManagementService.getCustomerByAccountRoleId(CUSTOMER_KEY));
    }

    // test delete customer account by accountRoleId -> non existing, fail
    @Test
    public void testDeleteCustomerAccountByInvalidAccountRoleId() {
        String error = null;
        try {
            accountManagementService.deleteCustomerByAccountRoleId(0);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Customer not found", error);
    }

    // test delete customer account by accountId -> success
    @Test
    public void testDeleteCustomerAccountByAccountId() {
        List<Customer> customers = accountManagementService.getAllCustomers();
        int size = customers.size();
        try {
            accountManagementService.deleteCustomerByAccountId(CUSTOMER_ACCOUNT_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }
        customers = toList(accountManagementService.getAllCustomers());
        assertEquals(size - 1, customers.size());
        assertNull(accountManagementService.getCustomerByAccountId(CUSTOMER_ACCOUNT_KEY));
    }

    // test delete customer account by accountId -> non existing, fail
    @Test
    public void testDeleteCustomerAccountByInvalidAccountId() {
        String error = null;
        try {
            accountManagementService.deleteCustomerByAccountId(0);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Customer not found", error);
    }

    // test delete customer account by username -> success
    @Test
    public void testDeleteCustomerAccountByUsername() {
        List<Customer> customers = accountManagementService.getAllCustomers();
        int size = customers.size();
        try {
            accountManagementService.deleteCustomerByUsername(CUSTOMER_USERNAME);
        } catch (IllegalArgumentException e) {
            fail();
        }
        customers = toList(accountManagementService.getAllCustomers());
        assertEquals(size - 1, customers.size());
        assertNull(accountManagementService.getCustomerByUsername(CUSTOMER_USERNAME));
    }

    // test delete customer account by username -> non existing, fail
    @Test
    public void testDeleteCustomerAccountByInvalidUsername() {
        String error = null;
        try {
            accountManagementService.deleteCustomerByUsername("invalid");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Customer not found", error);
    }

    // test delete customer account by username -> null, fail
    @Test
    public void testDeleteCustomerAccountByNullUsername() {
        String error = null;
        try {
            accountManagementService.deleteCustomerByUsername(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Username cannot be empty!", error);
    }

    // test delete customer account by username -> empty, fail
    @Test
    public void testDeleteCustomerAccountByEmptyUsername() {
        String error = null;
        try {
            accountManagementService.deleteCustomerByUsername("");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Username cannot be empty!", error);
    }

    // test delete customer account by username -> whitespace, fail
    @Test
    public void testDeleteCustomerAccountByWhitespaceUsername() {
        String error = null;
        try {
            accountManagementService.deleteCustomerByUsername(" ");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Username cannot be empty!", error);
    }

    /**
     * Convert Iterable to List
     * 
     * @param Iterable<T>
     * @return List<T>
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        iterable.forEach(resultList::add);
        return resultList;
    }
}