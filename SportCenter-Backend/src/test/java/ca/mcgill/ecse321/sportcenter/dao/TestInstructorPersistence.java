package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Account;

@SpringBootTest
public class TestInstructorPersistence {
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    public void clearDatabase() {
        instructorRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadInstructor() {
        
        
        Account account = new Account();
        int accountRoleId = 123;
        String username = "John";
        String password = "password";
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);

        Instructor instructor = new Instructor();
        InstructorStatus status = InstructorStatus.Active;
        String profilepicUrl = "https://www.google.com";
        String description = "Professional instructor with 10 years of experience.";
        instructor.setAccountRoleId(accountRoleId);
        instructor.setStatus(status);
        instructor.setProfilePicURL(profilepicUrl);
        instructor.setDescription(description);
        instructor.setAccount(account);
        instructorRepository.save(instructor);

        instructor = null;
        instructor = instructorRepository.findAccountRole(accountRoleId);

        assertNotNull(instructor);
        assertEquals(username, instructor.getAccount().getUsername());
        assertEquals(password, instructor.getAccount().getPassword());
        assertEquals(accountRoleId, instructor.getAccountRoleId());
        assertEquals(profilepicUrl, instructor.getProfilePicURL());
        assertEquals(status, instructor.getStatus());
        assertEquals(description, instructor.getDescription());
    }
}
