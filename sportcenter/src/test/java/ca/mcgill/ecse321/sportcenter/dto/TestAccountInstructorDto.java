package ca.mcgill.ecse321.sportcenter.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Account;

@SpringBootTest
public class TestAccountInstructorDto {
    @Autowired
    private InstructorRepository repo;

    @Autowired
    private AccountRepository accountRepo;

    @Test
    public void createAndReadInstructorAccount() {
        // Create an Account
        String username = "username";
        String password = "password";
        Instructor.InstructorStatus aStatus = Instructor.InstructorStatus.Pending;
        Account account = new Account(username, password);
        account = accountRepo.save(account);
        Instructor instructor = new Instructor(aStatus, "aDescription", "aProfilePicURL", account);

        // Save the Account in the database
        instructor = repo.save(instructor);
        int accountRoleId = instructor.getAccountRoleId();
        int accountId = instructor.getAccount().getAccountId();

        // Load the Account from the database
        Instructor instructorFromDatabase = repo.findInstructorByAccountRoleId(accountRoleId);

        // Check the attributes
        assertNotNull(instructorFromDatabase);
        assertEquals(accountRoleId, instructorFromDatabase.getAccountRoleId());
        assertEquals(accountId, instructorFromDatabase.getAccount().getAccountId());
        assertEquals(username, instructorFromDatabase.getAccount().getUsername());
        assertEquals(password, instructorFromDatabase.getAccount().getPassword());
        assertEquals(aStatus, instructorFromDatabase.getStatus());
        assertEquals("aDescription", instructorFromDatabase.getDescription());
        assertEquals("aProfilePicURL", instructorFromDatabase.getProfilePicURL());
    }

}
