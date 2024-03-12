package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.ActivityDto;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.service.ActivityService;

public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * Create an activity
     * @param name
     * @param description
     * @param price
     * @return
     * @Author Andrew Nemr
     */
    @PostMapping(value = {"/createActivity/{name}/{description}/{subcategory}", "/createActivity/{name}/{description}/{subcategory}/"})
    public ResponseEntity<?> createActivity(@PathVariable("name") String name, @PathVariable("description") String description, @PathVariable("subcategory") Activity.ClassCategory subcategory) {
        try {
            Activity activity = activityService.createActivity(name, description, subcategory);
            return ResponseEntity.ok(ActivityDto.convertToDto(activity));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get an activity by its name
     * @param name
     * @return
     */
    @GetMapping(value = {"/activity/{name}", "/activity/{name}/"})
    public ResponseEntity<?> getActivity(@PathVariable("name") String name) {
        try {
            Activity activity = activityService.getActivity(name);
            return ResponseEntity.ok(ActivityDto.convertToDto(activity));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get all activities
     * @return
     */
    @GetMapping(value = {"/activities", "/activities/"})
    public List<ActivityDto> getActivities() {
        List<Activity> activities = activityService.getActivities();
        List<ActivityDto> activityDtos = new ArrayList<ActivityDto>();
        for (Activity activity : activities) {
            activityDtos.add(ActivityDto.convertToDto(activity));
        }
        return activityDtos;
    }

    /**
     * Update an activity
     * @param name
     * @param description
     * @param subcategory
     * @return
     */
    @PutMapping(value = {"/activity/update/{name}/{newName}/{newDescription}/{newSubcategory}", "/activity/update/{name}/{newName}/{newDescription}/{newSubcategory}/"})
    public ResponseEntity<?> updateActivity(@PathVariable("name") String name, @PathVariable("newName") String newName, @PathVariable("newDescription") String newDescription, @PathVariable("newSubcategory") Activity.ClassCategory newSubcategory) {
        try {
            Activity activity = activityService.updateActivity(name, newName, newDescription, newSubcategory);
            return ResponseEntity.ok(ActivityDto.convertToDto(activity));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete an activity by its name
     * @param name
     */
    @DeleteMapping(value = {"/activity/delete/{name}", "/activity/delete/{name}/"})
    public ActivityDto deleteActivity(@PathVariable("name") String name) {
        Activity activity = activityService.deleteActivity(name);
        return ActivityDto.convertToDto(activity);
    }
    /**
     * Delete all activities
     */
    @DeleteMapping(value = {"/activities/delete", "/activities/delete/"})
    public void deleteAllActivities() {
        activityService.deleteAllActivities();
    }
    
}
