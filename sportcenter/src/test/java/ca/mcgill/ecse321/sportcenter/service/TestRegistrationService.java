package ca.mcgill.ecse321.sportcenter.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.sportcenter.dao.*;
import ca.mcgill.ecse321.sportcenter.model.*;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.dao.*;

/**
 * Test class for the Registration entity
 * 
 * @author Emilie Ruel
 */
@ExtendWith(MockitoExtension.class)
public class TestRegistrationService {

        @Mock
        private RegistrationRepository registrationDao;

        @Mock
        private AccountRepository accountDao;

        @Mock
        private CustomerRepository customerDao;

        @Mock
        private InstructorRepository instructorDao;

        @Mock
        private ScheduledActivityRepository scheduledActivityDao;

        @Mock
        private ActivityRepository activityDao;

        @Mock
        private OwnerRepository ownerDao;

        @InjectMocks
        private RegistrationManagementService registrationService;

        @BeforeEach
        void init() {

                // Create a customer
                Account customerAccount = new Account();
                customerAccount.setUsername("customer");
                customerAccount.setPassword("password");
                accountDao.save(customerAccount);

                Customer customer = new Customer();
                customer.setAccount(customerAccount);
                customerDao.save(customer);

                // Create an approved instructor
                Account approvedInstructorAccount = new Account();
                approvedInstructorAccount.setUsername("approvedInstructor");
                approvedInstructorAccount.setPassword("password");
                accountDao.save(approvedInstructorAccount);

                Instructor approvedInstructor = new Instructor();
                approvedInstructor.setAccount(approvedInstructorAccount);
                approvedInstructor.setStatus(InstructorStatus.Active);
                approvedInstructor.setDescription("description");
                approvedInstructor.setProfilePicURL("pictureURL");
                instructorDao.save(approvedInstructor);

                // Create a disapproved instructor
                Account disapprovedInstructorAccount = new Account();
                disapprovedInstructorAccount.setUsername("disapprovedInstructor");
                disapprovedInstructorAccount.setPassword("password");
                accountDao.save(disapprovedInstructorAccount);

                Instructor disapprovedInstructor = new Instructor();
                disapprovedInstructor.setAccount(approvedInstructorAccount);
                disapprovedInstructor.setStatus(InstructorStatus.Pending);
                disapprovedInstructor.setDescription("description");
                disapprovedInstructor.setProfilePicURL("pictureURL");
                instructorDao.save(disapprovedInstructor);

                // Create an owner
                Account ownerAccount = new Account();
                ownerAccount.setUsername("owner");
                ownerAccount.setPassword("password");
                accountDao.save(ownerAccount);

                Owner owner = new Owner();
                owner.setAccount(customerAccount);
                ownerDao.save(owner);

                // Create an activity
                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(false);
                activityDao.save(activity);

                // Create a scheduled activity
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity.setDate(LocalDate.now());
                scheduledActivity.setStartTime(LocalTime.now());
                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                scheduledActivity.setSupervisor(disapprovedInstructor);
                scheduledActivity.setActivity(activity);
                scheduledActivity.setCapacity(30);
                scheduledActivityDao.save(scheduledActivity);

        }

        @SuppressAjWarnings("null")
        /**
         * Tests the creation of a registration -> Sucess
         */
        @Test
        public void testCreateRegistration() {
                // Set up
                Account account = new Account();
                String username = "username";
                String password = "password";
                account.setUsername(username);
                account.setPassword(password);

                Customer customer = new Customer();
                customer.setAccount(account);

                Account instructorAccount = new Account();
                String instructorUsername = "instructor";
                String instructorPassword = "password";
                instructorAccount.setUsername(instructorUsername);
                instructorAccount.setPassword(instructorPassword);

                Instructor instructor = new Instructor();
                instructor.setAccount(instructorAccount);
                instructor.setStatus(InstructorStatus.Active);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");

                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(true);

                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity.setDate(LocalDate.now());
                scheduledActivity.setStartTime(LocalTime.now());
                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                scheduledActivity.setSupervisor(instructor);
                scheduledActivity.setActivity(activity);
                scheduledActivity.setCapacity(30);

                Registration registration = new Registration();
                registration.setCustomer(customer);
                registration.setScheduledActivity(scheduledActivity);

                // Mocking

                when(accountDao.findAccountByUsername(username)).thenReturn(account);
                when(accountDao.findAccountByUsername(instructorUsername)).thenReturn(instructorAccount);
                when(customerDao.findAccountRoleByAccountRoleId(customer.getAccountRoleId())).thenReturn(customer); //
                when(instructorDao.findAccountRoleByAccountRoleId(instructorAccount.getAccountId()))
                                .thenReturn(instructor);
                when(scheduledActivityDao
                                .findScheduledActivityByScheduledActivityId(scheduledActivity.getScheduledActivityId())) //
                                .thenReturn(scheduledActivity);
                when(registrationDao.save(any(Registration.class))).thenReturn(registration);

                // Test

                Registration createdRegistration = null;
                try {
                        createdRegistration = registrationService.createRegistration(customer.getAccountRoleId(),
                                        scheduledActivity.getScheduledActivityId());
                } catch (IllegalArgumentException e) {
                        fail();
                }

                // Assert

                assertNull(createdRegistration);
                assertEquals(customer, createdRegistration.getCustomer());
                assertEquals(scheduledActivity, createdRegistration.getScheduledActivity());
                assertEquals(1, createdRegistration.getRegistrationId());
        }

