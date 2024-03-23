package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

@SpringBootTest
public class TestActivityService {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityManagementService activityService;
    @Mock
    private AccountManagementService accountService;

    // Parameters for creating an activity
    private static final String EXISTING_NAME = "SoccerPrevious";
    private static final String NEW_NAME = "Soccer";
    private static final String DESCRIPTION = "Soccer for beginners";
    private static final ClassCategory SUBCATEGORY = ClassCategory.Cardio;
    private static final boolean ISAPPROVED = false;
    private static final String NEW_DESCRIPTION = "Soccer for beginners";
    private static final ClassCategory NEW_SUBCATEGORY = ClassCategory.Cardio;

    @SuppressWarnings("null")
    @BeforeEach
    public void setMockOutput() {
        lenient().when(activityRepository.save(any(Activity.class))).thenAnswer((invocation) -> {
            if (invocation.getArgument(0).equals(EXISTING_NAME)) {
                Activity activity = new Activity();
                activity.setName(EXISTING_NAME);
                activity.setDescription(DESCRIPTION);
                activity.setSubCategory(SUBCATEGORY);
                activity.setIsApproved(ISAPPROVED);
                return activity;
            } else {
                return null;
            }
        });

        lenient().when(activityRepository.findActivityByName(any(String.class))).thenReturn(null);
        lenient().when(accountService.checkAccountHasInstructorRole(any(Integer.class))).thenReturn(true);
        lenient().when(activityRepository.findActivityByName(EXISTING_NAME)).thenReturn(new Activity());
    }

