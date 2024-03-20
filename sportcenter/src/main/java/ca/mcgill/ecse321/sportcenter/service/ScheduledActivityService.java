package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalTime;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Activity;

import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

/**
 * Service class for ScheduledActivity entity
 * 
 * @author Fabian Saldana
 */
public class ScheduledActivityService {

    @Autowired
    ScheduledActivityRepository scheduledActivityRepository;

    /*
     * @Autowired
     * ActivityRepository activityRepository;
     */
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
     * @param instructorId
     * @param activityName
     * @param capacity
     * 
     * @return ScheduledActivity
     */
    @Transactional
    public ScheduledActivity createScheduledActivity(LocalDate date, LocalTime startTime, LocalTime endTime,
            Instructor instructor, Activity activity, int capacity) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be empty!");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be before the current date!");
        }
        if (startTime == null || startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be empty!");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be empty!");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be before end time!");
        }
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be empty!");
        }
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be empty!");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0!");
        }

        List<ScheduledActivity> scheduledActivities = getScheduledActivityByDate(date);
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
        ScheduledActivity scheduledActivity = new ScheduledActivity();
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setStartTime(endTime);
        scheduledActivity.setSupervisor(instructor);
        scheduledActivity.setActivity(activity);

        scheduledActivityRepository.save(scheduledActivity);
        return scheduledActivity;
    }

    /**
     * Get an activity by its Id (primary key)
     * 
     * @param scheduledActivityId
     * 
     * @return ScheduledActivity
     * 
     */
    @Transactional
    public ScheduledActivity getScheduledActivity(int scheduledActivityId) {
        if (scheduledActivityId <= 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        return scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId);
    }

    /**
     * Get an activity by its date
     * 
     * @param date
     * 
     * @return List<ScheduledActivity>
     */
    @Transactional
    public List<ScheduledActivity> getScheduledActivityByDate(LocalDate date) {
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
     * Update a scheduled activity
     * 
     * @param scheduledActivityId
     * @param date
     * @param startTime
     * @param endTime
     * @param instructor
     * @param activity
     * @param capacity
     * 
     * @return ScheduledActivity
     */
    @Transactional
    public ScheduledActivity updateScheduledActivity(int scheduledActivityId, LocalDate date, LocalTime startTime,
            LocalTime endTime, Instructor instructor, Activity activity, int capacity) {

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
            throw new IllegalArgumentException("Start time cannot be before end time!");
        }
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be empty!");
        }
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be empty!");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0!");
        }

        List<ScheduledActivity> scheduledActivities = getScheduledActivityByDate(date);
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

        ScheduledActivity scheduledActivity = getScheduledActivity(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled Activity does not exist!");
        }

        scheduledActivity.setScheduledActivityId(scheduledActivityId);
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setStartTime(endTime);
        scheduledActivity.setSupervisor(instructor);
        scheduledActivity.setActivity(activity);
        scheduledActivity.setCapacity(capacity);

        scheduledActivityRepository.save(scheduledActivity);
        return scheduledActivity;
    }

    /**
     * Delete a scheduled activity
     * 
     * @param scheduledActivityId
     * 
     * @return ScheduledActivity
     */
    @Transactional
    public ScheduledActivity deleteScheduledActivity(int scheduledActivityId) {
        if (scheduledActivityId <= 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        ScheduledActivity scheduledActivity = getScheduledActivity(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled Activity does not exist!");
        }
        scheduledActivityRepository.delete(scheduledActivity);
        return scheduledActivity;
    }

    /**
     * Delete all scheduled activities
     */
    @Transactional
    public void deleteAllScheduledActivities() {
        scheduledActivityRepository.deleteAll();
    }
}