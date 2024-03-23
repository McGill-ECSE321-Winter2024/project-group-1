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
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

@SpringBootTest
public class TestScheduledActivityDto {

    @Autowired
    private ScheduledActivityRepository repo;

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
        activityRepo.deleteAll();
        instructorRepo.deleteAll();
        customerRepo.deleteAll();
        accountRepo.deleteAll();

    }

    @Test
    public void createAndReadScheduledActivity() {
        // Create an Activity
        String name = "name";
        String description = "description";
        boolean isApproved = true;
        Activity.ClassCategory aClassCategory = Activity.ClassCategory.Cardio;
        Activity activity = new Activity(aClassCategory, name, isApproved, description);
        activity = activityRepo.save(activity);

        // Create an Account
        String username = "username";
        String password = "password";
        Account account = new Account(username, password);
        account = accountRepo.save(account);

        // Create an Instructor
        Instructor.InstructorStatus aStatus = Instructor.InstructorStatus.Active;
        Instructor anInstructor = new Instructor(aStatus, "aDescription", "aProfilePicURL", account);
        anInstructor = instructorRepo.save(anInstructor);

        // Create a ScheduledActivity
        int capacity = 10;
        LocalTime aStartTime = LocalTime.of(10, 0);
        LocalTime aEndTime = LocalTime.of(11, 0);
        LocalDate aDate = LocalDate.of(2026, 1, 1);
        ScheduledActivity scheduledActivity = new ScheduledActivity(aDate, aStartTime, aEndTime, anInstructor, activity,
                capacity);

        // Save the ScheduledActivity in the database
        scheduledActivity = repo.save(scheduledActivity);
        int scheduledActivityId = scheduledActivity.getScheduledActivityId();

        // Load the ScheduledActivity from the database
        ScheduledActivity scheduledActivityFromDatabase = repo
                .findScheduledActivityByScheduledActivityId(scheduledActivityId);

        // Check the attributes
        assertNotNull(scheduledActivityFromDatabase);
        assertEquals(anInstructor.getAccountRoleId(), scheduledActivityFromDatabase.getSupervisor().getAccountRoleId());
        assertEquals(scheduledActivityId, scheduledActivityFromDatabase.getScheduledActivityId());
        assertEquals(capacity, scheduledActivityFromDatabase.getCapacity());
        assertEquals(name, scheduledActivityFromDatabase.getActivity().getName());
        assertEquals(isApproved, scheduledActivityFromDatabase.getActivity().getIsApproved());
        assertEquals(description, scheduledActivityFromDatabase.getActivity().getDescription());
        assertEquals(aClassCategory, scheduledActivityFromDatabase.getActivity().getSubCategory());
        assertEquals(aDate, scheduledActivityFromDatabase.getDate());

    }

}
