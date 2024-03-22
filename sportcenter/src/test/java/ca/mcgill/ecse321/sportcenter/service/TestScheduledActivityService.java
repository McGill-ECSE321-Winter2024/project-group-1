package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;

import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;

import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;

@SpringBootTest
public class TestScheduledActivityService {
    @Mock
    private ScheduledActivityRepository scheduledActivityRepository;
    private AccountRepository accountRepository;
    private InstructorRepository instructorRepository;
    private ActivityRepository activityRepository;

    @InjectMocks
    private ScheduledActivityManagementService scheduledActivityService;

    private int oldAccountRoleId = 1;
    private int newAccountRoleId = 2;

    private String oldActivityName;
    private String newActivityName;

    @BeforeEach
    public void setInstructorAndActivity() {
        Account oldAccount = new Account();
        oldAccount.setUsername("Person1");
        oldAccount.setPassword("Password1");
        accountRepository.save(oldAccount);

        Account newAccount = new Account();
        newAccount.setUsername("Person2");
        newAccount.setPassword("Password2");
        accountRepository.save(newAccount);

        Instructor oldInstructor = instructorRepository.findAccountRoleByAccountRoleId(oldAccountRoleId);
        oldInstructor.setAccount(oldAccount);
        instructorRepository.save(oldInstructor);

        Instructor newInstructor = instructorRepository.findAccountRoleByAccountRoleId(newAccountRoleId);
        newInstructor.setAccount(newAccount);
        instructorRepository.save(newInstructor);

        Activity oldActivity = new Activity();
        oldActivity.setName("Cardio1");
        oldActivity.setDescription("Cardio2");
        ClassCategory oldSubcategory = ClassCategory.Cardio;
        oldActivity.setSubCategory(oldSubcategory);
        activityRepository.save(oldActivity);

        Activity newActivity = new Activity();
        newActivity.setName("Cardio2");
        newActivity.setDescription("Cardio3");
        ClassCategory newSubcategory = ClassCategory.Strength;
        newActivity.setSubCategory(newSubcategory);
        activityRepository.save(newActivity);
    }

    /**
     * Deletes all the data after each test
     */
    @AfterEach
    public void clearDatabase() {
        scheduledActivityRepository.deleteAll();
        accountRepository.deleteAll();
        instructorRepository.deleteAll();
        activityRepository.deleteAll();
    }

    /**
     * Test for creating a scheduled activity
     * 
     * @result A new scheduled activity is created
     */
    @Test
    public void testCreateScheduledActivity() {

        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = LocalTime.of(10, 0, 0);
        LocalTime endTime = LocalTime.of(12, 0, 0);
        int capacity = 10;

        int scheduledActivityId = 1;

        int oldAccountRoleId = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getSupervisor().getAccountRoleId();
        String oldActivityName = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getActivity().getName();

        when(scheduledActivityRepository.save(any(ScheduledActivity.class))).thenAnswer((invocation) -> {
            ScheduledActivity scheduledActivity = new ScheduledActivity();
            scheduledActivity.setDate(date);
            scheduledActivity.setStartTime(startTime);
            scheduledActivity.setEndTime(endTime);
            scheduledActivity.setSupervisor(instructorRepository.findAccountRoleByAccountRoleId(oldAccountRoleId));
            scheduledActivity.setActivity(activityRepository.findActivityByName(oldActivityName));
            scheduledActivity.setCapacity(capacity);
            return scheduledActivity;
        });

        ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(date, startTime,
                endTime, oldAccountRoleId, oldActivityName, capacity);
        verify(scheduledActivityRepository, times(1)).save(any(ScheduledActivity.class));

        assertNotNull(createdScheduledActivity);
        assertEquals(date, createdScheduledActivity.getDate());
        assertEquals(startTime, createdScheduledActivity.getStartTime());
        assertEquals(endTime, createdScheduledActivity.getEndTime());
        assertEquals(oldAccountRoleId, createdScheduledActivity.getSupervisor().getAccountRoleId());
        assertEquals(oldActivityName, createdScheduledActivity.getActivity().getName());
        assertEquals(capacity, createdScheduledActivity.getCapacity());
    }

