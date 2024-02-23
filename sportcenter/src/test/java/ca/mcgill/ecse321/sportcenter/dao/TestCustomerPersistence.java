package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Account;

/**
 * @author Andrew Nemr and Patrick Zakaria
 */

@SpringBootTest
public class TestCustomerPersistence {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Clear the database after each test
     */
    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    /**
     * Test the persistence of a Customer
     */
    @Test
    public void testPersistAndLoadCustomer() {

        /**
         * Create an Account
         */
        Account account = new Account();
        int accountId = 3;
        String username = "Mary";
        String password = "password";

        /**
         * Set the attributes of the Account
         */
        account.setUsername(username);
        account.setPassword(password);
        account.setAccountId(accountId);

        /**
         * Save the Account
         */
        accountRepository.save(account);

        /**
         * Create a Customer
         */
        Customer customer = new Customer();
        int accountRoleId = 300;
        /**
         * Set the attributes of the Customer
         */
        customer.setAccountRoleId(accountRoleId);
        customer.setAccount(account);
        /**
         * Save the Customer
         */
        customerRepository.save(customer);

        /**
         * Load the Customer
         */
        customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);

        /**
         * Check the attributes of the Customer
         */    
        assertNotNull(customer);
        assertEquals(accountRoleId, customer.getAccountRoleId());
        assertEquals(username, customer.getAccount().getUsername());
        assertEquals(password, customer.getAccount().getPassword());
        assertEquals(accountId, customer.getAccount().getAccountId());
    }
}

