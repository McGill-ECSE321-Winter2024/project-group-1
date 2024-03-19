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
//import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity.ClassCategory;

/* 
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
*/

public class ScheduledActivityService{

    //Need to know all the different autowired that need to get here
    @Autowired
    ScheduledActivityRepository scheduledActivityRepository;

    /*
    @Autowired
    ActivityRepository activityRepository;
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
     * @return SchedueledActivity
     * 
     * @Author FabianSaldana
     */
    public List<ScheduledActivity> getScheduledActivities() {
        return toList(scheduledActivityRepository.findAll());
    }
    /**
     * Get an activity by its Id (primary key)
     * @param scheduledActivityId
     * @return ScheduledActivity
     * 
     * @Author Fabian Saldana
     */
    @Transactional
    public ScheduledActivity getScheduledActivity(int scheduledActivityId) {
        if (scheduledActivityId == -1) {
            throw new IllegalArgumentException("Id cannot be empty!");
        }
        return scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId);
    }
    /**
     * Create an activity where only the instructor can create an activity
     * @param scheduledActivityId
     * @param date
     * @param startTime
     * @param endTime
     * @param instructorId
     * @param activityName
     * 
     * @return ScheduledActivity
     */
    @Transactional
    public ScheduledActivity createScheduledActivity(int scheduledActivityId, LocalDate date, LocalTime startTime, LocalTime endTime, Instructor instructor, Activity activity) {
        //create a scheduled activity 
        //Change params
        if (scheduledActivityId == -1) {
            throw new IllegalArgumentException("Id cannot be null!");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be empty!");
        }
        if (startTime == null || startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be empty!");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be empty!");
        }
        if (instructor == null) {
            throw new IllegalArgumentException("End time cannot be empty!");
        }
        if (activity == null) {
            throw new IllegalArgumentException("End time cannot be empty!");
        }

        ScheduledActivity scheduledActivity = new ScheduledActivity();
        scheduledActivity.setScheduledActivityId(scheduledActivityId);
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setStartTime(endTime);
        scheduledActivity.setSupervisor(instructor);
        scheduledActivity.setActivity(activity);

        scheduledActivityRepository.save(scheduledActivity);
        return scheduledActivity;
    } 

    @Transactional
    public ScheduledActivity updateScheduledActivity(int scheduledActivityId, LocalDate date, LocalTime startTime, LocalTime endTime, Instructor instructor, Activity activity){
        ScheduledActivity scheduledActivity = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId);
        scheduledActivity.setScheduledActivityId(scheduledActivityId);
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setStartTime(endTime);
        scheduledActivity.setSupervisor(instructor);
        scheduledActivityRepository.save(scheduledActivity);
        return scheduledActivity;
    } 
    /**
     * Delete a scheduled activity
     * @param scheduledActivityId
     * @param date
     * @param startTime
     * @param endTime
     * @return ScheduledActivity
     */
    @Transactional
    public ScheduledActivity deleteScheduledActivity(int scheduledActivityId){
        //delete activity once scheduled
        //Need to take the functions from the exceptions file to do throw new ...
        if (scheduledActivityId == -1) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        else{
            ScheduledActivity scheduledActivity = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId);
            if (scheduledActivity == null) {
                throw new IllegalArgumentException("Activity does not exist!");
            }
            else{
                scheduledActivityRepository.delete(scheduledActivity);
                return scheduledActivity;
            }
        }
    }
}