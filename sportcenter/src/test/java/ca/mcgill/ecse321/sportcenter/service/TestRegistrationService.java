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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.sportcenter.dao.*;
import ca.mcgill.ecse321.sportcenter.model.*;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

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
        private AccountService accountService;

        @InjectMocks
        private CustomerService customerService;

        @InjectMocks
        private ScheduledActivityService scheduledActivityService;

        @InjectMocks
        private ActivityService activityService;

        @InjectMocks
        private OwnerService ownerService;

        private <T> List<T> toList(Iterable<T> iterable) {
                List<T> resultList = new ArrayList<T>();
                for (T t : iterable) {
                        resultList.add(t);
                }
                return resultList;
        }

        @SuppressAjWarnings("null")

        /*
         * tests the creation of a registration
         * 
         */
        @Test
        public void testCreateRegistration() {
                String username = "username";
                String password = "password";
                Account account = new Account();
                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                account = accountService.createAccount(username, password);
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Customer customer = new Customer();
                customer = customerService.createCustomer(username);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());
                Activity activity = new Activity();
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                Owner owner = new Owner();
                ownerService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);
                Registration registration = new Registration();
                registration = registrationService.register(customer.getAccount().getAccountId(),
                                scheduledActivity.getScheduledActivityId());

                assertEquals(registration.getCustomer().getAccount().getAccountId(),
                                customer.getAccount().getAccountId());
                assertEquals(registration.getScheduledActivity().getScheduledActivityId(),
                                scheduledActivity.getScheduledActivityId());
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
                customer = customerService.createCustomer(username);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity = new Activity();
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                Owner owner = new Owner();
                ownerService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);
                Registration registration = new Registration();
                registration = registrationService.register(customer.getAccount().getAccountId(),
                                scheduledActivity.getScheduledActivityId());

                try {
                        registration = registrationService.register(customer.getAccount().getAccountId(),
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
                ownerService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);

                try {
                        Registration registration = new Registration();
                        registration = registrationService.register(owner.getAccount().getAccountId(),
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
                Instructor instructor = ownerService.approveInstructor(account.getAccountId());
                Activity activity = new Activity();
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                ownerService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);

                try {
                        Registration registration = new Registration();
                        registration = registrationService.register(instructor.getAccount().getAccountId(),
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
                customer = customerService.createCustomer(username);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity = new Activity();
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                ownerService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.of(2020, 1, 1),
                                LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);

                try {
                        Registration registration = new Registration();
                        registration = registrationService.register(customer.getAccount().getAccountId(),
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
                customer1 = customerService.createCustomer(username1);
                Customer customer2 = new Customer();
                customer2 = customerService.createCustomer(username2);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity = new Activity();
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                ownerService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 1);
                Registration registration = new Registration();
                registration = registrationService.register(customer1.getAccount().getAccountId(),
                                scheduledActivity.getScheduledActivityId());
                try {
                        registration = registrationService.register(customer2.getAccount().getAccountId(),
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
                ownerService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);

                try {
                        Registration registration = new Registration();
                        registration = registrationService.register(customer,
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
                customer = customerService.createCustomer(username);
                int scheduledActivity = -1;
                try {
                        registrationService.register(customer.getAccountRoleId(), scheduledActivity);
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
                customer = customerService.createCustomer(username);
                Activity activity1 = new Activity();
                activity1 = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                ownerService.approveActivity(activity1.getName());
                ScheduledActivity scheduledActivity1 = new ScheduledActivity();

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                scheduledActivity1 = scheduledActivityService.createScheduledActivity(LocalDate.of(2024, 12, 12),
                                LocalTime.of(10, 0),
                                LocalTime.of(12, 0), instructor, activity1, 30);

                Registration registration = new Registration();

                registration = registrationService.register(customer.getAccount().getAccountId(),
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
                customer1 = customerService.createCustomer(username1);
                customer2 = customerService.createCustomer(username2);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity1 = new Activity();
                Activity activity2 = new Activity();
                activity1 = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                activity2 = activityService.createActivity("activityName2", "activityDescription2",
                                ClassCategory.Cardio);
                ownerService.approveActivity(activity1.getName());
                ownerService.approveActivity(activity2.getName());
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
                registration1 = registrationService.register(customer1.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration2 = registrationService.register(customer1.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());
                registration3 = registrationService.register(customer2.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration4 = registrationService.register(customer2.getAccount().getAccountId(),
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
                customer = customerService.createCustomer(username);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity = new Activity();
                activity = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                ownerService.approveActivity(activity.getName());
                ScheduledActivity scheduledActivity = new ScheduledActivity();
                scheduledActivity = scheduledActivityService.createScheduledActivity(LocalDate.now(), LocalTime.now(),
                                LocalTime.of(12, 0), instructor, activity, 30);
                Registration registration = new Registration();
                registration = registrationService.register(customer.getAccount().getAccountId(),
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
                customer1 = customerService.createCustomer(username1);
                customer2 = customerService.createCustomer(username2);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity1 = new Activity();
                Activity activity2 = new Activity();
                activity1 = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                activity2 = activityService.createActivity("activityName2", "activityDescription2",
                                ClassCategory.Cardio);
                ownerService.approveActivity(activity1.getName());
                ownerService.approveActivity(activity2.getName());
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
                registration1 = registrationService.register(customer1.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration2 = registrationService.register(customer1.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());
                registration3 = registrationService.register(customer2.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration4 = registrationService.register(customer2.getAccount().getAccountId(),
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
                customer1 = customerService.createCustomer(username1);
                customer2 = customerService.createCustomer(username2);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity1 = new Activity();
                Activity activity2 = new Activity();
                activity1 = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                activity2 = activityService.createActivity("activityName2", "activityDescription2",
                                ClassCategory.Cardio);
                ownerService.approveActivity(activity1.getName());
                ownerService.approveActivity(activity2.getName());
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
                registration1 = registrationService.register(customer1.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration2 = registrationService.register(customer1.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());
                registration3 = registrationService.register(customer2.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration4 = registrationService.register(customer2.getAccount().getAccountId(),
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
                customer1 = customerService.createCustomer(username1);
                customer2 = customerService.createCustomer(username2);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity1 = new Activity();
                Activity activity2 = new Activity();
                activity1 = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                activity2 = activityService.createActivity("activityName2", "activityDescription2",
                                ClassCategory.Cardio);
                ownerService.approveActivity(activity1.getName());
                ownerService.approveActivity(activity2.getName());
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
                registration1 = registrationService.register(customer1.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration2 = registrationService.register(customer1.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());
                registration3 = registrationService.register(customer2.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration4 = registrationService.register(customer2.getAccount().getAccountId(),
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
                customer1 = customerService.createCustomer(username1);
                customer2 = customerService.createCustomer(username2);

                String instructorUsername = "instructor";
                Account instructorAccount = new Account();
                instructorAccount = accountService.createAccount(instructorUsername, password);
                Instructor instructor = new ownerService.approveInstructor(instructorAccount.getAccountId());

                Activity activity1 = new Activity();
                Activity activity2 = new Activity();
                activity1 = activityService.createActivity("activityName", "activityDescription", ClassCategory.Cardio);
                activity2 = activityService.createActivity("activityName2", "activityDescription2",
                                ClassCategory.Cardio);
                ownerService.approveActivity(activity1.getName());
                ownerService.approveActivity(activity2.getName());
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
                registration1 = registrationService.register(customer1.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration2 = registrationService.register(customer1.getAccount().getAccountId(),
                                scheduledActivity2.getScheduledActivityId());
                registration3 = registrationService.register(customer2.getAccount().getAccountId(),
                                scheduledActivity1.getScheduledActivityId());
                registration4 = registrationService.register(customer2.getAccount().getAccountId(),
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
