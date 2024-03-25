package ca.mcgill.ecse321.sportcenter.integration;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dto.ErrorDto;
import ca.mcgill.ecse321.sportcenter.dto.ScheduledActivityDto;
import ca.mcgill.ecse321.sportcenter.model.Instructor;

public class TestSchedueledActivityIntegration {
        // @Autowired
        private TestRestTemplate client; // Always stays the same
        private static final int INSTRUCTOR_ID = 1;
        private static final String ACTIVITY_NAME = "Olympic Weightlifting";

        private static final String DESCRIPTION = "Master the art of Clean And Jerks and be explosive.";

        private static final ClassCategory CATEGORY = ClassCategory.Strength;
        private static final boolean NOT_APPROVED = false;

        // Schedueled activty attributes
        private static final int CAPACITY = 10;
        private static final int NEW_CAPACITY = 15;
        private static final int ScheduledActivityId = 1;
        private static final int NEW_ScheduledActivityId = 2;
        private static final LocalDate DATE = LocalDate.of(2021, 10, 10);
        private static final LocalDate NEW_DATE = LocalDate.of(2021, 10, 11);
        private static final LocalTime START_TIME = LocalTime.of(10, 0);
        private static final LocalTime NEW_START_TIME = LocalTime.of(11, 0);
        private static final LocalTime END_TIME = LocalTime.of(11, 0);
        private static final LocalTime NEW_END_TIME = LocalTime.of(12, 0);

        @Autowired
        private ScheduledActivityRepository scheduledActivityRepository;

        @Autowired
        private ActivityRepository activityRepository;

        @BeforeEach
        @AfterEach
        public void clearDatabase() {
                scheduledActivityRepository.deleteAll();
                activityRepository.deleteAll();
        }

