package ca.mcgill.ecse321.sportcenter.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.model.Owner;

import ca.mcgill.ecse321.sportcenter.model.Activity;

public class ScheduledActivityService{

    @Autowired
    ActivityRepository activityRepository;

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    public List<Activity> getActivities() {
        return toList(activityRepository.findAll());
    }

    public ScheduledActivity setScheduledActivity(){
        //get the all activities
        //set it on the table (duration, date, instructor)
        
        
        
    } 

    public ScheduledActivity modifyScheduledActivity(){
        //modify the activity once scheduled
    } 

    public ScheduledActivity deleteScheduledActivity(){
        //delete activity once scheduled

    }
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

    //check activity uniqueness
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