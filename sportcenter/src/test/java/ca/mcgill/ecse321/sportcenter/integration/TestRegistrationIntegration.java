package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;
import ca.mcgill.ecse321.sportcenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportcenter.dto.RegistrationDto;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("null")
public class TestRegistrationIntegration {
        @Autowired
        private TestRestTemplate client; // Always stays the same

        @Autowired
        private RegistrationRepository registrationRepository;

        @Autowired
        private ScheduledActivityRepository scheduledActivityRepository;

        @Autowired
        private ActivityRepository activityRepository;

        @Autowired
        private CustomerRepository customerRepository;

        @Autowired
        private InstructorRepository instructorRepository; // TODO: Make sure it's the good type

        @Autowired
        private AccountRepository accountRepository;

        @BeforeEach
        @AfterEach
        public void clearDatabase() {
                registrationRepository.deleteAll();
                scheduledActivityRepository.deleteAll();
                activityRepository.deleteAll();
                customerRepository.deleteAll();
                instructorRepository.deleteAll();
                accountRepository.deleteAll();
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

        // attributes for instructor
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
                Instructor instructor = new Instructor(INSTRUCTOR_STATUS, INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE,
                                account2);
                instructorRepository.save(instructor);

                // create customer
                Customer customer = new Customer(account1);
                customer.setAccountRoleId(ACCOUNT1ROLEID);
                customerRepository.save(customer);

                // create activity
                Activity activity = new Activity(ACTIVITY_CATEGORY, ACTIVITY_NAME, ACTIVITY_ISAPPROVED,
                                ACTIVITY_DESCRIPTION);
                activityRepository.save(activity);

                // create scheduledActivity
                ScheduledActivity scheduledActivity = new ScheduledActivity(SCHEDULEDACTIVITY_DATE,
                                SCHEDULEDACTIVITY_STARTTIME,
                                SCHEDULEDACTIVITY_ENDTIME, instructor, activity, SCHEDULEDACTIVITY_CAPACITY);
                scheduledActivity.setScheduledActivityId(SCHEDULEDACTIVITYID);
                scheduledActivityRepository.save(scheduledActivity);

                // create registration
                Registration registration = new Registration(scheduledActivity, customer);
                registration.setRegistrationId(REGISTRATIONID);
                registrationRepository.save(registration);
        }

        @Test
        @Order(1)
        public void testABC() {
                // Create a new owner
                // Send a POST request to /owners with the owner’s name
                // Use client.postForEntity()
                // Check that the owner was created by sending a GET request to
                // /owners/{ownerId}
                // Use client.getForEntity()
                // Check that the owner’s name is correct
                // Use assertEquals()
        }

        // TODO: Continue the tests here
}
