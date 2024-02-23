package ca.mcgill.ecse321.sportcenter.dao;

import java.time.LocalDate;
import java.time.LocalTime;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;

import org.junit.jupiter.api.AfterEach;
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
    @AfterEach
    public void clearDatabase() {
        scheduledActivityRepository.deleteAll();
        registrationRepository.deleteAll();
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
         * Create an Activity, set the attributes of the Activity, //and save the Activity
         */
        Activity activity = new Activity();
        ClassCategory subcategory = ClassCategory.Strength;
        String name = "Yoga";
        String description = "Practice yoga with a professional instructor.";
        boolean isApproved = true;
        
        activity.setSubcategory(subcategory);
        activity.setName(name);
        activity.setIsApproved(isApproved);
        activity.setDescription(description);

        activityRepository.save(activity);
        activity = activityRepository.findActivityByName(name);

        /**
        * Create an Account, set the attributes of the Account, //and save the Account
        */
        Account account = new Account();
        //int accountId = 7;
        String username = "Juan";
        String password = "password";
        account.setUsername(username);
        account.setPassword(password);
        //account.setAccountId(accountId);

        accountRepository.save(account);
        int accountId = account.getAccountId();
        account = accountRepository.findAccountByAccountId(accountId);

        /**
         * Create an Instructor, set the attribute of the Instructor, //and save the Instructor
         */
        Instructor instructor = new Instructor();
        //int accountRoleId = 70;
        InstructorStatus status = InstructorStatus.Active;
        String instructorDescription = "Good at teaching yoga.";
        String profilePicURL = "https://media.istockphoto.com/id/1495088043/vector/user-profile-icon-avatar-or-person-icon-profile-picture-portrait-symbol-default-portrait.jpg?s=612x612&w=0&k=20&c=dhV2p1JwmloBTOaGAtaA3AW1KSnjsdMt7-U_3EZElZ0=";
        //instructor.setAccountRoleId(accountRoleId);
        instructor.setStatus(status);
        instructor.setDescription(instructorDescription);
        instructor.setProfilePicURL(profilePicURL);
        instructor.setAccount(account);

        instructorRepository.save(instructor);
        int accountRoleId = instructor.getAccountRoleId();
        instructor = instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);

        /**
         * Create a ScheduledActivity, set the attributes of the ScheduledActivity, and save the ScheduledActivity
         */
        ScheduledActivity scheduledActivity = new ScheduledActivity();
        //int scheduledActivityId = 700;
        LocalDate date = LocalDate.of(2021, 11, 11);
        LocalTime startTime = LocalTime.of(10, 30, 00);
        LocalTime endTime = LocalTime.of(11, 30, 00);

        //scheduledActivity.setScheduledActivityId(scheduledActivityId);
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
         * Create an Account, set the attributes of the Account, and save the Account
         */
        Account account = new Account();
        String username = "Marco";
        String password = "password";
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);
        int accountId = account.getAccountId();
        account = accountRepository.findAccountByAccountId(accountId);

        /**
         * Create a Customer, set the attributes of the Customer, and save the Customer
         */
        Customer customer = new Customer();
        customer.setAccount(account);
        customerRepository.save(customer);
        int accountRoleId = customer.getAccountRoleId();
        customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        
        /**
         * Create a Registration, set the attributes of the Registration, and save the Registration
         */
        Registration registration = new Registration();
        //int regID = 600;
        registration.setCustomer(customer);
        registration.setScheduledActivity(scheduledActivity);
        //registration.setRegId(regID);
        registrationRepository.save(registration);
        int regId = registration.getRegId();

        /**
         * Load the Registration and check the attributes of the Registration
         */
        registration = registrationRepository.findRegistrationByRegId(regId);//could be by id

        /**
         * Check the attributes of the Registration
         */
        assertNotNull(registration);
        assertEquals(regId, registration.getRegId());
        //assertEquals(customer, registration.getCustomer());
        //assertEquals(scheduledActivity, registration.getScheduledActivity());
        assertEquals(date, registration.getScheduledActivity().getDate());
        assertEquals(startTime, registration.getScheduledActivity().getStartTime());
        assertEquals(endTime, registration.getScheduledActivity().getEndTime());
        assertEquals(scheduledActivityId, registration.getScheduledActivity().getScheduledActivityId());
    }
}