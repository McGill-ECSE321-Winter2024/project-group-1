package ca.mcgill.ecse321.sportcenter.dao;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

/**
 * @author Andrew Nemr and Patrick Zakaria
 */
@SpringBootTest
public class TestRegistrationPersistence {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScheduledActivityRepository scheduledActivityRepository;

    /**
     * Clear the database after each test
     */
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

    /**
     * Test the persistence of a Registration
     */
    @Test
    public void testPersistAndLoadRegistration() {
        /**
         * Create an Activity, set the attributes of the Activity, //and save the
         * Activity
         */
        Activity activity = new Activity();
        ClassCategory subcategory = ClassCategory.Strength;
        String name = "Yoga";
        String description = "Practice yoga with a professional instructor.";
        boolean isApproved = true;

        activity.setSubCategory(subcategory);
        activity.setName(name);
        activity.setIsApproved(isApproved);
        activity.setDescription(description);

        activityRepository.save(activity);
        activity = activityRepository.findActivityByName(name);

        /**
         * Create an Account, set the attributes of the Account, //and save the Account
         */
        Account account = new Account();
        String username = "Juan";
        String password = "password";
        account.setUsername(username);
        account.setPassword(password);

        accountRepository.save(account);
        int accountId = account.getAccountId();
        account = accountRepository.findAccountByAccountId(accountId);

        /**
         * Create an Instructor, set the attribute of the Instructor, //and save the
         * Instructor
         */
        Instructor instructor = new Instructor();
        InstructorStatus status = InstructorStatus.Active;
        String instructorDescription = "Good at teaching yoga.";
        String profilePicURL = "https://media.istockphoto.com/id/1495088043/vector/user-profile-icon-avatar-or-person-icon-profile-picture-portrait-symbol-default-portrait.jpg?s=612x612&w=0&k=20&c=dhV2p1JwmloBTOaGAtaA3AW1KSnjsdMt7-U_3EZElZ0=";
        instructor.setStatus(status);
        instructor.setDescription(instructorDescription);
        instructor.setProfilePicURL(profilePicURL);
        instructor.setAccount(account);

        instructorRepository.save(instructor);
        int accountRoleId = instructor.getAccountRoleId();
        instructor = instructorRepository.findInstructorByAccountRoleId(accountRoleId);

        /**
         * Create a ScheduledActivity, set the attributes of the ScheduledActivity, and
         * save the ScheduledActivity
         */
        ScheduledActivity scheduledActivity = new ScheduledActivity();
        LocalDate date = LocalDate.of(2021, 11, 11);
        LocalTime startTime = LocalTime.of(10, 30, 00);
        LocalTime endTime = LocalTime.of(11, 30, 00);

        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setEndTime(endTime);
        scheduledActivity.setSupervisor(instructor);
        scheduledActivity.setActivity(activity);

        scheduledActivityRepository.save(scheduledActivity);
        int scheduledActivityId = scheduledActivity.getScheduledActivityId();

        /**
         * Load the ScheduledActivity
         */
        scheduledActivity = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId);

        /**
         * Create an Account
         */
        Account account1 = new Account();
        String username1 = "Maria";
        String password1 = "password";

        /**
         * Set the attributes of the Account
         */
        account1.setUsername(username1);
        account1.setPassword(password1);

        /**
         * Save the Account
         */
        accountRepository.save(account1);
        int accountId1 = account.getAccountId();
        account1 = accountRepository.findAccountByAccountId(accountId1);

        /**
         * Create a Customer
         */
        Customer customer = new Customer();
        /**
         * Set the attributes of the Customer
         */
        customer.setAccount(account);
        /**
         * Save the Customer
         */
        customerRepository.save(customer);
        int accountRoleId1 = customer.getAccountRoleId();

        /**
         * Load the Customer
         */
        customer = customerRepository.findCustomerByAccountRoleId(accountRoleId1);

        /**
         * Create a Registration, set the attributes of the Registration, and save the
         * Registration
         */
        Registration registration = new Registration();
        registration.setCustomer(customer);
        registration.setScheduledActivity(scheduledActivity);
        registrationRepository.save(registration);
        int regId = registration.getRegistrationId();

        /**
         * Load the Registration and check the attributes of the Registration
         */
        registration = registrationRepository.findRegistrationByRegId(regId);// could be by id

        /**
         * Check the attributes of the Registration
         */
        assertNotNull(registration);
        assertEquals(regId, registration.getRegistrationId());
        assertEquals(date, registration.getScheduledActivity().getDate());
        assertEquals(startTime, registration.getScheduledActivity().getStartTime());
        assertEquals(endTime, registration.getScheduledActivity().getEndTime());
        assertEquals(scheduledActivityId, registration.getScheduledActivity().getScheduledActivityId());
    }
}