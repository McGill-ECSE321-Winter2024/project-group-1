package ca.mcgill.ecse321.sportcenter.dao;

import java.time.LocalTime;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;

/**
 * @author Andrew Nemr and Patrick Zakaria
 */

@SpringBootTest
public class TestScheduledActivitytPersistence {
    @Autowired
    private ScheduledActivityRepository scheduledActivityRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Clear the database after each test
     */
    @AfterEach
    public void clearDatabase() {
        scheduledActivityRepository.deleteAll();
        accountRepository.deleteAll();
        instructorRepository.deleteAll();
        activityRepository.deleteAll();
    }

    /**
     * Test the persistence of a ScheduledActivity
     */
    @Test
    public void testPersistAndLoadScheduledActivity() {

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

        /**
        * Create an Account, set the attributes of the Account, //and save the Account
        */
        Account account = new Account();
        int accountId = 7;
        String username = "John";
        String password = "password";
        account.setUsername(username);
        account.setPassword(password);
        account.setAccountId(accountId);

        accountRepository.save(account);

        /**
         * Create an Instructor, set the attribute of the Instructor, //and save the Instructor
         */
        Instructor instructor = new Instructor();
        int accountRoleId = 70;
        InstructorStatus status = InstructorStatus.Active;
        String instructorDescription = "Good at teaching yoga.";
        String profilePicURL = "https://media.istockphoto.com/id/1495088043/vector/user-profile-icon-avatar-or-person-icon-profile-picture-portrait-symbol-default-portrait.jpg?s=612x612&w=0&k=20&c=dhV2p1JwmloBTOaGAtaA3AW1KSnjsdMt7-U_3EZElZ0=";
        instructor.setAccountRoleId(accountRoleId);
        instructor.setStatus(status);
        instructor.setDescription(instructorDescription);
        instructor.setProfilePicURL(profilePicURL);
        instructor.setAccount(account);

        instructorRepository.save(instructor);

        /**
         * Create a ScheduledActivity, set the attributes of the ScheduledActivity, and save the ScheduledActivity
         */
        ScheduledActivity scheduledActivity = new ScheduledActivity();
        int scheduledActivityId = 700;
        LocalDate date = LocalDate.of(2021, 11, 11);
        LocalTime startTime = LocalTime.of(10, 30, 00);
        LocalTime endTime = LocalTime.of(11, 30, 00);

        scheduledActivity.setScheduledActivityId(scheduledActivityId);
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setEndTime(endTime);
        scheduledActivity.setSupervisor(instructor);
        scheduledActivity.setActivity(activity);

        scheduledActivityRepository.save(scheduledActivity);

        /**
         * Load the ScheduledActivity
         */
        scheduledActivity = null;
        scheduledActivity = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId);

        /**
         * Check the attributes of the ScheduledActivity
         */
        assertNotNull(scheduledActivity);
        assertEquals(scheduledActivityId, scheduledActivity.getScheduledActivityId());
        assertEquals(date, scheduledActivity.getDate());
        assertEquals(startTime, scheduledActivity.getStartTime()); 
        assertEquals(endTime, scheduledActivity.getEndTime());
        
        assertEquals(activity, scheduledActivity.getActivity());
        assertEquals(name, scheduledActivity.getActivity().getName());
        assertEquals(description, scheduledActivity.getActivity().getDescription());
        assertEquals(subcategory, scheduledActivity.getActivity().getSubcategory());
        assertEquals(isApproved, scheduledActivity.getActivity().getIsApproved());
    }
}

