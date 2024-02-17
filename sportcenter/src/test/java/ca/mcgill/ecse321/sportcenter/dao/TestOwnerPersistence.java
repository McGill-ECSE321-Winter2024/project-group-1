package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.User;


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
        User user = new User();
        String username = "John";
        String password = "password";
        int accountId = 123;

        
        user.setUsername(username);
        user.setPassword(password);
        owner.setAccountId(accountId);
        ownerRepository.save(owner);

        owner = null;
        owner = ownerRepository.findAccount(accountId);//could be by id

        assertNotNull(owner);
        assertEquals(username, owner.getUser().getUsername());
        assertEquals(password, owner.getUser().getPassword());
        assertEquals(accountId, owner.getAccountId()); 
    }
}
