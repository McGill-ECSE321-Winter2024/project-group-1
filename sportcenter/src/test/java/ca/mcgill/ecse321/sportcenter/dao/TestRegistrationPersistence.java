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

@SpringBootTest
public class TestRegistrationPersistence {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ScheduledActivityRepository scheduledActivityRepository;

    @AfterEach
    public void clearDatabase() {
        registrationRepository.deleteAll();
        customerRepository.deleteAll();
        scheduledActivityRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadRegistration() {

        ScheduledActivity scheduledActivity = new ScheduledActivity();
        int schedueledActivityId = 123;
        LocalDate date = LocalDate.of(2021, 11, 11);
        LocalTime startTime = LocalTime.of(10, 30, 00);
        LocalTime endTime = LocalTime.of(11, 30, 00);

        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setEndTime(endTime);
        scheduledActivity.setScheduledActivityId(schedueledActivityId);
        scheduledActivityRepository.save(scheduledActivity);

        Customer customer = new Customer();
        customerRepository.save(customer);
        
        Registration registration = new Registration();
        int regID = 123;
        
        registration.setRegId(regID);
        registrationRepository.save(registration);

        registration = null;


        registration = registrationRepository.findAccount(regID);//could be by id

        assertNotNull(registration);
        assertEquals(regID, registration.getRegId());
        assertEquals(customer, registration.getCustomer());
        assertEquals(scheduledActivity, registration.getScheduledActivity());

    }
}