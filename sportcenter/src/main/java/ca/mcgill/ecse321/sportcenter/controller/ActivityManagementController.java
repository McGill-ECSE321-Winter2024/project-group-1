package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.ActivityDto;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.service.ActivityManagementService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * Rest controller for the activity management
 * 
 * @Author Mathias Lamina & Patrick Zacharia & Fabian Saldana
 */
@RestController
public class ActivityManagementController {
    @Autowired
    private ActivityManagementService activityManagementService;

    /**
     * Create an activity
     * 
     * @param name
     * @param description
     * @param subcategory
     * @return ActivityDto
     */
    @PostMapping(value = { "/createActivity/{name}/{description}/{subcategory}",
            "/createActivity/{name}/{description}/{subcategory}/" })
    public ActivityDto createActivity(@PathVariable("name") String name,
            @PathVariable("description") String description,
            @PathVariable("subcategory") Activity.ClassCategory subcategory) throws IllegalArgumentException {
        Activity activity = activityManagementService.createActivity(name, description, subcategory);
        return convertToDto(activity);
    }

    /**
     * Get an activity by its name
     * 
     * @param name
     * @return ActivityDto
     */
    @GetMapping(value = { "/activity/{name}", "/activity/{name}/" })
    public ActivityDto getActivity(@PathVariable("name") String name) throws IllegalArgumentException {
        Activity activity = activityManagementService.getActivity(name);
        return convertToDto(activity);
    }

    /**
     * Get all activities
     * 
     * @return List<ActivityDto>
     */
    @GetMapping(value = { "/activities", "/activities/" })
    public List<ActivityDto> getActivities() {
        List<Activity> activities = activityManagementService.getActivities();
        List<ActivityDto> activityDtos = new ArrayList<ActivityDto>();
        for (Activity activity : activities) {
            activityDtos.add(convertToDto(activity));
        }
        return activityDtos;
    }

    /**
     * Get all activities by activity subcategory
     * 
     * @param subcategory
     * @return List<ActivityDto>
     */
    @GetMapping(value = { "/activities/{subcategory}", "/activities/{subcategory}/" })
    public List<ActivityDto> getActivitiesBySubcategory(
            @PathVariable("subcategory") Activity.ClassCategory subcategory) {
        List<Activity> activities = activityManagementService.getActivitiesBySubcategory(subcategory);
        List<ActivityDto> activityDtos = new ArrayList<ActivityDto>();
        for (Activity activity : activities) {
            activityDtos.add(convertToDto(activity));
        }
        return activityDtos;
    }

    /**
     * Get all activities by approval status
     * 
     * @param isApproved
     * @return List<ActivityDto>
     */
    @GetMapping(value = { "/activities/{isApproved}", "/activities/{isApproved}/" })
    public List<ActivityDto> getActivitiesByIsApproved(@PathVariable("isApproved") boolean isApproved) {
        List<Activity> activities = activityManagementService.getActivitiesByIsApproved(isApproved);
        List<ActivityDto> activityDtos = new ArrayList<ActivityDto>();
        for (Activity activity : activities) {
            activityDtos.add(convertToDto(activity));
        }
        return activityDtos;
    }

    /**
     * Update an activity
     * 
     * @param name
     * @param description
     * @param subcategory
     * @return ActivityDto
     */
    @PutMapping(value = { "/activity/update/{name}/{newName}/{newDescription}/{newSubcategory}",
            "/activity/update/{name}/{newName}/{newDescription}/{newSubcategory}/" })
    public ActivityDto updateActivity(@PathVariable("name") String name, @PathVariable("newName") String newName,
            @PathVariable("newDescription") String newDescription,
            @PathVariable("newSubcategory") Activity.ClassCategory newSubcategory) throws IllegalArgumentException {
        Activity activity = activityManagementService.updateActivity(name, newName, newDescription, newSubcategory);
        return convertToDto(activity);
    }

    /**
     * Delete an activity using its name
     * 
     * @param name
     */
    @DeleteMapping(value = { "/activity/delete/{name}", "/activity/delete/{name}/" })
    public void deleteActivity(@PathVariable("name") String name) throws IllegalArgumentException {
        activityManagementService.deleteActivity(name);
    }

    /**
     * Delete all activities
     */
    @DeleteMapping(value = { "/activities/delete", "/activities/delete/" })
    public void deleteAllActivities() throws IllegalArgumentException {
        activityManagementService.deleteAllActivities();
    }

    /**
     * Approve an activity
     * 
     * @param name
     */
    @PutMapping(value = { "/activity/approve/{name}", "/activity/approve/{name}/" })
    public void approveActivity(@PathVariable("name") String name) throws IllegalArgumentException {
        activityManagementService.approveActivity(name);
    }

    /**
     * Disapprove an activity
     * 
     * @param name
     */
    @PutMapping(value = { "/activity/disapprove/{name}", "/activity/disapprove/{name}/" })
    public void disapproveActivity(@PathVariable("name") String name) throws IllegalArgumentException {
        activityManagementService.disapproveActivity(name);
    }

    /**
     * Convert activity to activity dto
     * 
     * @param activity
     * @return ActivityDto
     */
    public static ActivityDto convertToDto(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("There is no such activity!");
        }
        ActivityDto activityDto = new ActivityDto(activity.getSubCategory(), activity.getName(),
                activity.getIsApproved(), activity.getDescription());
        return activityDto;
    }

    /**
     * Convert a list of activities to a list of activities dtos
     * 
     * @param activities
     * @return List<ActivityDto>
     */
    public static List<ActivityDto> convertToDto(List<Activity> activities) {
        List<ActivityDto> activityDtos = new ArrayList<ActivityDto>();
        for (Activity activity : activities) {
            activityDtos.add(convertToDto(activity));
        }
        return activityDtos;
    }
}
