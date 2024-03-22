package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
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
    private AccountManagementService accountService;

    @AfterEach
    public void clearDatabase() {
        activityRepository.deleteAll();
    }

    @Test
    public void testCreateActivity() {
        String name = "Soccer";
        String description = "Soccer for beginners";
        ClassCategory subCategory = ClassCategory.Cardio;
        boolean isApproved = false;
        int instructorId = 1;

        when(activityRepository.findActivityByName(name)).thenReturn(null);
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);
        when(activityRepository.save(any(Activity.class))).thenAnswer((invocation) -> {
            Activity activity = new Activity();
            activity.setName(name);
            activity.setDescription(description);
            activity.setSubCategory(subCategory);
            activity.setIsApproved(isApproved);
            return activity;
        });

        Activity createdActivity = activityService.createActivity(name, description, subCategory);
        verify(activityRepository, times(1)).save(any(Activity.class));

        assertNotNull(createdActivity);
        assertEquals(name, createdActivity.getName());
        assertEquals(description, createdActivity.getDescription());
        assertEquals(subCategory, createdActivity.getSubCategory());
        assertEquals(isApproved, createdActivity.getIsApproved());
    }

    @Test
    public void testCreateActivityNullName() {
        String name = null;
        String description = "Soccer for beginners";
        ClassCategory subCategory = ClassCategory.Cardio;
        int instructorId = 1;

        String error = null;
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);
        try {
            Activity createdActivity = activityService.createActivity(name, description, subCategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testCreateActivityEmptyName() {
        String name = "";
        String description = "Soccer for beginners";
        ClassCategory subCategory = ClassCategory.Cardio;
        int instructorId = 1;

        String error = null;
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);
        try {
            Activity createdActivity = activityService.createActivity(name, description, subCategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testCreateActivityNullDescription() {
        String name = "Soccer";
        String description = null;
        ClassCategory subCategory = ClassCategory.Cardio;
        int instructorId = 1;

        String error = null;
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);
        try {
            Activity createdActivity = activityService.createActivity(name, description, subCategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Description cannot be empty!", error);
    }

    @Test
    public void testCreateActivityEmptyDescription() {
        String name = "Soccer";
        String description = "";
        ClassCategory subCategory = ClassCategory.Cardio;
        int instructorId = 1;

        String error = null;
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);
        try {
            Activity createdActivity = activityService.createActivity(name, description, subCategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Description cannot be empty!", error);
    }

    @Test
    public void testCreateActivityNullSubCategory() {
        String name = "Soccer";
        String description = "Soccer for beginners";
        ClassCategory subCategory = null;
        int instructorId = 1;

        String error = null;
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);
        try {
            Activity createdActivity = activityService.createActivity(name, description, subCategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Subcategory cannot be empty!", error);
    }

    @Test
    public void testCreateActivityInstructorDoesNotExist() {///////////////////////////////////////
        String name = "Soccer";
        String description = "Soccer for beginners";
        ClassCategory subCategory = ClassCategory.Cardio;
        int instructorId = 1;

        String error = null;
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(false);// this
        // checks if the instructor exists, if not, it will return false which will
        // throw an exception
        try {
            Activity createdActivity = activityService.createActivity(name, description, subCategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor does not exist!", error);
    }

    @Test
    public void testCreateActivityAlreadyExists() {
        String name = "Soccer";
        String description = "Soccer for beginners";
        ClassCategory subCategory = ClassCategory.Cardio;
        int instructorId = 1;

        String error = null;
        when(activityRepository.findActivityByName(name)).thenReturn(new Activity());
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);// this checks if the
                                                                                          // instructor
        // exists, if not, it will return
        // false which will throw an
        // exception
        try {
            Activity createdActivity = activityService.createActivity(name, description, subCategory);
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

    @Test
    public void testUpdateActivity() {
        String name = "Soccer";
        String newName = "Soccer for beginners";
        String newDescription = "Soccer for beginners";
        ClassCategory newSubcategory = ClassCategory.Cardio;
        int instructorId = 1;

        when(activityRepository.findActivityByName(name)).thenReturn(new Activity());
        when(accountService.checkAccountHasInstructorRole(instructorId)).thenReturn(true);// this checks if the
                                                                                          // instructor
        // exists, if not, it will return
        // false which will throw an
        // exception
        when(activityRepository.save(any(Activity.class))).thenAnswer((invocation) -> {
            Activity activity = new Activity();
            activity.setName(newName);
            activity.setDescription(newDescription);
            activity.setSubCategory(newSubcategory);
            return activity;
        });

        Activity updatedActivity = activityService.updateActivity(name, newName, newDescription, newSubcategory);
        verify(activityRepository, times(1)).save(any(Activity.class));

        assertNotNull(updatedActivity);
        assertEquals(newName, updatedActivity.getName());
        assertEquals(newDescription, updatedActivity.getDescription());
        assertEquals(newSubcategory, updatedActivity.getSubCategory());
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
            Activity updatedActivity = activityService.updateActivity(name, newName, newDescription, newSubcategory);
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
            Activity updatedActivity = activityService.updateActivity(name, newName, newDescription, newSubcategory);
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
            Activity updatedActivity = activityService.updateActivity(name, newName, newDescription, newSubcategory);
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
            Activity updatedActivity = activityService.updateActivity(name, newName, newDescription, newSubcategory);
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
            Activity updatedActivity = activityService.updateActivity(name, newName, newDescription, newSubcategory);
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
            Activity updatedActivity = activityService.updateActivity(name, newName, newDescription, newSubcategory);
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
            Activity updatedActivity = activityService.updateActivity(name, newName, newDescription, newSubcategory);
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
