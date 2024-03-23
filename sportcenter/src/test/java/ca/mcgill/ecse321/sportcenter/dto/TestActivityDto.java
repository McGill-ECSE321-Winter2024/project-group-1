package ca.mcgill.ecse321.sportcenter.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;

@SpringBootTest
public class TestActivityDto {

    @Autowired
    private ActivityRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void createAndReadActivity() {
        // Create an Activity
        String name = "name";
        String description = "description";
        boolean isApproved = false;
        Activity.ClassCategory aClassCategory = Activity.ClassCategory.Cardio;
        Activity activity = new Activity(aClassCategory, name, isApproved, description);

        // Save the Activity in the database
        activity = repo.save(activity);
        String nameOf = activity.getName();

        // Load the Activity from the database
        Activity activityFromDatabase = repo.findActivityByName(nameOf);

        // Check the attributes
        assertNotNull(activityFromDatabase);
        assertEquals(nameOf, activityFromDatabase.getName());
        assertEquals(isApproved, activityFromDatabase.getIsApproved());
        assertEquals(description, activityFromDatabase.getDescription());
        assertEquals(aClassCategory, activityFromDatabase.getSubCategory());
    }

}
