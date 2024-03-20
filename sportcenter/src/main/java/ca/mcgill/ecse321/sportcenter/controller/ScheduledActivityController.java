package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.ScheduledActivityDto;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.service.ScheduledActivityService;

/**
 * Controller class for the ScheduledActivity entity
 * 
 * @author Fabian Saldana
 */
@CrossOrigin(origins = "*")
@RestController
public class ScheduledActivityController {

    @Autowired
    private ScheduledActivityService scheduledActivityService;

    /**
     * Create an activity
     * 
     * @param scheduledActivityId
     * @param date
     * @param startTime
     * @param endTime
     * @param instructor
     * @param activity
     * @return
     * @Author Victor Fabian Saldana Arteaga
     */
    @PostMapping(value = {
            "/createScheduledActivity/{scheduledActivityId}/{date}/{startTime}/{endTime}/{instructor}/{activity}/{capacity}",
            "/createScheduledActivity/{scheduledActivityId}/{date}/{startTime}/{endTime}/{instructor}/{activity}/{capacity}/" })
    public ScheduledActivityDto createScheduledActivity(@PathVariable("date") LocalDate date,
            @PathVariable("startTime") LocalTime startTime,
            @PathVariable("startTime") LocalTime endTime, @PathVariable("instructor") Instructor instructor,
            @PathVariable("activity") Activity activity, @PathVariable("capacity") int capacity)
            throws IllegalArgumentException {
        return convertToDto(
                scheduledActivityService.createScheduledActivity(date, startTime, endTime, instructor, activity,
                        capacity));
    }

    /**
     * Get a scheduled activity by its id
     * 
     * @param scheduledActivityId
     * @return
     */
    @GetMapping(value = { "/scheduledActivity/{scheduledActivityId}", "/scheduledActivity/{scheduledActivityId}/" })
    public ScheduledActivityDto getScheduledActivity(@PathVariable("scheduledActivityId") int scheduledActivityId)
            throws IllegalArgumentException {
        return convertToDto(scheduledActivityService.getScheduledActivity(scheduledActivityId));
    }

    /**
     * Get a scheduled activity by its date
     * 
     * @param date
     * 
     * @return
     */
    @GetMapping(value = { "/scheduledActivity/getByDate/{date}", "/scheduledActivity/getByDate/{date}/" })
    public List<ScheduledActivityDto> getScheduledActivityByDate(@PathVariable("date") LocalDate date)
            throws IllegalArgumentException {
        return convertToDto(scheduledActivityService.getScheduledActivityByDate(date));
    }

    /**
     * Get all schduled activities
     * 
     * @return List<ScheduledActivityDto>
     */
    @GetMapping(value = { "/scheduledActivity/getAll", "/scheduledActivity/getAll/" })
    public List<ScheduledActivityDto> getAllScheduledActivities() {
        return convertToDto(scheduledActivityService.getAllScheduledActivities());
    }

    /**
     * Update an scheduled activity
     * 
     * @param scheduledActivityId
     * @param date
     * @param startTime
     * @param endTime
     * @return
     */
    @PutMapping(value = { "/activity/update/{name}/{newName}/{newDescription}/{newSubcategory}",
            "/activity/update/{name}/{newName}/{newDescription}/{newSubcategory}/" })
    public ScheduledActivityDto updateScheduledActivity(
            @PathVariable("scheduledActivityId") Integer scheduledActivityId,
            @PathVariable("date") LocalDate date,
            @PathVariable("startTime") LocalTime startTime,
            @PathVariable("startTime") LocalTime endTime,
            @PathVariable("instructor") Instructor instructor,
            @PathVariable("activity") Activity activity,
            @PathVariable("capacity") Integer capacity) throws IllegalArgumentException {
        return convertToDto(
                scheduledActivityService.updateScheduledActivity(scheduledActivityId, date, startTime, endTime,
                        instructor, activity, capacity));
    }

    /**
     * Delete a scheduled activity by its Id
     * 
     * @param scheduledActivityId
     */
    @DeleteMapping(value = { "/scheduledActivity/delete/{scheduledActivityId}",
            "/scheduledActivity/delete/{scheduledActivityId}/" })
    public ScheduledActivityDto deleteScheduledActivity(
            @PathVariable("scheduledActivityId") Integer scheduledActivityId) {
        ScheduledActivity scheduledActivity = scheduledActivityService.deleteScheduledActivity(scheduledActivityId);
        return convertToDto(scheduledActivity);
    }

    /**
     * Delete all scheduled activities
     */
    @DeleteMapping(value = { "/scheduledActivity/deleteAll", "/scheduledActivity/deleteAll/" })
    public void deleteAllScheduledActivities() {
        scheduledActivityService.deleteAllScheduledActivities();
    }

    /**
     * Convert a ScheduledActivity to a ScheduledActivityDto
     * 
     * @param scheduledActivity
     * @return ScheduledActivityDto
     */
    public static ScheduledActivityDto convertToDto(ScheduledActivity scheduledActivity) {
        return new ScheduledActivityDto(scheduledActivity.getScheduledActivityId(), scheduledActivity.getDate(),
                scheduledActivity.getStartTime(), scheduledActivity.getEndTime(), scheduledActivity.getSupervisor(),
                scheduledActivity.getActivity(), scheduledActivity.getCapacity());
    }

    /**
     * Convert a list of ScheduledActivity to a list of ScheduledActivityDto
     * 
     * @param scheduledActivities
     * @return List<ScheduledActivityDto>
     */
    public static List<ScheduledActivityDto> convertToDto(List<ScheduledActivity> scheduledActivities) {
        List<ScheduledActivityDto> scheduledActivityDtos = new ArrayList<ScheduledActivityDto>(
                scheduledActivities.size());
        for (ScheduledActivity scheduledActivity : scheduledActivities) {
            scheduledActivityDtos.add(convertToDto(scheduledActivity));
        }
        return scheduledActivityDtos;
    }

}
