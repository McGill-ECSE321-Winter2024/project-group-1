package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

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
        ClassCategory subcategory = ClassCategory.Strength;
        String name = "Yoga";
        String description = "Practice yoga with a professional instructor.";
        boolean isApproved = true;


        activity.setName(name);
        activity.setDescription(description);
        activity.setSubcategory(subcategory);
        activity.setIsApproved(isApproved);

        activityRepository.save(activity);

        activity = null;
        activity = activityRepository.findActivity(name);

        assertNotNull(activity);
        assertEquals(name, activity.getName());
        assertEquals(description, activity.getDescription());
        assertEquals(subcategory, activity.getSubcategory());
        assertEquals(isApproved, activity.getIsApproved());
    }
}
