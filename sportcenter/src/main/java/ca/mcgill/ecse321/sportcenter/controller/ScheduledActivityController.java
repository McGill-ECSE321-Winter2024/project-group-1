package ca.mcgill.ecse321.sportcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.sportcenter.dto.*;
import ca.mcgill.ecse321.sportcenter.model.*;
import ca.mcgill.ecse321.sportcenter.service.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Time;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.ArrayList;

import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.service.RegistrationService;

import ca.mcgill.ecse321.sportcenter.service.ScheduledActivityService;

/**
 * Rest controller for the ScheduledActivity entity
 * 
 * @author Fabian Saldana
 */
@CrossOrigin(origins = "*")
@RestController
public class ScheduledActivityController {

    @Autowired
    private ScheduledActivityService scheduledActivityService;

    /**
     * Create a scheduled activity where only the instructor can create it
     * 
     * @param scheduledActivityId
     * @param date
     * @param startTime
     * @param endTime
     * @param instructorId
     * @param activityName
     * @param capacity
     * @return ScheduledActivityDto
     */
    @PostMapping(value = {
            "/createScheduledActivity/{date}/{startTime}/{endTime}/{instructorId}/{activityName}/{capacity}" })
    public ScheduledActivityDto createScheduledActivity(@PathVariable("date") LocalDate date,
            @PathVariable("startTime") LocalTime startTime, @PathVariable("endTime") LocalTime endTime,
            @PathVariable("instructorId") int instructorId, @PathVariable("activityName") String activityName,
            @PathVariable("capacity") int capacity) throws IllegalArgumentException {
        ScheduledActivity scheduledActivity = scheduledActivityService.createScheduledActivity(date, startTime, endTime,
                instructorId, activityName, capacity);
        return convertToDto(scheduledActivity);
    }

    /**
     * Get a scheduled activity by its id
     * 
     * @param scheduledActivityId
     * @return ScheduledActivityDto
     */
    @GetMapping(value = { "/scheduledActivity/{scheduledActivityId}" })
    public ScheduledActivityDto getScheduledActivityById(@PathVariable("scheduledActivityId") int scheduledActivityId)
            throws IllegalArgumentException {
        ScheduledActivity scheduledActivity = scheduledActivityService.getScheduledActivityById(scheduledActivityId);
        return convertToDto(scheduledActivity);
    }

    /**
     * Get all scheduled activities by date
     * 
     * @param date
     * @return List<ScheduledActivityDto>
     */
    @GetMapping(value = { "/scheduledActivities/{date}" })
    public List<ScheduledActivityDto> getAllScheduledActivitiesByDate(@PathVariable("date") LocalDate date) {
        List<ScheduledActivity> scheduledActivities = scheduledActivityService.getAllScheduledActivitiesByDate(date);
        return convertToDto(scheduledActivities);
    }

    /**
     * Get all scheduled activities
     * 
     * @return List<ScheduledActivityDto>
     */
    @GetMapping(value = { "/scheduledActivities" })
    public List<ScheduledActivityDto> getAllScheduledActivities() {
        List<ScheduledActivity> scheduledActivities = scheduledActivityService.getAllScheduledActivities();
        return convertToDto(scheduledActivities);
    }

    /**
     * Get instructor by scheduled activity
     * 
     * @param scheduledActivity
     * @return InstructorDto
     */
    @GetMapping(value = { "/scheduledActivity/instructor/{scheduledActivityId}" })
    public InstructorDto getInstructorByScheduledActivityId(
            @PathVariable("scheduledActivityId") int scheduledActivityId)
            throws IllegalArgumentException {
        Instructor instructor = scheduledActivityService.getInstructorByScheduledActivityId(scheduledActivityId);
        return convertToDto(instructor);
    }

    /**
     * Get all scheduled activities by instructor id
     * 
     * @param instructorId
     * @return List<ScheduledActivityDto>
     */
    @GetMapping(value = { "/scheduledActivities/instructor/{instructorId}" })
    public List<ScheduledActivityDto> getAllScheduledActivitiesByInstructorId(
            @PathVariable("instructorId") int instructorId) {
        List<ScheduledActivity> scheduledActivities = scheduledActivityService
                .getAllScheduledActivitiesByInstructorId(instructorId);
        return convertToDto(scheduledActivities);
    }

