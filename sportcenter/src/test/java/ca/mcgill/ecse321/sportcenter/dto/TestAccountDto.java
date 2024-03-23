package ca.mcgill.ecse321.sportcenter.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;

@SpringBootTest
public class TestAccountDto {
    @Autowired
    private AccountRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void createAndReadAccount() {
        // Create an Account
        String username = "username";
        String password = "password";
        Account account = new Account(username, password);

        // Save the Account in the database
        account = repo.save(account);
        int accountId = account.getAccountId();

        // Load the Account from the database
        account = repo.findAccountByAccountId(accountId);

        // Check the attributes
        assertNotNull(account);
        assertEquals(accountId, account.getAccountId());
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
    }

}