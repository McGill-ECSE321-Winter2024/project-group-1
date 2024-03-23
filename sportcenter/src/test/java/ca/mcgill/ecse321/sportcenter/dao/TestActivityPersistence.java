package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Andrew Nemr and Patrick Zakaria
 */

@SpringBootTest
public class TestActivityPersistence {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private TestActivityPersistence testActivityPersistence;

    @AfterEach
    public void clearDatabase() {
        activityRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadActivity() {
        MockitoAnnotations.openMocks(this);

        Activity activity = new Activity();
        ClassCategory subcategory = ClassCategory.Strength;
        String name = "Yoga";
        String description = "Practice yoga with a professional instructor.";
        boolean isApproved = true;

        activity.setName(name);
        activity.setDescription(description);
        activity.setSubCategory(subcategory);
        activity.setIsApproved(isApproved);

        when(activityRepository.findActivityByName(name)).thenReturn(activity);

        activityRepository.save(activity);
        name = activity.getName();

        activity = activityRepository.findActivityByName(name);

        assertNotNull(activity);
        assertEquals(name, activity.getName());
        assertEquals(description, activity.getDescription());
        assertEquals(subcategory, activity.getSubCategory());
        assertEquals(isApproved, activity.getIsApproved());
    }
}

// Activity activity = new Activity();
// ClassCategory subcategory = ClassCategory.Strength;
// String name = "Yoga";
// String description = "Practice yoga with a professional instructor.";
// boolean isApproved = true;

// activity.setName(name);
// activity.setDescription(description);
// activity.setSubCategory(subcategory);
// activity.setIsApproved(isApproved);

// activityRepository.save(activity);
// name = activity.getName();

// /*
// * Load the Activity from the database, using the name as the key
// */
// activity = activityRepository.findActivityByName(name);

// /*
// * Check if the Activity was saved and loaded correctly and has the correct
// * attributes
// */
// assertNotNull(activity);
// assertEquals(name, activity.getName());
// assertEquals(description, activity.getDescription());
// assertEquals(subcategory, activity.getSubCategory());
// assertEquals(isApproved, activity.getIsApproved());
// }