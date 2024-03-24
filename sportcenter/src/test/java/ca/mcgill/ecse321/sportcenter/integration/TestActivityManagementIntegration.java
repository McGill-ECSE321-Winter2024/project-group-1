package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dto.AccountDto;
import ca.mcgill.ecse321.sportcenter.dto.ActivityDto;
import ca.mcgill.ecse321.sportcenter.dto.ErrorDto;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
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
    private static final String DESCRIPTION = "Master the art of Clean And Jerks and be explosive.";

    private static final ClassCategory CATEGORY = ClassCategory.Strength;
    private static final boolean NOT_APPROVED = false;

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
    public void testGetActivityBySubcategory() { // to change

        // to change to change
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
    public void testDeleteActivity() {

        Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
        activityRepository.save(newActivity);

        String endpoint = "/activity/delete/" + ACTIVITY_NAME;

        ResponseEntity<ActivityDto> response = activity.getForEntity(endpoint, ActivityDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }

    @Test
    public void testCreateAndGetAllAndDeleteActivity() {

        Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
        activityRepository.save(newActivity);

        String endpointToGetAll = "/activities";

        ResponseEntity<ActivityDto> response = activity.getForEntity(endpointToGetAll, ActivityDto.class);

        // assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ActivityDto activityDTO = response.getBody();

        assertEquals(activityDTO.getDescription(), DESCRIPTION);

        String endpointToDeleteAll = "/activity/delete/" + ACTIVITY_NAME;

        activity.delete(endpointToDeleteAll);

        response = activity.getForEntity(endpointToDeleteAll, ActivityDto.class);

        assertNotEquals(activityDTO.getDescription(), DESCRIPTION);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        activityDTO = response.getBody();

    }

}
