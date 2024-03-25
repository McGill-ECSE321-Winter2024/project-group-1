package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalTime;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

/**
 * Service class for ScheduledActivity entity
 * 
 * @author Fabian Saldana
 */
@Service
public class ScheduledActivityManagementService {

    @Autowired
    ScheduledActivityRepository scheduledActivityRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    InstructorRepository instructorRepository;

    /**
     * Convert an iterable to a list
     * 
     * @param iterable
     * @return List<T>
     **/
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * Create an activity where only the instructor can create an activity
     * 
     * @param scheduledActivityId
     * @param date
     * @param startTime
     * @param endTime
     * @param accountRoleId
     * @param activityName
     * @param capacity
     * @return ScheduledActivity
     */
    @Transactional
    public ScheduledActivity createScheduledActivity(LocalDate date, LocalTime startTime, LocalTime endTime,
            int accountRoleId, String activityName, int capacity) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be empty!");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be before the current date!");
        }
        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be empty!");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be empty!");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time!");
        }
        if (accountRoleId <= 0) {
            throw new IllegalArgumentException("Instructor Id must be greater than 0");
        }
        if (activityName == null || activityName.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity name cannot be empty!");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0!");
        }

        Instructor instructor = instructorRepository.findInstructorByAccountRoleId(accountRoleId);

        if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        }
        if (!instructor.getStatus().equals(InstructorStatus.Active)) {
            throw new IllegalArgumentException("Instructor is not approved!");
        }
        Activity activity = activityRepository.findActivityByName(activityName);
        if (activity == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        }
        if (!activity.getIsApproved()) {
            throw new IllegalArgumentException("Activity is not approved!");
        }

        List<ScheduledActivity> scheduledActivities = getAllScheduledActivitiesByDate(date);
        if (scheduledActivities != null) {
            for (ScheduledActivity scheduledActivity : scheduledActivities) {
                if (scheduledActivity.getStartTime().isBefore(startTime)
                        && scheduledActivity.getEndTime().isAfter(startTime)) {
                    throw new IllegalArgumentException("There is already a scheduled activity at this time!");
                }
                if (scheduledActivity.getStartTime().isBefore(endTime)
                        && scheduledActivity.getEndTime().isAfter(endTime)) {
                    throw new IllegalArgumentException("There is already a scheduled activity at this time!");
                }
            }
        }

        // Check if instructor is available
        for (ScheduledActivity scheduledActivity : getAllScheduledActivitiesByInstructorId(
                instructor.getAccountRoleId())) {
            if (scheduledActivity.getDate().equals(date)) {
                if (scheduledActivity.getStartTime().isBefore(startTime)
                        && scheduledActivity.getEndTime().isAfter(startTime)) {
                    throw new IllegalArgumentException("Instructor is not available at this time!");
                }
                if (scheduledActivity.getStartTime().isBefore(endTime)
                        && scheduledActivity.getEndTime().isAfter(endTime)) {
                    throw new IllegalArgumentException("Instructor is not available at this time!");
                }
            }
        }

        ScheduledActivity scheduledActivity = new ScheduledActivity();
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setEndTime(endTime);
        scheduledActivity.setSupervisor(instructor);
        scheduledActivity.setActivity(activity);
        scheduledActivity.setCapacity(capacity);

        scheduledActivityRepository.save(scheduledActivity);
        return scheduledActivity;
    }

    /**
     * Get a scheduled activity by its Id (primary key)
     * 
     * @param scheduledActivityId
     * @return ScheduledActivity
     */
    @Transactional
    public ScheduledActivity getScheduledActivityById(int scheduledActivityId) {
        if (scheduledActivityId <= 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        return scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId);
    }

    /**
     * Get all scheduled activities by their same date
     * 
     * @param date
     * @return List<ScheduledActivity>
     */
    @Transactional
    public List<ScheduledActivity> getAllScheduledActivitiesByDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be empty!");
        }
        return scheduledActivityRepository.findScheduledActivityByDate(date);
    }

    /**
     * Get all scheduled activities
     * 
     * @return List<ScheduledActivity>
     */
    @Transactional
    public List<ScheduledActivity> getAllScheduledActivities() {
        return toList(scheduledActivityRepository.findAll());
    }

    /**
     * Get the instructor of a scheduled activity
     * 
     * @param scheduledActivity
     * @return Instructor
     */
    @Transactional
    public Instructor getInstructorByScheduledActivityId(int scheduledActivityId) {
        if (scheduledActivityId < 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        ScheduledActivity scheduledActivity = getScheduledActivityById(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled Activity does not exist!");
        }
        return scheduledActivity.getSupervisor();
    }

    /**
     * Get all scheduled activities of an instructor
     * 
     * @param accountRoleId
     * @return List<ScheduledActivity>
     */
    @Transactional
    public List<ScheduledActivity> getAllScheduledActivitiesByInstructorId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        Instructor instructor = instructorRepository.findInstructorByAccountRoleId(accountRoleId);
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        }
        return toList(scheduledActivityRepository.findScheduledActivityBySupervisorAccountRoleId(accountRoleId));
    }

    /**
     * Get activity by scheduled activity id (primary key)
     * 
     * @param scheduledActivityId
     * @return
     */
    @Transactional
    public Activity getActivityByScheduledActivityId(int scheduledActivityId) {
        if (scheduledActivityId < 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        ScheduledActivity scheduledActivity = getScheduledActivityById(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled Activity does not exist!");
        }
        return scheduledActivity.getActivity();
    }

    /**
     * Get all scheduled activities of an activity
     * 
     * @param activityName
     * @return List<ScheduledActivity>
     */
    @Transactional
    public List<ScheduledActivity> getAllScheduledActivitiesByActivityName(String activityName) {
        if (activityName == null || activityName.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity name cannot be empty!");
        }
        Activity activity = activityRepository.findActivityByName(activityName);
        if (activity == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        }
        List<ScheduledActivity> scheduledActivities = new ArrayList<ScheduledActivity>();
        for (ScheduledActivity scheduledActivity : scheduledActivityRepository.findAll()) {
            if (scheduledActivity.getActivity().getName().equals(activityName)) {
                scheduledActivities.add(scheduledActivity);
            }
        }
        return scheduledActivities;
    }

    /**
     * Update a scheduled activity start time, end time, date
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
    @Transactional
    public ScheduledActivity updateScheduledActivityByDateAndTime(int scheduledActivityId, LocalDate oldDate,
            LocalDate newDate, LocalTime oldStartTime, LocalTime newStartTime, LocalTime oldEndTime,
            LocalTime newEndTime) {

        if (newDate == null) {
            throw new IllegalArgumentException("Date cannot be empty!");
        }
        if (oldDate == null) {
            throw new IllegalArgumentException("Date cannot be empty!");
        }
        if (newDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be before the current date!");
        }
        if (oldDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be before the current date!");
        }
        if (newStartTime == null) {
            throw new IllegalArgumentException("Start time cannot be empty!");
        }
        if (oldStartTime == null) {
            throw new IllegalArgumentException("Start time cannot be empty!");
        }
        if (newEndTime == null) {
            throw new IllegalArgumentException("End time cannot be empty!");
        }
        if (oldEndTime == null) {
            throw new IllegalArgumentException("End time cannot be empty!");
        }
        if (newStartTime.isAfter(newEndTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time!");
        }

        List<ScheduledActivity> scheduledActivities = getAllScheduledActivitiesByDate(newDate);
        if (scheduledActivities != null) {
            for (ScheduledActivity scheduledActivity : scheduledActivities) {
                if (scheduledActivity.getStartTime().isBefore(newStartTime)
                        && scheduledActivity.getEndTime().isAfter(newStartTime)) {
                    throw new IllegalArgumentException("There is already a scheduled activity at this time!");
                }
                if (scheduledActivity.getStartTime().isBefore(newEndTime)
                        && scheduledActivity.getEndTime().isAfter(newEndTime)) {
                    throw new IllegalArgumentException("There is already a scheduled activity at this time!");
                }
            }
        }

        ScheduledActivity scheduledActivity = getScheduledActivityById(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled Activity does not exist!");
        }

        scheduledActivity.setScheduledActivityId(scheduledActivityId);
        scheduledActivity.setDate(newDate);
        scheduledActivity.setStartTime(newStartTime);
        scheduledActivity.setStartTime(newEndTime);

        scheduledActivityRepository.save(scheduledActivity);
        return scheduledActivity;
    }

    /**
     * Update a scheduled activity instructor
     * 
     * @param scheduledActivityId
     * @param oldAccountRoleId
     * @param newAccountRoleId
     * @return ScheduledActivity
     */
    @Transactional
    public ScheduledActivity updateScheduledActivityInstructor(int scheduledActivityId, int oldAccountRoleId,
            int newAccountRoleId) {
        if (scheduledActivityId < 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        if (oldAccountRoleId < 0) {
            throw new IllegalArgumentException("Instructor Id cannot be negative!");
        }
        if (newAccountRoleId < 0) {
            throw new IllegalArgumentException("Instructor Id cannot be negative!");
        }
        Instructor oldInstructor = instructorRepository.findInstructorByAccountRoleId(oldAccountRoleId);
        if (oldInstructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        }
        Instructor newInstructor = instructorRepository.findInstructorByAccountRoleId(newAccountRoleId);
        if (newInstructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        }
        ScheduledActivity scheduledActivity = getScheduledActivityById(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled Activity does not exist!");
        }
        scheduledActivity.setSupervisor(newInstructor);
        scheduledActivityRepository.save(scheduledActivity);
        return scheduledActivity;
    }

    /**
     * Update a scheduled activity activity
     * 
     * @param scheduledActivityId
     * @param oldActivityName
     * @param newActivityName
     * @return ScheduledActivity
     */
    @Transactional
    public ScheduledActivity updateScheduledActivityActivity(int scheduledActivityId, String oldActivityName,
            String newActivityName) {
        if (scheduledActivityId < 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        if (oldActivityName == null || oldActivityName.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity name cannot be empty!");
        }
        if (newActivityName == null || newActivityName.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity name cannot be empty!");
        }
        Activity oldActivity = activityRepository.findActivityByName(oldActivityName);
        if (oldActivity == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        }
        Activity newActivity = activityRepository.findActivityByName(newActivityName);
        if (newActivity == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        }
        ScheduledActivity scheduledActivity = getScheduledActivityById(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled Activity does not exist!");
        }
        scheduledActivity.setActivity(newActivity);
        scheduledActivityRepository.save(scheduledActivity);
        return scheduledActivity;
    }

    /**
     * Update a scheduled activity capacity
     * 
     * @param scheduledActivityId
     * @param oldCapacity
     * @param newCapacity
     * @return ScheduledActivity
     */
    @Transactional
    public ScheduledActivity updateScheduledActivityCapacity(int scheduledActivityId, int oldCapacity,
            int newCapacity) {
        if (scheduledActivityId < 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        if (oldCapacity <= 0 || newCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0!");
        }
        ScheduledActivity scheduledActivity = getScheduledActivityById(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled Activity does not exist!");
        }
        scheduledActivity.setCapacity(newCapacity);
        scheduledActivityRepository.save(scheduledActivity);
        return scheduledActivity;
    }

    /**
     * Delete a scheduled activity
     * 
     * @param scheduledActivityId
     * @return boolean
     */
    @Transactional
    public boolean deleteScheduledActivity(int scheduledActivityId) {
        if (scheduledActivityId <= 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        ScheduledActivity scheduledActivity = getScheduledActivityById(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled Activity does not exist!");
        }

        scheduledActivityRepository.delete(scheduledActivity);
        return true;
    }

    /**
     * Delete all scheduled activities by instructor id
     * 
     * @param accountRoleId
     * @return boolean
     */
    @SuppressWarnings("null")
    @Transactional
    public boolean deleteAllScheduledActivitiesByInstructorId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }

        List<ScheduledActivity> scheduledActivities = getAllScheduledActivitiesByInstructorId(accountRoleId);
        if (scheduledActivities != null) {
            for (ScheduledActivity scheduledActivity : scheduledActivities) {
                scheduledActivityRepository.delete(scheduledActivity);
            }
        }
        return true;
    }
}