package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.config.ScheduledTaskHolder;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.service.ActivityService;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;

import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;
import ca.mcgill.ecse321.sportcenter.service.ScheduledActivityService;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.service.InstructorService;
import jakarta.persistence.criteria.CriteriaBuilder.In;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;

@SpringBootTest
public class TestScheduledActivityService {
    @Mock
    private ScheduledActivityRepository scheduledActivityRepository;
    private AccountRepository accountRepository;
    private InstructorRepository instructorRepository;
    private ActivityRepository activityRepository;

    @InjectMocks
    private ScheduledActivityService scheduledActivityService;
    //private AccountService accountService;
    //private InstructorService instructorService;
    //private ActivityService activityService;

    private int instructorId;
    private Instructor instructor;
    private Instructor newInstructor;
    
    private String activityName;
    private Activity activity;
    private Activity newActivity;

    @BeforeEach
    public void setInstructorAndActivity(){
        Account account = new Account();
        account.setUsername("Person1");
        account.setPassword("Password1");
        accountRepository.save(account);

        instructor = new Instructor();
        instructor.setAccount(account);
        instructorRepository.save(instructor);

        instructorId = instructor.getAccountRoleId();
        instructor = instructorRepository.findAccountRoleByAccountRoleId(instructorId);
        
        activity = new Activity();
        activity.setName("Cardio1");
        activity.setDescription("Cardio2");
        ClassCategory subcategory = ClassCategory.Cardio;
        activity.setSubCategory(subcategory);
        activityRepository.save(activity);
        activityName = activity.getName();
        activity = activityRepository.findActivityByName(activityName);
    }

