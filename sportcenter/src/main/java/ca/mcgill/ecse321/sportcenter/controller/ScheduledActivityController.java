package ca.mcgill.ecse321.sportcenter.controller;

//import java.util.ArrayList;
//import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;

//import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;

//import ca.mcgill.ecse321.sportcenter.dto.ActivityDto;
import ca.mcgill.ecse321.sportcenter.dto.ScheduledActivityDto;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
//import ca.mcgill.ecse321.sportcenter.service.ActivityService;
//import ca.mcgill.ecse321.sportcenter.service.InstructorService;
import ca.mcgill.ecse321.sportcenter.service.ScheduledActivityService; //Need to make changes to the dao I think

public class ScheduledActivityController {
    
    @Autowired
    private ScheduledActivityService scheduledActivityService;

    //@Autowired
    //private InstructorService instructorService;

    /**
     * Create an activity
     * @param scheduledActivityId
     * @param date
     * @param startTime
     * @param endTime
     * @param instructor
     * @param activity
     * @return
     * @Author Victor Fabian Saldana Arteaga
     */
    @PostMapping(value = {"/createScheduledActivity/{scheduledActivityId}/{date}/{startTime}/{endTime}/{instructor}/{activity}", "/createScheduledActivity/{scheduledActivityId}/{date}/{startTime}/{endTime}/{instructor}/{activity}/"})
    public ResponseEntity<?> createScheduledActivity(@PathVariable("date") LocalDate date, @PathVariable("startTime") LocalTime startTime, 
    @PathVariable("startTime") LocalTime endTime, @PathVariable("instructor") Instructor instructor, @PathVariable("activity") Activity activity) {
        try {
            ScheduledActivity scheduledActivity = scheduledActivityService.createScheduledActivity(date, startTime, endTime, instructor, activity);
            return ResponseEntity.ok(ScheduledActivityDto.convertToDto(scheduledActivity));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get a scheduled activity by its id
     * @param scheduledActivityId
     * @return
     */
    @GetMapping(value = {"/scheduledActivity/{scheduledActivityId}", "/scheduledActivity/{scheduledActivityId}/"})
    public ResponseEntity<?> getScheduledActivity(@PathVariable("scheduledActivityId") Integer scheduledActivityId) {
        try {
            ScheduledActivity scheduledActivity = scheduledActivityService.getScheduledActivity(scheduledActivityId);
            return ResponseEntity.ok(ScheduledActivityDto.convertToDto(scheduledActivity));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get all schduled activities
     * @return
     */
    /*
    @GetMapping(value = {"/activities", "/activities/"})
    public List<ActivityDto> getScheduledActivities() {
        List<ScheduledActivity> scheduledActivities = scheduledActivityService.getScheduledActivities();
        List<ScheduledActivity> schduledActivityDtos = new ArrayList<ScheduledActivityDto>();
        for (ScheduledActivity scheduledActivity : scheduledActivities) {
            scheduledActivityDtos.add(ScheduledActivityDto.convertToDto(scheduledActivity));
        }
        return scheduledActivityDtos;
    }
    */
    /**
     * Update an scheduled activity
     * @param scheduledActivityId
     * @param date
     * @param startTime
     * @param endTime
     * @return
     */
    @PutMapping(value = {"/activity/update/{name}/{newName}/{newDescription}/{newSubcategory}", "/activity/update/{name}/{newName}/{newDescription}/{newSubcategory}/"})
    public ResponseEntity<?> updateScheduledActivity(@PathVariable("scheduledActivityId") Integer scheduledActivityId, @PathVariable("date") LocalDate date, 
    @PathVariable("startTime") LocalTime startTime, @PathVariable("startTime") LocalTime endTime, @PathVariable("instructor") Instructor instructor, @PathVariable("activity") Activity activity) {
        try {
            ScheduledActivity scheduledActivity = scheduledActivityService.updateScheduledActivity(scheduledActivityId, date, startTime, endTime, instructor, activity);
            return ResponseEntity.ok(ScheduledActivityDto.convertToDto(scheduledActivity));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * Delete a scheduled activity by its Id
     * @param scheduledActivityId
     */
    @DeleteMapping(value = {"/scheduledActivity/delete/{scheduledActivityId}", "/scheduledActivity/delete/{scheduledActivityId}/"})
    public ScheduledActivityDto deleteScheduledActivity(@PathVariable("scheduledActivityId") Integer scheduledActivityId) {
        ScheduledActivity scheduledActivity = scheduledActivityService.deleteScheduledActivity(scheduledActivityId);
        return ScheduledActivityDto.convertToDto(scheduledActivity);
    }
    /**
     * Delete all scheduled activities
     */
    /* 
    @DeleteMapping(value = {"/scheduledActivities/delete", "/scheduledActivities/delete/"})
    public void deleteAllScheduledActivities() {
        scheduledActivityService.deleteAllScheduledActivities();
    }
    */
    
}
