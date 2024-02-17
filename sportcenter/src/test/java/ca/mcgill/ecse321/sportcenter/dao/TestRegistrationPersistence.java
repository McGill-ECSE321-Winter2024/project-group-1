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
        int regID = 123;
        
        registration.setRegId(regID);
        registrationRepository.save(registration);

        registration = null;
        registration = registrationRepository.findAccount(regID);//could be by id

        assertNotNull(registration);
        assertEquals(regID, registration.getRegId());
    }
}