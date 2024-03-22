package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

/**
 * Test class for the Registration entity
 * 
 * @author Patrick Zakaria
 */
@ExtendWith(MockitoExtension.class)
public class TestRegistrationService {

        @Mock
        private AccountRepository accountDao;
        @Mock
        private CustomerRepository customerDao;
        @Mock
        private InstructorRepository instructorDao;
        @Mock
        private ActivityRepository activityDao;
        @Mock
        private ScheduledActivityRepository scheduledActivityDao;
        @Mock
        private RegistrationRepository registrationDao;

        // Account keys
        private static final int CUSTOMER_ACCOUNT_KEY = 1;
        private static final int APPROVED_INSTRUCTOR_ACCOUNT_KEY = 2;
        private static final int DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY = 3;

        private static final int NEW_CUSTOMER_ACCOUNT_KEY = 4;

        // Account Role keys
        // Customer keys
        private static final int CUSTOMER_KEY = 1;

        private static final int NEW_CUSTOMER_KEY = 4;

        // Instructor keys
        private static final int APPROVED_INSTRUCTOR_KEY = 2;
        private static final int DISAPPROVED_INSTRUCTOR_KEY = 3;

        // Activity keys
        private static final String APPROVED_ACTIVITY_KEY = "ApprovedActivity";
        private static final String DISAPPROVED_ACTIVITY_KEY = "DisapprovedActivity";

        // Scheduled Activity keys
        private static final int SCHEDULED_ACTIVITY_KEY = 1;

        // Registration keys
        private static final int REGISTRATION_KEY = 1;

        @InjectMocks
        private RegistrationManagementService registrationService;

