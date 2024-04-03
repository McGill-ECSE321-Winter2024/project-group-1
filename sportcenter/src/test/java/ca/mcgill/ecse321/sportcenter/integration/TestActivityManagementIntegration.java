package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dto.ActivityDto;
import ca.mcgill.ecse321.sportcenter.dto.ErrorDto;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@SuppressWarnings("null")
public class TestActivityManagementIntegration {

    @Autowired
    private TestRestTemplate activity;

    @Autowired
    private ActivityRepository activityRepository;

    // @Autowired
    // private AccountRepository accountRepository;

    private static final String NULL_NAME = null;
    private static final String ACTIVITY_NAME = "Olympic Weightlifting";
    private static final String NEW_ACTIVITY_NAME = "Heavy Resistance III";
    private static final String NEW_ACTIVITY_NAME_2 = "Calisthenics";

    private static final String DESCRIPTION = "Master the art of Clean And Jerks and be explosive.";
    private static final String ANOTHER_DESCRIPTION = "Classic compound and dumbbell exercises";
    private static final String ANOTHER_DESCRIPTION_2 = "Bodyweight training and exercises";

    private static final ClassCategory CATEGORY = ClassCategory.Strength;
    private static final ClassCategory ANOTHER_CATEGORY = ClassCategory.Cardio;

