package ca.mcgill.ecse321.sportcenter.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.times;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;

import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Account;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestInstructorService {

    // mocking a database thanks to Mockito
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private RegistrationRepository registrationRepository;
    @Mock
    private ScheduledActivityRepository scheduledActivityRepository;

    @InjectMocks
    private InstructorService service;

    // First you need to check if your getters are working before creation and
    // deletion.

    @Test
    public void testCreateInstructor() {

        when(instructorRepository.save(any(Instructor.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        final String username = "gumball";
        final String password = "fiddlesticks";
        final String description = "He can beat Goku";
        final String image = "image";

        Account account = new Account();
        Instructor instructor = null;

        try {

            // note this method also create the account.
            instructor = service.createInstructor(username, password, InstructorStatus.Active, description, image);

        } catch (IllegalArgumentException e) {

            fail("IllegalArgumentException not expected here");

        }

        assertNotNull(instructor);
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
        assertEquals(description, instructor.getDescription());
        assertEquals(image, instructor.getProfilePicURL());

        // check if all was added
        verify(instructorRepository, times(1)).save(instructor);

    }

    @Test
    public void testCreateInstructorNull() { // make an empty username

        when(instructorRepository.save(any(Instructor.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        final String username = "";
        final String password = "fiddlesticks";
        final String description = "He can beat Goku";
        final String image = "image";

        // Account account = new Account();
        Instructor instructor = null;

        try {

            // note this method also create the account.
            instructor = service.createInstructor(username, password, InstructorStatus.Active, description, image);
            fail("IllegalArgumentException expected!");

        } catch (IllegalArgumentException e) {

            // expected here!
            assertNull(instructor);

        }

        // check if all was added
        verify(instructorRepository, times(0)).save(instructor);

    }

    @Test
    public void testCreateInstructorSpaces() { // make an empty username

        when(instructorRepository.save(any(Instructor.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        final String username = "  ";
        final String password = "fiddlesticks";
        final String description = "He can beat Goku";
        final String image = "image";

        // Account account = new Account();
        Instructor instructor = null;

        try {

            // note this method also create the account.
            instructor = service.createInstructor(username, password, InstructorStatus.Active, description, image);
            fail("IllegalArgumentException expected!");

        } catch (IllegalArgumentException e) {

            // expected here!
            assertNull(instructor);

        }

        // check if all was added
        verify(instructorRepository, times(0)).save(instructor);

    }

    @Test
    public void testCreateInstructorAndModifyUsername() {

        when(instructorRepository.save(any(Instructor.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        final String username = "gumball";
        final String password = "fiddlesticks";
        final String description = "He can beat Goku";
        final String image = "image";

        Account account = new Account();
        Instructor instructor = null;

        try {

            // note this method also create the account.
            instructor = service.createInstructor(username, password, InstructorStatus.Active, description, image);

        } catch (IllegalArgumentException e) {

            fail("IllegalArgumentException not expected here");

        }

        // NEXT STEP WE NEED TO MODIFY

        final String newUsername = "Vegeta";
        int accountID = account.getAccountId();

        // To this point, we want
        assertEquals(username, account.getUsername());

        try {

            service.updateInstructorUsername(accountID, username, newUsername);

        } catch (IllegalArgumentException e) {

            fail("IllegalArgumentException not expected here");

        }

        // assertion check
        assertNotNull(instructor);
        assertEquals(newUsername, account.getUsername());
        assertEquals(password, account.getPassword());
        assertEquals(description, instructor.getDescription());
        assertEquals(image, instructor.getProfilePicURL());

        // check if all was added
        verify(instructorRepository, times(1)).save(instructor);

    }

    // Test Updating

    // Test Deleting

    // Test unique methods!

}