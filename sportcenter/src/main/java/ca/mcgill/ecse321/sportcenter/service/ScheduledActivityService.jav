package ca.mcgill.ecse321.sportcenter.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.model.Owner;

import ca.mcgill.ecse321.sportcenter.model.Activity;

public class ScheduledActivityService{

    //Need to know all the different autowired that need to get here
    @Autowired
    ActivityRepository activityRepository;
    // Know wtf this is
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
    /**
     * Create an activity where only the instructor can create an activity
     * @return Activity
     * 
     * @Author FabianSaldana
     */
    public List<Activity> getActivities() {
        return toList(activityRepository.findAll());
    }
    /**
     * Create an activity where only the instructor can create an activity
     * @param name
     * @param description
     * @param subcategory
     * @return Activity
     */
    @Transactional
    public ScheduledActivity createScheduledActivity(String name, String description, ClassCategory subcategory){
        //create a scheduled activity 
        //Change params
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        if (description == null || description.trim().length() == 0) {
            throw new IllegalArgumentException("Description cannot be empty!");
        }
        if (subcategory == null) {
            throw new IllegalArgumentException("Subcategory cannot be empty!");
        }
        ScheduledActivity scheduledActivity = new ScheduledActivity();
        scheduledActivity.setName(name);
        scheduledActivity.setDescription(description);
        scheduledActivity.setSubcategory(subcategory);
        scheduledActivityRepository.save(scheduledActivity);
        return activity;
    } 
    //
    public ScheduledActivity updateScheduledActivity(String name, String newName, String newDescription, ClassCategory newSubcategory){
        //modify the activity once scheduled
        //Amount of instructors 
        //Date,timeslot, duration
        ScheduledActivity scheduledActivity = scheduledActivityRepository.findScheduledActivityByName(name);
        scheduledActivity.setName(name);
        scheduledActivity.setDescription(description);
        scheduledActivity.setSubcategory(subcategory);
        scheduledActivityRepository.save(scheduledActivity);
        return activity;
    } 
    /**
     * Create an activity where only the instructor can create an activity
     * @param name
     * @param description
     * @param subcategory
     * @return Activity
     */
    public ScheduledActivity deleteScheduledActivity(String name){
        //delete activity once scheduled
        //Need to take the functions from the exceptions file to do throw new ...
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        else{
            ScheduledActivity scheduledActivity = scheduledActivityRepository.findScheduledActivityByName(name);
            if (scheduledActivity == null) {
                throw new IllegalArgumentException("Activity does not exist!");
            }
            else{
                activityRepository.delete(scheduledActivity);
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
    /**
     * Get all activities by approval status
     * @param isApproved
     * @return List<Activity>
     **/
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
    /**
     * Check activity uniqueness
     * @param name
     * @return Activity
     */
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

}