package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Account;

@SpringBootTest
public class TestAccountPersistence {
    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    public void clearDatabase() {
        accountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadAccount() {
        
        Account account = new Account();
        int accountId = 123;
        String username = "John";
        String password = "password";
        
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);

        account = null;
        account = accountRepository.findAccount(accountId);

        assertNotNull(account);
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
        assertEquals(accountId, account.getAccountId());
    }
}
