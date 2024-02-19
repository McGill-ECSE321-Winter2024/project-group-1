package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.Account;


@SpringBootTest
public class TestOwnerPersistence {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    public void clearDatabase() {
        ownerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadOwner() {
        
        

        Account account = new Account();
        int accountRoleId = 123;
        String username = "John";
        String password = "password";
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);

        Owner owner = new Owner();
        owner.setAccountRoleId(accountRoleId);
        ownerRepository.save(owner);

        owner = null;
        owner = ownerRepository.findAccountRole(accountRoleId);//could be by id

        assertNotNull(owner);
        assertEquals(username, owner.getAccount().getUsername());
        assertEquals(password, owner.getAccount().getPassword());
        assertEquals(accountRoleId, owner.getAccountRoleId()); 
    }
}
