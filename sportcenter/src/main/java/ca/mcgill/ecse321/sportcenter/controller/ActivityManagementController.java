import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.sportcenter.service.ActivityManagementService;

@RestController
public class ActivityManagementController {
    @Autowired
    private ActivityManagementService activityManagementService;

    /*
     * Create an activity
     * 
     * @param name
     * 
     * @param description
     * 
     * @param subcategory
     * 
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

    /*
     * Get an activity by its name
     * 
     * @param name
     * 
     * @return ActivityDto
     */
    @GetMapping(value = { "/activity/{name}", "/activity/{name}/" })
    public ActivityDto getActivity(@PathVariable("name") String name) throws IllegalArgumentException {
        Activity activity = activityManagementService.getActivity(name);
        return convertToDto(activity);
    }

    /*
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

    /*
     * Get all activities by subcategory
     * 
     * @param subcategory
     * 
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

    /*
     * Get all activities by approval status
     * 
     * @param isApproved
     * 
     * @return List<ActivityDto>
     */
    @GetMapping(value = { "/activities/{isApproved}", "/activities/{isApproved}/" })
    public List<ActivityDto> getActivitiesByIsApproved(@PathVariable("isApproved") boolean isApproved) {
        List<Activity> activities = activityService.getActivitiesByIsApproved(isApproved);
        List<ActivityDto> activityDtos = new ArrayList<ActivityDto>();
        for (Activity activity : activities) {
            activityDtos.add(convertToDto(activity));
        }
        return activityDtos;
    }

    /*
     * Update an activity
     * 
     * @param name
     * 
     * @param description
     * 
     * @param subcategory
     * 
     * @return ActivityDto
     */
    @PutMapping(value = { "/activity/update/{name}/{newName}/{newDescription}/{newSubcategory}",
            "/activity/update/{name}/{newName}/{newDescription}/{newSubcategory}/" })
    public ActivityDto updateActivity(@PathVariable("name") String name, @PathVariable("newName") String newName,
            @PathVariable("newDescription") String newDescription,
            @PathVariable("newSubcategory") Activity.ClassCategory newSubcategory) throws IllegalArgumentException {
        Activity activity = activityService.updateActivity(name, newName, newDescription, newSubcategory);
        return convertToDto(activity);
    }

    /*
     * Delete an activity
     * 
     * @param name
     */
    @DeleteMapping(value = { "/activity/delete/{name}", "/activity/delete/{name}/" })
    public void deleteActivity(@PathVariable("name") String name) throws IllegalArgumentException {
        activityService.deleteActivity(name);
    }

    /*
     * Delete all activities
     */
    @DeleteMapping(value = { "/activities/delete", "/activities/delete/" })
    public void deleteAllActivities() throws IllegalArgumentException {
        activityService.deleteAllActivities();
    }

    /*
     * Activity approval
     */
    @PutMapping(value = { "/activity/approve/{name}", "/activity/approve/{name}/" })
    public ActivityDto approveActivity(@PathVariable("name") String name) throws IllegalArgumentException {
        Activity activity = activityService.approveActivity(name);
        return convertToDto(activity);
    }

    /*
     * Activity disapproval
     */
    @PutMapping(value = { "/activity/disapprove/{name}", "/activity/disapprove/{name}/" })
    public ActivityDto disapproveActivity(@PathVariable("name") String name) throws IllegalArgumentException {
        Activity activity = activityService.disapproveActivity(name);
        return convertToDto(activity);
    }

    /*
     * Convert an activity to a DTO
     */
    public static ActivityManagementDto convertToDto(Activity activity) {
        return new ActivityManagementDto(activity.getName(), activity.getDescription(), activity.getSubcategory());
    }

    /*
     * Convert a list of activities to a list of DTOs
     */
    public static List<ActivityManagementDto> convertToDto(List<Activity> activities) {
        List<ActivityManagementDto> activityDtos = new ArrayList<ActivityManagementDto>();
        for (Activity activity : activities) {
            activityDtos.add(convertToDto(activity));
        }
        return activityDtos;
    }
}
