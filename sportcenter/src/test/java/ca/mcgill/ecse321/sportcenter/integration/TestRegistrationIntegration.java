package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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
    private static final int INVALID_SCHEDULEDACTIVITYID = -1;

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
    public void testCreateRegistrationInvalid() {
        Registration registrationDto = registrationRepository.findRegistrationByRegId(REGISTRATIONID);

        ResponseEntity<RegistrationDto> response = registration.postForEntity(
                "/register/" + INVALID_ACCOUNTID + "/" + SCHEDULEDACTIVITYID, registrationDto, RegistrationDto.class);
        // check that registration was not created
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ResponseEntity<RegistrationDto> response2 = registration.postForEntity(
                "/register/" + ACCOUNT1ID + "/" + INVALID_SCHEDULEDACTIVITYID, registrationDto, RegistrationDto.class);
        // check that registration was not created
        assertNotNull(response2);
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetRegistrationByRegistrationId() {

        ResponseEntity<RegistrationDto> response = registration.getForEntity(
                "/getRegistrationByRegId/" + REGISTRATIONID,
                RegistrationDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RegistrationDto registration = response.getBody();
        assertNotNull(registration);
        assertEquals(REGISTRATIONID, registration.getRegistrationId());
        assertEquals(ACCOUNT1ID, registration.getCustomer().getAccountRoleId());
        assertEquals(SCHEDULEDACTIVITYID, registration.getScheduledActivity().getScheduledActivityId());
    }

    @Test
    @Order(4)
    public void testGetRegistrationByRegistrationIdInvalid() {
        ResponseEntity<RegistrationDto> response = registration.getForEntity(
                "/getRegistrationByRegId/" + INVALID_ACCOUNTID,
                RegistrationDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testGetRegistrationsByScheduledActivityIdandCustomerId() {
        ResponseEntity<RegistrationDto[]> response = registration.getForEntity(
                "/registration/" + SCHEDULEDACTIVITYID + ACCOUNT1ROLEID, RegistrationDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RegistrationDto[] registrations = response.getBody();
        assertNotNull(registrations);
        assertEquals(1, registrations.length);

    }

    @Test
    @Order(6)
    public void testGetRegistrationsByScheduledActivityIdandCustomerIdInvalid() {
        ResponseEntity<RegistrationDto[]> response = registration.getForEntity(
                "/registration/" + INVALID_SCHEDULEDACTIVITYID + ACCOUNT1ROLEID, RegistrationDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ResponseEntity<RegistrationDto[]> response2 = registration.getForEntity(
                "/registration/" + SCHEDULEDACTIVITYID + ACCOUNT2ROLEID, RegistrationDto[].class);
        assertNotNull(response2);
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    }

    @Test
    @Order(7)
    public void testGetAllRegistrations() {
        ResponseEntity<RegistrationDto[]> response = registration.getForEntity("/registrations",
                RegistrationDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RegistrationDto[] registrations = response.getBody();
        assertNotNull(registrations);
        assertEquals(1, registrations.length);

    }

    @Test
    @Order(8)
    public void testGetRegistrationsByCustomerId() {
        ResponseEntity<RegistrationDto[]> response = registration.getForEntity(
                "/getRegistrationsByAccountRoleId/" + ACCOUNT1ID,
                RegistrationDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RegistrationDto[] registrations = response.getBody();
        assertNotNull(registrations);
        assertEquals(1, registrations.length);
    }

    @Test
    @Order(9)
    public void testGetRegistrationsByCustomerIdInvalid() {
        ResponseEntity<RegistrationDto[]> response = registration.getForEntity(
                "/getRegistrationsByAccountRoleId/" + INVALID_ACCOUNTID,
                RegistrationDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(10)
    public void testGetRegistrationsByScheduledActivityId() {
        ResponseEntity<RegistrationDto[]> response = registration
                .getForEntity("/registrations/scheduledActivity/" + SCHEDULEDACTIVITYID, RegistrationDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RegistrationDto[] registrations = response.getBody();
        assertNotNull(registrations);
        assertEquals(1, registrations.length);

    }

    @Test
    @Order(11)
    public void testGetRegistrationsByScheduledActivityIdInvalid() {
        ResponseEntity<RegistrationDto[]> response = registration
                .getForEntity("/registrations/scheduledActivity/" + INVALID_SCHEDULEDACTIVITYID,
                        RegistrationDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(12)
    public void testGetCustomersByScheduledActivityId() {
        ResponseEntity<CustomerDto[]> response = registration.getForEntity(
                "/registrations/costumers/" + SCHEDULEDACTIVITYID,
                CustomerDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        CustomerDto[] customers = response.getBody();
        assertNotNull(customers);
        assertEquals(1, customers.length);
    }

    @Test
    @Order(13)
    public void testGetCustomersByScheduledActivityIdInvalid() {
        ResponseEntity<CustomerDto[]> response = registration.getForEntity(
                "/registrations/costumers/" + INVALID_SCHEDULEDACTIVITYID,
                CustomerDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(14)
    public void testGetScheduledActivitiesByCustomer() {
        ResponseEntity<RegistrationDto[]> response = registration.getForEntity(
                "/registrations/scheduledActivity/customer/" + ACCOUNT1ROLEID,
                RegistrationDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RegistrationDto[] registrations = response.getBody();
        assertNotNull(registrations);
        assertEquals(1, registrations.length);
    }

    @Test
    @Order(15)
    public void testGetScheduledActivitiesByCustomerInvalid() {
        ResponseEntity<RegistrationDto[]> response = registration.getForEntity(
                "/registrations/scheduledActivity/customer/" + INVALID_ACCOUNTID,
                RegistrationDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
     * @Test
     * 
     * @Order(9)
     * public void testDeleteRegistrationByegistrationId() {
     * 
     * ResponseEntity<RegistrationDto> response = registration.???("/registration/"
     * + REGISTRATIONID, Response.class);
     * assertNotNull(response);
     * assertEquals(HttpStatus.OK, response.getStatusCode());
     * 
     * // check that registration was deleted
     * Registration registration =
     * registrationRepository.findRegistrationByRegId(REGISTRATIONID);
     * assertTrue(registration == null);
     * }
     * 
     */
}