    /**
     * Get activity by scheduled activity id
     * 
     * @param scheduledActivityId
     * @return ActivityDto
     */
    @GetMapping(value = { "/scheduledActivity/activity/{scheduledActivityId}" })
    public ActivityDto getActivityByScheduledActivityId(@PathVariable("scheduledActivityId") int scheduledActivityId)
            throws IllegalArgumentException {
        Activity activity = scheduledActivityService.getActivityByScheduledActivityId(scheduledActivityId);
        return convertToDto(activity);
    }

    /**
     * Update a scheduled activity
     * 
     * @param scheduledActivityId
     * @param date
     * @param startTime
     * @param endTime
     * @param instructorId
     * @param activityName
     * @param capacity
     * 
     * @return ScheduledActivity
     */
    @PutMapping(value = {
            "/updateScheduledActivity/{scheduledActivityId}/{date}/{startTime}/{endTime}/{instructorId}/{activityName}/{capacity}" })
    public ScheduledActivityDto updateScheduledActivity(@PathVariable("scheduledActivityId") int scheduledActivityId,
            @PathVariable("date") Date date, @PathVariable("startTime") Time startTime,
            @PathVariable("endTime") Time endTime, @PathVariable("instructorId") int instructorId,
            @PathVariable("activityName") String activityName, @PathVariable("capacity") int capacity)
            throws IllegalArgumentException {
        ScheduledActivity scheduledActivity = scheduledActivityService.updateScheduledActivity(scheduledActivityId,
                date,
                startTime, endTime, instructorId, activityName, capacity);
        return convertToDto(scheduledActivity);
    }

    /**
     * Delete a scheduled activity by its id
     * 
     * @param scheduledActivityId
     * @return boolean
     */
    @DeleteMapping(value = { "/deleteScheduledActivity/{scheduledActivityId}" })
    public boolean deleteScheduledActivity(@PathVariable("scheduledActivityId") int scheduledActivityId)
            throws IllegalArgumentException {
        return scheduledActivityService.deleteScheduledActivity(scheduledActivityId);
    }

    /**
     * Delete all scheduled activities
     */
    @DeleteMapping(value = { "/deleteScheduledActivities" })
    public void deleteAllScheduledActivities() {
        scheduledActivityService.deleteAllScheduledActivities();
    }

    /**
     * Convert scheduled activity to scheduled activity dto
     * 
     * @param scheduledActivity
     * @return ScheduledActivityDto
     */
    private ScheduledActivityDto convertToDto(ScheduledActivity scheduledActivity) {
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("There is no such scheduled activity!");
        }
        ScheduledActivityDto scheduledActivityDto = new ScheduledActivityDto(scheduledActivity.getScheduledActivityId(),
                scheduledActivity.getDate(), scheduledActivity.getStartTime(), scheduledActivity.getEndTime(),
                scheduledActivity.getInstructor().getInstructorId(), scheduledActivity.getActivity().getActivityName(),
                scheduledActivity.getCapacity());
        return scheduledActivityDto;
    }

    /**
     * Convert instructor to instructor dto
     * 
     * @param instructor
     * @return InstructorDto
     */
    private InstructorDto convertToDto(Instructor instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("There is no such instructor!");
        }
        InstructorDto instructorDto = new InstructorDto(instructor.getInstructorId(), instructor.getFirstName(),
                instructor.getLastName(), instructor.getEmail(), instructor.getPhoneNumber());
        return instructorDto;
    }

    /**
     * Convert activity to activity dto
     * 
     * @param activity
     * @return ActivityDto
     */
    private ActivityDto convertToDto(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("There is no such activity!");
        }
        ActivityDto activityDto = new ActivityDto(activity.getActivityName(), activity.getDescription());
        return activityDto;
    }

    /**
     * Convert a list of Scheduled activities to a list of ScheduledActivityDto
     * 
     * @param scheduledActivities
     * @return List<ScheduledActivityDto>
     */
    private List<ScheduledActivityDto> convertToDto(List<ScheduledActivity> scheduledActivities) {
        List<ScheduledActivityDto> scheduledActivityDtos = new ArrayList<ScheduledActivityDto>();
        for (ScheduledActivity scheduledActivity : scheduledActivities) {
            scheduledActivityDtos.add(convertToDto(scheduledActivity));
        }
        return scheduledActivityDtos;
    }
}
