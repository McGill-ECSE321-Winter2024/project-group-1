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

@SpringBootTest
public class TestScheduledActivityService {
    @Mock
    private ScheduledActivityRepository scheduledActivityRepository;

    @InjectMocks
    private ScheduledActivityService scheduledActivityService;

    Instructor instructor = new Instructor();
    Activity activity = new Activity();

    @Test
    public void testCreateScheduledActivity(){
        int scheduledActivityId = 1;
        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = LocalTime.of(10, 0, 0);
        LocalTime endTime = LocalTime.of(12,0,0);
        //Instructor instructor = ; //How to initialize the variable.
        //Activity activity = ; //How to initialize the variable

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
        LocalDate date = LocalDate.of(2024, 3, 19);
        LocalTime startTime = LocalTime.of(10, 0, 0);
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

        assertEquals("Name cannot be empty!", error);
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

        assertEquals("Name cannot be empty!", error);
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

        assertEquals("Name cannot be empty!", error);
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

        assertEquals("Name cannot be empty!", error);
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

        assertEquals("Name cannot be empty!", error);
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

        assertEquals("Name cannot be empty!", error);
    }

    @Test
    public void testUpdateScheduledActivity(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = LocalTime.of(12,0,0);
        Instructor newInstructor = new Instructor(); //How to initialize the variable.
        Activity newActivity = new Activity(); //How to initialize the variable

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
        Instructor newInstructor = new Instructor(); //How to initialize the variable.
        Activity newActivity = new Activity(); //How to initialize the variable

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test 
    public void testUpdateScheduledActivityNullStartTime(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = null;
        LocalTime newEndTime = LocalTime.of(12,0,0);
        Instructor newInstructor = new Instructor(); //How to initialize the variable.
        Activity newActivity = new Activity(); //How to initialize the variable

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test 
    public void testUpdateScheduledActivityNullEndTime(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = null;
        Instructor newInstructor = new Instructor(); //How to initialize the variable.
        Activity newActivity = new Activity(); //How to initialize the variable

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test 
    public void testUpdateScheduledActivityNullInstructor(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = LocalTime.of(12,0,0);
        Instructor newInstructor = null; //How to initialize the variable.
        Activity newActivity = new Activity(); //How to initialize the variable

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test 
    public void testUpdateScheduledActivityNullActivity(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = LocalTime.of(12,0,0);
        Instructor newInstructor = new Instructor(); //How to initialize the variable.
        Activity newActivity = null; //How to initialize the variable

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
    }

    @Test 
    public void testUpdateScheduledActivityNullStartTimeAfterEndTime(){
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(20, 0, 0);
        LocalTime newEndTime = LocalTime.of(18,0,0);
        Instructor newInstructor = new Instructor(); //How to initialize the variable.
        Activity newActivity = new Activity(); //How to initialize the variable

        String error = null;
        //when(activityService.(instructorId)).thenReturn(true);//this checks if the instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.createScheduledActivity(scheduledActivityId, newDate, newStartTime, newEndTime, newInstructor, newActivity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Name cannot be empty!", error);
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

        assertEquals("Activity does not exist!", error);
    }

}