        /*
         * Tests the creation of a registration with an invalid accountRoleId -> Fail
         */
        @Test
        public void testCreateRegistrationInvalidAccountRoleId() {
                // Set up
                Account account = new Account();
                String username = "username";
                String password = "password";
                account.setUsername(username);
                account.setPassword(password);

                Customer customer = new Customer();
                customer.setAccount(account);

                Account instructorAccount = new Account();
                String instructorUsername = "instructor";
                String instructorPassword = "password";
                instructorAccount.setUsername(instructorUsername);
                instructorAccount.setPassword(instructorPassword);

                Instructor instructor = new Instructor();
                instructor.setAccount(instructorAccount);
                instructor.setStatus(InstructorStatus.Active);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");

                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(true);

                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity.setDate(LocalDate.now());
                scheduledActivity.setStartTime(LocalTime.now());
                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                scheduledActivity.setSupervisor(instructor);
                scheduledActivity.setActivity(activity);
                scheduledActivity.setCapacity(30);

                // Mocking

                when(accountDao.findAccountByUsername(username)).thenReturn(account);
                when(accountDao.findAccountByUsername(instructorUsername)).thenReturn(instructorAccount);
                when(customerDao.findAccountRoleByAccountRoleId(customer.getAccountRoleId())).thenReturn(customer); //
                when(instructorDao.findAccountRoleByAccountRoleId(instructorAccount.getAccountId()))
                                .thenReturn(instructor);
                when(scheduledActivityDao
                                .findScheduledActivityByScheduledActivityId(scheduledActivity.getScheduledActivityId())) //
                                .thenReturn(scheduledActivity);

                // Test and Assert

                try {
                        Registration registration = new Registration();
                        registration = registrationService.createRegistration(-1,
                                        scheduledActivity.getScheduledActivityId());
                } catch (IllegalArgumentException e) {
                        assertEquals("Account role id not valid!", e.getMessage());
                }
                fail();
        }

        /*
         * Tests the creation of a registration with an invalid scheduledActivityId ->
         * Fail
         */
        @Test
        public void testCreateRegistrationInvalidScheduledActivityId() {
                // Set up
                Account account = new Account();
                String username = "username";
                String password = "password";
                account.setUsername(username);
                account.setPassword(password);

                Customer customer = new Customer();
                customer.setAccount(account);

                Account instructorAccount = new Account();
                String instructorUsername = "instructor";
                String instructorPassword = "password";
                instructorAccount.setUsername(instructorUsername);
                instructorAccount.setPassword(instructorPassword);

                Instructor instructor = new Instructor();
                instructor.setAccount(instructorAccount);
                instructor.setStatus(InstructorStatus.Active);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");

                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(true);

                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity.setDate(LocalDate.now());
                scheduledActivity.setStartTime(LocalTime.now());
                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                scheduledActivity.setSupervisor(instructor);
                scheduledActivity.setActivity(activity);
                scheduledActivity.setCapacity(30);

                // Mocking

                when(accountDao.findAccountByUsername(username)).thenReturn(account);
                when(accountDao.findAccountByUsername(instructorUsername)).thenReturn(instructorAccount);
                when(customerDao.findAccountRoleByAccountRoleId(customer.getAccountRoleId())).thenReturn(customer); //
                when(instructorDao.findAccountRoleByAccountRoleId(instructorAccount.getAccountId()))
                                .thenReturn(instructor);
                when(scheduledActivityDao
                                .findScheduledActivityByScheduledActivityId(scheduledActivity.getScheduledActivityId())) //
                                .thenReturn(scheduledActivity);

                // Test and Assert

                try {
                        Registration registration = new Registration();
                        registration = registrationService.createRegistration(customer.getAccountRoleId(), -1);
                } catch (IllegalArgumentException e) {
                        assertEquals("Scheduled activity id not valid!", e.getMessage());
                }
                fail();
        }

