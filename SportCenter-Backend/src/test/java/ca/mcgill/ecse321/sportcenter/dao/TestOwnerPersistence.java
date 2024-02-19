package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.Account;

/**
 * Author: Andrew Nemr
 */

@SpringBootTest
public class TestOwnerPersistence {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Clear the database after each test
     */
    @AfterEach
    public void clearDatabase() {
        ownerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    /**
     * Test the persistence of an Owner
     */
    @Test
    public void testPersistAndLoadOwner() {
        
        /**
         * Create an Account, set the attributes of the Account, and save the Account
         */
        Account account = new Account();
        int accountId = 001;
        String username = "John";
        String password = "password";
        account.setUsername(username);
        account.setPassword(password);
        account.setAccountId(accountId);
        accountRepository.save(account);

        /**
         * Create an Owner, set the attributes of the Owner, and save the Owner
         */
        Owner owner = new Owner();
        int accountRoleId = 100;
        owner.setAccountRoleId(accountRoleId);
        ownerRepository.save(owner);

        /**
         * Load the Owner
         */
        owner = null;
        owner = ownerRepository.findAccountRole(accountRoleId);

        /**
         * Check the attributes of the Owner
         */
        assertNotNull(owner);
        assertEquals(username, owner.getAccount().getUsername());
        assertEquals(account, owner.getAccount().getAccountId());
        assertEquals(password, owner.getAccount().getPassword());
        assertEquals(accountRoleId, owner.getAccountRoleId());
    }
}
