package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalTime;
import java.time.LocalDate;

import org.checkerframework.checker.units.qual.s;
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
     * @return ScheduledActivity
     * 
     * @Author FabianSaldana
     */
    public List<ScheduledActivity> getScheduledActivities() {
        return toList(scheduledActivityRepository.findAll());
    }

    /**
     * Get an activity by its Id (primary key)
     * 
     * @param scheduledActivityId
     * @return ScheduledActivity
     * 
     * @Author Fabian Saldana
     */
    @Transactional
    public ScheduledActivity getScheduledActivity(int scheduledActivityId) {
        if (scheduledActivityId <= 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        return scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId);
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
        // create a scheduled activity
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
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be empty!");
        }
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be empty!");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0!");
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

    @Transactional
    public ScheduledActivity updateScheduledActivity(int scheduledActivityId, LocalDate date, LocalTime startTime,
            LocalTime endTime, Instructor instructor, Activity activity, int capacity) {
        ScheduledActivity scheduledActivity = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId);
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be empty!");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be before the current date!");
        }
        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be empty!");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be before end time!");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be empty!");
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
     * @param date
     * @param startTime
     * @param endTime
     * @return ScheduledActivity
     */
    @Transactional
    public ScheduledActivity deleteScheduledActivity(int scheduledActivityId) {
        if (scheduledActivityId <= 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        ScheduledActivity scheduledActivity = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled Activity does not exist!");
        }
        scheduledActivityRepository.delete(scheduledActivity);
        return scheduledActivity;
    }
}