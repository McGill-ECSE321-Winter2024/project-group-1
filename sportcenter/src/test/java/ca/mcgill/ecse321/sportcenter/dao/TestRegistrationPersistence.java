package ca.mcgill.ecse321.sportcenter.dao;

import java.time.LocalDate;
import java.time.LocalTime;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

/**
 * @author Andrew Nemr and Patrick Zakaria
 */
@SpringBootTest
public class TestRegistrationPersistence {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScheduledActivityRepository scheduledActivityRepository;

    /**
     * Clear the database after each test
     */
    @AfterEach
    public void clearDatabase() {
        registrationRepository.deleteAll();
        customerRepository.deleteAll();
        scheduledActivityRepository.deleteAll();
    }

    /**
     * Test the persistence of a Registration
     */
    @Test
    public void testPersistAndLoadRegistration() {

        /**
         * Create a ScheduledActivity, set the attributes of the ScheduledActivity, and save the ScheduledActivity
         */
        ScheduledActivity scheduledActivity = new ScheduledActivity();
        int scheduledActivityId = 6;
        LocalDate date = LocalDate.of(2021, 11, 11);
        LocalTime startTime = LocalTime.of(10, 30, 00);
        LocalTime endTime = LocalTime.of(11, 30, 00);
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setEndTime(endTime);
        scheduledActivity.setScheduledActivityId(scheduledActivityId);
        scheduledActivityRepository.save(scheduledActivity);

        /**
         * Create a Customer, set the attributes of the Customer, and save the Customer
         */
        Customer customer = new Customer();
        customerRepository.save(customer);
        
        /**
         * Create a Registration, set the attributes of the Registration, and save the Registration
         */
        Registration registration = new Registration();
        int regID = 600;
        registration.setCustomer(customer);
        registration.setScheduledActivity(scheduledActivity);
        registration.setRegId(regID);
        registrationRepository.save(registration);

        /**
         * Load the Registration and check the attributes of the Registration
         */
        registration = registrationRepository.findRegistrationByRegId(regID);//could be by id

        /**
         * Check the attributes of the Registration
         */
        assertNotNull(registration);
        assertEquals(regID, registration.getRegId());
        assertEquals(customer, registration.getCustomer());
        assertEquals(scheduledActivity, registration.getScheduledActivity());
        assertEquals(date, registration.getScheduledActivity().getDate());
        assertEquals(startTime, registration.getScheduledActivity().getStartTime());
        assertEquals(endTime, registration.getScheduledActivity().getEndTime());
        assertEquals(scheduledActivityId, registration.getScheduledActivity().getScheduledActivityId());
    }
}