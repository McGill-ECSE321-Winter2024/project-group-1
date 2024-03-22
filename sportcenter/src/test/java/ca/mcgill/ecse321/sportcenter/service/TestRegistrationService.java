package ca.mcgill.ecse321.sportcenter.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

//Registration service unit tests
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
        private Registration registration;

        @Mock
        private Account account;

        @Mock
        private Customer customer;

        @Mock
        private ScheduledActivity scheduledActivity;

        @Mock
        private Owner owner;

        @Mock
        private OwnerRepository ownerDao;

        @InjectMocks
        private RegistrationService registrationService;

        @InjectMocks
        private AccountManagementService accountService;

        @InjectMocks
        private ScheduledActivityService scheduledActivityService;

        @InjectMocks
        private ActivityManagementService activityService;

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
         * Tests the creation of a registration
         * 
         * @author Emilie Ruel
         */
        @Test
        public void testCreateRegistration() {
                /*
                 * String username = "username";
                 * String password = "password";
                 * Account account = new Account(username, password);
                 * String instructorUsername = "instructor";
                 * Account instructorAccount = new Account();
                 * account = accountService.createAccount(username, password);
                 * instructorAccount = accountService.createAccount(instructorUsername,
                 * password);
                 * Customer customer = new Customer();
                 * customer = accountService.createCustomer(username);
                 * Instructor instructor = new
                 * ownerService.approveInstructor(instructorAccount.getAccountId());
                 * Activity activity = new Activity();
                 * activity = activityService.createActivity("activityName",
                 * "activityDescription", ClassCategory.Cardio);
                 * Owner owner = new Owner();
                 * accountService.approveActivity(activity.getName());
                 * ScheduledActivity scheduledActivity = new ScheduledActivity();
                 * scheduledActivity =
                 * scheduledActivityService.createScheduledActivity(LocalDate.now(),
                 * LocalTime.now(),
                 * LocalTime.of(12, 0), instructor, activity, 30);
                 * Registration registration = new Registration();
                 * registration =
                 * registrationService.createRegistration(customer.getAccount().getAccountId(),
                 * scheduledActivity.getScheduledActivityId());
                 */

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

                when(accountDao.findAccountByUsername(username)).thenReturn(account);
                when(accountDao.findAccountByUsername(instructorUsername)).thenReturn(instructorAccount);
                when(customerDao.findAccountRoleByAccountRoleId(account.getAccountId())).thenReturn(customer);
                when(instructorDao.findAccountRoleByAccountRoleId(instructorAccount.getAccountId()))
                                .thenReturn(instructor);
                when(scheduledActivityDao
                                .findScheduledActivityByScheduledActivityId(scheduledActivity.getScheduledActivityId()))
                                .thenReturn(scheduledActivity);
                when(registrationDao.save(any(Registration.class))).thenAnswer((InvocationOnMock invocation) -> {
                        registration.setRegistrationId(1);
                        return registration;
                });

                Registration createdRegistration = null;
                try {
                        createdRegistration = registrationService.createRegistration(account.getAccountId(),
                                        scheduledActivity.getScheduledActivityId());
                } catch (IllegalArgumentException e) {
                        fail();
                }

                assertNull(createdRegistration);
                assertEquals(customer, createdRegistration.getCustomer());
                assertEquals(scheduledActivity, createdRegistration.getScheduledActivity());
                assertEquals(1, createdRegistration.getRegistrationId());
        }

        /*
         * tests the creation of a registration with one already existing
         * 
         */
        @Test
        public void testCreateRegistrationDuplicate() {
                String username = "username";
                String password = "password";
                Account account = new Account();
                account = accountService.createAccount(username, password);
                Customer customer = new Customer();
                customer = accountService.createCustomer(username);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity = new Activity();
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                Owner owner = new Owner();
                accountService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);
                Registration registration = new Registration();
                registration = registrationService.createRegistration(customer.getAccount().getAccountId(),
                                scheduledActivity.getScheduledActivityId());

                try {
                        registration = registrationService.createRegistration(customer.getAccount().getAccountId(),
                                        scheduledActivity.getScheduledActivityId());
                } catch (IllegalArgumentException e) {
                        assertEquals("Registration already exists", e.getMessage());
                }
        }

        // owner tries to register
        @Test
        public void testCreateRegistrationOwner() {
                Owner owner = new Owner();
                String password = "password";
                Activity activity = new Activity();
                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                accountService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);

                try {
                        Registration registration = new Registration();
                        registration = registrationService.createRegistration(owner.getAccount().getAccountId(),
                                        scheduledActivity.getScheduledActivityId());
                } catch (IllegalArgumentException e) {
                        assertEquals("Id is not a customer account role", e.getMessage());
                }
        }

        // intructor tries to register
        @Test
        public void testCreateRegistrationInstructor() {
                Account account = new Account();
                account = accountService.createAccount("username", "password");
                Owner owner = new Owner();
                Instructor instructor = accountService.approveInstructor(account.getAccountId());
                Activity activity = new Activity();
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                accountService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);

                try {
                        Registration registration = new Registration();
                        registration = registrationService.createRegistration(instructor.getAccount().getAccountId(),
                                        scheduledActivity.getScheduledActivityId());
                } catch (IllegalArgumentException e) {
                        assertEquals("Id is not a customer account role", e.getMessage());
                }
        }

        // date passed
        @Test
        public void testCreateRegistrationDatePassed() {
                String username = "username";
                String password = "password";
                Account account = new Account();
                account = accountService.createAccount(username, password);
                Customer customer = new Customer();
                customer = accountService.createCustomer(username);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity = new Activity();
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                accountService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.of(2020, 1, 1),
                                LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);

                try {
                        Registration registration = new Registration();
                        registration = registrationService.createRegistration(customer.getAccount().getAccountId(),
                                        scheduledActivity.getScheduledActivityId());
                } catch (IllegalArgumentException e) {
                        assertEquals("Scheduled activity is in the past", e.getMessage());
                }
        }

        // class full
        @Test
        public void testCreateRegistrationFull() {
                String username1 = "username1";
                String username2 = "username2";
                String password = "password";
                Account account1 = new Account();
                account1 = accountService.createAccount(username1, password);
                Account account2 = new Account();
                account2 = accountService.createAccount(username2, password);
                Customer customer1 = new Customer();
                customer1 = accountService.createCustomer(username1);
                Customer customer2 = new Customer();
                customer2 = accountService.createCustomer(username2);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity = new Activity();
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                accountService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 1);
                Registration registration = new Registration();
                registration = registrationService.createRegistration(customer1.getAccount().getAccountId(),
                                scheduledActivity.getScheduledActivityId());
                try {
                        registration = registrationService.createRegistration(customer2.getAccount().getAccountId(),
                                        scheduledActivity.getScheduledActivityId());
                } catch (IllegalArgumentException e) {
                        assertEquals("Scheduled activity is full", e.getMessage());
                }
        }

        // customer null
        @Test
        public void testCreateRegistrationInvalidId() {
                int customer = -1;

                String password = "password";
                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity = new Activity();
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                accountService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);

                try {
                        Registration registration = new Registration();
                        registration = registrationService.createRegistration(customer,
                                        scheduledActivity.getScheduledActivityId());

                } catch (IllegalArgumentException e) {
                        assertEquals("Id is not a customer account role", e.getMessage());
                }
        }

        // scheduled activity null
        @Test
        public void testCreateRegistrationInvalidScheduledActivity() {
                String username = "username";
                String password = "password";
                Account account = new Account();
                account = accountService.createAccount(username, password);
                Customer customer = new Customer();
                customer = accountService.createCustomer(username);
                int scheduledActivity = -1;
                try {
                        Registration registration = new Registration();
                        registration = registrationService.createRegistration(customer.getAccountRoleId(),
                                        scheduledActivity);
                } catch (IllegalArgumentException e) {
                        assertEquals("Scheduled activity does not exist", e.getMessage());
                }
        }

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

        // tests getRegistrationById
        @Test
        public void testGetRegistrationById() {
                String username = "username";
                String password = "password";
                Account account = new Account();
                account = accountService.createAccount(username, password);
                Customer customer = new Customer();
                customer = accountService.createCustomer(username);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity = new Activity();
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                accountService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);
                Registration registration = new Registration();
                registration = registrationService.createRegistration(customer.getAccount().getAccountId(),
                                scheduledActivity.getScheduledActivityId());

                assertEquals(registrationService.getRegistrationById(registration.getRegistrationId()), registration);
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
