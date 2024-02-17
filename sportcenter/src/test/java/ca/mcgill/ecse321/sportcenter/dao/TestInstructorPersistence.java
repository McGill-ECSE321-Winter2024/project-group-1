package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.User;

@SpringBootTest
public class TestInstructorPersistence {
    @Autowired
    private InstructorRepository instructorRepository;

    @AfterEach
    public void clearDatabase() {
        instructorRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadInstructor() {
        
        Instructor instructor = new Instructor();
        User user = new User();
        int accountId = 123;
        String username = "John";
        String password = "password";
        InstructorStatus status = InstructorStatus.Active;
        String profilepicUrl = "https://www.google.com";
        
        instructor.setAccountId(accountId);
        user.setUsername(username);
        user.setPassword(password);
        instructor.setStatus(status);
        instructor.setProfilePicURL(profilepicUrl);
        instructorRepository.save(instructor);

        instructor = null;
        instructor = instructorRepository.findAccount(accountId);

        assertNotNull(instructor);
        assertEquals(username, instructor.getUser().getUsername());
        assertEquals(password, instructor.getUser().getPassword());
        assertEquals(accountId, instructor.getAccountId());
        assertEquals(profilepicUrl, instructor.getProfilePicURL());
        assertEquals(status, instructor.getStatus());
    }
}
