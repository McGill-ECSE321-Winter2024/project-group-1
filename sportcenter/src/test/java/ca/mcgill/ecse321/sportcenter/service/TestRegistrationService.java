package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.lenient;

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
        private static final int PASSED_SCHEDULED_ACTIVITY_KEY = 2;
        private static final int FULL_SCHEDULED_ACTIVITY_KEY = 3;

        // Registration keys
        private static final int REGISTRATION_KEY = 1;

        @InjectMocks
        private RegistrationManagementService registrationService;

        @SuppressWarnings("null")
        @BeforeEach
        void setMockOutput() {
                lenient().when(accountDao.findAccountByAccountId(anyInt()))
                                .thenAnswer((InvocationOnMock invocation) -> {
                                        if (invocation.getArgument(0).equals(CUSTOMER_ACCOUNT_KEY)) {
                                                Account account = new Account();
                                                account.setUsername("customer");
                                                account.setPassword("password");
                                                account.setAccountId(CUSTOMER_ACCOUNT_KEY);
                                                return account;
                                        } else if (invocation.getArgument(0).equals(APPROVED_INSTRUCTOR_ACCOUNT_KEY)) {
                                                Account account = new Account();
                                                account.setUsername("approvedInstructor");
                                                account.setPassword("password");
                                                account.setAccountId(APPROVED_INSTRUCTOR_ACCOUNT_KEY);
                                                return account;
                                        } else if (invocation.getArgument(0)
                                                        .equals(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY)) {
                                                Account account = new Account();
                                                account.setUsername("disapprovedInstructor");
                                                account.setPassword("password");
                                                account.setAccountId(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY);
                                                return account;
                                        } else if (invocation.getArgument(0).equals(NEW_CUSTOMER_ACCOUNT_KEY)) {
                                                Account account = new Account();
                                                account.setUsername("newCustomer");
                                                account.setPassword("password");
                                                account.setAccountId(NEW_CUSTOMER_ACCOUNT_KEY);
                                                return account;
                                        } else {
                                                return null;
                                        }
                                });

                lenient().when(customerDao.findCustomerByAccountRoleId(anyInt()))
                                .thenAnswer((InvocationOnMock invocation) -> {
                                        if (invocation.getArgument(0).equals(CUSTOMER_KEY)) {
                                                Customer customer = new Customer();
                                                customer.setAccount(accountDao
                                                                .findAccountByAccountId(CUSTOMER_ACCOUNT_KEY));
                                                customer.setAccountRoleId(CUSTOMER_KEY);
                                                return customer;
                                        } else if (invocation.getArgument(0).equals(NEW_CUSTOMER_KEY)) {
                                                Customer customer = new Customer();
                                                customer.setAccount(accountDao
                                                                .findAccountByAccountId(NEW_CUSTOMER_ACCOUNT_KEY));
                                                customer.setAccountRoleId(NEW_CUSTOMER_KEY);
                                                return customer;
                                        } else {
                                                return null;
                                        }
                                });

                lenient().when(instructorDao.findInstructorByAccountRoleId(anyInt()))
                                .thenAnswer((InvocationOnMock invocation) -> {
                                        if (invocation.getArgument(0).equals(APPROVED_INSTRUCTOR_KEY)) {
                                                Instructor instructor = new Instructor();
                                                instructor.setAccount(accountDao.findAccountByAccountId(
                                                                APPROVED_INSTRUCTOR_ACCOUNT_KEY));
                                                instructor.setStatus(InstructorStatus.Active);
                                                instructor.setDescription("description");
                                                instructor.setProfilePicURL("pictureURL");
                                                instructor.setAccountRoleId(APPROVED_INSTRUCTOR_KEY);
                                                return instructor;
                                        } else if (invocation.getArgument(0).equals(DISAPPROVED_INSTRUCTOR_KEY)) {
                                                Instructor instructor = new Instructor();
                                                instructor.setAccount(accountDao.findAccountByAccountId(
                                                                DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY));
                                                instructor.setStatus(InstructorStatus.Pending);
                                                instructor.setDescription("description");
                                                instructor.setProfilePicURL("pictureURL");
                                                instructor.setAccountRoleId(DISAPPROVED_INSTRUCTOR_KEY);
                                                return instructor;
                                        } else {
                                                return null;
                                        }
                                });

                lenient().when(activityDao.findActivityByName(anyString()))
                                .thenAnswer((InvocationOnMock invocation) -> {
                                        if (invocation.getArgument(0).equals(APPROVED_ACTIVITY_KEY)) {
                                                Activity activity = new Activity();
                                                activity.setName("activity");
                                                activity.setDescription("description");
                                                activity.setSubCategory(ClassCategory.Cardio);
                                                activity.setIsApproved(true);
                                                activity.setName(APPROVED_ACTIVITY_KEY);
                                                return activity;
                                        } else if (invocation.getArgument(0).equals(DISAPPROVED_ACTIVITY_KEY)) {
                                                Activity activity = new Activity();
                                                activity.setName("activity");
                                                activity.setDescription("description");
                                                activity.setSubCategory(ClassCategory.Cardio);
                                                activity.setIsApproved(false);
                                                activity.setName(DISAPPROVED_ACTIVITY_KEY);
                                                return activity;
                                        } else {
                                                return null;
                                        }
                                });

                lenient().when(scheduledActivityDao.findScheduledActivityByScheduledActivityId(anyInt()))
                                .thenAnswer((InvocationOnMock invocation) -> {
                                        if (invocation.getArgument(0).equals(SCHEDULED_ACTIVITY_KEY)) {
                                                ScheduledActivity scheduledActivity = new ScheduledActivity();
                                                scheduledActivity.setDate(LocalDate.now());
                                                scheduledActivity.setStartTime(LocalTime.now());
                                                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                                                scheduledActivity.setSupervisor(
                                                                instructorDao.findInstructorByAccountRoleId(
                                                                                APPROVED_INSTRUCTOR_KEY));
                                                scheduledActivity.setActivity(
                                                                activityDao.findActivityByName(
                                                                                APPROVED_ACTIVITY_KEY));
                                                scheduledActivity.setCapacity(30);
                                                scheduledActivity.setScheduledActivityId(SCHEDULED_ACTIVITY_KEY);
                                                return scheduledActivity;
                                        } else if (invocation.getArgument(0).equals(PASSED_SCHEDULED_ACTIVITY_KEY)) {
                                                ScheduledActivity scheduledActivity = new ScheduledActivity();
                                                scheduledActivity.setDate(LocalDate.of(2020, 1, 1));
                                                scheduledActivity.setStartTime(LocalTime.now());
                                                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                                                scheduledActivity.setSupervisor(
                                                                instructorDao.findInstructorByAccountRoleId(
                                                                                APPROVED_INSTRUCTOR_KEY));
                                                scheduledActivity.setActivity(
                                                                activityDao.findActivityByName(
                                                                                APPROVED_ACTIVITY_KEY));
                                                scheduledActivity.setCapacity(30);
                                                scheduledActivity.setScheduledActivityId(PASSED_SCHEDULED_ACTIVITY_KEY);
                                                return scheduledActivity;
                                        } else if (invocation.getArgument(0).equals(FULL_SCHEDULED_ACTIVITY_KEY)) {
                                                ScheduledActivity scheduledActivity = new ScheduledActivity();
                                                scheduledActivity.setDate(LocalDate.now());
                                                scheduledActivity.setStartTime(LocalTime.now());
                                                scheduledActivity.setEndTime(LocalTime.of(12, 0));
                                                scheduledActivity.setSupervisor(
                                                                instructorDao.findInstructorByAccountRoleId(
                                                                                APPROVED_INSTRUCTOR_KEY));
                                                scheduledActivity.setActivity(
                                                                activityDao.findActivityByName(
                                                                                APPROVED_ACTIVITY_KEY));
                                                scheduledActivity.setCapacity(0);
                                                scheduledActivity.setScheduledActivityId(FULL_SCHEDULED_ACTIVITY_KEY);
                                                return scheduledActivity;
                                        } else {
                                                return null;
                                        }
                                });

                lenient().when(registrationDao.findRegistrationByRegId(anyInt()))
                                .thenAnswer((InvocationOnMock invocation) -> {
                                        if (invocation.getArgument(0).equals(REGISTRATION_KEY)) {
                                                Registration registration = new Registration();
                                                registration.setCustomer(
                                                                customerDao.findCustomerByAccountRoleId(CUSTOMER_KEY));
                                                registration.setScheduledActivity(
                                                                scheduledActivityDao
                                                                                .findScheduledActivityByScheduledActivityId(
                                                                                                SCHEDULED_ACTIVITY_KEY));
                                                registration.setRegistrationId(REGISTRATION_KEY);
                                                return registration;
                                        } else {
                                                return null;
                                        }
                                });

                lenient().when(registrationDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
                        List<Registration> registrations = new ArrayList<Registration>();
                        Registration registration = new Registration();
                        registration.setCustomer(customerDao.findCustomerByAccountRoleId(CUSTOMER_KEY));
                        registration.setScheduledActivity(scheduledActivityDao
                                        .findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY));
                        registration.setRegistrationId(REGISTRATION_KEY);
                        registrations.add(registration);
                        return registrations;
                });

                Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
                        return invocation.getArgument(0);
                };
                lenient().when(accountDao.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
                lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
                lenient().when(instructorDao.save(any(Instructor.class))).thenAnswer(returnParameterAsAnswer);
                lenient().when(activityDao.save(any(Activity.class))).thenAnswer(returnParameterAsAnswer);
                lenient().when(scheduledActivityDao.save(any(ScheduledActivity.class)))
                                .thenAnswer(returnParameterAsAnswer);
                lenient().when(registrationDao.save(any(Registration.class))).thenAnswer(returnParameterAsAnswer);
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
                        fail(e.getMessage());
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
                Registration registration = null;
                try {
                        registration = registrationService.createRegistration(-1, SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
                assertEquals("Account role id not valid!", error);
        }

        /**
         * Tests the creation of a registration with an invalid scheduled activity id ->
         * Fail
         */
        @Test
        public void testCreateRegistrationInvalidScheduledActivity() {
                String error = null;
                Registration registration = null;
                try {
                        registration = registrationService.createRegistration(CUSTOMER_KEY, -1);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
                assertEquals("Scheduled activity id not valid!", error);
        }

        /**
         * Tests the creation of a registration with a customer that doesn't exist ->
         * Fail
         */
        @Test
        public void testCreateRegistrationCustomerDoesNotExist() {
                String error = null;
                Registration registration = null;
                try {
                        registration = registrationService.createRegistration(6, SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
                assertEquals("Customer does not exist", error);
        }

        /**
         * Tests the creation of a registration with a scheduled activity that doesn't
         * exist -> Fail
         */
        @Test
        public void testCreateRegistrationScheduledActivityDoesNotExist() {
                String error = null;
                Registration registration = null;
                try {
                        registration = registrationService.createRegistration(CUSTOMER_KEY, 6);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
                assertEquals("Scheduled activity does not exist", error);
        }

        /**
         * Tests the creation of a duplicate registration -> Fail
         */
        @Test
        public void testCreateRegistrationDuplicate() {
                String error = null;
                Registration registration = null;
                try {
                        registration = registrationService.createRegistration(CUSTOMER_KEY, SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
                assertEquals("Registration already exists for this customer and scheduled activity", error);
        }

        /**
         * Tests the creation of a registration with a scheduled activity in the past ->
         * Fail
         */
        @Test
        public void testCreateRegistrationScheduledActivityInPast() {
                String error = null;
                Registration registration = null;
                try {
                        registration = registrationService.createRegistration(CUSTOMER_KEY,
                                        PASSED_SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
                assertEquals("Scheduled activity is in the past", error);
        }

        /**
         * Tests the creation of a registration with a full scheduled activity -> Fail
         */
        @Test
        public void testCreateRegistrationScheduledActivityFull() {
                String error = null;
                Registration registration = null;
                try {
                        registration = registrationService.createRegistration(CUSTOMER_KEY,
                                        FULL_SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registration);
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
                        registration = registrationService.getRegistrationByRegId(-1);
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
                        registration = registrationService.getRegistrationByRegId(5);
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
                        registrations = registrationService.getRegistrationByAccountRoleId(-1);
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
                        registrations = registrationService.getRegistrationByAccountRoleId(5);
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
                        registrations = registrationService.getRegistrationByScheduledActivityId(-1);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertNull(registrations);
                assertEquals("Id not valid!", error);
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
                        registrations = registrationService.getRegistrationByScheduledActivityId(5);
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
                        registration = registrationService.getRegistrationByCustomerIdAndScheduledActivityId(
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
                        registration = registrationService.getRegistrationByCustomerIdAndScheduledActivityId(-1,
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
                        registration = registrationService.getRegistrationByCustomerIdAndScheduledActivityId(
                                        CUSTOMER_KEY,
                                        -1);
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
                        registration = registrationService.getRegistrationByCustomerIdAndScheduledActivityId(5,
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
                        registration = registrationService.getRegistrationByCustomerIdAndScheduledActivityId(
                                        CUSTOMER_KEY,
                                        5);
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
                        customers = registrationService.getCustomersByScheduledActivityId(-1);
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
                        customers = registrationService.getCustomersByScheduledActivityId(5);
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
                        scheduledActivities = registrationService.getScheduledActivitiesByCustomerId(-1);
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
                        scheduledActivities = registrationService.getScheduledActivitiesByCustomerId(5);
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
                        scheduledActivities = registrationService.getScheduledActivitiesByInstructorId(-1);
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
                        scheduledActivities = registrationService.getScheduledActivitiesByInstructorId(5);
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
                boolean deleted = false;
                try {
                        deleted = registrationService.deleteRegistration(REGISTRATION_KEY);
                } catch (IllegalArgumentException e) {
                        fail();
                }
                assertTrue(deleted);
        }

        /**
         * Tests the deletion of a registration with an invalid id -> Fail
         */
        @Test
        public void testDeleteRegistrationInvalidId() {
                String error = null;
                try {
                        registrationService.deleteRegistration(-1);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Registration id not valid!", error);
        }

        /**
         * Tests the deletion of a non existing registration -> Fail
         */
        @Test
        public void testDeleteNonExistingRegistration() {
                String error = null;
                try {
                        registrationService.deleteRegistration(5);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Registration does not exist", error);
        }

        /**
         * Tests the deletion of all registrations of a customer -> Success
         */
        @Test
        public void testDeleteRegistrationsByCustomer() {
                boolean deleted = false;
                try {
                        deleted = registrationService.deleteRegistrationsByAccountRoleId(CUSTOMER_KEY);
                } catch (IllegalArgumentException e) {
                        fail();
                }
                assertTrue(deleted);
        }

        /**
         * Tests the deletion of all registrations of a customer with an invalid id ->
         * Fail
         */
        @Test
        public void testDeleteRegistrationsByInvalidCustomer() {
                String error = null;
                try {
                        registrationService.deleteRegistrationsByAccountRoleId(-1);
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
                        registrationService.deleteRegistrationsByAccountRoleId(5);
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
                boolean deleted = false;
                try {
                        deleted = registrationService.deleteRegistrationsByScheduledActivityId(SCHEDULED_ACTIVITY_KEY);
                } catch (IllegalArgumentException e) {
                        fail();
                }
                assertTrue(deleted);
        }

        /**
         * Tests the deletion of all registrations of a scheduled activity with an
         * invalid id -> Fail
         */
        @Test
        public void testDeleteRegistrationsByInvalidScheduledActivity() {
                String error = null;
                try {
                        registrationService.deleteRegistrationsByScheduledActivityId(-1);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Scheduled activity id not valid!", error);
        }

        /**
         * Tests the deletion of all registrations of a non existing scheduled activity
         * -> Fail
         */
        @Test
        public void testDeleteRegistrationsByNonExistingScheduledActivity() {
                String error = null;
                try {
                        registrationService.deleteRegistrationsByScheduledActivityId(5);
                } catch (IllegalArgumentException e) {
                        error = e.getMessage();
                }
                assertEquals("Scheduled activity does not exist", error);
        }
}
