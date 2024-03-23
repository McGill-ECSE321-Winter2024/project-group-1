package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;

/**
 * This class tests the ActivityManagementService class
 * 
 * @author: Mathias Pacheco Lemina
 */
@ExtendWith(MockitoExtension.class)
public class TestActivityManagementService {
    @Mock
    private ActivityRepository activityDao;

    @Mock
    private InstructorRepository instructorDao;

    // Activity keys
    private static final String APPROVED_ACTIVITY_KEY = "ApprovedActivity";
    private static final String DISAPPROVED_ACTIVITY_KEY = "DisapprovedActivity";
    private static final String INEXISTENT_ACTIVITY_KEY = "InexistentActivity";

    private static final String CREATED_ACTIVITY = "CreatedActivity";
    private static final String DESCRIPTION = "description";
    private static final ClassCategory SUBCATEGORY = ClassCategory.Cardio;

    private static final String NEW_NAME = "newName";
    private static final String NEW_DESCRIPTION = "newDescription";
    private static final ClassCategory NEW_SUBCATEGORY = ClassCategory.Strength;

    @InjectMocks
    private ActivityManagementService activityManagementService;

    @SuppressWarnings("null")
    @BeforeEach
    void setMockOutput() {
        lenient().when(activityDao.findActivityByName(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(APPROVED_ACTIVITY_KEY)) {
                        Activity activity = new Activity();
                        activity.setName("activity");
                        activity.setDescription("description");
                        activity.setSubCategory(ClassCategory.Cardio);
                        activity.setIsApproved(true);
                        activity.setName(APPROVED_ACTIVITY_KEY);
                        return activity;
                    } else if (invocation.getArgument(0).equals(DISAPPROVED_ACTIVITY_KEY)) {
                        Activity activity = new Activity();
                        activity.setName("activity");
                        activity.setDescription("description");
                        activity.setSubCategory(ClassCategory.Cardio);
                        activity.setIsApproved(false);
                        activity.setName(DISAPPROVED_ACTIVITY_KEY);
                        return activity;
                    } else if (invocation.getArgument(0).equals(INEXISTENT_ACTIVITY_KEY)) {
                        return null;
                    } else {
                        return null;
                    }
                });
    }

    // TEST #1 - CREATE ACTIVITY
    @Test
    public void testCreateActivity() {
        Activity activity = null;

        try {
            activity = activityManagementService.createActivity(CREATED_ACTIVITY, DESCRIPTION, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(activity);
        assertEquals(CREATED_ACTIVITY, activity.getName());
        assertEquals(DESCRIPTION, activity.getDescription());
        assertEquals(SUBCATEGORY, activity.getSubCategory());
    }

    @Test
    public void testCreateActivityNameNull() {
        String error = null;

        try {
            activity = activityManagementService.createActivity(null, DESCRIPTION, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testCreateActivityNameEmpty() {
        String error = null;

        try {
            activity = activityManagementService.createActivity("", DESCRIPTION, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testCreateActivityNameWhitespace() {
        String error = null;

        try {
            activity = activityManagementService.createActivity("       ", DESCRIPTION, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testCreateActivityDescriptionNull() {
        String error = null;

        try {
            activity = activityManagementService.createActivity(CREATED_ACTIVITY, null, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Description cannot be empty!", error);
    }

    @Test
    public void testCreateActivityDescriptionEmpty() {
        String error = null;

        try {
            activity = activityManagementService.createActivity(CREATED_ACTIVITY, "", SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Description cannot be empty!", error);
    }

    @Test
    public void testCreateActivityDescriptionWhitespace() {
        String error = null;

        try {
            activity = activityManagementService.createActivity(CREATED_ACTIVITY, "             ", SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Description cannot be empty!", error);
    }

    @Test
    public void testCreateActivitySubcategoryNull() {
        String error = null;

        try {
            activity = activityManagementService.createActivity(CREATED_ACTIVITY, DESCRIPTION, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Subcategory cannot be empty!", error);
    }

    @Test // NOT SURE
    public void testCreateActivityAlreadyExists() {
        String error = null;
        Activity activity = null;

        try {
            activity = activityManagementService.createActivity(name, description, subcategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity already exists!", error);
    }

    // TEST #2 - UPDATE ACTIVITY
    // TODO: REALLY UNSURE THAT IT WORKS
    @Test
    public void testUpdateActivity() {
        Activity activity = null;

        try {
            activity = activityManagementService.updateActivity(CREATED_ACTIVITY, NEW_NAME, NEW_DESCRIPTION,
                    NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(activity);
        assertEquals(NEW_NAME, activity.getName());
        assertEquals(NEW_DESCRIPTION, activity.getDescription());
        assertEquals(NEW_SUBCATEGORY, activity.getSubCategory());
    }

    @Test
    public void testUpdateActivityNameNull() {
        String error = null;

        try {
            activity = activityManagementService.updateActivity(null, NEW_NAME, NEW_DESCRIPTION, NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNameEmpty() {
        String error = null;

        try {
            activity = activityManagementService.updateActivity("", NEW_NAME, NEW_DESCRIPTION, NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNameWhitespace() {
        String error = null;

        try {
            activity = activityManagementService.updateActivity("       ", NEW_NAME, NEW_DESCRIPTION, NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNewNameNull() {
        String error = null;

        try {
            activity = activityManagementService.updateActivity(CREATED_ACTIVITY, null, NEW_DESCRIPTION,
                    NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNewNameEmpty() {
        String error = null;

        try {
            activity = activityManagementService.updateActivity(CREATED_ACTIVITY, "", NEW_DESCRIPTION, NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNewNameWhitespace() {
        String error = null;

        try {
            activity = activityManagementService.updateActivity(CREATED_ACTIVITY, "       ", NEW_DESCRIPTION,
                    NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNewDescriptionNull() {
        String error = null;

        try {
            activity = activityManagementService.updateActivity(CREATED_ACTIVITY, NEW_NAME, null, NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New description cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNewDescriptionEmpty() {
        String error = null;

        try {
            activity = activityManagementService.updateActivity(CREATED_ACTIVITY, NEW_NAME, "", NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New description cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNewDescriptionWhitespace() {
        String error = null;

        try {
            activity = activityManagementService.updateActivity(CREATED_ACTIVITY, NEW_NAME, "       ", NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New description cannot be empty!", error);
    }

    @Test
    public void testUpdateActivitySubcategoryNull() {
        String error = null;

        try {
            activity = activityManagementService.updateActivity(CREATED_ACTIVITY, NEW_NAME, NEW_DESCRIPTION, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New subcategory cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityDoesntExist() {
        String error = null;

        try {
            activity = activityManagementService.updateActivity("does not exist", NEW_NAME, NEW_DESCRIPTION,
                    NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity does not exist!", error);
    }

    @Test // TODO: How to test that the activity is updated?
    public void testUpdateActivityAlreadyApproved() {
        String error = null;

        try {
            activity = activityManagementService.updateActivity(APPROVED_ACTIVITY_KEY, NEW_NAME, NEW_DESCRIPTION,
                    NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity is already approved!", error);
    }

    // TEST #3 - APPROVE ACTIVITY
    @Test
    public void testApproveActivity() {
        try {
            activityManagementService.approveActivity(APPROVED_ACTIVITY_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertTrue(activityRepository.findActivityByName(APPROVED_ACTIVITY_KEY).getIsApproved());
    }

    @Test
    public void testApproveActivityEmpty() {
        String error = null;
        try {
            activityManagementService.approveActivity("");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    @Test
    public void testApproveActivityNull() {
        String error = null;
        try {
            activityManagementService.approveActivity(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    @Test
    public void testApproveActivityWhitespaces() {
        String error = null;
        try {
            activityManagementService.approveActivity("            ");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    @Test
    public void testApproveActivityDoesntExist() {
        String error = null;
        try {
            activityManagementService.approveActivity(INEXISTENT_ACTIVITY_KEY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity does not exist!", error);
    }

    @Test
    public void testApproveActivityAlreadyTrue() {
        String error = null;

        try {
            activityManagementService.approveActivity(APPROVED_ACTIVITY_KEY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity is already approved!", error);
    }

    // TEST #4 - DISAPPROVE ACTIVITY
    @Test
    public void testDisapproveActivity() {
        try {
            activityManagementService.disapproveActivity(DISAPPROVED_ACTIVITY_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertFalse(activityRepository.findActivityByName(DISAPPROVED_ACTIVITY_KEY).getIsApproved());
    }

    @Test
    public void testDisapproveActivityEmpty() {
        String error = null;

        try {
            activityManagementService.disapproveActivity("");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    @Test
    public void testDisapproveActivityNull() {
        String error = null;

        try {
            activityManagementService.disapproveActivity(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    @Test
    public void testDisapproveActivityWhitespaces() {
        String error = null;

        try {
            activityManagementService.disapproveActivity("            ");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    @Test
    public void testDisapproveActivityDoesNotExist() {
        String error = null;

        try {
            activityManagementService.disapproveActivity(INEXISTENT_ACTIVITY_KEY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity does not exist!", error);
    }
}
