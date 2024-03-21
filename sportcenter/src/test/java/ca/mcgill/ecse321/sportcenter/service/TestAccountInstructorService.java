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
import static org.mockito.Mockito.never;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestAccountInstructorService {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private AccountManagementService accountService;

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
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);

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
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);
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
        String username = " ";
        String password = "fiddlesticks";
        String description = "He can beat Goku";
        String image = "image";
        String error = null;

        when(instructorRepository.save(any(Instructor.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Account account = new Account();
        Instructor instructor = null;

        try {

            // note this method also create the account.
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);
            fail("IllegalArgumentException expected!");
        } catch (IllegalArgumentException e) {

            // expected here!
            assertNull(instructor);

        }
    }

    // RETRIEVE TESTING
    // These tests assume that creation works.

    @Test
    public void testGetInstructorById() {

        // again we now assume that creating will work now
        final String username = "Shakira";
        final String password = "elo111";
        final String description = "Made hips don't lie";
        final String image = "image1";

        Instructor sampleInstructor = accountService.createInstructor(username, InstructorStatus.Active,
                description, image);
        Instructor verifyInstructor = new Instructor();

        Account sampleAccount = sampleInstructor.getAccount();
        int sampleInstructorID = sampleAccount.getAccountId();

        when(instructorRepository.findAccountRoleByAccountRoleId(sampleInstructorID))
                .thenAnswer((InvocationOnMock invocation) -> sampleInstructor);

        try {

            verifyInstructor = accountService.getAccountByAccountId(sampleInstructorID);

        } catch (IllegalArgumentException e) {

            fail("IllegalArgumentException not expected here");

        }

        Account sampleAccount2 = verifyInstructor.getAccount();

        assertNotNull(verifyInstructor);
        assertEquals(sampleInstructorID, sampleAccount2.getAccountId()); // verify if ID match up
        assertEquals(sampleAccount, sampleAccount2);
        assertEquals(sampleInstructor, verifyInstructor);

    }

    // REMOVE TESTING

    @Test
    public void testCreateAndDeleteInstructor() {

        // again we now assume that creating will work now
        final String username = "Beatrix Kiddo";
        final String password = "Kill Bill";
        final String description = "Okinawa Katana Expert";
        final String image = "image67";

        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Active,
                description, image);
        Account account = instructor.getAccount();
        int instructorID = account.getAccountId();

        when(instructorRepository.findAccountRoleByAccountRoleId(instructorID))
                .thenAnswer((InvocationOnMock invocation) -> instructor);

        try {

            accountService.deleteInstructor(instructorID);

        } catch (IllegalArgumentException e) {

            fail("IllegalArgumentException not expected here");

        }

        verify(instructorRepository, times(0)).save(instructor);

    }

}