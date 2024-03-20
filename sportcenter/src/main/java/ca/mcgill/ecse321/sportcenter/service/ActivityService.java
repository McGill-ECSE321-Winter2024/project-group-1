package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;

/**
 * Service class for the Activity entity
 * 
 * @Author Andrew Nemr
 */
public class ActivityService {
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
     * @param name
     * @param description
     * @param subcategory
     * @return Activity
     **/
    @Transactional
    public Activity createActivity(String name, String description, ClassCategory subcategory) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        if (description == null || description.trim().length() == 0) {
            throw new IllegalArgumentException("Description cannot be empty!");
        }
        if (subcategory == null) {
            throw new IllegalArgumentException("Subcategory cannot be empty!");
        }
        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        activity.setSubCategory(subcategory);
        activity.setIsApproved(false);
        activityRepository.save(activity);
        return activity;
    }

    /**
     * Get an activity by its name (primary key)
     * 
     * @param name
     * @return Activity
     **/
    @Transactional
    public Activity getActivity(String name) {
        if (name == null || name.trim().length() == 0) {// trim removes any whitespace
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        return activityRepository.findActivityByName(name);
    }

    /**
     * Get all activities
     * 
     * @return List<Activity>
     **/
    public List<Activity> getActivities() {
        return toList(activityRepository.findAll());
    }

    /**
     * Get all activities by subcategory
     * 
     * @param subcategory
     * @return List<Activity>
     **/
    @Transactional
    public List<Activity> getActivitiesBySubcategory(ClassCategory subcategory) {
        List<Activity> activities = toList(activityRepository.findAll());
        List<Activity> activitiesBySubcategory = new ArrayList<Activity>();
        for (Activity activity : activities) {
            if (activity.getSubCategory() == subcategory) {
                activitiesBySubcategory.add(activity);
            }
        }
        return activitiesBySubcategory;
    }

    /**
     * Get all activities by approval status
     * 
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
     * Update an activity
     * 
     * @param name
     * @param newName
     * @return Activity
     **/
    @Transactional
    public Activity updateActivity(String name, String newName, String newDescription, ClassCategory newSubcategory) {
        Activity activity = activityRepository.findActivityByName(name);
        activity.setName(newName);
        activity.setDescription(newDescription);
        activity.setSubCategory(newSubcategory);
        activityRepository.save(activity);
        return activity;
    }

    /**
     * Delete an activity
     * 
     * @param name
     * @return Activity
     **/
    @Transactional
    public void deleteActivity(String name) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        Activity activity = activityRepository.findActivityByName(name);
        if (activity == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        }
        activityRepository.delete(activity);
    }

    /**
     * Delete all activities
     **/
    @Transactional
    public void deleteAllActivities() {
        activityRepository.deleteAll();
    }
}