package ca.mcgill.ecse321.sportcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.sportcenter.dto.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.ArrayList;

import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.service.ScheduledActivityManagementService;

/**
 * Rest controller for the ScheduledActivity entity
 * 
 * @author Fabian Saldana
 */
@CrossOrigin(origins = "*")
@RestController
public class ScheduledActivityManagementController {

        @Autowired
        private ScheduledActivityManagementService scheduledActivityService;

        /**
         * Create a scheduled activity
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
                        "/createScheduledActivity/{date}/{startTime}/{endTime}/{instructorId}/{activityName}/{capacity}",
                        "/createScheduledActivity/{date}/{startTime}/{endTime}/{instructorId}/{activityName}/{capacity}/" })
        public ScheduledActivityDto createScheduledActivity(@PathVariable("date") LocalDate date,
                        @PathVariable("startTime") LocalTime startTime, @PathVariable("endTime") LocalTime endTime,
                        @PathVariable("instructorId") int instructorId,
                        @PathVariable("activityName") String activityName,
                        @PathVariable("capacity") int capacity) throws IllegalArgumentException {
                ScheduledActivity scheduledActivity = scheduledActivityService.createScheduledActivity(date, startTime,
                                endTime,
                                instructorId, activityName, capacity);
                return convertToDto(scheduledActivity);
        }

        /**
         * Get a scheduled activity by its id
         * 
         * @param scheduledActivityId
         * @return ScheduledActivityDto
         */
        @GetMapping(value = { "/scheduledActivity/{scheduledActivityId}", "/scheduledActivity/{scheduledActivityId}/" })
        public ScheduledActivityDto getScheduledActivityById(
                        @PathVariable("scheduledActivityId") int scheduledActivityId)
                        throws IllegalArgumentException {
                ScheduledActivity scheduledActivity = scheduledActivityService
                                .getScheduledActivityById(scheduledActivityId);
                return convertToDto(scheduledActivity);
        }

        /**
         * Get all scheduled activities by date
         * 
         * @param date
         * @return List<ScheduledActivityDto>
         */
        @GetMapping(value = { "/scheduledActivities/{date}", "/scheduledActivities/{date}/" })
        public List<ScheduledActivityDto> getAllScheduledActivitiesByDate(@PathVariable("date") LocalDate date) {
                List<ScheduledActivity> scheduledActivities = scheduledActivityService
                                .getAllScheduledActivitiesByDate(date);
                return convertToDto(scheduledActivities);
        }

        /**
         * Get all scheduled activities
         * 
         * @return List<ScheduledActivityDto>
         */
        @GetMapping(value = { "/scheduledActivities", "/scheduledActivities/" })
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
        @GetMapping(value = { "/scheduledActivity/instructor/{scheduledActivityId}",
                        "/scheduledActivity/instructor/{scheduledActivityId}" })
        public InstructorDto getInstructorByScheduledActivityId(
                        @PathVariable("scheduledActivityId") int scheduledActivityId)
                        throws IllegalArgumentException {
                Instructor instructor = scheduledActivityService
                                .getInstructorByScheduledActivityId(scheduledActivityId);
                return AccountManagementController.convertInstructorToDto(instructor);
        }

        /**
         * Get all scheduled activities by instructor id
         * 
         * @param instructorId
         * @return List<ScheduledActivityDto>
         */
        @GetMapping(value = { "/scheduledActivities/instructor/{instructorId}",
                        "/scheduledActivities/instructor/{instructorId}/" })
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
        @GetMapping(value = { "/scheduledActivity/activity/{scheduledActivityId}",
                        "/scheduledActivity/activity/{scheduledActivityId}" })
        public ActivityDto getActivityByScheduledActivityId(
                        @PathVariable("scheduledActivityId") int scheduledActivityId)
                        throws IllegalArgumentException {
                Activity activity = scheduledActivityService.getActivityByScheduledActivityId(scheduledActivityId);
                return ActivityManagementController.convertToDto(activity);
        }

        /**
         * Update a scheduled activity date, start time, end time
         * 
         * @param scheduledActivityId
         * @param oldDate
         * @param newDate
         * @param oldStartTime
         * @param newStartTime
         * @param oldEndTime
         * @param newEndTime
         * @return ScheduledActivity
         */
        @PutMapping(value = {
                        "/updateScheduledActivity/{scheduledActivityId}/{oldDate}/{newDate}/{oldStartTime}/{newStartTime}/{oldEndTime}/{newEndTime}",
                        "/updateScheduledActivity/{scheduledActivityId}/{oldDate}/{newDate}/{oldStartTime}/{newStartTime}/{oldEndTime}/{newEndTime}/" })
        public ScheduledActivityDto updateScheduledActivity(
                        @PathVariable("scheduledActivityId") int scheduledActivityId,
                        @PathVariable("oldDate") LocalDate oldDate, @PathVariable("newDate") LocalDate newDate,
                        @PathVariable("oldStartTime") LocalTime oldStartTime,
                        @PathVariable("newStartTime") LocalTime newStartTime,
                        @PathVariable("oldEndTime") LocalTime oldEndTime,
                        @PathVariable("newEndTime") LocalTime newEndTime)
                        throws IllegalArgumentException {
                ScheduledActivity scheduledActivity = scheduledActivityService.updateScheduledActivityByDateAndTime(
                                scheduledActivityId,
                                oldDate, newDate, oldStartTime, newStartTime, oldEndTime, newEndTime);
                return convertToDto(scheduledActivity);
        }

