package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Registration;

@SpringBootTest
public class TestRegistrationPersistence {
    @Autowired
    private RegistrationRepository registrationRepository;

    @AfterEach
    public void clearDatabase() {
        registrationRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadRegistration() {
        
        Registration registration = new Registration();
        String name = "John";
        String email = "john@gmail.com";
        String password = "password";

        registration.setName(name);
        registration.setEmail(email);
        registration.setPassword(password);
        registrationRepository.save(registration);

        registration = null;
        registration = registrationRepository.findRegistrationByEmail(email);//could be by id

        assertNotNull(registration);
        assertEquals(name, registration.getName());
        assertEquals(email, registration.getEmail());
        assertEquals(password, registration.getPassword());
    }
}