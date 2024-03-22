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

@SpringBootTest
public class TestActivityManagementService {
    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private ActivityManagementService activityManagementService;

    // TEST #1 - CREATE ACTIVITY
    @Test
    public void testCreateActivity() {
        String name = "activity";
        ClassCategory subcategory = ClassCategory.Strength;
        String description = "description";
        boolean isApproved = false;

        when(activityRepository.findActivityByName(name)).thenReturn(null);// this checks if the activity already
                                                                           // exists, and if it does, it will return
                                                                           // null
        when(activityRepository.save(any(Activity.class))).thenAnswer((invocation) -> {
            Activity activity = new Activity();
            activity.setName(name);
            activity.setDescription(description);
            activity.setSubCategory(subcategory);
            activity.setIsApproved(isApproved);
            return (Activity) activity; // Add type cast here
        });

        Activity createdActivity = activityManagementService.createActivity(name, description, subcategory);
        verify(activityRepository, times(1)).save(any(Activity.class));

        assertNotNull(createdActivity);
        assertEquals(name, createdActivity.getName());
        assertEquals(description, createdActivity.getDescription());
        assertEquals(subcategory, createdActivity.getSubCategory());
        assertEquals(isApproved, createdActivity.getIsApproved());
    }

    @Test
    public void testCreateActivityNameNull() {
        String name = null;
        ClassCategory subcategory = ClassCategory.Strength;
        String description = "description";
        String error = "";

        try {
            activityManagementService.createActivity(name, description, subcategory);
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
            activityManagementService.createActivity(name, description, subcategory);
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
            activityManagementService.createActivity(name, description, subcategory);
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
            activityManagementService.createActivity(name, description, subcategory);
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
            activityManagementService.createActivity(name, description, subcategory);
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
            activityManagementService.createActivity(name, description, subcategory);
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
            activityManagementService.createActivity(name, description, subcategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Subcategory cannot be empty!", error);
    }

    @Test
    public void testCreateActivityAlreadyExists() {
        String name = "activity";
        ClassCategory subcategory = ClassCategory.Strength;
        String description = "description";
        String error = "";

        when(activityRepository.findActivityByName(name))
                .thenReturn(new Activity(subcategory, name, false, description));

        activityManagementService.createActivity(name, description, subcategory);

        try {
            activityManagementService.createActivity(name, description, subcategory);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity already exists!", error);
    }

    // TEST #2 - UPDATE ACTIVITY
    @Test
    public void testUpdateActivity() {
        // TODO - COMPLETE BAREBONE
    }

    @Test
    public void testProposeActivityNameEmpty() {
        // TODO
    }

    @Test
    public void testProposeActivityNameWhitespace() {
        // TODO
    }

    @Test
    public void testProposeActivityDescriptionNull() {
        // TODO
    }

    @Test
    public void testProposeActivityDescriptionEmpty() {
        // TODO
    }

    @Test
    public void testProposeActivityDescriptionWhitespace() {
        // TODO
    }

    @Test
    public void testProposeActivitySubcategoryNull() {
        // TODO - TBD
    }

    // TEST #4 - APPROVE ACTIVITY
    @Test
    public void testApproveActivity() {
        String name = "activity";
        ClassCategory subcategory = ClassCategory.Strength;
        boolean isApproved = false;
        String description = "description";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityManagementService.approveActivity(name);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        assertTrue(activityRepository.findActivityByName(name).getIsApproved());
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
        String name = "activity";
        ClassCategory subcategory = ClassCategory.Strength;
        boolean isApproved = true;
        String description = "description";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityManagementService.disapproveActivity(name);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        assertFalse(activityRepository.findActivityByName(name).getIsApproved());
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