        /**
         * Update a scheduled activity instructor
         * 
         * @param scheduledActivityId
         * @param oldAccountId
         * @param newAccountId
         * @return ScheduledActivity
         */
        @PutMapping(value = { "/updateScheduledActivity/instructor/{scheduledActivityId}/{oldAccountId}/{newAccountId}",
                        "/updateScheduledActivity/instructor/{scheduledActivityId}/{oldAccountId}/{newAccountId}/" })
        public ScheduledActivityDto updateScheduledActivityInstructor(
                        @PathVariable("scheduledActivityId") int scheduledActivityId,
                        @PathVariable("oldAccountId") int oldAccountId, @PathVariable("newAccountId") int newAccountId)
                        throws IllegalArgumentException {
                ScheduledActivity scheduledActivity = scheduledActivityService.updateScheduledActivityInstructor(
                                scheduledActivityId,
                                oldAccountId, newAccountId);
                return convertToDto(scheduledActivity);
        }

        /**
         * Update a scheduled activity activity
         * 
         * @param scheduledActivityId
         * @param oldActivityName
         * @param newActivityName
         * @return ScheduledActivity
         */
        @PutMapping(value = {
                        "/updateScheduledActivity/activity/{scheduledActivityId}/{oldActivityName}/{newActivityName}",
                        "/updateScheduledActivity/activity/{scheduledActivityId}/{oldActivityName}/{newActivityName}/" })
        public ScheduledActivityDto updateScheduledActivityActivity(
                        @PathVariable("scheduledActivityId") int scheduledActivityId,
                        @PathVariable("oldActivityName") String oldActivityName,
                        @PathVariable("newActivityName") String newActivityName)
                        throws IllegalArgumentException {
                ScheduledActivity scheduledActivity = scheduledActivityService.updateScheduledActivityActivity(
                                scheduledActivityId,
                                oldActivityName, newActivityName);
                return convertToDto(scheduledActivity);
        }

        /**
         * Update a scheduled activity capacity
         * 
         * @param scheduledActivityId
         * @param oldCapacity
         * @param newCapacity
         * @return ScheduledActivity
         */
        @PutMapping(value = { "/updateScheduledActivity/capacity/{scheduledActivityId}/{oldCapacity}/{newCapacity}",
                        "/updateScheduledActivity/capacity/{scheduledActivityId}/{oldCapacity}/{newCapacity}/" })
        public ScheduledActivityDto updateScheduledActivityCapacity(
                        @PathVariable("scheduledActivityId") int scheduledActivityId,
                        @PathVariable("oldCapacity") int oldCapacity, @PathVariable("newCapacity") int newCapacity)
                        throws IllegalArgumentException {
                ScheduledActivity scheduledActivity = scheduledActivityService.updateScheduledActivityCapacity(
                                scheduledActivityId,
                                oldCapacity, newCapacity);
                return convertToDto(scheduledActivity);
        }

        /**
         * Delete a scheduled activity by its id
         * 
         * @param scheduledActivityId
         * @return boolean
         */
        @DeleteMapping(value = { "/deleteScheduledActivity/{scheduledActivityId}",
                        "/deleteScheduledActivity/{scheduledActivityId}/" })
        public void deleteScheduledActivity(@PathVariable("scheduledActivityId") int scheduledActivityId)
                        throws IllegalArgumentException {
                scheduledActivityService.deleteScheduledActivity(scheduledActivityId);
        }

        /**
         * Delete all scheduled activities
         */
        @DeleteMapping(value = { "/deleteScheduledActivities", "/deleteScheduledActivities/" })
        public void deleteAllScheduledActivities() {
                scheduledActivityService.deleteAllScheduledActivities();
        }

        /*
         * Delete all scheduled activities by instructor id
         */
        @DeleteMapping(value = { "/deleteScheduledActivities/instructor/{instructorId}",
                        "/deleteScheduledActivities/instructor/{instructorId}/" })
        public void deleteAllScheduledActivitiesByInstructorId(@PathVariable("instructorId") int instructorId) {
                scheduledActivityService.deleteAllScheduledActivitiesByInstructorId(instructorId);
        }

        /**
         * Convert scheduled activity to scheduled activity dto
         * 
         * @param scheduledActivity
         * @return ScheduledActivityDto
         */
        public static ScheduledActivityDto convertToDto(ScheduledActivity scheduledActivity) {
                if (scheduledActivity == null) {
                        throw new IllegalArgumentException("There is no such scheduled activity!");
                }
                ScheduledActivityDto scheduledActivityDto = new ScheduledActivityDto(
                                scheduledActivity.getScheduledActivityId(),
                                scheduledActivity.getDate(), scheduledActivity.getStartTime(),
                                scheduledActivity.getEndTime(),
                                scheduledActivity.getSupervisor(), scheduledActivity.getActivity(),
                                scheduledActivity.getCapacity());
                return scheduledActivityDto;
        }

        /**
         * Convert a list of Scheduled activities to a list of ScheduledActivityDto
         * 
         * @param scheduledActivities
         * @return List<ScheduledActivityDto>
         */
        public static List<ScheduledActivityDto> convertToDto(List<ScheduledActivity> scheduledActivities) {
                List<ScheduledActivityDto> scheduledActivityDtos = new ArrayList<ScheduledActivityDto>();
                for (ScheduledActivity scheduledActivity : scheduledActivities) {
                        scheduledActivityDtos.add(convertToDto(scheduledActivity));
                }
                return scheduledActivityDtos;
        }
}
