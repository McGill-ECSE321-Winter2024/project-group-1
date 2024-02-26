package ca.mcgill.ecse321.sportcenter.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;


public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;

    public List<Activity> getActivities() {
        return toList(activityRepository.findAll());
    }
    /**
     * Get an activity by its name (primary key)
     * @param name
     * @return Activity
     * 
     * @Author Andrew Nemr
     */
    @Transactional
    public Activity getActivity(String name) {
        if (name == null || name.trim().length() == 0) {//trim removes any whitespace
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        return activityRepository.findActivityByName(name);
    }

    /**
     * Create an activity
     * @param name
     * @param description
     * @param isApproved
     * @param subcategory
     * @return Activity
     */
    @Transactional
    public Activity createActivity(String name, String description, Boolean isApproved, ClassCategory subcategory) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setIsApproved(isApproved);
        activity.setSubcategory(subcategory);
        activity.setDescription(description);

        activityRepository.save(activity);
        return activity;
    }
    /**
     * Get all activities
     * @return List<Activity>
     */
    @Transactional
    public List<Activity> getAllActivities() {
        return toList(activityRepository.findAll());
    }

    /**
     * Update an activity's name
     * @param name
     * @param newName
     * @return Activity
     **/
    @Transactional
    public Activity updateActivityName(String name, String newName) {
        Activity activity = activityRepository.findActivityByName(name);
        activity.setName(newName);
        activityRepository.save(activity);
        return activity;
    }

    /**
     * Update an activity's description
     * @param name
     * @param newDescription
     * @return Activity
     **/
    @Transactional
    public Activity updateActivityDescription(String name, String newDescription) {
        Activity activity = activityRepository.findActivityByName(name);
        activity.setDescription(newDescription);
        activityRepository.save(activity);
        return activity;
    }

    /**
     * Update an activity's approval status
     * @param name
     * @param newIsApproved
     * @return Activity
     **/
    @Transactional
    public Activity updateActivityIsApproved(String name, Boolean newIsApproved) {
        Activity activity = activityRepository.findActivityByName(name);
        activity.setIsApproved(newIsApproved);
        activityRepository.save(activity);
        return activity;
    }

    /**????????????????????????????????????
     * Update an activity's subcategory
     * @param name
     * @param newSubcategory
     * @return Activity
     **/
    @Transactional
    public Activity updateActivitySubcategory(String name, ClassCategory newSubcategory) {
        Activity activity = activityRepository.findActivityByName(name);
        activity.setSubcategory(newSubcategory);
        activityRepository.save(activity);
        return activity;
    }

    /**
     * Delete an activity
     * @param name
     * @return void
     **/
    @Transactional
    public void deleteActivity(String name) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        else{
            Activity activity = activityRepository.findActivityByName(name);
            activityRepository.delete(activity);
        }
    }

    /**
     * Delete all activities
     * @return void
     **/
    @Transactional
    public void deleteAllActivities() {
        activityRepository.deleteAll();
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

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }


}
