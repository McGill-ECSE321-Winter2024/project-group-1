package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

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
        String name = "Yoga";
        String description = "Practice yoga with a professional instructor.";
        String date = "2021-11-11";
        String startTime = "12:00";
        String endTime = "13:00";
        
        scheduledActivity.setName(name);
        scheduledActivity.setDescription(description);
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setEndTime(endTime);
        
        scheduledActivityRepository.save(scheduledActivity);

        scheduledActivity = null;
        scheduledActivity = scheduledActivityRepository.findScheduledActivityByName(name);

        assertNotNull(scheduledActivity);
        assertEquals(name, scheduledActivity.getName());
        assertEquals(description, scheduledActivity.getDescription());
        assertEquals(date, scheduledActivity.getDate());
        assertEquals(startTime, scheduledActivity.getStartTime()); 
        assertEquals(endTime, scheduledActivity.getEndTime());
    }
}

