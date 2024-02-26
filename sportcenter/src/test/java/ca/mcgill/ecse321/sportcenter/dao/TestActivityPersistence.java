package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

/**
 * @author Andrew Nemr and Patrick Zakaria
 */

@SpringBootTest
public class TestActivityPersistence {
    /**
     * The repository for Activity
     */
    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Clear the database after each test
     */
    @AfterEach
    public void clearDatabase() {
        activityRepository.deleteAll();
    }
    /**
     * Test the persistence of an Activity
     */
    @Test
    public void testPersistAndLoadActivity() {

        /*
         * Create an Activity, set the attributes of the Activity, and save the Activity
         */
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
        name = activity.getName();

        /*
         * Load the Activity from the database, using the name as the key
         */
        activity = activityRepository.findActivityByName(name);

        /*
         * Check if the Activity was saved and loaded correctly and has the correct attributes
         */
        assertNotNull(activity);
        assertEquals(name, activity.getName());
        assertEquals(description, activity.getDescription());
        assertEquals(subcategory, activity.getSubcategory());
        assertEquals(isApproved, activity.getIsApproved());
    }
}
