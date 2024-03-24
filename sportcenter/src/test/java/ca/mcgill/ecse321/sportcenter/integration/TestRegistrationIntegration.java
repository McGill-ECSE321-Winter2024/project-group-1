package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

import ca.mcgill.ecse321.sportcenter.dto.*;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.*;
import ca.mcgill.ecse321.sportcenter.dao.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestRegistrationIntegration {
    @Autowired
    private TestRestTemplate registration; // Always stays the same

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ScheduledActivityRepository scheduledActivityRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        registrationRepository.deleteAll();
        customerRepository.deleteAll();
        scheduledActivityRepository.deleteAll();
        instructorRepository.deleteAll();
        accountRepository.deleteAll();
        activityRepository.deleteAll();
    }

    // attributes for account
    private static final String ACCOUNT1_USERNAME = "account1Username";
    private static final String ACCOUNT2_USERNAME = "account2Username";
    private static final String ACCOUNT_PASSWORD = "accountPassword";
    private static final int ACCOUNT1ID = 1;
    private static final int ACCOUNT2ID = 2;
    private static final int INVALID_ACCOUNTID = -1;
    private static final int ACCOUNT1ROLEID = 1;
    private static final int ACCOUNT2ROLEID = 2;

    private static final String OLD_USERNAME = "testUsername_old";
    private static final String OLD_PASSWORD = "testPassword_old";

    // attributes for intructor
    private static final String INSTRUCTOR_DESCRIPRION = "testDescription";
    private static final String INSTRUCTOR_PICTURE = "testPicture";
    private static final InstructorStatus INSTRUCTOR_STATUS = InstructorStatus.Pending;

    // attributes for activity
    private static final String ACTIVITY_NAME = "testActivityName";
    private static final String ACTIVITY_DESCRIPTION = "testActivityDescription";
    // never set!! private static final int ACTIVITYID = 1;
    private static final ClassCategory ACTIVITY_CATEGORY = ClassCategory.Cardio;
    private static final boolean ACTIVITY_ISAPPROVED = true;

    // attributes for scheduledActivity
    private static final int SCHEDULEDACTIVITYID = 1;
    private static final LocalDate SCHEDULEDACTIVITY_DATE = LocalDate.of(2025, 1, 1);
    private static final LocalTime SCHEDULEDACTIVITY_STARTTIME = LocalTime.of(10, 0);
    private static final LocalTime SCHEDULEDACTIVITY_ENDTIME = LocalTime.of(11, 0);
    private static final int SCHEDULEDACTIVITY_CAPACITY = 10;

    // attributes for registration
    private static final int REGISTRATIONID = 1;

    // create all
    @BeforeEach
    @AfterEach
    public void createAll() {
        // create account
        Account account1 = new Account(ACCOUNT1_USERNAME, ACCOUNT_PASSWORD);
        account1.setAccountId(ACCOUNT1ID);
        accountRepository.save(account1);
        Account account2 = new Account(ACCOUNT2_USERNAME, ACCOUNT_PASSWORD);
        account2.setAccountId(ACCOUNT2ID);
        accountRepository.save(account2);

        // create instructor
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS, INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account2);
        instructorRepository.save(instructor);

        // create customer
        Customer customer = new Customer(account1);
        customer.setAccountRoleId(ACCOUNT1ROLEID);
        customerRepository.save(customer);

        // create activity
        Activity activity = new Activity(ACTIVITY_CATEGORY, ACTIVITY_NAME, ACTIVITY_ISAPPROVED, ACTIVITY_DESCRIPTION);
        activityRepository.save(activity);

        // create scheduledActivity
        ScheduledActivity scheduledActivity = new ScheduledActivity(SCHEDULEDACTIVITY_DATE, SCHEDULEDACTIVITY_STARTTIME,
                SCHEDULEDACTIVITY_ENDTIME, instructor, activity, SCHEDULEDACTIVITY_CAPACITY);
        scheduledActivity.setScheduledActivityId(SCHEDULEDACTIVITYID);
        scheduledActivityRepository.save(scheduledActivity);

        // create registration
        Registration registration = new Registration(scheduledActivity, customer);
        registration.setRegistrationId(REGISTRATIONID);
        registrationRepository.save(registration);
    }

    // TODO: Insert your constants here
    /*
     * @Test
     * 
     * @Order(1)
     * public void testABC() {
     * // Create a new owner
     * // Send a POST request to /owners with the owner’s name
     * // Use client.postForEntity()
     * // Check that the owner was created by sending a GET request to
     * // /owners/{ownerId}
     * // Use client.getForEntity()
     * // Check that the owner’s name is correct
     * // Use assertEquals()
     * }
     */

    @Test
    @Order(1)
    public void testCreateRegistration() {
        Registration registrationDto = registrationRepository.findRegistrationByRegId(REGISTRATIONID);

        ResponseEntity<RegistrationDto> response = registration.postForEntity(
                "/register/" + ACCOUNT1ID + "/" + SCHEDULEDACTIVITYID, registrationDto, RegistrationDto.class);
        // check that registration was created
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        RegistrationDto registrations = response.getBody();
        assertNotNull(registrations);
        assertEquals(REGISTRATIONID, registrations.getRegistrationId());
        assertEquals(ACCOUNT1ID, registrations.getCustomer().getAccountRoleId());
        assertEquals(SCHEDULEDACTIVITYID, registrations.getScheduledActivity());
    }

    @Test
    @Order(2)
    public void testGetRegistrationByRegistrationId() {
        ResponseEntity<RegistrationDto> response = registration.getForEntity("/register/" + REGISTRATIONID,
                RegistrationDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RegistrationDto registration = response.getBody();
        assertNotNull(registration);
        assertEquals(REGISTRATIONID, registration.getRegistrationId());
        assertEquals(ACCOUNT1ID, registration.getCustomer().getAccountRoleId());
        assertEquals(SCHEDULEDACTIVITYID, registration.getScheduledActivity());
    }

}