        @Test
        public void testCreateScheduledActivity() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);

                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                ScheduledActivityDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals(ScheduledActivityId, act2.getScheduledActivityId());
                assertEquals(DATE, act2.getDate());
                assertEquals(START_TIME, act2.getStartTime());
                assertEquals(END_TIME, act2.getEndTime());
                assertEquals(instructor, act2.getSupervisor());
                assertEquals(newActivity, act2.getActivity());
                assertEquals(CAPACITY, act2.getCapacity());
        }

        @Test
        public void testCreateScheduledActivityNull() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);

                ResponseEntity<ErrorDto> response = client.postForEntity("/scheduledActivity", act, ErrorDto.class);

                ErrorDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals("ScheduledActivity cannot be null", act2.getErrors());
        }

        @Test
        public void testCreateScheduledActivityNullDate() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, null, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);

                ResponseEntity<ErrorDto> response = client.postForEntity("/scheduledActivity", act, ErrorDto.class);

                ErrorDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals("Date cannot be null", act2.getErrors());
        }

        @Test
        public void testCreateScheduledActivityNullStartTime() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, null, END_TIME,
                                instructor,
                                newActivity, CAPACITY);

                ResponseEntity<ErrorDto> response = client.postForEntity("/scheduledActivity", act, ErrorDto.class);

                ErrorDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals("Start time cannot be null", act2.getErrors());
        }

        @Test
        public void testCreateScheduledActivityNullEndTime() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, null,
                                instructor,
                                newActivity, CAPACITY);

                ResponseEntity<ErrorDto> response = client.postForEntity("/scheduledActivity", act, ErrorDto.class);

                ErrorDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals("End time cannot be null", act2.getErrors());
        }

        @Test
        public void testCreateScheduledActivityNullInstructor() {
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                null,
                                newActivity, CAPACITY);

                ResponseEntity<ErrorDto> response = client.postForEntity("/scheduledActivity", act, ErrorDto.class);

                ErrorDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals("Instructor cannot be null", act2.getErrors());
        }

        @Test
        public void testCreateScheduledActivityNullActivity() {
                Instructor instructor = new Instructor();
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                null, CAPACITY);

                ResponseEntity<ErrorDto> response = client.postForEntity("/scheduledActivity", act, ErrorDto.class);

                ErrorDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals("Activity cannot be null", act2.getErrors());
        }

        @Test
        public void testCreateScheduledActivityNullCapacity() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, 0);

                ResponseEntity<ErrorDto> response = client.postForEntity("/scheduledActivity", act, ErrorDto.class);

                ErrorDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals("Capacity cannot be 0", act2.getErrors());
        }

        @Test
        public void testGetScheduledActivityById() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                ScheduledActivityDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals(ScheduledActivityId, act2.getScheduledActivityId());
                assertEquals(DATE, act2.getDate());
                assertEquals(START_TIME, act2.getStartTime());
                assertEquals(END_TIME, act2.getEndTime());
                assertEquals(instructor, act2.getSupervisor());
                assertEquals(newActivity, act2.getActivity());
                assertEquals(CAPACITY, act2.getCapacity());

                ResponseEntity<ScheduledActivityDto> response2 = client.getForEntity(
                                "/scheduledActivity/" + ScheduledActivityId,
                                ScheduledActivityDto.class);
                ScheduledActivityDto act3 = response2.getBody();
                assertNotNull(act3);
                assertEquals(ScheduledActivityId, act3.getScheduledActivityId());
                assertEquals(DATE, act3.getDate());
                assertEquals(START_TIME, act3.getStartTime());
                assertEquals(END_TIME, act3.getEndTime());
                assertEquals(instructor, act3.getSupervisor());
                assertEquals(newActivity, act3.getActivity());
                assertEquals(CAPACITY, act3.getCapacity());
        }

        @Test
        public void testGetScheduledActivityByIdNull() {
                ResponseEntity<ErrorDto> response = client.getForEntity("/scheduledActivity/" + ScheduledActivityId,
                                ErrorDto.class);
                ErrorDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals("ScheduledActivity does not exist", act2.getErrors());
        }

        @Test
        public void testGetAllScheduledActivity() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                ScheduledActivityDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals(ScheduledActivityId, act2.getScheduledActivityId());
                assertEquals(DATE, act2.getDate());
                assertEquals(START_TIME, act2.getStartTime());
                assertEquals(END_TIME, act2.getEndTime());
                assertEquals(instructor, act2.getSupervisor());
                assertEquals(newActivity, act2.getActivity());
                assertEquals(CAPACITY, act2.getCapacity());

                ResponseEntity<ScheduledActivityDto[]> response2 = client.getForEntity("/scheduledActivity",
                                ScheduledActivityDto[].class);
                ScheduledActivityDto[] act3 = response2.getBody();
                assertNotNull(act3);
                assertEquals(1, act3.length);
                assertEquals(ScheduledActivityId, act3[0].getScheduledActivityId());
                assertEquals(DATE, act3[0].getDate());
                assertEquals(START_TIME, act3[0].getStartTime());
                assertEquals(END_TIME, act3[0].getEndTime());
                assertEquals(instructor, act3[0].getSupervisor());
                assertEquals(newActivity, act3[0].getActivity());
                assertEquals(CAPACITY, act3[0].getCapacity());
        }

        @Test
        public void testGetAllScheduledActivityEmpty() {
                ResponseEntity<ScheduledActivityDto[]> response = client.getForEntity("/scheduledActivity",
                                ScheduledActivityDto[].class);
                ScheduledActivityDto[] act2 = response.getBody();
                assertNotNull(act2);
                assertEquals(0, act2.length);
        }

        @Test
        public void testGetScheduledActivityByDate() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                ScheduledActivityDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals(ScheduledActivityId, act2.getScheduledActivityId());
                assertEquals(DATE, act2.getDate());
                assertEquals(START_TIME, act2.getStartTime());
                assertEquals(END_TIME, act2.getEndTime());
                assertEquals(instructor, act2.getSupervisor());
                assertEquals(newActivity, act2.getActivity());
                assertEquals(CAPACITY, act2.getCapacity());

                ResponseEntity<ScheduledActivityDto[]> response2 = client.getForEntity("/scheduledActivity/" + DATE,
                                ScheduledActivityDto[].class);
                ScheduledActivityDto[] act3 = response2.getBody();
                assertNotNull(act3);
                assertEquals(1, act3.length);
                assertEquals(ScheduledActivityId, act3[0].getScheduledActivityId());
                assertEquals(DATE, act3[0].getDate());
                assertEquals(START_TIME, act3[0].getStartTime());
                assertEquals(END_TIME, act3[0].getEndTime());
                assertEquals(instructor, act3[0].getSupervisor());
                assertEquals(newActivity, act3[0].getActivity());
                assertEquals(CAPACITY, act3[0].getCapacity());
        }

        @Test
        public void testGetScheduledActivityByDateEmpty() {
                ResponseEntity<ScheduledActivityDto[]> response = client.getForEntity("/scheduledActivity/" + DATE,
                                ScheduledActivityDto[].class);
                ScheduledActivityDto[] act2 = response.getBody();
                assertNotNull(act2);
                assertEquals(0, act2.length);
        }

        @Test
        public void testGetInstructorByScheduledActivityId() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                ScheduledActivityDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals(ScheduledActivityId, act2.getScheduledActivityId());
                assertEquals(DATE, act2.getDate());
                assertEquals(START_TIME, act2.getStartTime());
                assertEquals(END_TIME, act2.getEndTime());
                assertEquals(instructor, act2.getSupervisor());
                assertEquals(newActivity, act2.getActivity());
                assertEquals(CAPACITY, act2.getCapacity());

                ResponseEntity<Instructor> response2 = client.getForEntity(
                                "/scheduledActivity/" + ScheduledActivityId + "/instructor",
                                Instructor.class);
                Instructor act3 = response2.getBody();
                assertNotNull(act3);
                assertEquals(instructor, act3);
        }

        @Test
        public void testGetAllScheduledActivityByInstructorId() {
                Instructor instructor = new Instructor();
                instructor.setAccountRoleId(INSTRUCTOR_ID);
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                ScheduledActivityDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals(ScheduledActivityId, act2.getScheduledActivityId());
                assertEquals(DATE, act2.getDate());
                assertEquals(START_TIME, act2.getStartTime());
                assertEquals(END_TIME, act2.getEndTime());
                assertEquals(instructor, act2.getSupervisor());
                assertEquals(newActivity, act2.getActivity());
                assertEquals(CAPACITY, act2.getCapacity());

                ResponseEntity<ScheduledActivityDto[]> response2 = client.getForEntity(
                                "/scheduledActivity/" + INSTRUCTOR_ID,
                                ScheduledActivityDto[].class);
                ScheduledActivityDto[] act3 = response2.getBody();
                assertNotNull(act3);
                assertEquals(1, act3.length);
                assertEquals(ScheduledActivityId, act3[0].getScheduledActivityId());
                assertEquals(DATE, act3[0].getDate());
                assertEquals(START_TIME, act3[0].getStartTime());
                assertEquals(END_TIME, act3[0].getEndTime());
                assertEquals(instructor, act3[0].getSupervisor());
                assertEquals(newActivity, act3[0].getActivity());
                assertEquals(CAPACITY, act3[0].getCapacity());
        }

        @Test
        public void testUpdateScheduledActivity() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                assertNotNull(response);

                ScheduledActivityDto act3 = new ScheduledActivityDto(ScheduledActivityId, NEW_DATE, NEW_START_TIME,
                                NEW_END_TIME, instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response2 = client.postForEntity(
                                "/scheduledActivity/" + ScheduledActivityId, act3,
                                ScheduledActivityDto.class);
                ScheduledActivityDto act4 = response2.getBody();
                assertNotNull(act4);
                assertEquals(ScheduledActivityId, act4.getScheduledActivityId());
                assertEquals(NEW_DATE, act4.getDate());
                assertEquals(NEW_START_TIME, act4.getStartTime());
                assertEquals(NEW_END_TIME, act4.getEndTime());
                assertEquals(instructor, act4.getSupervisor());
                assertEquals(newActivity, act4.getActivity());
                assertEquals(NEW_CAPACITY, act4.getCapacity());
        }

        @Test
        public void testUpdateScheduledActivityNull() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                assertNotNull(response);

                ScheduledActivityDto act3 = new ScheduledActivityDto(ScheduledActivityId, NEW_DATE, NEW_START_TIME,
                                NEW_END_TIME, instructor,
                                newActivity, NEW_CAPACITY);
                ResponseEntity<ErrorDto> response2 = client.postForEntity(
                                "/scheduledActivity/" + NEW_ScheduledActivityId, act3,
                                ErrorDto.class);
                ErrorDto act4 = response2.getBody();
                assertNotNull(act4);
                assertEquals("ScheduledActivity does not exist", act4.getErrors());
        }

        @Test
        public void testUpdateScheduledActivityNullDate() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                assertNotNull(response);
                ScheduledActivityDto act3 = new ScheduledActivityDto(ScheduledActivityId, null, NEW_START_TIME,
                                NEW_END_TIME, instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ErrorDto> response2 = client.postForEntity("/scheduledActivity/" + ScheduledActivityId,
                                act3,
                                ErrorDto.class);
                ErrorDto act4 = response2.getBody();
                assertNotNull(act4);
                assertEquals("Date cannot be null", act4.getErrors());
        }

        @Test
        public void testUpdateScheduledActivityNullStartTime() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                assertNotNull(response);

                ScheduledActivityDto act3 = new ScheduledActivityDto(ScheduledActivityId, NEW_DATE, null,
                                NEW_END_TIME, instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ErrorDto> response2 = client.postForEntity("/scheduledActivity/" + ScheduledActivityId,
                                act3,
                                ErrorDto.class);
                ErrorDto act4 = response2.getBody();
                assertNotNull(act4);
                assertEquals("Start time cannot be null", act4.getErrors());
        }

        @Test
        public void testUpdateScheduledActivityNullEndTime() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                assertNotNull(response);

                ScheduledActivityDto act3 = new ScheduledActivityDto(ScheduledActivityId, NEW_DATE, NEW_START_TIME,
                                null, instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ErrorDto> response2 = client.postForEntity("/scheduledActivity/" + ScheduledActivityId,
                                act3,
                                ErrorDto.class);
                ErrorDto act4 = response2.getBody();
                assertNotNull(act4);
                assertEquals("End time cannot be null", act4.getErrors());
        }

        @Test
        public void testUpdateScheduledActivityNullInstructor() {
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                null,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                assertNotNull(response);

                ScheduledActivityDto act3 = new ScheduledActivityDto(ScheduledActivityId, NEW_DATE, NEW_START_TIME,
                                NEW_END_TIME, null,
                                newActivity, CAPACITY);
                ResponseEntity<ErrorDto> response2 = client.postForEntity("/scheduledActivity/" + ScheduledActivityId,
                                act3,
                                ErrorDto.class);
                ErrorDto act4 = response2.getBody();
                assertNotNull(act4);
                assertEquals("Instructor cannot be null", act4.getErrors());
        }

        @Test
        public void testUpdateScheduledActivityNullActivity() {
                Instructor instructor = new Instructor();
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                null, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                assertNotNull(response);

                ScheduledActivityDto act3 = new ScheduledActivityDto(ScheduledActivityId, NEW_DATE, NEW_START_TIME,
                                NEW_END_TIME, instructor,
                                null, CAPACITY);
                ResponseEntity<ErrorDto> response2 = client.postForEntity("/scheduledActivity/" + ScheduledActivityId,
                                act3,
                                ErrorDto.class);
                ErrorDto act4 = response2.getBody();
                assertNotNull(act4);
                assertEquals("Activity cannot be null", act4.getErrors());
        }

        @Test
        public void testUpdateScheduledActivityCapacity() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                assertNotNull(response);

                ScheduledActivityDto act3 = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME,
                                END_TIME, instructor,
                                newActivity, NEW_CAPACITY);
                ResponseEntity<ScheduledActivityDto> response2 = client.postForEntity(
                                "/scheduledActivity/" + ScheduledActivityId, act3,
                                ScheduledActivityDto.class);
                ScheduledActivityDto act4 = response2.getBody();
                assertNotNull(act4);
                assertEquals(ScheduledActivityId, act4.getScheduledActivityId());

        }

        @Test
        public void testDeleteScheduledActivity() {
                Instructor instructor = new Instructor();
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);

                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                ScheduledActivityDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals(ScheduledActivityId, act2.getScheduledActivityId());

                ResponseEntity<ScheduledActivityDto> response2 = client.getForEntity(
                                "/deleteScheduledActivity/" + ScheduledActivityId,
                                ScheduledActivityDto.class);
                ScheduledActivityDto act3 = response2.getBody();
                assertNull(act3);
        }

        @Test
        public void testDeleteScheduledActivityByInstructorId() {
                Instructor instructor = new Instructor();
                instructor.setAccountRoleId(INSTRUCTOR_ID);
                Activity newActivity = new Activity(CATEGORY, ACTIVITY_NAME, NOT_APPROVED, DESCRIPTION);
                activityRepository.save(newActivity);
                ScheduledActivityDto act = new ScheduledActivityDto(ScheduledActivityId, DATE, START_TIME, END_TIME,
                                instructor,
                                newActivity, CAPACITY);
                ResponseEntity<ScheduledActivityDto> response = client.postForEntity("/scheduledActivity", act,
                                ScheduledActivityDto.class);

                ScheduledActivityDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals(ScheduledActivityId, act2.getScheduledActivityId());

                ResponseEntity<ScheduledActivityDto> response2 = client.getForEntity(
                                "/deleteScheduledActivity/" + INSTRUCTOR_ID,
                                ScheduledActivityDto.class);
                ScheduledActivityDto act3 = response2.getBody();
                assertNull(act3);
        }

        @Test
        public void testDeleteScheduledActivityNull() {
                ResponseEntity<ErrorDto> response = client.getForEntity("/scheduledActivity/" + ScheduledActivityId,
                                ErrorDto.class);
                ErrorDto act2 = response.getBody();
                assertNotNull(act2);
                assertEquals("ScheduledActivity does not exist", act2.getErrors());
        }
}
