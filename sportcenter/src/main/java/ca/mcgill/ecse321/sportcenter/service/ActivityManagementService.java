package ca.mcgill.ecse321.sportcenter.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;

/*
 * Service class for the Activity entity
 * @author: Mathias Pacheco Lemina
 */
public class ActivityManagementService {
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    OwnerRepository ownerRepository;

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
        } else if (newName == null || newName.trim().length() == 0) {
            throw new IllegalArgumentException("New name cannot be empty!");
        } else if (newDescription == null || newDescription.trim().length() == 0) {
            throw new IllegalArgumentException("New description cannot be empty!");
        } else if (newSubcategory == null) {
            throw new IllegalArgumentException("New subcategory cannot be empty!");
        }

        Activity activity = activityRepository.findActivityByName(name);

        if (activity == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        } else if (activity.getIsApproved()) {
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

        activityRepository.delete(activity);
    }

    /**
     * Delete all activities
     **/
    @Transactional
    public void deleteAllActivities() {
        activityRepository.deleteAll();
    }

    /**
     * Propose an acitvity to the owner.
     * 
     * @return Activity
     * @author Anslean AJ
     */
    @Transactional
    public Activity proposeActivity(String name, String description, ClassCategory subcategory) {
        ActivityService activityService = new ActivityService();

        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty!");
            } else if (description == null || description.trim().isEmpty()) {
                throw new IllegalArgumentException("Description cannot be empty!");
            } else if (subcategory == null) {
                throw new IllegalArgumentException("Subcategory cannot be empty!");
            }
            return activityService.createActivity(name, description, subcategory);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid inputs!");
        }
    }

    /**
     * Make a new scheduled activity to the system.
     * 
     * @return ScheduledActivity
     * @author Anslean AJ
     */
    @Transactional
    public ScheduledActivity makeScheduledActivity(LocalDate date, LocalTime startTime, LocalTime endTime,
            Instructor instructor, Activity activity, int capacity) {

        ScheduledActivityService scheduledActivityService = new ScheduledActivityService();

        try {
            if (date == null) {
                throw new IllegalArgumentException("Date cannot be empty!");
            } else if (startTime == null) {
                throw new IllegalArgumentException("Start time cannot be empty!");
            } else if (endTime == null) {
                throw new IllegalArgumentException("End time cannot be empty!");
            } else if (instructor == null) {
                throw new IllegalArgumentException("Instructor cannot be empty!");
            } else if (activity == null) {
                throw new IllegalArgumentException("Activity cannot be empty!");
            } else if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity cannot be empty!");
            }

            return scheduledActivityService.createScheduledActivity(date, endTime, endTime, instructor, activity,
                    capacity);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid inputs!");
        }
    }

    /**
     * Set isApproved of activity to true
     * 
     * @param activityName
     * @author Mathias Pacheco Lemina
     */
    @Transactional
    public void approveActivity(String activityName) {
        Activity activity = activityRepository.findActivityByName(activityName);

        if (activityName == "" || activityName == null) {
            throw new IllegalArgumentException("Activity name cannot be empty!");
        } else if (activity == null) {
            System.out.println("Activity does not exist!");
        } else if (activity.getIsApproved()) {
            throw new IllegalArgumentException("Activity is already approved!");
        } else {
            activity.setIsApproved(true);
            activityRepository.save(activity);
        }
    }

    /**
     * Set isApproved of activity to false
     * 
     * @param activityName
     * @author Mathias Pacheco Lemina
     */
    @Transactional
    public void disapproveActivity(String activityName) {
        Activity activity = activityRepository.findActivityByName(activityName);

        if (activityName.trim() == "" || activityName == null) {
            throw new IllegalArgumentException("Activity name cannot be empty!");
        } else if (activityRepository.findActivityByName(activityName) == null) {
            System.out.println("Activity does not exist!");
        } else if (!activity.getIsApproved()) {
            throw new IllegalArgumentException("Activity is already not approved!");
        } else {
            activity.setIsApproved(false);
            activityRepository.save(activity);
        }
    }
}