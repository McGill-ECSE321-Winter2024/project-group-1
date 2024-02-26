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
         * Create an Account, set the attributes of the Account, and save the Account
         */
        Account account = new Account();
        String username = "Maria";
        String password = "password";

        account.setUsername(username);
        account.setPassword(password);

        accountRepository.save(account);
        int accountId = account.getAccountId();
        account = accountRepository.findAccountByAccountId(accountId);

        /**
         * Create a Customer, set the attributes of the Customer, and save the Customer
         */
        Customer customer = new Customer();

        customer.setAccount(account);

        customerRepository.save(customer);
        int accountRoleId = customer.getAccountRoleId();

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