        @SuppressWarnings("null")
        @BeforeEach
        void setMockOutput() {
                when(accountDao.findAccountByAccountId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(CUSTOMER_ACCOUNT_KEY)) {
                                Account account = new Account();
                                account.setUsername("customer");
                                account.setPassword("password");
                                return account;
                        } else if (invocation.getArgument(0).equals(APPROVED_INSTRUCTOR_ACCOUNT_KEY)) {
                                Account account = new Account();
                                account.setUsername("approvedInstructor");
                                account.setPassword("password");
                                return account;
                        } else if (invocation.getArgument(0).equals(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY)) {
                                Account account = new Account();
                                account.setUsername("disapprovedInstructor");
                                account.setPassword("password");
                                return account;
                        } else {
                                return null;
                        }
                });

                when(customerDao.findCustomerByAccountRoleId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(CUSTOMER_KEY)) {
                                Customer customer = new Customer();
                                customer.setAccount(accountDao.findAccountByAccountId(CUSTOMER_ACCOUNT_KEY));
                                return customer;
                        } else if (invocation.getArgument(0).equals(NEW_CUSTOMER_KEY)) {
                                Customer customer = new Customer();
                                customer.setAccount(accountDao.findAccountByAccountId(NEW_CUSTOMER_ACCOUNT_KEY));
                                return customer;
                        } else {
                                return null;
                        }
                });

                when(instructorDao.findAccountRoleByAccountRoleId(anyInt()))
                                .thenAnswer((InvocationOnMock invocation) -> {
                                        if (invocation.getArgument(0).equals(APPROVED_INSTRUCTOR_KEY)) {
                                                Instructor instructor = new Instructor();
                                                instructor.setAccount(accountDao.findAccountByAccountId(
                                                                APPROVED_INSTRUCTOR_ACCOUNT_KEY));
                                                instructor.setStatus(InstructorStatus.Active);
                                                instructor.setDescription("description");
                                                instructor.setProfilePicURL("pictureURL");
                                                return instructor;
                                        } else if (invocation.getArgument(0).equals(DISAPPROVED_INSTRUCTOR_KEY)) {
                                                Instructor instructor = new Instructor();
                                                instructor.setAccount(accountDao.findAccountByAccountId(
                                                                DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY));
                                                instructor.setStatus(InstructorStatus.Pending);
                                                instructor.setDescription("description");
                                                instructor.setProfilePicURL("pictureURL");
                                                return instructor;
                                        } else {
                                                return null;
                                        }
                                });

                when(activityDao.findActivityByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(APPROVED_ACTIVITY_KEY)) {
                                Activity activity = new Activity();
                                activity.setName("activity");
                                activity.setDescription("description");
                                activity.setSubCategory(ClassCategory.Cardio);
                                activity.setIsApproved(true);
                                return activity;
                        } else if (invocation.getArgument(0).equals(DISAPPROVED_ACTIVITY_KEY)) {
                                Activity activity = new Activity();
                                activity.setName("activity");
                                activity.setDescription("description");
                                activity.setSubCategory(ClassCategory.Cardio);
                                activity.setIsApproved(false);
                                return activity;
                        } else {
                                return null;
                        }
                });

                when(scheduledActivityDao.findScheduledActivityByScheduledActivityId(anyInt()))
                                .thenAnswer((InvocationOnMock invocation) -> {
                                        if (invocation.getArgument(0).equals(SCHEDULED_ACTIVITY_KEY)) {
                                                ScheduledActivity scheduledActivity = new ScheduledActivity();
                                                scheduledActivity.setDate(LocalDate.now());
                                                scheduledActivity.setStartTime(LocalTime.now());
                                                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                                                scheduledActivity.setSupervisor(
                                                                instructorDao.findAccountRoleByAccountRoleId(
                                                                                DISAPPROVED_INSTRUCTOR_KEY));
                                                scheduledActivity.setActivity(
                                                                activityDao.findActivityByName(
                                                                                DISAPPROVED_ACTIVITY_KEY));
                                                scheduledActivity.setCapacity(30);
                                                return scheduledActivity;
                                        } else {
                                                return null;
                                        }
                                });

                when(registrationDao.findRegistrationByRegId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
                        if (invocation.getArgument(0).equals(REGISTRATION_KEY)) {
                                Registration registration = new Registration();
                                registration.setCustomer(customerDao.findCustomerByAccountRoleId(CUSTOMER_KEY));
                                registration.setScheduledActivity(
                                                scheduledActivityDao.findScheduledActivityByScheduledActivityId(
                                                                SCHEDULED_ACTIVITY_KEY));
                                return registration;
                        } else {
                                return null;
                        }
                });

                Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
                        return invocation.getArgument(0);
                };
                when(accountDao.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
                when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
                when(instructorDao.save(any(Instructor.class))).thenAnswer(returnParameterAsAnswer);
                when(activityDao.save(any(Activity.class))).thenAnswer(returnParameterAsAnswer);
                when(scheduledActivityDao.save(any(ScheduledActivity.class))).thenAnswer(returnParameterAsAnswer);
                when(registrationDao.save(any(Registration.class))).thenAnswer(returnParameterAsAnswer);
        }

        @SuppressAjWarnings("null")
        /**
         * Tests the creation of a registration -> Sucess
         */
        @Test
        public void testCreateRegistration() {
                Registration registration = null;
                try {
                        registration = registrationService.createRegistration(NEW_CUSTOMER_ACCOUNT_KEY,
                                        SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        fail();
                }
                assertNotNull(registration);
                assertEquals(NEW_CUSTOMER_ACCOUNT_KEY, registration.getCustomer().getAccountRoleId());
                assertEquals(SCHEDULED_ACTIVITY_KEY, registration.getScheduledActivity().getScheduledActivityId());
        }

        /**
         * Tests the creation of a registration with an invalid customer account role id
         * -> Fail
         */
        @Test
        public void testCreateRegistrationInvalidCustomer() {
                String error = null;
                try {
                        registrationService.createRegistration(-1, SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Account role id not valid!", error);
        }

        /**
         * Tests the creation of a registration with an invalid scheduled activity id ->
         * Fail
         */
        @Test
        public void testCreateRegistrationInvalidScheduledActivity() {
                String error = null;
                try {
                        registrationService.createRegistration(CUSTOMER_KEY, -1);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Scheduled activity id not valid!", error);
        }

        /**
         * Tests the creation of a registration with a customer that doesn't exist ->
         * Fail
         */
        @Test
        public void testCreateRegistrationCustomerDoesNotExist() {
                String error = null;
                try {
                        registrationService.createRegistration(0, SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Customer does not exist", error);
        }

        /**
         * Tests the creation of a registration with a scheduled activity that doesn't
         * exist -> Fail
         */
        @Test
        public void testCreateRegistrationScheduledActivityDoesNotExist() {
                String error = null;
                try {
                        registrationService.createRegistration(CUSTOMER_KEY, 0);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Scheduled activity does not exist", error);
        }

        /**
         * Tests the creation of a duplicate registration -> Fail
         */
        @Test
        public void testCreateRegistrationDuplicate() {
                String error = null;
                try {
                        registrationService.createRegistration(CUSTOMER_KEY, SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Registration already exists", error);
        }

        /**
         * Tests the creation of a registration with a scheduled activity in the past ->
         * Fail
         */
        @Test
        public void testCreateRegistrationScheduledActivityInPast() {
                String error = null;
                try {
                        ScheduledActivity scheduledActivity = scheduledActivityDao
                                        .findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY);
                        scheduledActivity.setDate(LocalDate.of(2020, 1, 1));
                        registrationService.createRegistration(CUSTOMER_KEY, SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Scheduled activity is in the past", error);
        }

        /**
         * Tests the creation of a registration with a full scheduled activity -> Fail
         */
        @Test
        public void testCreateRegistrationScheduledActivityFull() {
                String error = null;
                try {
                        ScheduledActivity scheduledActivity = scheduledActivityDao
                                        .findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY);
                        scheduledActivity.setCapacity(0);
                        registrationService.createRegistration(CUSTOMER_KEY, SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Scheduled activity is full", error);
        }

        /**
         * Tests the getting of a registration by its id -> Success
         */
        @Test
        public void testGetRegistrationById() {
                Registration registration = null;
                try {
                        registration = registrationService.getRegistrationByRegId(REGISTRATION_KEY);
                } catch (IllegalArgumentException e) {
                        fail();
                }
                assertNotNull(registration);
                assertEquals(CUSTOMER_KEY, registration.getCustomer().getAccountRoleId());
                assertEquals(SCHEDULED_ACTIVITY_KEY, registration.getScheduledActivity().getScheduledActivityId());
        }

        /**
         * Tests the getting of a registration by an invalid id -> Fail
         */
        @Test
        public void testGetRegistrationByInvalidId() {
                String error = null;
                Registration registration = null;
                try {
                        registration = registrationService.getRegistrationByRegId(0);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
                assertEquals("Registration Id not valid!", error);
        }

        /**
         * Tests the getting of a non existing registration -> Fail
         */
        @Test
        public void testGetNonExistingRegistration() {
                String error = null;
                Registration registration = null;
                try {
                        registration = registrationService.getRegistrationByRegId(2);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
                assertEquals("Registration does not exist", error);
        }

        /**
         * Tests the getting of all registrations -> Success
         */
        @Test
        public void testGetAllRegistrations() {
                List<Registration> registrations = registrationService.getAllRegistrations();
                assertNotNull(registrations);
                assertEquals(1, registrations.size());
        }

        /**
         * Tests the getting of all registrations of a customer -> Success
         */
        @Test
        public void testGetRegistrationsByCustomer() {
                List<Registration> registrations = registrationService.getRegistrationByAccountRoleId(CUSTOMER_KEY);
                assertNotNull(registrations);
                assertEquals(1, registrations.size());
        }

        /**
         * Tests the getting of all registrations of a customer with an invalid id ->
         * Fail
         */
        @Test
        public void testGetRegistrationsByInvalidCustomer() {
                String error = null;
                List<Registration> registrations = null;
                try {
                        registrations = registrationService.getRegistrationByAccountRoleId(0);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registrations);
                assertEquals("Account role id not valid!", error);
        }

        /**
         * Tests the getting of all registrations of a non existing customer -> Fail
         */
        @Test
        public void testGetRegistrationsByNonExistingCustomer() {
                String error = null;
                List<Registration> registrations = null;
                try {
                        registrations = registrationService.getRegistrationByAccountRoleId(2);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registrations);
                assertEquals("Customer does not exist", error);
        }

        /**
         * Tests the getting of all registrations of a scheduled activity -> Success
         */
        @Test
        public void testGetRegistrationsByScheduledActivity() {
                List<Registration> registrations = registrationService
                                .getRegistrationByScheduledActivityId(SCHEDULED_ACTIVITY_KEY);
                assertNotNull(registrations);
                assertEquals(1, registrations.size());
        }

        /**
         * Tests the getting of all registrations of a scheduled activity with an
         * invalid
         * id -> Fail
         */
        @Test
        public void testGetRegistrationsByInvalidScheduledActivity() {
                String error = null;
                List<Registration> registrations = null;
                try {
                        registrations = registrationService.getRegistrationByScheduledActivityId(0);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registrations);
                assertEquals("Scheduled activity Id not valid!", error);
        }

        /**
         * Tests the getting of all registrations of a non existing scheduled activity
         * ->
         * Fail
         */
        @Test
        public void testGetRegistrationsByNonExistingScheduledActivity() {
                String error = null;
                List<Registration> registrations = null;
                try {
                        registrations = registrationService.getRegistrationByScheduledActivityId(2);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registrations);
                assertEquals("Scheduled activity does not exist", error);
        }

        /**
         * Tests the getting of a registration by its customer and scheduled activity
         * -> Success
         */
        @Test
        public void testGetRegistrationByCustomerAndScheduledActivity() {
                Registration registration = null;
                try {
                        registration = registrationService.getRegistrationByAccountRoleIdAndScheduledActivityId(
                                        CUSTOMER_KEY,
                                        SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        fail();
                }
                assertNotNull(registration);
                assertEquals(CUSTOMER_KEY, registration.getCustomer().getAccountRoleId());
                assertEquals(SCHEDULED_ACTIVITY_KEY, registration.getScheduledActivity().getScheduledActivityId());
        }

        /**
         * Tests the getting of a registration by an invalid customer id -> Fail
         */
        @Test
        public void testGetRegistrationByInvalidCustomer() {
                String error = null;
                Registration registration = null;
                try {
                        registration = registrationService.getRegistrationByAccountRoleIdAndScheduledActivityId(0,
                                        SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
                assertEquals("Account role id not valid!", error);
        }

        /**
         * Tests the getting of a registration by an invalid scheduled activity id ->
         * Fail
         */
        @Test
        public void testGetRegistrationByInvalidScheduledActivity() {
                String error = null;
                Registration registration = null;
                try {
                        registration = registrationService.getRegistrationByAccountRoleIdAndScheduledActivityId(
                                        CUSTOMER_KEY,
                                        0);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
                assertEquals("Scheduled activity id not valid!", error);
        }

        /**
         * Tests the getting of a registration by a non existing customer -> Fail
         */
        @Test
        public void testGetRegistrationByNonExistingCustomer() {
                String error = null;
                Registration registration = null;
                try {
                        registration = registrationService.getRegistrationByAccountRoleIdAndScheduledActivityId(2,
                                        SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
                assertEquals("Customer does not exist", error);
        }

        /**
         * Tests the getting of a registration by a non existing scheduled activity ->
         * Fail
         */
        @Test
        public void testGetRegistrationByNonExistingScheduledActivity() {
                String error = null;
                Registration registration = null;
                try {
                        registration = registrationService.getRegistrationByAccountRoleIdAndScheduledActivityId(
                                        CUSTOMER_KEY,
                                        2);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
                assertEquals("Scheduled activity does not exist", error);
        }

        /**
         * Tests the getting of all costumers attending a scheduled activity -> Success
         */
        @Test
        public void testGetCustomersByScheduledActivity() {
                List<Customer> customers = registrationService
                                .getCustomersByScheduledActivityId(SCHEDULED_ACTIVITY_KEY);
                assertNotNull(customers);
                assertEquals(1, customers.size());
        }

        /**
         * Tests the getting of all costumers attending a scheduled activity with an
         * invalid id -> Fail
         */
        @Test
        public void testGetCustomersByInvalidScheduledActivity() {
                String error = null;
                List<Customer> customers = null;
                try {
                        customers = registrationService.getCustomersByScheduledActivityId(0);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(customers);
                assertEquals("Scheduled activity Id not valid!", error);
        }

        /**
         * Tests the getting of all costumers attending a non existing scheduled
         * activity
         * -> Fail
         */
        @Test
        public void testGetCustomersByNonExistingScheduledActivity() {
                String error = null;
                List<Customer> customers = null;
                try {
                        customers = registrationService.getCustomersByScheduledActivityId(2);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(customers);
                assertEquals("Scheduled activity does not exist", error);
        }

        /**
         * Tests the getting of all scheduled activities attended by a customer ->
         * Success
         */
        @Test
        public void testGetScheduledActivitiesByCustomer() {
                List<ScheduledActivity> scheduledActivities = registrationService
                                .getScheduledActivitiesByCustomerId(CUSTOMER_KEY);
                assertNotNull(scheduledActivities);
                assertEquals(1, scheduledActivities.size());
        }

        /**
         * Tests the getting of all scheduled activities attended by a customer with an
         * invalid id -> Fail
         */
        @Test
        public void testGetScheduledActivitiesByInvalidCustomer() {
                String error = null;
                List<ScheduledActivity> scheduledActivities = null;
                try {
                        scheduledActivities = registrationService.getScheduledActivitiesByCustomerId(0);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(scheduledActivities);
                assertEquals("Account role id not valid!", error);
        }

        /**
         * Tests the getting of all scheduled activities attended by a non existing
         * customer -> Fail
         */
        @Test
        public void testGetScheduledActivitiesByNonExistingCustomer() {
                String error = null;
                List<ScheduledActivity> scheduledActivities = null;
                try {
                        scheduledActivities = registrationService.getScheduledActivitiesByCustomerId(2);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(scheduledActivities);
                assertEquals("Customer does not exist", error);
        }

        /**
         * Tests the getting of all scheduled activities of an instructor -> Success
         */
        @Test
        public void testGetScheduledActivitiesByInstructor() {
                List<ScheduledActivity> scheduledActivities = registrationService
                                .getScheduledActivitiesByInstructorId(APPROVED_INSTRUCTOR_KEY);
                assertNotNull(scheduledActivities);
                assertEquals(1, scheduledActivities.size());
        }

        /**
         * Tests the getting of all scheduled activities of an instructor with an
         * invalid id -> Fail
         */
        @Test
        public void testGetScheduledActivitiesByInvalidInstructor() {
                String error = null;
                List<ScheduledActivity> scheduledActivities = null;
                try {
                        scheduledActivities = registrationService.getScheduledActivitiesByInstructorId(0);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(scheduledActivities);
                assertEquals("Account role id not valid!", error);
        }

        /**
         * Tests the getting of all scheduled activities of a non existing instructor ->
         * Fail
         */
        @Test
        public void testGetScheduledActivitiesByNonExistingInstructor() {
                String error = null;
                List<ScheduledActivity> scheduledActivities = null;
                try {
                        scheduledActivities = registrationService.getScheduledActivitiesByInstructorId(2);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(scheduledActivities);
                assertEquals("Instructor does not exist", error);
        }

        /**
         * Tests the deletion of a registration -> Success
         */
        @Test
        public void testDeleteRegistration() {
                List<Registration> registrations = toList(registrationDao.findAll());
                int size = registrations.size();
                try {
                        registrationService.deleteRegistration(REGISTRATION_KEY);
                } catch (IllegalArgumentException e) {
                        fail();
                }
                registrations = toList(registrationDao.findAll());
                assertEquals(size - 1, registrations.size());
        }

        /**
         * Tests the deletion of a registration with an invalid id -> Fail
         */
        @Test
        public void testDeleteRegistrationInvalidId() {
                String error = null;
                try {
                        registrationService.deleteRegistration(0);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Registration Id not valid!", error);
        }

        /**
         * Tests the deletion of a non existing registration -> Fail
         */
        @Test
        public void testDeleteNonExistingRegistration() {
                String error = null;
                try {
                        registrationService.deleteRegistration(2);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Registration does not exist", error);
        }

        /**
         * Tests the deletion of all registrations -> Success
         */
        @Test
        public void testDeleteAllRegistrations() {
                List<Registration> registrations = toList(registrationDao.findAll());
                registrationService.deleteAllRegistrations();
                registrations = toList(registrationDao.findAll());
                assertEquals(0, registrations.size());
        }

        /**
         * Tests the deletion of all registrations of a customer -> Success
         */
        @Test
        public void testDeleteRegistrationsByCustomer() {
                List<Registration> registrations = toList(registrationDao.findAll());
                int size = registrations.size();
                List<Registration> registrationsByCustomer = registrationService
                                .getRegistrationByAccountRoleId(CUSTOMER_KEY);
                int sizeByCustomer = registrationsByCustomer.size();
                registrationService.deleteRegistrationsByAccountRoleId(CUSTOMER_KEY);
                registrations = toList(registrationDao.findAll());
                assertEquals(size - sizeByCustomer, registrations.size());
        }

        /**
         * Tests the deletion of all registrations of a customer with an invalid id ->
         * Fail
         */
        @Test
        public void testDeleteRegistrationsByInvalidCustomer() {
                String error = null;
                try {
                        registrationService.deleteRegistrationsByAccountRoleId(0);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Account role id not valid!", error);
        }

        /**
         * Tests the deletion of all registrations of a non existing customer -> Fail
         */
        @Test
        public void testDeleteRegistrationsByNonExistingCustomer() {
                String error = null;
                try {
                        registrationService.deleteRegistrationsByAccountRoleId(2);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Customer does not exist", error);
        }

        /**
         * Tests the deletion of all registrations of a scheduled activity -> Success
         */
        @Test
        public void testDeleteRegistrationsByScheduledActivity() {
                List<Registration> registrations = toList(registrationDao.findAll());
                int size = registrations.size();
                List<Registration> registrationsByScheduledActivity = registrationService
                                .getRegistrationByScheduledActivityId(SCHEDULED_ACTIVITY_KEY);
                int sizeByScheduledActivity = registrationsByScheduledActivity.size();
                registrationService.deleteRegistrationsByScheduledActivityId(SCHEDULED_ACTIVITY_KEY);
                registrations = toList(registrationDao.findAll());
                assertEquals(size - sizeByScheduledActivity, registrations.size());
        }

        /**
         * Tests the deletion of all registrations of a scheduled activity with an
         * invalid id -> Fail
         */
        @Test
        public void testDeleteRegistrationsByInvalidScheduledActivity() {
                String error = null;
                try {
                        registrationService.deleteRegistrationsByScheduledActivityId(0);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Scheduled activity Id not valid!", error);
        }

        /**
         * Tests the deletion of all registrations of a non existing scheduled activity
         * -> Fail
         */
        @Test
        public void testDeleteRegistrationsByNonExistingScheduledActivity() {
                String error = null;
                try {
                        registrationService.deleteRegistrationsByScheduledActivityId(2);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Scheduled activity does not exist", error);
        }

        /**
         * Convert Iterable to List
         * 
         * @param Iterable<T>
         * @return List<T>
         */
        private <T> List<T> toList(Iterable<T> iterable) {
                List<T> resultList = new ArrayList<T>();
                iterable.forEach(resultList::add);
                return resultList;
        }
}
