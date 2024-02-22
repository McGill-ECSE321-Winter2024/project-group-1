package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
/**
 * Author: Andrew Nemr
 */

@SpringBootTest
public class TestAccountPersistence {
    @Autowired
    private AccountRepository accountRepository;

    /*
     * Clear the database after each test
     */
    @AfterEach
    public void clearDatabase() {
        accountRepository.deleteAll();
    }

    /*
     * Test the persistence of an Account
     */
    @Test
    public void testPersistAndLoadAccount() {
        
        /*
         * Create an Account
         */

        SportCenter sportCenter = new SportCenter();
        Account account = new Account();
        int accountId = 123;
        String username = "John";
        String password = "password";
        
        /*
         * Set the attributes of the Account
         */
        account.setUsername(username);
        account.setPassword(password);
        account.setSportCenter(sportCenter);
        accountRepository.save(account);

        /*
         * Load the Account
         */
        account = null;
        account = accountRepository.findAccountByAccountId(accountId);

        /*
         * Check the attributes of the Account
         */
        assertNotNull(account);
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
        assertEquals(accountId, account.getAccountId());
    }
}
