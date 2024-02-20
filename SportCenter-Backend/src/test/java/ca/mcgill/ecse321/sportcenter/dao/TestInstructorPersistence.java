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
/**
 * Author: Andrew Nemr

 */

@SpringBootTest
public class TestInstructorPersistence {
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Clear the database after each test
     */
    @AfterEach
    public void clearDatabase() {
        instructorRepository.deleteAll();
        accountRepository.deleteAll();
    }

    /**
     * Test the persistence of an Instructor
     */
    @Test
    public void testPersistAndLoadInstructor() {
        
        /**
        * Create an Account, set the attributes of the Account, and save the Account
        */
        Account account = new Account();
        int accountId = 001;
        String username = "John";
        String password = "password";
        account.setUsername(username);
        account.setPassword(password);
        account.setAccountId(accountId);
        accountRepository.save(account);

        /**
         * Create an Instructor, set the attributes of the Instructor, and save the Instructor
         */
        Instructor instructor = new Instructor();
        int accountRoleId = 100;
        InstructorStatus status = InstructorStatus.Active;
        String profilepicUrl = "https://www.google.com";
        String description = "Professional instructor with 10 years of experience.";
        instructor.setAccountRoleId(accountRoleId);
        instructor.setStatus(status);
        instructor.setProfilePicURL(profilepicUrl);
        instructor.setDescription(description);
        instructor.setAccount(account);
        instructorRepository.save(instructor);

        /**
         * Load the Instructor and check the attributes of the Instructor
         */
        instructor = null;
        instructor = instructorRepository.findAccountRoleByaccountRoleId(accountRoleId);

        /**
         * Check the attributes of the Instructor
         */
        assertNotNull(instructor);
        assertEquals(username, instructor.getAccount().getUsername());
        assertEquals(password, instructor.getAccount().getPassword());
        assertEquals(accountRoleId, instructor.getAccountRoleId());
        assertEquals(profilepicUrl, instructor.getProfilePicURL());
        assertEquals(status, instructor.getStatus());
        assertEquals(description, instructor.getDescription());
    }
}