        /*
         * Tests the creation of a registration with a customer that doesn't exist ->
         * Fail
         */
        @Test
        public void testCreateRegistrationNonExistingCustomer() {
                // Set up
                Account account = new Account();
                String username = "username";
                String password = "password";
                account.setUsername(username);
                account.setPassword(password);

                Customer customer = new Customer();
                customer.setAccount(account);

                Account instructorAccount = new Account();
                String instructorUsername = "instructor";
                String instructorPassword = "password";
                instructorAccount.setUsername(instructorUsername);
                instructorAccount.setPassword(instructorPassword);

                Instructor instructor = new Instructor();
                instructor.setAccount(instructorAccount);
                instructor.setStatus(InstructorStatus.Active);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");

                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(true);

                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity.setDate(LocalDate.now());
                scheduledActivity.setStartTime(LocalTime.now());
                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                scheduledActivity.setSupervisor(instructor);
                scheduledActivity.setActivity(activity);
                scheduledActivity.setCapacity(30);

                // Mocking

                when(accountDao.findAccountByUsername(username)).thenReturn(account);
                when(accountDao.findAccountByUsername(instructorUsername)).thenReturn(instructorAccount);
                when(customerDao.findAccountRoleByAccountRoleId(customer.getAccountRoleId())).thenReturn(customer); //
                when(instructorDao.findAccountRoleByAccountRoleId(instructorAccount.getAccountId()))
                                .thenReturn(instructor);
                when(scheduledActivityDao
                                .findScheduledActivityByScheduledActivityId(scheduledActivity.getScheduledActivityId())) //
                                .thenReturn(scheduledActivity);

                // Test and Assert

                try {
                        Registration registration = new Registration();
                        registration = registrationService.createRegistration(2,
                                        scheduledActivity.getScheduledActivityId());
                } catch (IllegalArgumentException e) {
                        assertEquals("Customer does not exist", e.getMessage());
                }
                fail();
        }

        /*
         * Tests the creation of a registration with scheduled activity that doesn't
         * exist ->
         * Fail
         */
        @Test
        public void testCreateRegistrationNonExistingScheduledActivity() {
                // Set up
                Account account = new Account();
                String username = "username";
                String password = "password";
                account.setUsername(username);
                account.setPassword(password);

                Customer customer = new Customer();
                customer.setAccount(account);

                Account instructorAccount = new Account();
                String instructorUsername = "instructor";
                String instructorPassword = "password";
                instructorAccount.setUsername(instructorUsername);
                instructorAccount.setPassword(instructorPassword);

                Instructor instructor = new Instructor();
                instructor.setAccount(instructorAccount);
                instructor.setStatus(InstructorStatus.Active);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");

                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(true);

                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity.setDate(LocalDate.now());
                scheduledActivity.setStartTime(LocalTime.now());
                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                scheduledActivity.setSupervisor(instructor);
                scheduledActivity.setActivity(activity);
                scheduledActivity.setCapacity(30);

                // Mocking

                when(accountDao.findAccountByUsername(username)).thenReturn(account);
                when(accountDao.findAccountByUsername(instructorUsername)).thenReturn(instructorAccount);
                when(customerDao.findAccountRoleByAccountRoleId(customer.getAccountRoleId())).thenReturn(customer); //
                when(instructorDao.findAccountRoleByAccountRoleId(instructorAccount.getAccountId()))
                                .thenReturn(instructor);
                when(scheduledActivityDao
                                .findScheduledActivityByScheduledActivityId(scheduledActivity.getScheduledActivityId())) //
                                .thenReturn(scheduledActivity);

                // Test and Assert

                try {
                        Registration registration = new Registration();
                        registration = registrationService.createRegistration(customer.getAccountRoleId(), 2);
                } catch (IllegalArgumentException e) {
                        assertEquals("Scheduled activity does not exist", e.getMessage());
                }
                fail();
        }

        /*
         * Tests the creation of a registration that already exists -> Fail
         */
        @Test
        public void testCreateRegistrationDuplicate() {
                // Set up
                Account account = new Account();
                String username = "username";
                String password = "password";
                account.setUsername(username);
                account.setPassword(password);

                Customer customer = new Customer();
                customer.setAccount(account);

                Account instructorAccount = new Account();
                String instructorUsername = "instructor";
                String instructorPassword = "password";
                instructorAccount.setUsername(instructorUsername);
                instructorAccount.setPassword(instructorPassword);

                Instructor instructor = new Instructor();
                instructor.setAccount(instructorAccount);
                instructor.setStatus(InstructorStatus.Active);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");

                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(true);

                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity.setDate(LocalDate.now());
                scheduledActivity.setStartTime(LocalTime.now());
                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                scheduledActivity.setSupervisor(instructor);
                scheduledActivity.setActivity(activity);
                scheduledActivity.setCapacity(30);

                Registration registration = new Registration();
                registration.setCustomer(customer);
                registration.setScheduledActivity(scheduledActivity);

                // Mocking

                when(accountDao.findAccountByUsername(username)).thenReturn(account);
                when(accountDao.findAccountByUsername(instructorUsername)).thenReturn(instructorAccount);
                when(customerDao.findAccountRoleByAccountRoleId(customer.getAccountRoleId())).thenReturn(customer); //
                when(instructorDao.findAccountRoleByAccountRoleId(instructorAccount.getAccountId()))
                                .thenReturn(instructor);
                when(scheduledActivityDao
                                .findScheduledActivityByScheduledActivityId(scheduledActivity.getScheduledActivityId())) //
                                .thenReturn(scheduledActivity);

                // Save the original registration
                registrationDao.save(registration); // Don't know if this would work

                // Test and Assert

                try {
                        Registration createdRegistration = registrationService.createRegistration(
                                        customer.getAccountRoleId(),
                                        scheduledActivity.getScheduledActivityId());
                } catch (IllegalArgumentException e) {
                        assertEquals("Registration already exists for this customer and scheduled activity",
                                        e.getMessage());
                }
                fail();

        }

