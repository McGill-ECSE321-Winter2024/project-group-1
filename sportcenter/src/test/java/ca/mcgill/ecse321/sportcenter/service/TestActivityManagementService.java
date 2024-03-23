package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

@SpringBootTest
public class TestActivityManagementService {
    @Mock
    private ActivityRepository activityRepository;

    // Activity keys
    private static final String APPROVED_ACTIVITY_KEY = "ApprovedActivity";
    private static final String DISAPPROVED_ACTIVITY_KEY = "DisapprovedActivity";
    private static final String INEXISTENT_ACTIVITY_KEY = "InexistentActivity";

    private static final String CREATED_ACTIVITY_KEY = "CreatedActivity";
    private static final String DESCRIPTION = "description";
    private static final ClassCategory SUBCATEGORY = ClassCategory.Cardio;

    private static final String NEW_NAME = "newName";
    private static final String NEW_DESCRIPTION = "newDescription";
    private static final ClassCategory NEW_SUBCATEGORY = ClassCategory.Strength;

    @InjectMocks
    private ActivityManagementService activityManagementService;

    // @SuppressWarnings("null")
    @BeforeEach
    void setMockOutput() {
        lenient().when(activityDao.findActivityByName(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(APPROVED_ACTIVITY_KEY)) {
                        Activity activity = new Activity();
                        activity.setName(APPROVED_ACTIVITY_KEY);
                        activity.setDescription("description");
                        activity.setSubCategory(ClassCategory.Cardio);
                        activity.setIsApproved(true);
                        return activity;
                    } else if (invocation.getArgument(0).equals(DISAPPROVED_ACTIVITY_KEY)) {
                        Activity activity = new Activity();
                        activity.setName(DISAPPROVED_ACTIVITY_KEY);
                        activity.setDescription("description");
                        activity.setSubCategory(ClassCategory.Cardio);
                        activity.setIsApproved(false);
                        return activity;
                    } else if (invocation.getArgument(0).equals(CREATED_ACTIVITY_KEY)) {
                        Activity activity = new Activity();
                        activity.setDescription("description");
                        activity.setSubCategory(ClassCategory.Cardio);
                        activity.setIsApproved(false);
                        activity.setName(CREATED_ACTIVITY_KEY);
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
        String name = "activity";
        ClassCategory subcategory = ClassCategory.Strength;
        String description = "description";
        boolean isApproved = false;

        try {
            activity = activityManagementService.createActivity(INEXISTENT_ACTIVITY_KEY, DESCRIPTION, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }

        assertNotNull(activity);
        assertEquals(INEXISTENT_ACTIVITY_KEY, activity.getName());
        assertEquals(DESCRIPTION, activity.getDescription());
        assertEquals(SUBCATEGORY, activity.getSubCategory());
    }

    @Test
    public void testCreateActivityNameNull() {
        String name = null;
        ClassCategory subcategory = ClassCategory.Strength;
        String description = "description";
        String error = "";

        try {
            activityManagementService.createActivity(null, DESCRIPTION, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testCreateActivityNameEmpty() {
        String name = "";
        ClassCategory subcategory = ClassCategory.Strength;
        String description = "description";
        String error = "";

        try {
            activityManagementService.createActivity("", DESCRIPTION, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testCreateActivityNameWhitespace() {
        String name = "             "; // 3x (space then tab)
        ClassCategory subcategory = ClassCategory.Strength;
        String description = "          ";
        String error = "";

        try {
            activityManagementService.createActivity("       ", DESCRIPTION, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testCreateActivityDescriptionNull() {
        String name = "activity";
        ClassCategory subcategory = ClassCategory.Strength;
        String description = null;
        String error = "";

        try {
            activityManagementService.createActivity(CREATED_ACTIVITY_KEY, null, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Description cannot be empty!", error);
    }

    @Test
    public void testCreateActivityDescriptionEmpty() {
        String name = "activity";
        ClassCategory subcategory = ClassCategory.Strength;
        String description = "";
        String error = "";

        try {
            activityManagementService.createActivity(CREATED_ACTIVITY_KEY, "", SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Description cannot be empty!", error);
    }

    @Test
    public void testCreateActivityDescriptionWhitespace() {
        String name = "activity";
        ClassCategory subcategory = ClassCategory.Strength;
        String description = "          ";
        String error = "";

        try {
            activityManagementService.createActivity(CREATED_ACTIVITY_KEY, "             ", SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Description cannot be empty!", error);
    }

    @Test
    public void testCreateActivitySubcategoryNull() {
        String name = "activity";
        ClassCategory subcategory = null;
        String description = "description";
        String error = "";

        try {
            activityManagementService.createActivity(CREATED_ACTIVITY_KEY, DESCRIPTION, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Subcategory cannot be empty!", error);
    }

    @Test
    public void testCreateActivityAlreadyExists() {
        String error = null;

        try {
            activityManagementService.createActivity(CREATED_ACTIVITY_KEY, DESCRIPTION, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity already exists!", error);
    }

    // TEST #2 - UPDATE ACTIVITY
    @Test
    public void testUpdateActivity() {
        Activity activity = null;

        try {
            activity = activityManagementService.updateActivity(CREATED_ACTIVITY_KEY, NEW_NAME, NEW_DESCRIPTION,
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
            activityManagementService.updateActivity(null, NEW_NAME, NEW_DESCRIPTION, NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNameEmpty() {
        String error = null;

        try {
            activityManagementService.updateActivity("", NEW_NAME, NEW_DESCRIPTION, NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNameWhitespace() {
        String error = null;

        try {
            activityManagementService.updateActivity("       ", NEW_NAME, NEW_DESCRIPTION, NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNewNameNull() {
        String error = null;

        try {
            activityManagementService.updateActivity(CREATED_ACTIVITY_KEY, null, NEW_DESCRIPTION,
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
            activityManagementService.updateActivity(CREATED_ACTIVITY_KEY, "", NEW_DESCRIPTION, NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNewNameWhitespace() {
        String error = null;

        try {
            activityManagementService.updateActivity(CREATED_ACTIVITY_KEY, "       ", NEW_DESCRIPTION,
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
            activityManagementService.updateActivity(CREATED_ACTIVITY_KEY, NEW_NAME, null, NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New description cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNewDescriptionEmpty() {
        String error = null;

        try {
            activityManagementService.updateActivity(CREATED_ACTIVITY_KEY, NEW_NAME, "", NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New description cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNewDescriptionWhitespace() {
        String error = null;

        try {
            activityManagementService.updateActivity(CREATED_ACTIVITY_KEY, NEW_NAME, "       ", NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New description cannot be empty!", error);
    }

    @Test
    public void testUpdateActivitySubcategoryNull() {
        String error = null;

        try {
            activityManagementService.updateActivity(CREATED_ACTIVITY_KEY, NEW_NAME, NEW_DESCRIPTION, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New subcategory cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityDoesntExist() {
        String error = null;

        try {
            activityManagementService.updateActivity("does not exist", NEW_NAME, NEW_DESCRIPTION,
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
            activityManagementService.updateActivity(APPROVED_ACTIVITY_KEY, NEW_NAME, NEW_DESCRIPTION,
                    NEW_SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity is already approved!", error);
    }

    // TEST #3 - APPROVE ACTIVITY
    @Test
    public void testApproveActivity() {
        Activity activity = null;

        try {
            activity = activityManagementService.approveActivity(DISAPPROVED_ACTIVITY_KEY);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        assertNotNull(activity);
        assertTrue(activity.getIsApproved());
    }

    @Test
    public void testApproveActivityEmpty() {
        String name = "";
        ClassCategory subcategory = ClassCategory.Strength;
        boolean isApproved = false;
        String description = "";
        String error = "";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityManagementService.approveActivity(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    @Test
    public void testApproveActivityNull() {
        String name = null;
        ClassCategory subcategory = ClassCategory.Strength;
        boolean isApproved = false;
        String description = "description";
        String error = "";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityManagementService.approveActivity(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    @Test
    public void testApproveActivityWhitespaces() {
        String name = "             "; // 3x (space then tab)
        ClassCategory subcategory = ClassCategory.Strength;
        boolean isApproved = false;
        String description = "          ";
        String error = "";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityManagementService.approveActivity(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    @Test
    public void testApproveActivityDoesntExist() {
        String name = "doesn't exist";
        ClassCategory subcategory = ClassCategory.Strength;
        boolean isApproved = false;
        String description = "description";
        String error = "";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityManagementService.approveActivity(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity does not exist!", error);
    }

    @Test
    public void testApproveActivityAlreadyTrue() {
        String name = "activity";
        ClassCategory subcategory = ClassCategory.Strength;
        boolean isApproved = false;
        String description = "description";
        String error = "";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityManagementService.approveActivity(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity is already approved!", error);
    }

    // TEST #5 - DISAPPROVE ACTIVITY
    @Test
    public void testDisapproveActivity() {
        Activity activity = null;

        try {
            activity = activityManagementService.disapproveActivity(APPROVED_ACTIVITY_KEY);
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }

        assertNotNull(activity);
        assertFalse(activity.getIsApproved());
    }

    @Test
    public void testDisapproveActivityEmpty() {
        String name = "";
        ClassCategory subcategory = ClassCategory.Strength;
        boolean isApproved = true;
        String description = "";
        String error = "";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityManagementService.disapproveActivity(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    @Test
    public void testDisapproveActivityNull() {
        String name = null;
        ClassCategory subcategory = null;
        boolean isApproved = true;
        String description = null;
        String error = "";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityManagementService.disapproveActivity(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    @Test
    public void testDisapproveActivityWhitespaces() {
        String name = "             ";
        ClassCategory subcategory = ClassCategory.Strength;
        boolean isApproved = true;
        String description = "          ";
        String error = "";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityManagementService.disapproveActivity(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    @Test
    public void testDisapproveActivityAlreadyFalse() {
        String name = "activity";
        ClassCategory subcategory = ClassCategory.Strength;
        boolean isApproved = false;
        String description = "description";
        String error = "";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityManagementService.disapproveActivity(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity is already not approved!", error);
    }

    @Test
    public void testDisapproveActivityDoesNotExist() {
        String name = "does not exist";
        String error = "";

        try {
            activityManagementService.disapproveActivity(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity does not exist!", error);
    }
}
