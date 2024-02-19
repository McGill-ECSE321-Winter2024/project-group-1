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
 * Author: Andrew Nemr
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
        int accountId = 001;
        String username = "John";
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
        int accountRoleId = 100;
        /**
         * Set the attributes of the Customer
         */
        customer.setAccount(account);
        /**
         * Save the Customer
         */
        customerRepository.save(customer);

        /**
         * Load the Customer
         */
        customer = null;
        Customer foundCustomer = customerRepository.findAccountRole(accountRoleId);

        /**
         * Check the attributes of the Customer
         */    
        assertNotNull(foundCustomer);
        assertEquals(accountRoleId, foundCustomer.getAccountRoleId());
        assertEquals(username, foundCustomer.getAccount().getUsername());
        assertEquals(password, foundCustomer.getAccount().getPassword());
        assertEquals(accountId,foundCustomer.getAccount().getAccountId());
    }
}