        /*
         * Tests the creation of a registration with a scheduled activity in the past ->
         * Fail
         */
        @Test
        public void testCreateRegistrationDatePassed() {

                // Set up
                Account account = new Account();
                String username = "username";
                String password = "password";
                account.setUsername(username);
                account.setPassword(password);

                Customer customer = new Customer();
                customer.setAccount(account);

                Account instructorAccount = new Account();
                String instructorUsername = "instructor";
                String instructorPassword = "password";
                instructorAccount.setUsername(instructorUsername);
                instructorAccount.setPassword(instructorPassword);

                Instructor instructor = new Instructor();
                instructor.setAccount(instructorAccount);
                instructor.setStatus(InstructorStatus.Active);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");

                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(true);

                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity.setDate(LocalDate.now().minusDays(1));
                scheduledActivity.setStartTime(LocalTime.now());
                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                scheduledActivity.setSupervisor(instructor);
                scheduledActivity.setActivity(activity);
                scheduledActivity.setCapacity(30);

                // Mocking

                when(accountDao.findAccountByUsername(username)).thenReturn(account);
                when(accountDao.findAccountByUsername(instructorUsername)).thenReturn(instructorAccount);
                when(customerDao.findAccountRoleByAccountRoleId(customer.getAccountRoleId())).thenReturn(customer); //
                when(instructorDao.findAccountRoleByAccountRoleId(instructorAccount.getAccountId()))
                                .thenReturn(instructor);
                when(scheduledActivityDao
                                .findScheduledActivityByScheduledActivityId(scheduledActivity.getScheduledActivityId())) //
                                .thenReturn(scheduledActivity);

                // Test and Assert

                try {
                        Registration registration = new Registration();
                        registration = registrationService.createRegistration(customer.getAccountRoleId(),
                                        scheduledActivity.getScheduledActivityId());
                } catch (IllegalArgumentException e) {
                        assertEquals("Scheduled activity is in the past", e.getMessage());
                }
                fail();
        }

        /*
         * Tests the creation of a registration with a scheduled activity that is full
         * ->
         * Fail
         */
        @Test
        public void testCreateRegistrationFull() {

                // Set up
                Account account1 = new Account();
                String username1 = "username";
                String password1 = "password";
                account1.setUsername(username1);
                account1.setPassword(password1);

                Customer customer1 = new Customer();
                customer1.setAccount(account1);

                Account instructorAccount = new Account();
                String instructorUsername = "instructor";
                String instructorPassword = "password";
                instructorAccount.setUsername(instructorUsername);
                instructorAccount.setPassword(instructorPassword);

                Instructor instructor = new Instructor();
                instructor.setAccount(instructorAccount);
                instructor.setStatus(InstructorStatus.Active);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");

                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(true);

                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity.setDate(LocalDate.now());
                scheduledActivity.setStartTime(LocalTime.now());
                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                scheduledActivity.setSupervisor(instructor);
                scheduledActivity.setActivity(activity);
                scheduledActivity.setCapacity(1);

                Registration registration1 = new Registration();
                registration1.setCustomer(customer1);
                registration1.setScheduledActivity(scheduledActivity);

                Account account2 = new Account();
                String username2 = "username2";
                String password2 = "password";
                account2.setUsername(username2);
                account2.setPassword(password2);

                Customer customer2 = new Customer();
                customer2.setAccount(account2);

                Registration registration2 = new Registration();
                registration2.setCustomer(customer2);
                registration2.setScheduledActivity(scheduledActivity);

                // Mocking

                when(accountDao.findAccountByUsername(username1)).thenReturn(account1);
                when(accountDao.findAccountByUsername(instructorUsername)).thenReturn(instructorAccount);
                when(customerDao.findAccountRoleByAccountRoleId(customer1.getAccountRoleId())).thenReturn(customer1); //
                when(instructorDao.findAccountRoleByAccountRoleId(instructorAccount.getAccountId()))
                                .thenReturn(instructor);
                when(scheduledActivityDao
                                .findScheduledActivityByScheduledActivityId(scheduledActivity.getScheduledActivityId())) //
                                .thenReturn(scheduledActivity);
                when(registrationDao.save(any(Registration.class))).thenReturn(registration1);

                // Save the original registration

                registrationDao.save(registration1); // Don't know if this would work

                // Test and Assert

                try {
                        Registration createdRegistration = registrationService.createRegistration(
                                        customer2.getAccountRoleId(),
                                        scheduledActivity.getScheduledActivityId());
                } catch (IllegalArgumentException e) {
                        assertEquals("Scheduled activity is full", e.getMessage());
                }
                fail();
        }

