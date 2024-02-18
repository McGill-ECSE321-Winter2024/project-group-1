package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.User;

@SpringBootTest
public class TestUserPersistence {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void clearDatabase() {
        userRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadUser() {
        
        User user = new User();
        int accountId = 123;
        String username = "John";
        String password = "password";
        
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);

        user = null;
        user = userRepository.findAccount(accountId);

        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(accountId, user.getUserId());
    }
}