    /**
     * Test for creating a scheduled activity with a null date
     * 
     * @result An exception is thrown
     */
    @Test
    public void testCreateScheduledActivityNullDate() {
        LocalDate date = null;
        LocalTime startTime = LocalTime.of(10, 0, 0);
        LocalTime endTime = LocalTime.of(12, 0, 0);
        int capacity = 10;

        int scheduledActivityId = 1;

        int oldAccountRoleId = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getSupervisor().getAccountRoleId();
        String oldActivityName = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getActivity().getName();

        String error = null;
        try {
            ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(date,
                    startTime, endTime, oldAccountRoleId, oldActivityName, capacity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Date cannot be empty!", error);
    }

    @Test
    public void testCreateScheduledActivityNullStartTime() {
        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = null;
        LocalTime endTime = LocalTime.of(12, 0, 0);
        int capacity = 10;

        int scheduledActivityId = 1;

        int oldAccountRoleId = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getSupervisor().getAccountRoleId();
        String oldActivityName = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getActivity().getName();

        String error = null;
        try {
            ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(date,
                    startTime, endTime, oldAccountRoleId, oldActivityName, capacity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be empty!", error);
    }

    @Test
    public void testCreateScheduledActivityNullEndTime() {
        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = LocalTime.of(10, 0, 0);
        LocalTime endTime = null;
        int capacity = 10;

        int scheduledActivityId = 1;

        int oldAccountRoleId = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getSupervisor().getAccountRoleId();
        String oldActivityName = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getActivity().getName();

        String error = null;
        // when(activityService.(instructorId)).thenReturn(true);//this checks if the
        // instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(date,
                    startTime, endTime, oldAccountRoleId, oldActivityName, capacity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("End time cannot be empty!", error);
    }

    @Test
    public void testCreateScheduledActivityNullInstructor() {
        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = LocalTime.of(10, 0, 0);
        LocalTime endTime = LocalTime.of(12, 0, 0);
        int capacity = 10;

        int oldAccountRoleId = -1;

        String error = null;
        try {
            ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(date,
                    startTime, endTime, oldAccountRoleId, oldActivityName, capacity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor cannot be empty!", error);
    }

    /**
     * Test for creating a scheduled activity with a null activity
     * 
     * @result An exception is thrown
     */
    @Test
    public void testCreateScheduledActivityNullActivity() {
        int scheduledActivityId = 1;
        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = LocalTime.of(10, 0, 0);
        LocalTime endTime = LocalTime.of(12, 0, 0);
        int capacity = 10;

        int oldAccountRoleId = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getSupervisor().getAccountRoleId();

        String oldActivityName = null;

        String error = null;
        try {
            ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(date,
                    startTime, endTime, oldAccountRoleId, oldActivityName, capacity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity cannot be empty!", error);
    }

    @Test
    public void testCreateScheduledActivityStartTimeAfterEndTime() {
        int scheduledActivityId = 1;
        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = LocalTime.of(12, 0, 0);
        LocalTime endTime = LocalTime.of(10, 0, 0);
        int capacity = 10;

        int oldAccountRoleId = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getSupervisor().getAccountRoleId();
        String oldActivityName = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getActivity().getName();

        String error = null;
        try {
            ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(date,
                    startTime, endTime, oldAccountRoleId, oldActivityName, capacity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be after end time!", error);
    }

    @Test
    public void testUpdateScheduledActivityDateAndTime() {
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = LocalTime.of(12, 0, 0);
        // int newCapacity = 20;

        int oldScheduledActivityId = 1;

        LocalDate oldDate = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(oldScheduledActivityId)
                .getDate();
        LocalTime oldStartTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(oldScheduledActivityId)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(oldScheduledActivityId)
                .getEndTime();

        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(oldScheduledActivityId))
                .thenReturn(null);// this checks if the activity already exists, and if it does, it will return
                                  // null

        when(scheduledActivityRepository.save(any(ScheduledActivity.class))).thenAnswer((invocation) -> {
            ScheduledActivity scheduledActivity = new ScheduledActivity();
            scheduledActivity.setDate(newDate);
            scheduledActivity.setStartTime(newStartTime);
            scheduledActivity.setEndTime(newEndTime);
            scheduledActivity.setSupervisor(instructorRepository.findAccountRoleByAccountRoleId(newAccountRoleId));
            scheduledActivity.setActivity(activityRepository.findActivityByName(newActivityName));
            return scheduledActivity;
        });

        ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityByDateAndTime(
                oldScheduledActivityId, newDate, oldDate, newStartTime, oldStartTime, newEndTime, oldEndTime);
        verify(scheduledActivityRepository, times(1)).save(any(ScheduledActivity.class));

        assertNotNull(updatedScheduledActivity);
        assertEquals(newDate, updatedScheduledActivity.getDate());
        assertEquals(newStartTime, updatedScheduledActivity.getStartTime());
        assertEquals(newEndTime, updatedScheduledActivity.getEndTime());
    }

    @Test
    public void testUpdateScheduledActivityInstructor() {
        int oldScheduledActivityId = 1;
        int newScheduledActivityId = 2;

        int oldAccountRoleId = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(oldScheduledActivityId)
                .getSupervisor().getAccountRoleId();

        int newAccountRoleId = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(newScheduledActivityId)
                .getSupervisor().getAccountRoleId();

        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(oldScheduledActivityId))
                .thenReturn(null);// this checks if the activity already exists, and if it does, it will return
                                  // null

        when(scheduledActivityRepository.save(any(ScheduledActivity.class))).thenAnswer((invocation) -> {
            ScheduledActivity scheduledActivity = new ScheduledActivity();
            scheduledActivity.setSupervisor(instructorRepository.findAccountRoleByAccountRoleId(newAccountRoleId));
            return scheduledActivity;
        });

        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(oldScheduledActivityId))
                .thenReturn(new ScheduledActivity());

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityInstructor(
                    oldScheduledActivityId, newAccountRoleId, oldAccountRoleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testUpdateScheduledActivityInvalidDate() {
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2010, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = LocalTime.of(12, 0, 0);
        int newCapacity = 20;

        LocalDate oldDate = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getDate();
        LocalTime oldStartTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getEndTime();

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityByDateAndTime(
                    scheduledActivityId, newDate, oldDate, newStartTime, oldStartTime, newEndTime, oldEndTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Date cannot be before the current date!", error);
    }

    @Test
    public void testUpdateScheduledActivityNullStartTime() {
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = null;
        LocalTime newEndTime = LocalTime.of(12, 0, 0);
        int newCapacity = 20;

        LocalDate oldDate = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getDate();
        LocalTime oldStartTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getEndTime();

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityByDateAndTime(
                    scheduledActivityId, newDate, oldDate, newStartTime, oldStartTime, newEndTime, oldEndTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be empty!", error);
    }

    @Test
    public void testUpdateScheduledActivityNullEndTime() {
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = null;

        LocalDate oldDate = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getDate();
        LocalTime oldStartTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getEndTime();

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityByDateAndTime(
                    scheduledActivityId, newDate, oldDate, newStartTime, oldStartTime, newEndTime, oldEndTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("End time cannot be empty!", error);
    }

    @Test
    public void testUpdateScheduledActivityNullInstructor() {
        int oldScheduledActivityId = 1;
        int newScheduledActivityId = 2;

        int oldAccountRoleId = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(oldScheduledActivityId)
                .getSupervisor().getAccountRoleId();
        int newAccountRoleId = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(newScheduledActivityId)
                .getSupervisor().getAccountRoleId();

        String error = null;
        // when(activityService.(instructorId)).thenReturn(true);//this checks if the
        // instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityInstructor(
                    oldScheduledActivityId, newAccountRoleId, oldAccountRoleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor cannot be empty!", error);
    }

    @Test
    public void testUpdateScheduledActivityNullActivity() {
        int oldScheduledActivityId = 1;
        int newScheduledActivityId = 2;

        String oldActivityName = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(oldScheduledActivityId)
                .getActivity().getName();
        String newActivityName = null;

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityActivity(
                    oldScheduledActivityId, newActivityName, oldActivityName);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Scheduled Activity cannot be empty!", error);
    }

    @Test
    public void testUpdateScheduledActivityNullStartTimeAfterEndTime() {
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(20, 0, 0);
        LocalTime newEndTime = LocalTime.of(18, 0, 0);

        LocalTime oldStartTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getEndTime();
        LocalDate oldDate = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getDate();

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityByDateAndTime(
                    scheduledActivityId, newDate, oldDate, newStartTime, oldStartTime, newEndTime, oldEndTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be after end time!", error);
    }

    @Test
    public void testUpdateScheduledActivityCapacity() {
        int scheduledActivityId = 1;
        int newCapacity = 20;

        int oldCapacity = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getCapacity();

        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId))
                .thenReturn(null);// this checks if the activity already exists, and if it does, it will return
                                  // null

        when(scheduledActivityRepository.save(any(ScheduledActivity.class))).thenAnswer((invocation) -> {
            ScheduledActivity scheduledActivity = new ScheduledActivity();
            scheduledActivity.setCapacity(newCapacity);
            return scheduledActivity;
        });

        ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityCapacity(
                scheduledActivityId, newCapacity, oldCapacity);
        verify(scheduledActivityRepository, times(1)).save(any(ScheduledActivity.class));

        assertNotNull(updatedScheduledActivity);
        assertEquals(newCapacity, updatedScheduledActivity.getCapacity());
    }

    @Test
    public void testUpdateScheduledActivityInvalidCapacity() {
        int scheduledActivityId = 1;
        int newCapacity = -20;

        int oldCapacity = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getCapacity();

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityCapacity(
                    scheduledActivityId, newCapacity, oldCapacity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Capacity cannot be negative!", error);
    }

    @Test
    public void testDeleteScheduledActivity() {
        int scheduledActivityId = 1;

        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId))
                .thenReturn(new ScheduledActivity());
        // when(scheduledActivityRepository.checkAccountInstructor(instructorId)).thenReturn(true);//this
        // checks if the instructor exists, if not, it will return false which will
        // throw an exception

        scheduledActivityService.deleteScheduledActivity(scheduledActivityId);
        verify(scheduledActivityRepository, times(1)).delete(any(ScheduledActivity.class));

        // assertNotNull(deletedScheduledActivity());
        // assertEquals(scheduledActivityId,
        // deletedScheduledActivity.getScheduledActivityId());
    }

    @Test
    public void testDeleteScheduledActivityInvalidId() {
        int scheduledActivityId = -1;

        String error = null;
        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId))
                .thenReturn(null);
        try {
            scheduledActivityService.deleteScheduledActivity(scheduledActivityId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Scheduled Activity does not exist!", error);
    }

}
