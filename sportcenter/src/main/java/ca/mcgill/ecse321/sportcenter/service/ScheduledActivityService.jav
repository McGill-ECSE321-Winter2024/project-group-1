package ca.mcgill.ecse321.sportcenter.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity.ClassCategory;

/* 
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
*/

public class ScheduledActivityService{

    //Need to know all the different autowired that need to get here
    @Autowired
    ScheduledActivityRepository scheduledActivityRepository;

    @Autowired
    ActivityRepository activityRepository;

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
        return toList(schduledActivityRepository.findAll());
    }
    /**
     * Get an activity by its Id (primary key)
     * @param scheduledActivityId
     * @return ScheduledActivity
     * 
     * @Author Andrew Nemr
     */
    @Transactional
    public ScheduledActivity getScheduledActivity(int scheduledActivityId) {
        if (scheduledActivityId == null || scheduledActivityId.trim().length() == 0) {
            throw new IllegalArgumentException("Id cannot be empty!");
        }
        return scheduledActivityRepository.findScheduledActivityById(scheduledActivityId);
    }
    /**
     * Create an activity where only the instructor can create an activity
     * @param scheduledActivityId
     * @param date
     * @param startTime
     * @param endTime
     * @return ScheduledActivity
     */
    @Transactional
    public ScheduledActivity createScheduledActivity(int scheduledActivityId, Date date, Time startTime, Time endTime){
        //create a scheduled activity 
        //Change params
        if (scheduledActivityId == null || scheduledActivityId.trim().length() == 0) {
            throw new IllegalArgumentException("Id cannot be null!");
        }
        if (date == null || date.trim().length() == 0) {
            throw new IllegalArgumentException("Date cannot be empty!");
        }
        if (startTime == null || startTime > endTime) {
            throw new IllegalArgumentException("Start time cannot be empty!");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be empty!");
        }
        ScheduledActivity scheduledActivity = new ScheduledActivity();
        scheduledActivity.setScheduledActivityId(scheduledActivityId);
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setStartTime(endTime);
        scheduledActivityRepository.save(scheduledActivity);
        return scheduledActivity;
    } 
    
    public ScheduledActivity updateScheduledActivity(int scheduledActivityId, Date date, Time startTime, Time endTime){
        ScheduledActivity scheduledActivity = scheduledActivityRepository.findScheduledActivityById(scheduledActivityId);
        scheduledActivity.setScheduledActivityId(name);
        scheduledActivity.setDate(date);
        scheduledActivity.setStartTime(startTime);
        scheduledActivity.setStartTime(endTime);
        scheduledActivityRepository.save(scheduledActivity);
        return activity;
    } 
    /**
     * Delete a scheduled activity
     * @param scheduledActivityId
     * @param date
     * @param startTime
     * @param endTime
     * @return ScheduledActivity
     */
    public ScheduledActivity deleteScheduledActivity(int scheduledActivityId){
        //delete activity once scheduled
        //Need to take the functions from the exceptions file to do throw new ...
        if (scheduledActivityId == null || scheduledActivityId.trim().length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        else{
            ScheduledActivity scheduledActivity = scheduledActivityRepository.findScheduledActivityById(scheduledActivityId);
            if (scheduledActivity == null) {
                throw new IllegalArgumentException("Activity does not exist!");
            }
            else{
                scheduledActivityRepository.delete(scheduledActivity);
                return scheduledActivity;
            }
        }

    }

    //The following code is from the Activity service
    /**
     * Get all activities by subcategory
     * @param subcategory
     * @return List<Activity>
     **/
    /*
    @Transactional
    public List<Activity> getActivitiesBySubcategory(ClassCategory subcategory) {
        List<Activity> activities = toList(activityRepository.findAll());
        List<Activity> activitiesBySubcategory = new ArrayList<Activity>();
        for (Activity activity : activities) {
            if (activity.getSubcategory() == subcategory) {
                activitiesBySubcategory.add(activity);
            }
        }
        return activitiesBySubcategory;
    }
    */
    /**
     * Get all activities by approval status
     * @param isApproved
     * @return List<Activity>
     **/
    /*
    @Transactional
    public List<Activity> getActivitiesByIsApproved(Boolean isApproved) {
        List<Activity> activities = toList(activityRepository.findAll());
        List<Activity> activitiesByIsApproved = new ArrayList<Activity>();
        for (Activity activity : activities) {
            if (activity.getIsApproved() == isApproved) {
                activitiesByIsApproved.add(activity);
            }
        }
        return activitiesByIsApproved;
    }
    */
    
    /**
     * Check activity uniqueness
     * @param name
     * @return Activity
     */
    /*
    public boolean checkActivityUniqueness(String name) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        Activity activity = activityRepository.findActivityByName(name);
        if (activity == null) {
            return true;
        }
        else {
            return false;
        }
    }
    */
}