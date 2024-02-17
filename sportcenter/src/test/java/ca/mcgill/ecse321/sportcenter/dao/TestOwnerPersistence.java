package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Owner;

@SpringBootTest
public class TestOwnerPersistence {
    @Autowired
    private OwnerRepository ownerRepository;

    @AfterEach
    public void clearDatabase() {
        ownerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadOwner() {
        
        Owner owner = new Owner();
        String username = "John";
        String password = "password";
        int accountId = 123;

        
        owner.setUsername(username);
        owner.setPassword(password);
        owner.setAccountId(accountId);
        ownerRepository.save(owner);

        owner = null;
        owner = ownerRepository.findAccount(accountId);//could be by id

        assertNotNull(owner);
        assertEquals(name, owner.getUsername());
        assertEquals(accountId, owner.getAccountId()); 
        assertEquals(password, owner.getPassword());
    }
}
