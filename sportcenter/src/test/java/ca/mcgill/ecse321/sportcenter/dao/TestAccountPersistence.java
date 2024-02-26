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
        
        // Create an Account, Save in the database and Load from the database
        String username = "Pablo";
        String password = "password";
        Account account = new Account(username, password);

        account = accountRepository.save(account);
        int accountId = account.getAccountId();

        account = accountRepository.findAccountByAccountId(accountId);

        // Check the attributes
        assertNotNull(account);
        assertEquals(accountId, account.getAccountId());
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
    }
}