        /*
         * Tests the getting of a registration by registration ID -> Success
         */
        @Test
        public void testGetRegistrationById() {
                // Set up
                Account account = new Account();
                String username = "username";
                String password = "password";
                account.setUsername(username);
                account.setPassword(password);

                Customer customer = new Customer();
                customer.setAccount(account);

                Account instructorAccount = new Account();
                String instructorUsername = "instructor";
                String instructorPassword = "password";
                instructorAccount.setUsername(instructorUsername);
                instructorAccount.setPassword(instructorPassword);

                Instructor instructor = new Instructor();
                instructor.setAccount(instructorAccount);
                instructor.setStatus(InstructorStatus.Active);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");

                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(true);

                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity.setDate(LocalDate.now());
                scheduledActivity.setStartTime(LocalTime.now());
                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                scheduledActivity.setSupervisor(instructor);
                scheduledActivity.setActivity(activity);
                scheduledActivity.setCapacity(30);

                Registration registration = new Registration();
                registration.setCustomer(customer);
                registration.setScheduledActivity(scheduledActivity);

                // Mocking

                when(registrationDao.findRegistrationByRegId(registration.getRegistrationId()))
                                .thenReturn(registration);

                // Test

                Registration foundRegistration = null;
                try {
                        foundRegistration = registrationService
                                        .getRegistrationByRegId(registration.getRegistrationId());
                } catch (IllegalArgumentException e) {
                        fail();
                }

                // Assert

                assertNotNull(foundRegistration);
                assertEquals(customer, foundRegistration.getCustomer());
                assertEquals(scheduledActivity, foundRegistration.getScheduledActivity());
        }

        /*
         * Tests the getting of a registration by registration ID that is invalid ->
         * Fail
         */
        @Test
        public void testGetRegistrationByIdInvalid() {
                // Set up
                Account account = new Account();
                String username = "username";
                String password = "password";
                account.setUsername(username);
                account.setPassword(password);

                Customer customer = new Customer();
                customer.setAccount(account);

                Account instructorAccount = new Account();
                String instructorUsername = "instructor";
                String instructorPassword = "password";
                instructorAccount.setUsername(instructorUsername);
                instructorAccount.setPassword(instructorPassword);

                Instructor instructor = new Instructor();
                instructor.setAccount(instructorAccount);
                instructor.setStatus(InstructorStatus.Active);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");

                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(true);

                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity.setDate(LocalDate.now());
                scheduledActivity.setStartTime(LocalTime.now());
                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                scheduledActivity.setSupervisor(instructor);
                scheduledActivity.setActivity(activity);
                scheduledActivity.setCapacity(30);

                // Mocking

                when(registrationDao.findRegistrationByRegId(registration.getRegistrationId()))
                                .thenReturn(registration);

                // Test and Assert

                try {
                        Registration foundRegistration = registrationService.getRegistrationByRegId(-1);
                } catch (IllegalArgumentException e) {
                        assertEquals("Registration Id not valid!", e.getMessage());
                }
                fail();
        }

        /*
         * Tests the getting of a registration by registration ID that doesn't exist ->
         * Fail
         */
        @Test
        public void testGetRegistrationByIdNonExisting() {
                // Set up
                Account account = new Account();
                String username = "username";
                String password = "password";
                account.setUsername(username);
                account.setPassword(password);

                Customer customer = new Customer();
                customer.setAccount(account);

                Account instructorAccount = new Account();
                String instructorUsername = "instructor";
                String instructorPassword = "password";
                instructorAccount.setUsername(instructorUsername);
                instructorAccount.setPassword(instructorPassword);

                Instructor instructor = new Instructor();
                instructor.setAccount(instructorAccount);
                instructor.setStatus(InstructorStatus.Active);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");

                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(true);

                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity.setDate(LocalDate.now());
                scheduledActivity.setStartTime(LocalTime.now());
                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                scheduledActivity.setSupervisor(instructor);
                scheduledActivity.setActivity(activity);
                scheduledActivity.setCapacity(30);

                // Mocking

                when(registrationDao.findRegistrationByRegId(registration.getRegistrationId()))
                                .thenReturn(registration);

                // Test and Assert

                try {
                        Registration foundRegistration = registrationService.getRegistrationByRegId(2);
                } catch (IllegalArgumentException e) {
                        assertEquals("Registration does not exist", e.getMessage());
                }
                fail();
        }

        // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        // test delete registration
        @Test
        public void testDeleteRegistration() {
                String username = "username";
                String password = "password";
                Account account = new Account();
                account = accountService.createAccount(username, password);
                Customer customer = new Customer();
                customer = accountService.createCustomer(username);
                Activity activity1 = new Activity();
                activity1 = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                accountService.approveActivity(activity1.getName());
                ScheduledActivity scheduledActivity1 = new ScheduledActivity();

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                scheduledActivity1 = scheduledActivityService.createScheduledActivity(LocalDate.of(2024, 12, 12),
                                LocalTime.of(10, 0),
                                LocalTime.of(12, 0), instructor, activity1, 30);

                Registration registration = new Registration();

                registration = registrationService.createRegistration(customer.getAccount().getAccountId(),
                                scheduledActivity.getScheduledActivityId());

                registrationService.deleteRegistration(registration.getRegistrationId());

                assertNull(registrationService.getRegistrationById(registration.getRegistrationId()));
        }

        // test delete registration null
        @Test
        public void testDeleteRegistrationNull() {
                try {
                        registrationService.deleteRegistration(-1);
                } catch (IllegalArgumentException e) {
                        assertEquals("Id not valid!", e.getMessage());
                }
        }

        // test delete all registrations
        @Test
        public void testDeleteAllRegistrations() {
                String username1 = "username1";
                String username2 = "username2";
                String password = "password";
                Account account1 = new Account();
                Account account2 = new Account();
                account1 = accountService.createAccount(username1, password);
                account2 = accountService.createAccount(username1, password);
                Customer customer1 = new Customer();
                Customer customer2 = new Customer();
                customer1 = accountService.createCustomer(username1);
                customer2 = accountService.createCustomer(username2);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity1 = new Activity();
                Activity activity2 = new Activity();
                activity1 = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                activity2 = activityService.createActivity("activityName2", "activityDescription2",
                                ClassCategory.Cardio);
                accountService.approveActivity(activity1.getName());
                accountService.approveActivity(activity2.getName());
                ScheduledActivity scheduledActivity1 = new ScheduledActivity();
                ScheduledActivity scheduledActivity2 = new ScheduledActivity();
                scheduledActivity1 = scheduledActivityService.createScheduledActivity(LocalDate.of(2024, 12, 12),
                                LocalTime.of(10, 0),
                                LocalTime.of(12, 0), instructor, activity1, 30);
                scheduledActivity2 = scheduledActivityService.createScheduledActivity(LocalDate.of(2024, 11, 11),
                                LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity2, 30);

                Registration registration1 = new Registration();
                Registration registration2 = new Registration();
                Registration registration3 = new Registration();
                Registration registration4 = new Registration();
                registration1 = registrationService.createRegistration(customer1.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration2 = registrationService.createRegistration(customer1.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());
                registration3 = registrationService.createRegistration(customer2.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration4 = registrationService.createRegistration(customer2.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());

                registrationService.deleteAllRegistrations();

                assertTrue(registrationService.getAllRegistrations().size() == 0);
        }

        // tests getRegistrationById null
        @Test
        public void testGetRegistrationByIdNull() {
                try {
                        registrationService.getRegistrationById(-1);
                } catch (IllegalArgumentException e) {
                        assertEquals("Id not valid!", e.getMessage());
                }

        }

        // tests getAllRegistrations()
        @Test
        public void testGetAllRegistrations() {
                String username1 = "username1";
                String username2 = "username2";
                String password = "password";
                Account account1 = new Account();
                Account account2 = new Account();
                account1 = accountService.createAccount(username1, password);
                account2 = accountService.createAccount(username1, password);
                Customer customer1 = new Customer();
                Customer customer2 = new Customer();
                customer1 = accountService.createCustomer(username1);
                customer2 = accountService.createCustomer(username2);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity1 = new Activity();
                Activity activity2 = new Activity();
                activity1 = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                activity2 = activityService.createActivity("activityName2", "activityDescription2",
                                ClassCategory.Cardio);
                accountService.approveActivity(activity1.getName());
                accountService.approveActivity(activity2.getName());
                ScheduledActivity scheduledActivity1 = new ScheduledActivity();
                ScheduledActivity scheduledActivity2 = new ScheduledActivity();
                scheduledActivity1 = scheduledActivityService.createScheduledActivity(LocalDate.of(2024, 12, 12),
                                LocalTime.of(10, 0),
                                LocalTime.of(12, 0), instructor, activity1, 30);
                scheduledActivity2 = scheduledActivityService.createScheduledActivity(LocalDate.of(2024, 11, 11),
                                LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity2, 30);

                Registration registration1 = new Registration();
                Registration registration2 = new Registration();
                Registration registration3 = new Registration();
                Registration registration4 = new Registration();
                registration1 = registrationService.createRegistration(customer1.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration2 = registrationService.createRegistration(customer1.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());
                registration3 = registrationService.createRegistration(customer2.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration4 = registrationService.createRegistration(customer2.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());

                List<Registration> registrations = registrationService.getAllRegistrations();
                assertTrue(registrations.size() == 4);
                assertEquals(registrations.get(0), registrationService.getRegistrationByCustomerAndScheduledActivity(
                                customer1.getAccountRoleId(), scheduledActivity1.getScheduledActivityId()));
                assertEquals(registrations.get(1), registrationService.getRegistrationByCustomerAndScheduledActivity(
                                customer1.getAccountRoleId(), scheduledActivity2.getScheduledActivityId()));
                assertEquals(registrations.get(2), registrationService.getRegistrationByCustomerAndScheduledActivity(
                                customer2.getAccountRoleId(), scheduledActivity1.getScheduledActivityId()));
                assertEquals(registrations.get(3), registrationService.getRegistrationByCustomerAndScheduledActivity(
                                customer2.getAccountRoleId(), scheduledActivity2.getScheduledActivityId()));
        }

        // tests getRegistrationByCostumerId
        @Test
        public void testGetRegistrationByCostumerId() {
                String username1 = "username1";
                String username2 = "username2";
                String password = "password";
                Account account1 = new Account();
                Account account2 = new Account();
                account1 = accountService.createAccount(username1, password);
                account2 = accountService.createAccount(username1, password);
                Customer customer1 = new Customer();
                Customer customer2 = new Customer();
                customer1 = accountService.createCustomer(username1);
                customer2 = accountService.createCustomer(username2);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity1 = new Activity();
                Activity activity2 = new Activity();
                activity1 = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                activity2 = activityService.createActivity("activityName2", "activityDescription2",
                                ClassCategory.Cardio);
                accountService.approveActivity(activity1.getName());
                accountService.approveActivity(activity2.getName());
                ScheduledActivity scheduledActivity1 = new ScheduledActivity();
                ScheduledActivity scheduledActivity2 = new ScheduledActivity();
                scheduledActivity1 = scheduledActivityService.createScheduledActivity(LocalDate.of(2024, 12, 12),
                                LocalTime.of(10, 0),
                                LocalTime.of(12, 0), instructor, activity1, 30);
                scheduledActivity2 = scheduledActivityService.createScheduledActivity(LocalDate.of(2024, 11, 11),
                                LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity2, 30);

                Registration registration1 = new Registration();
                Registration registration2 = new Registration();
                Registration registration3 = new Registration();
                Registration registration4 = new Registration();
                registration1 = registrationService.createRegistration(customer1.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration2 = registrationService.createRegistration(customer1.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());
                registration3 = registrationService.createRegistration(customer2.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration4 = registrationService.createRegistration(customer2.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());

                List<Registration> registrations = registrationService
                                .getRegistrationByCostumerId(customer1.getAccountRoleId());
                assertTrue(registrations.size() == 2);
                assertEquals(registrations.get(0), registrationService.getRegistrationByCustomerAndScheduledActivity(
                                customer1.getAccountRoleId(), scheduledActivity1.getScheduledActivityId()));
                assertEquals(registrations.get(1), registrationService.getRegistrationByCustomerAndScheduledActivity(
                                customer1.getAccountRoleId(), scheduledActivity2.getScheduledActivityId()));
        }

        // tests getRegistrationByCostumerId null
        @Test
        public void testGetRegistrationByCostumerIdNull() {
                try {
                        registrationService.getRegistrationByCostumerId(-1);
                } catch (IllegalArgumentException e) {
                        assertEquals("Id not valid!", e.getMessage());
                }
        }

        // tests getRegistrationByScheduledActivityId
        @Test
        public void testGetRegistrationByScheduledActivityId() {
                String username1 = "username1";
                String username2 = "username2";
                String password = "password";
                Account account1 = new Account();
                Account account2 = new Account();
                account1 = accountService.createAccount(username1, password);
                account2 = accountService.createAccount(username1, password);
                Customer customer1 = new Customer();
                Customer customer2 = new Customer();
                customer1 = accountService.createCustomer(username1);
                customer2 = accountService.createCustomer(username2);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity1 = new Activity();
                Activity activity2 = new Activity();
                activity1 = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                activity2 = activityService.createActivity("activityName2", "activityDescription2",
                                ClassCategory.Cardio);
                accountService.approveActivity(activity1.getName());
                accountService.approveActivity(activity2.getName());
                ScheduledActivity scheduledActivity1 = new ScheduledActivity();
                ScheduledActivity scheduledActivity2 = new ScheduledActivity();
                scheduledActivity1 = scheduledActivityService.createScheduledActivity(LocalDate.of(2024, 12, 12),
                                LocalTime.of(10, 0),
                                LocalTime.of(12, 0), instructor, activity1, 30);
                scheduledActivity2 = scheduledActivityService.createScheduledActivity(LocalDate.of(2024, 11, 11),
                                LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity2, 30);

                Registration registration1 = new Registration();
                Registration registration2 = new Registration();
                Registration registration3 = new Registration();
                Registration registration4 = new Registration();
                registration1 = registrationService.createRegistration(customer1.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration2 = registrationService.createRegistration(customer1.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());
                registration3 = registrationService.createRegistration(customer2.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration4 = registrationService.createRegistration(customer2.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());

                List<Registration> registrations = registrationService
                                .getRegistrationByScheduledActivityId(scheduledActivity1.getScheduledActivityId());
                assertTrue(registrations.size() == 2);
                assertEquals(registrations.get(0), registrationService.getRegistrationByCustomerAndScheduledActivity(
                                customer1.getAccountRoleId(), scheduledActivity1.getScheduledActivityId()));
                assertEquals(registrations.get(1), registrationService.getRegistrationByCustomerAndScheduledActivity(
                                customer2.getAccountRoleId(), scheduledActivity1.getScheduledActivityId()));
        }

        // tests getRegistrationByScheduledActivityId null
        @Test
        public void testGetRegistrationByScheduledActivityIdNull() {
                try {
                        registrationService.getRegistrationByScheduledActivityId(-1);
                } catch (IllegalArgumentException e) {
                        assertEquals("Id not valid!", e.getMessage());
                }
        }

        // tests getRegistrationByCustomerAndScheduledActivity
        @Test
        public void testGetRegistrationByCustomerAndScheduledActivity() {
                String username1 = "username1";
                String username2 = "username2";
                String password = "password";
                Account account1 = new Account();
                Account account2 = new Account();
                account1 = accountService.createAccount(username1, password);
                account2 = accountService.createAccount(username1, password);
                Customer customer1 = new Customer();
                Customer customer2 = new Customer();
                customer1 = accountService.createCustomer(username1);
                customer2 = accountService.createCustomer(username2);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity1 = new Activity();
                Activity activity2 = new Activity();
                activity1 = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                activity2 = activityService.createActivity("activityName2", "activityDescription2",
                                ClassCategory.Cardio);
                accountService.approveActivity(activity1.getName());
                accountService.approveActivity(activity2.getName());
                ScheduledActivity scheduledActivity1 = new ScheduledActivity();
                ScheduledActivity scheduledActivity2 = new ScheduledActivity();
                scheduledActivity1 = scheduledActivityService.createScheduledActivity(LocalDate.of(2024, 12, 12),
                                LocalTime.of(10, 0),
                                LocalTime.of(12, 0), instructor, activity1, 30);
                scheduledActivity2 = scheduledActivityService.createScheduledActivity(LocalDate.of(2024, 11, 11),
                                LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity2, 30);

                Registration registration1 = new Registration();
                Registration registration2 = new Registration();
                Registration registration3 = new Registration();
                Registration registration4 = new Registration();
                registration1 = registrationService.createRegistration(customer1.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration2 = registrationService.createRegistration(customer1.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());
                registration3 = registrationService.createRegistration(customer2.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration4 = registrationService.createRegistration(customer2.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());

                assertEquals(registrationService.getRegistrationByCustomerAndScheduledActivity(
                                customer1.getAccountRoleId(),
                                scheduledActivity1.getScheduledActivityId()), registration1);
                assertEquals(registrationService.getRegistrationByCustomerAndScheduledActivity(
                                customer1.getAccountRoleId(),
                                scheduledActivity2.getScheduledActivityId()), registration2);
                assertEquals(registrationService.getRegistrationByCustomerAndScheduledActivity(
                                customer2.getAccountRoleId(),
                                scheduledActivity1.getScheduledActivityId()), registration3);
                assertEquals(registrationService.getRegistrationByCustomerAndScheduledActivity(
                                customer2.getAccountRoleId(),
                                scheduledActivity2.getScheduledActivityId()), registration4);

        }

        // tests getRegistrationByCustomerAndScheduledActivity null
        @Test
        public void testGetRegistrationByCustomerAndScheduledActivityNull() {
                try {
                        registrationService.getRegistrationByCustomerAndScheduledActivity(-1, -1);
                } catch (IllegalArgumentException e) {
                        assertEquals("Id not valid!", e.getMessage());
                }
        }

}
