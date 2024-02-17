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
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void clearDatabase() {
        instructorRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadInstructor() {
        
        
        User user = new User();
        int accountId = 123;
        String username = "John";
        String password = "password";

        Instructor instructor = new Instructor();
        InstructorStatus status = InstructorStatus.Active;
        String profilepicUrl = "https://www.google.com";
        String description = "Professional instructor with 10 years of experience.";

        instructor.setAccountId(accountId);
        instructor.setStatus(status);
        instructor.setProfilePicURL(profilepicUrl);
        instructor.setDescription(description);
        instructor.setUser(user);
        instructorRepository.save(instructor);

        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);


        instructor = null;
        instructor = instructorRepository.findAccount(accountId);

        assertNotNull(instructor);
        assertEquals(username, instructor.getUser().getUsername());
        assertEquals(password, instructor.getUser().getPassword());
        assertEquals(accountId, instructor.getAccountId());
        assertEquals(profilepicUrl, instructor.getProfilePicURL());
        assertEquals(status, instructor.getStatus());
        assertEquals(description, instructor.getDescription());
    }
}
