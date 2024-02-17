package ca.mcgill.ecse321.sportcenter.dao;
//import java.sql.Date;
//import java.sql.Time;
import java.time.LocalTime;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;


@SpringBootTest
public class TestSchedueledActivitytPersistence {
    @Autowired
    private ScheduledActivityRepository scheduledActivityRepository;

    @AfterEach
    public void clearDatabase() {
        scheduledActivityRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadScheduledActivity() {
        
        ScheduledActivity scheduledActivity = new ScheduledActivity();
        Activity activity = new Activity();
        int schedueledActivityId = 123;
        LocalDate date = LocalDate.of(2021, 11, 11);
        LocalTime startTime = LocalTime.of(10, 30, 00);
        LocalTime endTime = LocalTime.of(11, 30, 00);
        ClassCategory subcategory = ClassCategory.Strength;
        String name = "Yoga";
        String description = "Practice yoga with a professional instructor.";
        boolean isApproved = true;


        
        scheduledActivity.setScheduledActivityId(schedueledActivityId);
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setEndTime(endTime);
        activity.setName(name);
        activity.setDescription(description);
        activity.setSubcategory(subcategory);
        activity.setIsApproved(isApproved);
        
        scheduledActivityRepository.save(scheduledActivity);

        scheduledActivity = null;
        scheduledActivity = scheduledActivityRepository.findAccount(schedueledActivityId);

        assertNotNull(scheduledActivity);
        assertEquals(schedueledActivityId, scheduledActivity.getScheduledActivityId());
        assertEquals(date, scheduledActivity.getDate());
        assertEquals(startTime, scheduledActivity.getStartTime()); 
        assertEquals(endTime, scheduledActivity.getEndTime());
        assertEquals(name, scheduledActivity.getActivity().getName());
        assertEquals(description, scheduledActivity.getActivity().getDescription());
        assertEquals(subcategory, scheduledActivity.getActivity().getSubcategory());
        assertEquals(isApproved, scheduledActivity.getActivity().getIsApproved());
    }
}

