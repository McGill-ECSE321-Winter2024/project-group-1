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
        String name = "John";
        String email = "john@gmail.com";
        String password = "password";
        
        owner.setName(name);
        owner.setEmail(email);
        owner.setPassword(password);
        ownerRepository.save(owner);

        owner = null;
        owner = ownerRepository.findownerByEmail(email);//could be by id

        assertNotNull(owner);
        assertEquals(name, owner.getName());
        assertEquals(email, owner.getEmail());
        assertEquals(password, owner.getPassword());
    }
}
