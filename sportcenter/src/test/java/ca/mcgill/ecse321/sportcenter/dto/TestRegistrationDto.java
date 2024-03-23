package ca.mcgill.ecse321.sportcenter.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.model.Registration;

@SpringBootTest
public class TestRegistrationDto {

    @Autowired
    private RegistrationRepository repo;

    @Autowired
    private ScheduledActivityRepository scheduledActivityRepo;

    @Autowired
    private ActivityRepository activityRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private InstructorRepository instructorRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
        scheduledActivityRepo.deleteAll();
        activityRepo.deleteAll();
        instructorRepo.deleteAll();
        customerRepo.deleteAll();
        accountRepo.deleteAll();
    }

    @Test
    public void createAndReadRegistration() {
        // Create an Activity
        String name = "name";
        String description = "description";
        Activity.ClassCategory aClassCategory = Activity.ClassCategory.Cardio;
        Activity activity = new Activity(aClassCategory, name, true, description);
        activityRepo.save(activity);

        // Create 2 Accounts
        String username1 = "username1";
        String password1 = "password1";
        Account acc1 = new Account(username1, password1);
        accountRepo.save(acc1);
        String username2 = "username2";
        String password2 = "password2";
        Account acc2 = new Account(username2, password2);
        accountRepo.save(acc2);

        // Create an Instructor
        Instructor.InstructorStatus aStatus = Instructor.InstructorStatus.Active;
        Instructor instructor = new Instructor(aStatus, "aDescription", "url", acc1);
        instructorRepo.save(instructor);

        // Create a Customer
        Customer customer = new Customer(acc2);
        customerRepo.save(customer);

        // Create a ScheduledActivity
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = LocalTime.now().plusHours(1);
        ScheduledActivity scheduledActivity = new ScheduledActivity(date, startTime, endTime, instructor, activity, 10);
        scheduledActivityRepo.save(scheduledActivity);

        // Create a Registration
        Registration registration = new Registration(scheduledActivity, customer);
        int registrationId = registration.getRegistrationId();
        repo.save(registration);

        // Read the Registration
        Registration registrationFromDatabase = repo.findRegistrationByRegId(registrationId);

        // checks
        assertNotNull(registrationFromDatabase);
        assertEquals(customer.getAccountRoleId(), registrationFromDatabase.getCustomer().getAccountRoleId());
        assertEquals(scheduledActivity.getScheduledActivityId(),
                registrationFromDatabase.getScheduledActivity().getScheduledActivityId());
        assertEquals(registration.getRegistrationId(), registrationFromDatabase.getRegistrationId());

    }

}
