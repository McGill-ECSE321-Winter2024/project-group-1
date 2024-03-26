package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.Account;

/**
 * @author Andrew Nemr, Patrick Zakaria and Mathias Pacheco Lemina
 *         WASN'T UPDATED IN A WHILE - YOU SHOULD CHECK IT
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
    @BeforeEach
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
        String username = "Pepe";
        String password = "password";
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);
        int accountId = account.getAccountId();
        account = accountRepository.findAccountByAccountId(accountId);

        /**
         * Create an Owner, set the attributes of the Owner, and save the Owner
         */
        Owner owner = new Owner();
        owner.setAccount(account);
        ownerRepository.save(owner);
        int accountRoleId = owner.getAccountRoleId();

        /**
         * Load the Owner
         */
        owner = ownerRepository.findOwnerByAccountRoleId(accountRoleId);

        /**
         * Check the attributes of the Owner
         */
        assertNotNull(owner);
        assertEquals(username, owner.getAccount().getUsername());
        assertEquals(password, owner.getAccount().getPassword());
        assertEquals(accountRoleId, owner.getAccountRoleId());
    }
}
