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
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

/**
 * Author: Andrew Nemr
 */

@SpringBootTest
public class TestScheduledActivitytPersistence {
    @Autowired
    private ScheduledActivityRepository scheduledActivityRepository;
    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Clear the database after each test
     */
    @AfterEach
    public void clearDatabase() {
        scheduledActivityRepository.deleteAll();
        activityRepository.deleteAll();
    }

    /**
     * Test the persistence of a ScheduledActivity
     */
    @Test
    public void testPersistAndLoadScheduledActivity() {
        
        /**
         * Create a ScheduledActivity, set the attributes of the ScheduledActivity, and save the ScheduledActivity
         */
        SportCenter sportCenter = new SportCenter();
        ScheduledActivity scheduledActivity = new ScheduledActivity();
        int scheduledActivityId = 123;
        LocalDate date = LocalDate.of(2021, 11, 11);
        LocalTime startTime = LocalTime.of(10, 30, 00);
        LocalTime endTime = LocalTime.of(11, 30, 00);

        scheduledActivity.setScheduledActivityId(scheduledActivityId);
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setEndTime(endTime);
        scheduledActivity.setSportCenter(sportCenter);
        
        scheduledActivityRepository.save(scheduledActivity);

        /**
         * Create an Activity, set the attributes of the Activity, and save the Activity
         */
        Activity activity = new Activity();
        ClassCategory subcategory = ClassCategory.Strength;
        String name = "Yoga";
        String description = "Practice yoga with a professional instructor.";
        boolean isApproved = true;
        
        scheduledActivity.setActivity(activity);

        activity.setName(name);
        activity.setDescription(description);
        activity.setSubcategory(subcategory);
        activity.setIsApproved(isApproved);
        activity.setSportCenter(sportCenter);
        activityRepository.save(activity);

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