    @Test
    public void testCreateScheduledActivity(){
        int scheduledActivityId = 1;
        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = LocalTime.of(10, 0, 0);
        LocalTime endTime = LocalTime.of(12,0,0);

        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)).thenReturn(null);//this checks if the activity already exists, and if it does, it will return null
        //when(scheduledActivityRepository(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        when(scheduledActivityRepository.save(any(ScheduledActivity.class))).thenAnswer( (invocation) -> {
            ScheduledActivity scheduledActivity = new ScheduledActivity();
            scheduledActivity.setScheduledActivityId(scheduledActivityId);
            scheduledActivity.setDate(date);
            scheduledActivity.setStartTime(startTime);
            scheduledActivity.setEndTime(endTime);
            scheduledActivity.setSupervisor(instructor);
            scheduledActivity.setActivity(activity);
            return scheduledActivity;
        });

        ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, date, startTime, endTime, instructor, activity);
        verify(scheduledActivityRepository, times(1)).save(any(ScheduledActivity.class));

        assertNotNull(createdScheduledActivity);
        assertEquals(scheduledActivityId, createdScheduledActivity.getScheduledActivityId());
        assertEquals(date, createdScheduledActivity.getDate());
        assertEquals(startTime, createdScheduledActivity.getStartTime());
        assertEquals(endTime, createdScheduledActivity.getEndTime());
        assertEquals(instructor, createdScheduledActivity.getSupervisor());
        assertEquals(activity, createdScheduledActivity.getActivity());
    }

    @Test
    public void testCreateScheduledActivityNullDate() {
        int scheduledActivityId = 1;
        LocalDate date = null;
        LocalTime startTime = LocalTime.of(10, 0, 0);
        LocalTime endTime = LocalTime.of(12,0,0);
        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, date, startTime, endTime, instructor, activity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Date cannot be empty!", error);
    }

    @Test
    public void testCreateScheduledActivityNullStartTime() {
        int scheduledActivityId = 1;
        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = null;
        LocalTime endTime = LocalTime.of(12,0,0);
        //Instructor instructor = ; //How to initialize the variable.
        //Activity activity = ; //How to initialize the variable

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, date, startTime, endTime, instructor, activity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be empty!", error);
    }

    @Test
    public void testCreateScheduledActivityNullEndTime() {
        int scheduledActivityId = 1;
        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = LocalTime.of(10, 0, 0);
        LocalTime endTime = null;
        //Instructor instructor = ; //How to initialize the variable.
        //Activity activity = ; //How to initialize the variable

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, date, startTime, endTime, instructor, activity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("End time cannot be empty!", error);
    }

    @Test
    public void testCreateScheduledActivityNullInstructor() {
        int scheduledActivityId = 1;
        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = LocalTime.of(10, 0, 0);
        LocalTime endTime = LocalTime.of(12,0,0);
        Instructor instructor = null; //How to initialize the variable.
        //Activity activity = ; //How to initialize the variable

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, date, startTime, endTime, instructor, activity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor cannot be empty!", error);
    }

    @Test
    public void testCreateScheduledActivityNullActivity() {
        int scheduledActivityId = 1;
        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = LocalTime.of(10, 0, 0);
        LocalTime endTime = LocalTime.of(12,0,0);
        //Instructor instructor = null; //How to initialize the variable.
        Activity activity = null; //How to initialize the variable

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, date, startTime, endTime, instructor, activity);
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
        LocalTime endTime = LocalTime.of(10,0,0);
        //Instructor instructor = null; //How to initialize the variable.
        //Activity activity = null; //How to initialize the variable

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity createdScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, date, startTime, endTime, instructor, activity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be after end time!", error);
    }

    @Test
    public void testUpdateScheduledActivity(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = LocalTime.of(12,0,0);

        //#region Creating new instructor and activity

        Account newAccount = new Account();
        newAccount.setUsername("Person2");
        newAccount.setPassword("Password3");
        accountRepository.save(newAccount);

        newInstructor = new Instructor();
        newInstructor.setAccount(newAccount);

        int newInstructorId;
        newInstructorId = newInstructor.getAccountRoleId();
        instructorRepository.save(newInstructor);
        newInstructor = instructorRepository.findAccountRoleByAccountRoleId(newInstructorId);

        newActivity = new Activity(); //How to initialize the variable
        newActivity.setName("Cardio2");
        newActivity.setDescription("Cardio3");
        ClassCategory subcategory = ClassCategory.Strength;
        newActivity.setSubCategory(subcategory);
        activityRepository.save(newActivity);
        String newActivityName;
        newActivityName = newActivity.getName();
        newActivity = activityRepository.findActivityByName(newActivityName);

        //#endregion

        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)).thenReturn(null);//this checks if the activity already exists, and if it does, it will return null
        //when(scheduledActivityRepository(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        when(scheduledActivityRepository.save(any(ScheduledActivity.class))).thenAnswer( (invocation) -> {
            ScheduledActivity scheduledActivity = new ScheduledActivity();
            scheduledActivity.setScheduledActivityId(scheduledActivityId);
            scheduledActivity.setDate(newDate);
            scheduledActivity.setStartTime(newStartTime);
            scheduledActivity.setEndTime(newEndTime);
            scheduledActivity.setSupervisor(newInstructor);
            scheduledActivity.setActivity(newActivity);
            return scheduledActivity;
        });

        ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        verify(scheduledActivityRepository, times(1)).save(any(ScheduledActivity.class));

        assertNotNull(updatedScheduledActivity);
        assertEquals(scheduledActivityId, updatedScheduledActivity.getScheduledActivityId());
        assertEquals(newDate, updatedScheduledActivity.getDate());
        assertEquals(newStartTime, updatedScheduledActivity.getStartTime());
        assertEquals(newEndTime, updatedScheduledActivity.getEndTime());
        assertEquals(newInstructor, updatedScheduledActivity.getSupervisor());
        assertEquals(newActivity, updatedScheduledActivity.getActivity());
    }

    @Test 
    public void testUpdateScheduledActivityNullDate(){
        int scheduledActivityId = 1;
        LocalDate newDate = null;
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = LocalTime.of(12,0,0);
        
        //#region Creating new instructor and activity

        Account newAccount = new Account();
        newAccount.setUsername("Person2");
        newAccount.setPassword("Password3");
        accountRepository.save(newAccount);

        newInstructor = new Instructor();
        newInstructor.setAccount(newAccount);

        int newInstructorId;
        newInstructorId = newInstructor.getAccountRoleId();
        instructorRepository.save(newInstructor);
        newInstructor = instructorRepository.findAccountRoleByAccountRoleId(newInstructorId);

        newActivity = new Activity(); //How to initialize the variable
        newActivity.setName("Cardio2");
        newActivity.setDescription("Cardio3");
        ClassCategory subcategory = ClassCategory.Strength;
        newActivity.setSubCategory(subcategory);
        activityRepository.save(newActivity);
        String newActivityName;
        newActivityName = newActivity.getName();
        newActivity = activityRepository.findActivityByName(newActivityName);

        //#endregion

        String error = null;
        //when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)).thenReturn(null);
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testUpdateScheduledActivityInvalidDate(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2010, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = LocalTime.of(12,0,0);

        //#region Creating new instructor and activity

        Account newAccount = new Account();
        newAccount.setUsername("Person2");
        newAccount.setPassword("Password3");
        accountRepository.save(newAccount);

        newInstructor = new Instructor();
        newInstructor.setAccount(newAccount);

        int newInstructorId;
        newInstructorId = newInstructor.getAccountRoleId();
        instructorRepository.save(newInstructor);
        newInstructor = instructorRepository.findAccountRoleByAccountRoleId(newInstructorId);

        newActivity = new Activity(); //How to initialize the variable
        newActivity.setName("Cardio2");
        newActivity.setDescription("Cardio3");
        ClassCategory subcategory = ClassCategory.Strength;
        newActivity.setSubCategory(subcategory);
        activityRepository.save(newActivity);
        String newActivityName;
        newActivityName = newActivity.getName();
        newActivity = activityRepository.findActivityByName(newActivityName);

        //#endregion

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Date cannot be before the current date!", error);
    } 

    @Test 
    public void testUpdateScheduledActivityNullStartTime(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = null;
        LocalTime newEndTime = LocalTime.of(12,0,0);
        //#region Creating new instructor and activity

        Account newAccount = new Account();
        newAccount.setUsername("Person2");
        newAccount.setPassword("Password3");
        accountRepository.save(newAccount);

        newInstructor = new Instructor();
        newInstructor.setAccount(newAccount);

        int newInstructorId;
        newInstructorId = newInstructor.getAccountRoleId();
        instructorRepository.save(newInstructor);
        newInstructor = instructorRepository.findAccountRoleByAccountRoleId(newInstructorId);

        newActivity = new Activity(); //How to initialize the variable
        newActivity.setName("Cardio2");
        newActivity.setDescription("Cardio3");
        ClassCategory subcategory = ClassCategory.Strength;
        newActivity.setSubCategory(subcategory);
        activityRepository.save(newActivity);
        String newActivityName;
        newActivityName = newActivity.getName();
        newActivity = activityRepository.findActivityByName(newActivityName);

        //#endregion

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be empty!", error);
    }

    @Test 
    public void testUpdateScheduledActivityNullEndTime(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = null;
        //#region Creating new instructor and activity

        Account newAccount = new Account();
        newAccount.setUsername("Person2");
        newAccount.setPassword("Password3");
        accountRepository.save(newAccount);

        newInstructor = new Instructor();
        newInstructor.setAccount(newAccount);

        int newInstructorId;
        newInstructorId = newInstructor.getAccountRoleId();
        instructorRepository.save(newInstructor);
        newInstructor = instructorRepository.findAccountRoleByAccountRoleId(newInstructorId);

        newActivity = new Activity(); //How to initialize the variable
        newActivity.setName("Cardio2");
        newActivity.setDescription("Cardio3");
        ClassCategory subcategory = ClassCategory.Strength;
        newActivity.setSubCategory(subcategory);
        activityRepository.save(newActivity);
        String newActivityName;
        newActivityName = newActivity.getName();
        newActivity = activityRepository.findActivityByName(newActivityName);

        //#endregion

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("End time cannot be empty!", error);
    }

    @Test 
    public void testUpdateScheduledActivityNullInstructor(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = LocalTime.of(12,0,0);
        Instructor newInstructor = null; //How to initialize the variable.
        //#region Creating new instructor and activity

        /* 
        Account newAccount = new Account();
        newAccount.setUsername("Person2");
        newAccount.setPassword("Password3");
        accountRepository.save(newAccount);

        newInstructor = new Instructor();
        newInstructor.setAccount(newAccount);

        int newInstructorId;
        newInstructorId = newInstructor.getAccountRoleId();
        instructorRepository.save(newInstructor);
        newInstructor = instructorRepository.findAccountRoleByAccountRoleId(newInstructorId);

        */
        newActivity = new Activity(); //How to initialize the variable
        newActivity.setName("Cardio2");
        newActivity.setDescription("Cardio3");
        ClassCategory subcategory = ClassCategory.Strength;
        newActivity.setSubCategory(subcategory);
        activityRepository.save(newActivity);
        String newActivityName;
        newActivityName = newActivity.getName();
        newActivity = activityRepository.findActivityByName(newActivityName);

        //#endregion

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor cannot be empty!", error);
    }

    @Test 
    public void testUpdateScheduledActivityNullActivity(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = LocalTime.of(12,0,0);
        Activity newActivity = null;
        //#region Creating new instructor and activity

        Account newAccount = new Account();
        newAccount.setUsername("Person2");
        newAccount.setPassword("Password3");
        accountRepository.save(newAccount);

        newInstructor = new Instructor();
        newInstructor.setAccount(newAccount);

        int newInstructorId;
        newInstructorId = newInstructor.getAccountRoleId();
        instructorRepository.save(newInstructor);
        newInstructor = instructorRepository.findAccountRoleByAccountRoleId(newInstructorId);

        /*
        newActivity = new Activity(); //How to initialize the variable
        newActivity.setName("Cardio2");
        newActivity.setDescription("Cardio3");
        ClassCategory subcategory = ClassCategory.Strength;
        newActivity.setSubCategory(subcategory);
        activityRepository.save(newActivity);
        String newActivityName;
        newActivityName = newActivity.getName();
        newActivity = activityRepository.findActivityByName(newActivityName);
        */

        //#endregion

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity cannot be empty!", error);
    }

    @Test 
    public void testUpdateScheduledActivityNullStartTimeAfterEndTime(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(20, 0, 0);
        LocalTime newEndTime = LocalTime.of(18,0,0);
        //#region Creating new instructor and activity

        Account newAccount = new Account();
        newAccount.setUsername("Person2");
        newAccount.setPassword("Password3");
        accountRepository.save(newAccount);

        newInstructor = new Instructor();
        newInstructor.setAccount(newAccount);

        int newInstructorId;
        newInstructorId = newInstructor.getAccountRoleId();
        instructorRepository.save(newInstructor);
        newInstructor = instructorRepository.findAccountRoleByAccountRoleId(newInstructorId);

        newActivity = new Activity(); //How to initialize the variable
        newActivity.setName("Cardio2");
        newActivity.setDescription("Cardio3");
        ClassCategory subcategory = ClassCategory.Strength;
        newActivity.setSubCategory(subcategory);
        activityRepository.save(newActivity);
        String newActivityName;
        newActivityName = newActivity.getName();
        newActivity = activityRepository.findActivityByName(newActivityName);

        //#endregion

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be after end time!", error);
    }

    @Test
    public void testDeleteScheduledActivity() {
        int scheduledActivityId = 1;

        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)).thenReturn(new ScheduledActivity());
        //when(scheduledActivityRepository.checkAccountInstructor(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception

        ScheduledActivity deletedScheduledActivity = scheduledActivityService.deleteScheduledActivity(scheduledActivityId);
        verify(scheduledActivityRepository, times(1)).delete(any(ScheduledActivity.class));

        assertNotNull(deletedScheduledActivity);
        assertEquals(scheduledActivityId, deletedScheduledActivity.getScheduledActivityId());
    }

    @Test
    public void testDeleteScheduledActivityInvalidId() {
        int scheduledActivityId = -1;

        String error = null;
        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)).thenReturn(null);
        //when(activityService.checkAccountInstructor(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity deletedScheduledActivity = scheduledActivityService.deleteScheduledActivity(scheduledActivityId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Scheduled Activity does not exist!", error);
    }

}
