package ca.mcgill.ecse321.sportcenter.dao;
//import java.sql.Date;
//import java.sql.Time;
import java.time.LocalTime;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.description;

//import org.checkerframework.checker.units.qual.Time;
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
        int schedueledActivityId = 123;
        LocalDate date = LocalDate.of(2021, 11, 11);
        LocalTime startTime = LocalTime.of(10, 30, 00);
        LocalTime endTime = LocalTime.of(11, 30, 00);
        //Date date = new Date(2021, 11, 11);
        //Time startTime = new Time(10, 30, 00);
        //Time endTime = new Time(11, 30, 00);

        
        scheduledActivity.setScheduledActivityId(schedueledActivityId);
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setEndTime(endTime);
        
        scheduledActivityRepository.save(scheduledActivity);

        scheduledActivity = null;
        scheduledActivity = scheduledActivityRepository.findAccount(schedueledActivityId);

        assertNotNull(scheduledActivity);
        assertEquals(schedueledActivityId, scheduledActivity.getScheduledActivityId());
        assertEquals(date, scheduledActivity.getDate());
        assertEquals(startTime, scheduledActivity.getStartTime()); 
        assertEquals(endTime, scheduledActivity.getEndTime());
    }
}

