package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Account;
/**
 * @author Andrew Nemr and Patrick Zakaria
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
        
        // Create an Account
        String username = "John";
        String password = "password";
        Account account = new Account(username, password);

        // Save in the database
        account = accountRepository.save(account);
        int accountId = account.getAccountId();

        // Load from the database
        Account accountDB = accountRepository.findAccountByAccountId(accountId);

        // Check the attributes
        assertNotNull(accountDB);
        assertEquals(accountId, accountDB.getAccountId());
        assertEquals(username, accountDB.getUsername());
        assertEquals(password, accountDB.getPassword());
    }
}
