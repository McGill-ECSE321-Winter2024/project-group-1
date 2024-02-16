package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Activity;

@SpringBootTest
public class TestActivityPersistence {
    @Autowired
    private ActivityRepository activityRepository;

    @AfterEach
    public void clearDatabase() {
        activityRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadActivity() {
        
        Activity activity = new Activity();
        String name = "Yoga";
        String description = "Practice yoga with a professional instructor.";

        
        activity.setName(name);
        activity.setDescription(description);

        activityRepository.save(activity);

        activity = null;
        activity = activityRepository.findActivityByName(name);//could be by id

        assertNotNull(activity);
        assertEquals(name, activity.getName());
        assertEquals(description, activity.getDescription());s
    }
}
```