    private static final boolean NOT_APPROVED = false;
    private static final boolean APPROVED = true;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        activityRepository.deleteAll();
    }

    @Test
    public void testCreateActivity() {

        ActivityDto request = new ActivityDto(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);

        ResponseEntity<ActivityDto> response = activity.postForEntity("/activity/" + ACTIVITY_NAME, request,
                ActivityDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");

        ActivityDto responseActivity = response.getBody();

        assertNotNull(responseActivity);
        assertEquals(responseActivity.getName(), ACTIVITY_NAME);
        assertEquals(responseActivity.getDescription(), DESCRIPTION);
        assertEquals(responseActivity.getIsApproved(), NOT_APPROVED);
        assertEquals(responseActivity.getSubcategory(), CATEGORY);

        assertNotNull(activityRepository.findActivityByName(ACTIVITY_NAME));

    }

    @Test
    public void testCreateActivityAndApprove() {

        ActivityDto request = new ActivityDto(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);

        ResponseEntity<ActivityDto> response = activity.postForEntity("/activity/" + ACTIVITY_NAME, request,
                ActivityDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");

        ActivityDto responseActivity = response.getBody();

        assertNotNull(responseActivity);
        assertEquals(responseActivity.getName(), ACTIVITY_NAME);
        assertEquals(responseActivity.getDescription(), DESCRIPTION);
        assertEquals(responseActivity.getIsApproved(), NOT_APPROVED);
        assertEquals(responseActivity.getSubcategory(), CATEGORY);

        assertNotNull(activityRepository.findActivityByName(ACTIVITY_NAME));

        String endpoint = "/activity/approve/" + ACTIVITY_NAME;

        ResponseEntity<ActivityDto> approvalResponse = activity.getForEntity(endpoint, ActivityDto.class);

        assertNotNull(approvalResponse);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ActivityDto approvedResponseActivity = response.getBody();

        assertNotNull(approvedResponseActivity);
        assertEquals(approvedResponseActivity.getName(), ACTIVITY_NAME);
        assertEquals(approvedResponseActivity.getDescription(), DESCRIPTION);
        assertEquals(approvedResponseActivity.getIsApproved(), APPROVED);
        assertEquals(approvedResponseActivity.getSubcategory(), CATEGORY);

    }

    @Test
    public void testCreateActivityInvalidUsername() {

        ActivityDto request = new ActivityDto(CATEGORY, NULL_NAME, NOT_APPROVED, DESCRIPTION);

        ResponseEntity<ErrorDto> response = activity.postForEntity("/activity/" + NULL_NAME, request, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorDto responseError = response.getBody();

        assertNotNull(responseError);
        assertEquals(1, responseError.getErrors().size());
        assertEquals("Name cannot be empty!", responseError.getErrors().get(0));

    }

    @Test
    public void testCreateActivityInvalidDescription() {

        ActivityDto request = new ActivityDto(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, NULL_NAME);

        ResponseEntity<ErrorDto> response = activity.postForEntity("/activity/" + ACTIVITY_NAME, request,
                ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorDto responseError = response.getBody();

        assertNotNull(responseError);
        assertEquals(1, responseError.getErrors().size());
        assertEquals("Description cannot be empty!", responseError.getErrors().get(0));

    }

    @Test
    public void testGetActivity() {

        Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
        activityRepository.save(newActivity);

        String endpoint = "/activity/" + ACTIVITY_NAME;

        ResponseEntity<ActivityDto> response = activity.getForEntity(endpoint, ActivityDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ActivityDto responseActivity = response.getBody();

        assertNotNull(responseActivity);
        assertEquals(responseActivity.getName(), ACTIVITY_NAME);
        assertEquals(responseActivity.getDescription(), DESCRIPTION);
        assertEquals(responseActivity.getIsApproved(), NOT_APPROVED);
        assertEquals(responseActivity.getSubcategory(), CATEGORY);

        assertNotNull(activityRepository.findActivityByName(ACTIVITY_NAME));

    }

    @Test
    public void testGetActivityBySubcategory() {

        Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
        activityRepository.save(newActivity);

        String endpoint = "/activities/" + CATEGORY;

        ResponseEntity<ActivityDto> response = activity.getForEntity(endpoint, ActivityDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ActivityDto responseActivity = response.getBody();

        assertNotNull(responseActivity);
        assertEquals(responseActivity.getName(), ACTIVITY_NAME);
        assertEquals(responseActivity.getDescription(), DESCRIPTION);
        assertEquals(responseActivity.getIsApproved(), NOT_APPROVED);
        assertEquals(responseActivity.getSubcategory(), CATEGORY);

        assertNotNull(activityRepository.findActivityByName(ACTIVITY_NAME));

    }

    @Test
    public void testGetActivityByIsApproved() {

        Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, APPROVED, DESCRIPTION);
        activityRepository.save(newActivity);

        // "/activities/{isApproved}"
        String endpoint = "/activities/" + APPROVED;

        ResponseEntity<ActivityDto> response = activity.getForEntity(endpoint, ActivityDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ActivityDto responseActivity = response.getBody();

        assertNotNull(responseActivity);
        assertEquals(responseActivity.getName(), ACTIVITY_NAME);
        assertEquals(responseActivity.getDescription(), DESCRIPTION);
        assertEquals(responseActivity.getIsApproved(), NOT_APPROVED);
        assertEquals(responseActivity.getSubcategory(), CATEGORY);

        assertNotNull(activityRepository.findActivityByName(ACTIVITY_NAME));

    }

    @Test
    public void testGetActivitiesSizeAndGetByIsApproved() {

        Activity newActivity1 = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
        Activity newActivity2 = new Activity(CATEGORY, NEW_ACTIVITY_NAME, APPROVED, ANOTHER_DESCRIPTION);
        Activity newActivity3 = new Activity(CATEGORY, NEW_ACTIVITY_NAME_2, APPROVED, ANOTHER_DESCRIPTION_2);
        activityRepository.save(newActivity1);
        activityRepository.save(newActivity2);
        activityRepository.save(newActivity3);

        // "/activities/{isApproved}"
        String endpoint = "/activities/" + APPROVED;

        ResponseEntity<ActivityDto[]> response = activity.getForEntity(endpoint, ActivityDto[].class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ActivityDto[] responseActivity = response.getBody();

        assertNotNull(responseActivity);
        // check if 3 activities were added
        assertEquals(responseActivity.length, 3);
        assertEquals(responseActivity[0].getIsApproved(), NOT_APPROVED);
        assertEquals(responseActivity[1].getIsApproved(), APPROVED);
        assertEquals(responseActivity[2].getIsApproved(), APPROVED);
        assertEquals(responseActivity[0].getName(), ACTIVITY_NAME);

        assertNotNull(activityRepository.findActivityByName(ACTIVITY_NAME));

    }

    @Test
    public void testGetActivityByWrongSubcategory() {

        Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
        activityRepository.save(newActivity);

        String endpoint = "/activity/" + ANOTHER_CATEGORY;

        ResponseEntity<ActivityDto> response = activity.getForEntity(endpoint, ActivityDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ActivityDto responseActivity = response.getBody();

        assertNotNull(responseActivity);
        assertNotEquals(responseActivity.getSubcategory(), ANOTHER_CATEGORY);
        assertEquals(activityRepository.findActivityBySubcategory(ANOTHER_CATEGORY).size(), 0);
        assertEquals(activityRepository.findActivityBySubcategory(CATEGORY).size(), 1);
        assertNotNull(activityRepository.findActivityByName(ACTIVITY_NAME)); // still check if the activity is still
                                                                             // there

    }

    @Test
    public void testUpdateActivityName() {

        ActivityDto activityDto = new ActivityDto(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);

        String endpoint = "/activity/update/" + ACTIVITY_NAME + "/" + NEW_ACTIVITY_NAME + "/" + DESCRIPTION + "/"
                + CATEGORY;

        ResponseEntity<ActivityDto> response = activity.postForEntity(endpoint, activityDto, ActivityDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ActivityDto responseActivity = response.getBody();

        assertNotNull(responseActivity);
        assertNotEquals(ACTIVITY_NAME, responseActivity.getName());
        assertEquals(responseActivity.getName(), NEW_ACTIVITY_NAME);
        assertEquals(responseActivity.getDescription(), DESCRIPTION);
        assertEquals(responseActivity.getIsApproved(), NOT_APPROVED);
        assertEquals(responseActivity.getSubcategory(), CATEGORY);

        assertNotNull(activityRepository.findActivityByName(ACTIVITY_NAME));

    }

    @Test
    public void testUpdateActivityNameAndDescription() {

        ActivityDto activityDto = new ActivityDto(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);

        String endpoint = "/activity/update/" + ACTIVITY_NAME + "/" + NEW_ACTIVITY_NAME + "/" + ANOTHER_DESCRIPTION
                + "/"
                + CATEGORY;

        ResponseEntity<ActivityDto> response = activity.postForEntity(endpoint, activityDto, ActivityDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ActivityDto responseActivity = response.getBody();

        assertNotNull(responseActivity);
        assertNotEquals(ACTIVITY_NAME, responseActivity.getName());
        assertNotEquals(DESCRIPTION, responseActivity.getDescription());
        assertEquals(responseActivity.getName(), NEW_ACTIVITY_NAME);
        assertEquals(responseActivity.getDescription(), ANOTHER_DESCRIPTION);
        assertEquals(responseActivity.getIsApproved(), NOT_APPROVED);
        assertEquals(responseActivity.getSubcategory(), CATEGORY);

        assertNotNull(activityRepository.findActivityByName(ACTIVITY_NAME));

    }

    @Test
    public void testUpdateActivityEverything() {

        ActivityDto activityDto = new ActivityDto(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);

        String endpoint = "/activity/update/" + ACTIVITY_NAME + "/" + NEW_ACTIVITY_NAME + "/" + ANOTHER_DESCRIPTION
                + "/"
                + ANOTHER_CATEGORY;

        ResponseEntity<ActivityDto> response = activity.postForEntity(endpoint, activityDto, ActivityDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ActivityDto responseActivity = response.getBody();

        assertNotNull(responseActivity);
        assertNotEquals(ACTIVITY_NAME, responseActivity.getName());
        assertNotEquals(DESCRIPTION, responseActivity.getDescription());
        assertNotEquals(CATEGORY, responseActivity.getSubcategory());
        assertEquals(responseActivity.getName(), NEW_ACTIVITY_NAME);
        assertEquals(responseActivity.getDescription(), ANOTHER_DESCRIPTION);
        assertEquals(responseActivity.getIsApproved(), NOT_APPROVED);
        assertEquals(responseActivity.getSubcategory(), ANOTHER_CATEGORY);

        assertNotNull(activityRepository.findActivityByName(ACTIVITY_NAME));

    }

    @Test
    public void testDeleteActivity() {

        Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
        activityRepository.save(newActivity);

        String endpoint = "/activity/delete/" + ACTIVITY_NAME;

        ResponseEntity<ActivityDto> response = activity.getForEntity(endpoint, ActivityDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }

    @Test
    public void testDeleteSpecificActivity() {

        Activity newActivity1 = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
        Activity newActivity2 = new Activity(CATEGORY, NEW_ACTIVITY_NAME, APPROVED, ANOTHER_DESCRIPTION);
        Activity newActivity3 = new Activity(CATEGORY, NEW_ACTIVITY_NAME_2, APPROVED, ANOTHER_DESCRIPTION_2);
        activityRepository.save(newActivity1);
        activityRepository.save(newActivity2);
        activityRepository.save(newActivity3);

        String endpoint = "/activity/delete/" + NEW_ACTIVITY_NAME;

        ResponseEntity<ActivityDto[]> response = activity.getForEntity(endpoint, ActivityDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        ActivityDto[] responseActivity = response.getBody();

        assertNotNull(responseActivity);
        assertEquals(responseActivity.length, 2);

        assertNull(activityRepository.findActivityByName(NEW_ACTIVITY_NAME));

    }

    @Test
    public void testGetAllAndDeleteActivity() {

        Activity newActivity1 = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
        Activity newActivity2 = new Activity(CATEGORY, NEW_ACTIVITY_NAME, APPROVED, ANOTHER_DESCRIPTION);
        Activity newActivity3 = new Activity(CATEGORY, NEW_ACTIVITY_NAME_2, APPROVED, ANOTHER_DESCRIPTION_2);
        activityRepository.save(newActivity1);
        activityRepository.save(newActivity2);
        activityRepository.save(newActivity3);

        String endpointToGetAll = "/activities";

        ResponseEntity<ActivityDto[]> response = activity.getForEntity(endpointToGetAll, ActivityDto[].class);

        // assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ActivityDto[] activityDTO = response.getBody();

        assertEquals(activityDTO.length, 3);
        assertEquals(activityDTO[0].getDescription(), DESCRIPTION);

        String endpointToDelete1 = "/activity/delete/" + ACTIVITY_NAME;
        String endpointToDelete2 = "/activity/delete/" + NEW_ACTIVITY_NAME;
        String endpointToDelete3 = "/activity/delete/" + NEW_ACTIVITY_NAME_2;

        response = activity.getForEntity(endpointToDelete1, ActivityDto[].class);

        activityDTO = response.getBody();
        assertEquals(activityDTO.length, 2);

        response = activity.getForEntity(endpointToDelete2, ActivityDto[].class);

        activityDTO = response.getBody();
        assertEquals(activityDTO.length, 1);

        response = activity.getForEntity(endpointToDelete3, ActivityDto[].class);

        activityDTO = response.getBody();
        assertEquals(activityDTO.length, 0);

        assertNotNull(response);

    }

    @Test
    public void testDissaproveActivity() {

        Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, APPROVED, DESCRIPTION);
        activityRepository.save(newActivity);

        String endpoint = "/activity/disapprove/" + ACTIVITY_NAME;

        ResponseEntity<ActivityDto> response = activity.getForEntity(endpoint, ActivityDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ActivityDto responseActivity = response.getBody();

        assertNotNull(responseActivity);
        assertEquals(responseActivity.getName(), ACTIVITY_NAME);
        assertEquals(responseActivity.getIsApproved(), NOT_APPROVED);

    }

}
