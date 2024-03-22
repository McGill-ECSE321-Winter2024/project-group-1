package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when; // Add this import statement

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository; // Add this import statement
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Owner;

/**
 * @author Mathias Pacheco Lemina
 */
public class TestOwnerService {
    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private AccountManagementService ownerService;

    @InjectMocks
    private ActivityManagementService activityService;

    /*
     * getOwnerByAccountRoleId - TODO
     * checkAccountOwner - TODO
     * approveActivity - DONE
     * disapproveActivity - DONE
     * approveInstructor - DONE
     * disapproveInstructor - DONE
     */

    // TODO 1 - getOwnerByAccountRoleId
    @Test
    public void testGetOwnerByAccountRoleId() {
        String username = "testname";
        String password = "testpassword";

        ownerRepository.save(new Owner(new Account(username, password)));
        int id = 1;
        Owner owner = ownerService.getOwnerByAccountRoleId(id);

        try {
            owner = ownerService.getOwnerByAccountRoleId(id);
        } catch (IllegalArgumentException e) {
            assertEquals("AccountRoleId cannot be found!", e.getMessage()); // TODO: Find the exact message
        }

        assertNotNull(owner);
    }

    @Test
    public void testGetOwnerByAccountRoleIdZero() {
        int accountRoleId = 0;
        String error = null;
        Owner owner = null;

        try {
            owner = ownerService.getOwnerByAccountRoleId(accountRoleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("AccountRoleId cannot be negative!", error); // TODO: Modify the message
        assertEquals(null, owner);
    }

    @Test
    public void testGetOwnerByAccountRoleIdNegative() {
        int accountRoleId = -1;
        String error = null;
        Owner owner = null;

        try {
            owner = ownerService.getOwnerByAccountRoleId(accountRoleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("AccountRoleId cannot be negative!", error);
        assertEquals(null, owner);
    }

    // TODO 2 - checkAccountOwner
    @Test
    public void testCheckAccountOwner() {
        int id = 1;
        // ownerRepository.Owner o = new Owner();
        when(ownerRepository.findAccountRoleByAccountRoleId(id)).thenReturn(new Owner());
        assertTrue(ownerService.checkAccountHasOwnerRole(id));
    }

    @Test
    public void testCheckAccountOwnerNegative() {
        int id = -1;
        when(ownerRepository.findAccountRoleByAccountRoleId(id)).thenReturn(new Owner());
        assertTrue(ownerService.checkAccountHasOwnerRole(id));
    }

    @Test
    public void testCheckAccountOwnerZero() {
        int id = 0;
        when(ownerRepository.findAccountRoleByAccountRoleId(id)).thenReturn(new Owner());
        assertTrue(ownerService.checkAccountHasOwnerRole(id));
    }

    @Test
    public void testCheckAccountOwnerDoesNotExist() {
        int id = 1;
        when(ownerRepository.findAccountRoleByAccountRoleId(id)).thenReturn(null);
        assertFalse(ownerService.checkAccountHasOwnerRole(id));
    }

    // 3 - approveActivity
    @Test
    public void testApproveActivity() {
        String name = "activity";
        ClassCategory subcategory = ClassCategory.Strength;
        boolean isApproved = false;
        String description = "description";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityService.approveActivity(name);
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
            activityService.approveActivity(name);
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
            activityService.approveActivity(name);
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
            activityService.approveActivity(name);
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
            activityService.approveActivity(name);
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
            activityService.approveActivity(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity is already approved!", error);
    }

    // 4 - disapproveActivity
    @Test
    public void testDisapproveActivity() {
        String name = "activity";
        ClassCategory subcategory = ClassCategory.Strength;
        boolean isApproved = true;
        String description = "description";

        activityRepository.save(new Activity(subcategory, name, isApproved, description));

        try {
            activityService.disapproveActivity(name);
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
            activityService.disapproveActivity(name);
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
            activityService.disapproveActivity(name);
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
            activityService.disapproveActivity(name);
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
            activityService.disapproveActivity(name);
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
            activityService.disapproveActivity(name);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity does not exist!", error);
    }

    // 5 - approveInstructor
    @Test
    public void testApproveInstructor() {
        int id = 1;
        Instructor instructor = new Instructor();
        instructorRepository.save(instructor);

        try {
            ownerService.approveInstructor(id);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        assertEquals(Instructor.InstructorStatus.Active,
                instructorRepository.findAccountRoleByAccountRoleId(id).getStatus());
    }

    @Test
    public void testApproveInstructorNegative() {
        int id = -1;
        Instructor instructor = new Instructor();
        instructorRepository.save(instructor);
        String error = "";

        try {
            ownerService.approveInstructor(id);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor id cannot be empty!", error);
    }

    @Test
    public void testApproveInstructorDoesNotExist() {
        int id = 1;
        Instructor instructor = new Instructor();
        instructorRepository.save(instructor);
        String error = "";

        try {
            ownerService.approveInstructor(id);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor does not exist!", error);
    }

    @Test
    public void testApproveInstructorAlreadyActive() {
        int id = 1;
        Instructor instructor = new Instructor();
        instructor.setStatus(Instructor.InstructorStatus.Active);
        instructorRepository.save(instructor);
        String error = "";

        try {
            ownerService.approveInstructor(id);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor is already approved!", error);
    }

    // 6 - disapproveInstructor
    @Test
    public void testDisapproveInstructorToInactive() {
        int id = 1;
        Instructor instructor = new Instructor();
        instructorRepository.save(instructor);
        instructorRepository.findAccountRoleByAccountRoleId(id).setStatus(InstructorStatus.Active);

        try {
            ownerService.disapproveInstructor(id);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        assertEquals(Instructor.InstructorStatus.Inactive,
                instructorRepository.findAccountRoleByAccountRoleId(id).getStatus());
    }

    @Test
    public void testDisapproveInstructorToInactiveDuplicate() {
        int id = 1;
        Instructor instructor = new Instructor();
        String error = "";

        instructorRepository.save(instructor);
        instructorRepository.findAccountRoleByAccountRoleId(id).setStatus(InstructorStatus.Inactive);

        try {
            ownerService.disapproveInstructor(id);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor is already not approved!", error);
    }

    @Test
    public void testDisapproveInstructorToFiredDuplicate() {
        int id = 1;
        Instructor instructor = new Instructor();
        String error = "";

        instructorRepository.save(instructor);
        instructorRepository.findAccountRoleByAccountRoleId(id).setStatus(InstructorStatus.Inactive);
        try {
            ownerService.disapproveInstructor(id);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor is already fired!", error);
    }

    @Test
    public void testDisapproveInstructorDoesntExist() {
        int id = 1;
        String error = "";

        // instructorRepository.save(instructor);

        try {
            ownerService.disapproveInstructor(id);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor does not exist!", error);
    }
}
