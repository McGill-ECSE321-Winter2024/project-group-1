package ca.mcgill.ecse321.sportcenter.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.dto.AccountDto;
import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;

@SpringBootTest
public class TestAccountRepository {
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        accountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadAccount() {
        String username = "testUsername";
        String password = "testPassword";
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);

        account = null;

        account = accountRepository.findAccountByUsername(username);
        assertNotNull(account);
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
    }

    @Test
    public void testPersistAndLoadAccountDto() {
        String username = "testUsername";
        String password = "testPassword";
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);

        account = null;

        AccountDto accountDto = new AccountDto(username, password);
        accountDto.setUsername(username);
        accountDto.setPassword(password);
        account = accountRepository.findAccountByUsername(username);
        assertNotNull(account);
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
    }
}
