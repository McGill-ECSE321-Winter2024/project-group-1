package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.checkerframework.checker.units.qual.Time;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dto.ActivityDto;
import ca.mcgill.ecse321.sportcenter.model.Activity;

@SpringBootTest
public class TestActivityRepository {
    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        activityRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadActivity() {
        String name = "testName";
        String description = "testDescription";
        Date date = new Date(2021, 1, 1);
        Time startTime = new Time(12, 0, 0);
        Time endTime = new Time(13, 0, 0);
        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        activity.setDate(date);
        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        activityRepository.save(activity);

        activity = activityRepository.findActivityByName(name);
        assertNotNull(activity);
        assertEquals(name, activity.getName());
        assertEquals(description, activity.getDescription());
        assertEquals(date, activity.getDate());
        assertEquals(startTime, activity.getStartTime());
        assertEquals(endTime, activity.getEndTime());
    }

    @Test
    public void testPersistAndLoadActivityDto() {
        String name = "testName";
        String description = "testDescription";
        Date date = new Date(2021, 1, 1);
        Time startTime = new Time(12, 0, 0);
        Time endTime = new Time(13, 0, 0);
        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        activity.setDate(date);
        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        activityRepository.save(activity);

        ActivityDto activityDto = new ActivityDto(name, description, date, startTime, endTime);
        activityDto.setName(name);
        activityDto.setDescription(description);
        activityDto.setDate(date);
        activityDto.setStartTime(startTime);
        activityDto.setEndTime(endTime);
    }
}
