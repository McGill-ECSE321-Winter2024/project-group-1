package ca.mcgill.ecse321.sportcenter.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;<<<<<<<HEAD
import static org.mockito.Mockito.never;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

=======>>>>>>>360 b3259cedbe841bb5302a89c98f813b7f5a642
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
public class TestInstructorService {

    <<<<<<<HEAD
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
    @InjectMocks
    private ActivityService activityService;

    // CREATE TESTS
    =======
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
    private AccountManagementService service;

    // First you need to check if your getters are working before creation and
    // deletion.
    >>>>>>>360 b3259cedbe841bb5302a89c98f813b7f5a642

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

<<<<<<< HEAD
        final String username = "Goku";
        final String password = "power!";
        final String description = "Super Saiyan Potential";
        final String image = "image2";
        
=======
        final String username = "gumball";
        final String password = "fiddlesticks";
        final String description = "He can beat Goku";
        final String image = "image";

>>>>>>> 360b3259cedbe841bb5302a89c98f813b7f5a642
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

    <<<<<<<HEAD
    // RETRIEVE TESTING
    // These tests assume that creation works.

    @Test
    public void testGetInstructorById() {

    //again we now assume that creating will work now
    final String username = "Shakira";
    final String password = "elo111";
    final String description = "Made hips don't lie";
    final String image = "image1";

    Instructor sampleInstructor = service.createInstructor(username, password, InstructorStatus.Active, description, image);
    Instructor verifyInstructor = new Instructor();

    Account sampleAccount = sampleInstructor.getAccount();
    int sampleInstructorID = sampleAccount.getAccountId();
=======
    // Test Updating
>>>>>>> 360b3259cedbe841bb5302a89c98f813b7f5a642

    // Test Deleting

<<<<<<< HEAD
    when(instructorRepository.findAccountRoleByAccountRoleId(sampleInstructorID))
        .thenAnswer((InvocationOnMock invocation) -> sampleInstructor);
    
    try {

        verifyInstructor = service.getInstructor(sampleInstructorID);


    } catch (IllegalArgumentException e) {

        fail("IllegalArgumentException not expected here");

    }

    Account sampleAccount2 = verifyInstructor.getAccount();

    assertNotNull(verifyInstructor);
    assertEquals(sampleInstructorID, sampleAccount2.getAccountId()); //verify if ID match up
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

        Instructor instructor = service.createInstructor(username, password, InstructorStatus.Active, description,
                image);
        Account account = instructor.getAccount();
        int instructorID = account.getAccountId();

        when(instructorRepository.findAccountRoleByAccountRoleId(instructorID))
                .thenAnswer((InvocationOnMock invocation) -> instructor);

        try {

            service.deleteInstructor(instructorID);

        } catch (IllegalArgumentException e) {

            fail("IllegalArgumentException not expected here");

        }

        verify(instructorRepository, times(0)).save(instructor);

    }

    // Test unique methods!

    @Test
    public void testCreateInstructorAndProposeActivity() {

        /*
         * //again we now assume that creating will work now
         * final String username = "Rocky Balboa";
         * final String password = "iLikeRunning";
         * final String description = "Boxing champion";
         * final String image = "image342";
         * 
         * Instructor instructor = service.createInstructor(username, password,
         * InstructorStatus.Active, description, image);
         * 
         */

        final String activityName = "Boxing";
        final String activityDescription = "Insane cardio and martial arts";
        final ClassCategory category = ClassCategory.Cardio;

        Activity activity = new Activity(); // declare a new Activity

        when(activityRepository.save(any(Activity.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        try {

            activity = service.proposeActivity(activityName, activityDescription, category);

        } catch (IllegalArgumentException e) {

            fail("IllegalArgumentException not expected here");

        }

        assertNotNull(activity);
        assertEquals(activityName, activity.getName());
        assertEquals(activityDescription, activity.getDescription());
        assertEquals(category, activity.getSubCategory());

        verify(activityRepository, times(1)).save(activity);

    }

    @Test
    public void testCreateInstructorAndMakeScheduledActivity() {

        //again we now assume that creating will work now

        
        final String username = "Jim Morrison";
        final String password = "LightMyFire";
        final String description = "Guitar Swingin'";
        final String image = "image342";

        Instructor instructor = service.createInstructor(username, password, InstructorStatus.Active, description, image);
  
        final String activityName = "Boxing";
        final String activityDescription = "Insane cardio and martial arts";
        final ClassCategory category = ClassCategory.Cardio;

        Activity activity = activityService.createActivity(activityName, activityDescription, category);

        LocalDate date = LocalDate.of(2025, 3, 19);
        LocalTime startTime = LocalTime.of(10, 0, 0);
        LocalTime endTime = LocalTime.of(12, 0, 0);
        int capacity = 20;
        // #region Creating new instructor and activity

        //assuming this test will work.
        activityService.createActivity(activityName, activityDescription, category);

        ScheduledActivity scheduledActivity = new ScheduledActivity(); //declare a new Activity

        when(scheduledActivityRepository.save(any(ScheduledActivity.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        try {

            scheduledActivity = service.makeScheduledActivity(date, startTime, endTime, instructor, activity, capacity);

        } catch (IllegalArgumentException e) {

            fail("IllegalArgumentException not expected here");

        }

        assertNotNull(scheduledActivity);
        assertEquals(activity, scheduledActivity.getActivity());
        assertEquals(instructor, scheduledActivity.getSupervisor());
        assertEquals(capacity, scheduledActivity.getCapacity());

        assertEquals(date, scheduledActivity.getDate());
        assertEquals(startTime, scheduledActivity.getStartTime());
        assertEquals(endTime, scheduledActivity.getEndTime());

        verify(scheduledActivityRepository, times(1)).save(scheduledActivity);
    }

    =======
    // Test unique methods!

    >>>>>>>360 b3259cedbe841bb5302a89c98f813b7f5a642
}