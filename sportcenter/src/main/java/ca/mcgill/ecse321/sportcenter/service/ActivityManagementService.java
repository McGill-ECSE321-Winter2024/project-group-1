package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import jakarta.transaction.Transactional;

/**
 * Service class for the Activity entity
 * 
 * @author: Mathias Pacheco Lemina
 */
@Service
public class ActivityManagementService {
    @Autowired
    ActivityRepository activityRepository;

<<<<<<< Updated upstream
=======
    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    OwnerRepository ownerRepository;

>>>>>>> Stashed changes
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
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty!");
        }
        if (subcategory == null) {
            throw new IllegalArgumentException("Subcategory cannot be empty!");
        }
        if (activityRepository.findActivityByName(name) != null) {
            throw new IllegalArgumentException("Activity already exists!");
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

        Activity activity = activityRepository.findActivityByName(name); // XXXXXXXX needs to be tested XXXXXXXX
        if (activity == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        }

        return activity;
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

        if (subcategory == null) {
            throw new IllegalArgumentException("Subcategory cannot be empty!");
        }

        for (Activity activity : activities) {
            if (activity.getSubCategory().equals(subcategory)) {
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

        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        if (newName == null || newName.trim().length() == 0) {
            throw new IllegalArgumentException("New name cannot be empty!");
        }
        if (newDescription == null || newDescription.trim().length() == 0) {
            throw new IllegalArgumentException("New description cannot be empty!");
        }
        if (newSubcategory == null) {
            throw new IllegalArgumentException("New subcategory cannot be empty!");
        }

        Activity activity = activityRepository.findActivityByName(name);

        if (activity == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        }
        if (activity.getIsApproved()) {
            throw new IllegalArgumentException("Activity is already approved!");
        }

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
        if (name == null || name.trim().length() == 0 || name.contains("")) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }

        Activity activity = activityRepository.findActivityByName(name);
        if (activity == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        }

        // Delete all scheduled activities associated with the activity
        ScheduledActivityManagementService scheduledActivityManagementService = new ScheduledActivityManagementService();
        for (ScheduledActivity scheduledActivity : scheduledActivityManagementService
                .getAllScheduledActivitiesByActivityName(activity.getName())) {
            scheduledActivityManagementService.deleteScheduledActivity(scheduledActivity.getScheduledActivityId());
        }

        activityRepository.delete(activity);
    }

    /**
     * Set isApproved of activity to true
     * 
     * @param activityName
     * @author Mathias Pacheco Lemina
     */
    @Transactional
    public Activity approveActivity(String activityName) {
        if (activityName == null || activityName.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity name cannot be empty!");
        }

        Activity activity = activityRepository.findActivityByName(activityName);

        if (activity == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        }

        if (activity.getIsApproved()) {
            throw new IllegalArgumentException("Activity is already approved!");
        }

        activity.setIsApproved(true);
        activityRepository.save(activity);
        return activity;
    }

    /**
     * Set isApproved of activity to false
     * 
     * @param activityName
     * @author Mathias Pacheco Lemina
     */
    @Transactional
    public Activity disapproveActivity(String activityName) {
        Activity activity = activityRepository.findActivityByName(activityName);

        if (activityName == null || activityName.trim() == "") {
            throw new IllegalArgumentException("Activity name cannot be empty!");
        }
        if (activityRepository.findActivityByName(activityName) == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        }
        // XXXXXXXXXXXXXXXXX Need to rethink this XXXXXXXXXXXXXXXXX
        if (!activity.getIsApproved()) {
            throw new IllegalArgumentException("Activity is already not approved!");
        }
        activity.setIsApproved(false);
        activityRepository.save(activity);
        return activity;
    }
}