    @Test
    public void testCreateActivityNullName() {
        String error = null;

        try {
            activityService.createActivity(null, DESCRIPTION, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testCreateActivityEmptyName() {
        String error = null;

        try {
            activityService.createActivity("", DESCRIPTION, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testCreateActivityNullDescription() {
        String error = null;

        try {
            activityService.createActivity(NEW_NAME, null, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Description cannot be empty!", error);
    }

    @Test
    public void testCreateActivityEmptyDescription() {
        String error = null;

        try {
            activityService.createActivity(NEW_NAME, "", SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Description cannot be empty!", error);
    }

    @Test
    public void testCreateActivityNullSubCategory() {
        String error = null;

        try {
            activityService.createActivity(NEW_NAME, DESCRIPTION, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Subcategory cannot be empty!", error);
    }

    // @Test
    // public void testCreateActivityInstructorDoesNotExist()
    // {///////////////////////////////////////
    // String error = null;

    // try {
    // activityService.createActivity(NEW_NAME, DESCRIPTION, SUBCATEGORY);
    // } catch (IllegalArgumentException e) {
    // error = e.getMessage();
    // }

    // assertEquals("Instructor does not exist!", error);
    // }

    @Test
    public void testCreateActivityAlreadyExists() {
        String error = null;

        try {
            activityService.createActivity(EXISTING_NAME, DESCRIPTION, SUBCATEGORY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity already exists!", error);
    }

    @Test
    public void testGetAllActivities() {
        when(activityRepository.findAll()).thenReturn(null);
        assertEquals(null, activityRepository.findAll());
    }

    @SuppressWarnings("null")
    @Test
    public void testUpdateActivity() {

        // this checks if the
        // instructor
        // exists, if not, it will return
        // false which will throw an
        // exception
        when(activityRepository.save(any(Activity.class))).thenAnswer((invocation) -> {
            Activity activity = new Activity();
            activity.setName(NEW_NAME);
            activity.setDescription(NEW_DESCRIPTION);
            activity.setSubCategory(NEW_SUBCATEGORY);
            return activity;
        });

        // assertNotNull(updatedActivity);
        // assertEquals(newName, updatedActivity.getName());
        // assertEquals(newDescription, updatedActivity.getDescription());
        // assertEquals(newSubcategory, updatedActivity.getSubCategory());
    }

    @Test
    public void testUpdateActivityNullName() {
        String name = null;
        String newName = "Soccer for beginners";
        String newDescription = "Soccer for beginners";
        ClassCategory newSubcategory = ClassCategory.Cardio;
        int instructorId = 1;

        String error = null;
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);// this checks if the
                                                                                          // instructor
        // exists, if not, it will return
        // false which will throw an
        // exception
        try {
            activityService.updateActivity(name, newName, newDescription, newSubcategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNullNewName() {
        String name = "Soccer";
        String newName = null;
        String newDescription = "Soccer for beginners";
        ClassCategory newSubcategory = ClassCategory.Cardio;
        int instructorId = 1;

        String error = null;
        when(activityRepository.findActivityByName(name)).thenReturn(new Activity());
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);// this checks if the
                                                                                          // instructor
        // exists, if not, it will return
        // false which will throw an
        // exception
        try {
            activityService.updateActivity(name, newName, newDescription, newSubcategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityEmptyNewName() {
        String name = "Soccer";
        String newName = "";
        String newDescription = "Soccer for beginners";
        ClassCategory newSubcategory = ClassCategory.Cardio;
        int instructorId = 1;

        String error = null;
        when(activityRepository.findActivityByName(name)).thenReturn(new Activity());
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);// this checks if the
                                                                                          // instructor
        // exists, if not, it will return
        // false which will throw an
        // exception
        try {
            activityService.updateActivity(name, newName, newDescription, newSubcategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New name cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNullNewDescription() {
        String name = "Soccer";
        String newName = "Soccer for beginners";
        String newDescription = null;
        ClassCategory newSubcategory = ClassCategory.Cardio;
        int instructorId = 1;

        String error = null;
        when(activityRepository.findActivityByName(name)).thenReturn(new Activity());
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);// this checks if the
                                                                                          // instructor
        // exists, if not, it will return
        // false which will throw an
        // exception
        try {
            activityService.updateActivity(name, newName, newDescription, newSubcategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New description cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityEmptyNewDescription() {
        String name = "Soccer";
        String newName = "Soccer for beginners";
        String newDescription = "";
        ClassCategory newSubcategory = ClassCategory.Cardio;
        int instructorId = 1;

        String error = null;
        when(activityRepository.findActivityByName(name)).thenReturn(new Activity());
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);// this checks if the
                                                                                          // instructor
        // exists, if not, it will return
        // false which will throw an
        // exception
        try {
            activityService.updateActivity(name, newName, newDescription, newSubcategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New description cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityNullNewSubCategory() {
        String name = "Soccer";
        String newName = "Soccer for beginners";
        String newDescription = "Soccer for beginners";
        ClassCategory newSubcategory = null;
        int instructorId = 1;

        String error = null;
        when(activityRepository.findActivityByName(name)).thenReturn(new Activity());
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);// this checks if the
                                                                                          // instructor
        // exists, if not, it will return
        // false which will throw an
        // exception
        try {
            activityService.updateActivity(name, newName, newDescription, newSubcategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New subcategory cannot be empty!", error);
    }

    @Test
    public void testUpdateActivityDoesNotExist() {
        String name = "Soccer";
        String newName = "Soccer for beginners";
        String newDescription = "Soccer for beginners";
        ClassCategory newSubcategory = ClassCategory.Cardio;
        int instructorId = 1;

        String error = null;
        when(activityRepository.findActivityByName(name)).thenReturn(null);
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);// this checks if the
                                                                                          // instructor
        // exists, if not, it will return
        // false which will throw an
        // exception
        try {
            activityService.updateActivity(name, newName, newDescription, newSubcategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity does not exist!", error);
    }

    @Test
    public void testDeleteActivity() {
        ;
        String name = "Soccer";
        String description = "Soccer for beginners";
        ClassCategory subCategory = ClassCategory.Cardio;
        activityService.createActivity(name, description, subCategory);
        activityService.deleteActivity(name);
    }

    @Test
    public void testDeleteActivityNullName() {
        String name = "Soccer";
        String description = "Soccer for beginners";
        ClassCategory subCategory = ClassCategory.Cardio;
        activityService.createActivity(name, description, subCategory);
        String error = null;
        try {
            activityService.deleteActivity(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testDeleteActivityEmptyName() {
        String name = "Soccer";
        String description = "Soccer for beginners";
        ClassCategory subCategory = ClassCategory.Cardio;
        activityService.createActivity(name, description, subCategory);
        String error = null;
        try {
            activityService.deleteActivity("");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testDeleteActivityDoesNotExist() {
        String name = "Soccer";
        String description = "Soccer for beginners";
        ClassCategory subCategory = ClassCategory.Cardio;
        activityService.createActivity(name, description, subCategory);
        String error = null;
        try {
            activityService.deleteActivity("Basketball");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity does not exist!", error);
    }

    @Test
    public void testGetActivitiesBySubcategory() {
        ClassCategory subcategory = ClassCategory.Cardio;
        when(activityRepository.findAll()).thenReturn(null);
        assertEquals(null, activityService.getActivitiesBySubcategory(subcategory));
    }

    @Test
    public void testGetActivitiesByIsApproved() {
        boolean isApproved = false;
        when(activityRepository.findAll()).thenReturn(null);
        assertEquals(null, activityService.getActivitiesByIsApproved(isApproved));
    }

}
