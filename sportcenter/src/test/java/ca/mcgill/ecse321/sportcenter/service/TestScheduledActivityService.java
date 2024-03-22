package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;
import ca.mcgill.ecse321.sportcenter.dto.InstructorDto.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;

/**
 * Test class for the ScheduledActivity entity
 * 
 * @author Fabian Saldana
 */
@ExtendWith(MockitoExtension.class)
public class TestScheduledActivityService {

    @Mock
    private AccountRepository accountDao;
    @Mock
    private CustomerRepository customerDao;
    @Mock
    private InstructorRepository instructorDao;
    @Mock
    private ActivityRepository activityDao;
    @Mock
    private ScheduledActivityRepository scheduledActivityDao;
    @Mock
    private RegistrationRepository registrationDao;

    // Account keys
    private static final int CUSTOMER_ACCOUNT_KEY = 1;
    private static final int APPROVED_INSTRUCTOR_ACCOUNT_KEY = 2;
    private static final int DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY = 3;

    private static final int NEW_CUSTOMER_ACCOUNT_KEY = 4;

    // Account Role keys
    // Customer keys
    private static final int CUSTOMER_KEY = 1;

    private static final int NEW_CUSTOMER_KEY = 4;

    // Instructor keys
    private static final int APPROVED_INSTRUCTOR_KEY = 2;
    private static final int DISAPPROVED_INSTRUCTOR_KEY = 3;

    // Activity keys
    private static final String APPROVED_ACTIVITY_KEY = "ApprovedActivity";
    private static final String DISAPPROVED_ACTIVITY_KEY = "DisapprovedActivity";

    // Scheduled Activity keys
    private static final int SCHEDULED_ACTIVITY_KEY = 1;
    private static final int NEW_SCHEDULED_ACTIVITY_KEY = 2;
    private static final LocalDate DATE = LocalDate.of(2025, 3, 19);
    private static final LocalTime START_TIME = LocalTime.of(10, 0, 0);
    private static final LocalTime END_TIME = LocalTime.of(12, 0, 0);
    private static final int CAPACITY = 20;

    // Registration keys
    private static final int REGISTRATION_KEY = 1;

    @InjectMocks
    private ScheduledActivityManagementService scheduledActivityService;

    @SuppressWarnings("null")
    @BeforeEach
    void setMockOutput() {
        when(accountDao.findAccountByAccountId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(CUSTOMER_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername("customer");
                account.setPassword("password");
                return account;
            } else if (invocation.getArgument(0).equals(APPROVED_INSTRUCTOR_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername("approvedInstructor");
                account.setPassword("password");
                return account;
            } else if (invocation.getArgument(0).equals(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername("disapprovedInstructor");
                account.setPassword("password");
                return account;
            } else {
                return null;
            }
        });

        when(instructorDao.findAccountRoleByAccountRoleId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(APPROVED_INSTRUCTOR_KEY)) {
                Instructor instructor = new Instructor();
                instructor.setAccount(accountDao.findAccountByAccountId(APPROVED_INSTRUCTOR_ACCOUNT_KEY));
                // instructor.setStatus(InstructorStatus.Active);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");
                return instructor;
            } else if (invocation.getArgument(0).equals(DISAPPROVED_INSTRUCTOR_KEY)) {
                Instructor instructor = new Instructor();
                instructor.setAccount(accountDao.findAccountByAccountId(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY));
                // instructor.setStatus(InstructorStatus.Pending);
                instructor.setDescription("description");
                instructor.setProfilePicURL("pictureURL");
                return instructor;
            } else {
                return null;
            }
        });

        when(activityDao.findActivityByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(APPROVED_ACTIVITY_KEY)) {
                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(true);
                return activity;
            } else if (invocation.getArgument(0).equals(DISAPPROVED_ACTIVITY_KEY)) {
                Activity activity = new Activity();
                activity.setName("activity");
                activity.setDescription("description");
                activity.setSubCategory(ClassCategory.Cardio);
                activity.setIsApproved(false);
                return activity;
            } else {
                return null;
            }
        });

        when(scheduledActivityDao.findScheduledActivityByScheduledActivityId(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(SCHEDULED_ACTIVITY_KEY)) {
                        ScheduledActivity scheduledActivity = new ScheduledActivity();
                        scheduledActivity.setDate(LocalDate.now());
                        scheduledActivity.setStartTime(LocalTime.now());
                        scheduledActivity.setEndTime(LocalTime.of(12, 0));
                        scheduledActivity.setSupervisor(
                                instructorDao.findAccountRoleByAccountRoleId(
                                        DISAPPROVED_INSTRUCTOR_KEY));
                        scheduledActivity.setActivity(
                                activityDao.findActivityByName(
                                        DISAPPROVED_ACTIVITY_KEY));
                        scheduledActivity.setCapacity(30);
                        return scheduledActivity;
                    } else {
                        return null;
                    }
                });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };

        when(scheduledActivityDao.save(any(ScheduledActivity.class))).thenAnswer(returnParameterAsAnswer);
        when(accountDao.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
        when(instructorDao.save(any(Instructor.class))).thenAnswer(returnParameterAsAnswer);
        when(activityDao.save(any(Activity.class))).thenAnswer(returnParameterAsAnswer);

    }

    @SuppressAjWarnings("null")
    /**
     * Tests the creation of a scheduled activity -> Sucess
     */
    @Test
    public void testCreateScheduledActivity() {
        ScheduledActivity scheduledActivity = null;
        try {
            scheduledActivity = scheduledActivityService.createScheduledActivity(DATE, START_TIME, END_TIME,
                    APPROVED_INSTRUCTOR_KEY, APPROVED_ACTIVITY_KEY, CAPACITY);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(scheduledActivity);
        assertEquals(DATE, scheduledActivity.getDate());
        assertEquals(START_TIME, scheduledActivity.getStartTime());
        assertEquals(END_TIME, scheduledActivity.getEndTime());
        assertEquals(APPROVED_INSTRUCTOR_KEY, scheduledActivity.getSupervisor().getAccountRoleId());
        assertEquals(APPROVED_ACTIVITY_KEY, scheduledActivity.getActivity().getName());
        assertEquals(CAPACITY, scheduledActivity.getCapacity());
    }

    /**
     * Tests the creation of a scheduled activity with a null date -> Fail
     */
    @Test
    public void testCreateScheduledActivityNullDate() {
        String error = null;
        try {
            ScheduledActivity scheduledActivity = scheduledActivityService.createScheduledActivity(null, START_TIME,
                    END_TIME, APPROVED_INSTRUCTOR_KEY, APPROVED_ACTIVITY_KEY, CAPACITY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Date cannot be empty!", error);
    }

    /**
     * Tests the creation of a scheduled activity with a null start time -> Fail
     */
    @Test
    public void testCreateScheduledActivityNullStartTime() {
        String error = null;
        try {
            ScheduledActivity scheduledActivity = scheduledActivityService.createScheduledActivity(DATE, null, END_TIME,
                    APPROVED_INSTRUCTOR_KEY, APPROVED_ACTIVITY_KEY, CAPACITY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be empty!", error);
    }

    /**
     * Tests the creation of a scheduled activity with a null end time -> Fail
     */
    @Test
    public void testCreateScheduledActivityNullEndTime() {
        String error = null;
        try {
            ScheduledActivity scheduledActivity = scheduledActivityService.createScheduledActivity(DATE, START_TIME,
                    null,
                    APPROVED_INSTRUCTOR_KEY, APPROVED_ACTIVITY_KEY, CAPACITY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("End time cannot be empty!", error);
    }

    /**
     * Tests the creation of a scheduled activity with a null instructor -> Fail
     */
    @Test
    public void testCreateScheduledActivityNullInstructor() {
        String error = null;
        try {
            ScheduledActivity scheduledActivity = scheduledActivityService.createScheduledActivity(DATE, START_TIME,
                    END_TIME, -1, APPROVED_ACTIVITY_KEY, CAPACITY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor cannot be empty!", error);
    }

    /**
     * Tests the creation of a scheduled activity with a null activity -> Fail
     */
    @Test
    public void testCreateScheduledActivityNullActivity() {
        String error = null;
        try {
            ScheduledActivity scheduledActivity = scheduledActivityService.createScheduledActivity(DATE, START_TIME,
                    END_TIME, APPROVED_INSTRUCTOR_KEY, null, CAPACITY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Scheduled Activity cannot be empty!", error);
    }

    /**
     * Tests the creation of a scheduled activity with a start time after the end
     * time -> Fail
     */
    @Test
    public void testCreateScheduledActivityStartTimeAfterEndTime() {
        String error = null;
        try {
            ScheduledActivity scheduledActivity = scheduledActivityService.createScheduledActivity(DATE, END_TIME,
                    START_TIME, APPROVED_INSTRUCTOR_KEY, APPROVED_ACTIVITY_KEY, CAPACITY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be after end time!", error);
    }

    /**
     * Tests updating the date and time of a scheduled activity -> Success
     */
    @Test
    public void testUpdateScheduledActivityByDateAndTime() {
        LocalDate oldDate = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getDate();
        LocalTime oldStartTime = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getEndTime();

        ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityByDateAndTime(
                SCHEDULED_ACTIVITY_KEY, DATE, oldDate, START_TIME, oldStartTime, END_TIME, oldEndTime);
        verify(scheduledActivityDao, times(1)).save(any(ScheduledActivity.class));
    }

    /**
     * Tests updating the date and time of a scheduled activity with a null date ->
     * Fail
     */
    @Test
    public void testUpdateScheduledActivityByDateAndTimeNullDate() {
        LocalDate oldDate = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getDate();
        LocalTime oldStartTime = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getEndTime();

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityByDateAndTime(
                    SCHEDULED_ACTIVITY_KEY, null, oldDate, START_TIME, oldStartTime, END_TIME, oldEndTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Date cannot be empty!", error);
    }

    /**
     * Tests updating the date and time of a scheduled activity with a null start
     * time -> Fail
     */
    @Test
    public void testUpdateScheduledActivityByDateAndTimeNullStartTime() {
        LocalDate oldDate = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getDate();
        LocalTime oldStartTime = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getEndTime();

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityByDateAndTime(
                    SCHEDULED_ACTIVITY_KEY, DATE, oldDate, null, oldStartTime, END_TIME, oldEndTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be empty!", error);
    }

    /**
     * Tests updating the date and time of a scheduled activity with a null end time
     * -> Fail
     */
    @Test
    public void testUpdateScheduledActivityByDateAndTimeNullEndTime() {
        LocalDate oldDate = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getDate();
        LocalTime oldStartTime = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getEndTime();

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityByDateAndTime(
                    SCHEDULED_ACTIVITY_KEY, DATE, oldDate, START_TIME, oldStartTime, null, oldEndTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("End time cannot be empty!", error);
    }

    /**
     * Tests updating the instructor of a scheduled activity -> Success
     */
    @Test
    public void testUpdateScheduledActivityInstructor() {
        int oldAccountRoleId = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getSupervisor().getAccountRoleId();
        int newAccountRoleId = APPROVED_INSTRUCTOR_KEY;

        ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityInstructor(
                SCHEDULED_ACTIVITY_KEY, newAccountRoleId, oldAccountRoleId);
        verify(scheduledActivityDao, times(1)).save(any(ScheduledActivity.class));
    }

    /**
     * Tests updating the instructor of a scheduled activity with a null instructor
     * -> Fail
     */
    @Test
    public void testUpdateScheduledActivityInstructorNullInstructor() {
        int oldAccountRoleId = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getSupervisor().getAccountRoleId();
        int newAccountRoleId = -1;

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityInstructor(
                    SCHEDULED_ACTIVITY_KEY, newAccountRoleId, oldAccountRoleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor cannot be empty!", error);
    }

    /**
     * Tests updating the activity of a scheduled activity -> Success
     */
    @Test
    public void testUpdateScheduledActivityActivity() {
        String oldActivityName = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getActivity().getName();
        String newActivityName = APPROVED_ACTIVITY_KEY;

        ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityActivity(
                SCHEDULED_ACTIVITY_KEY, newActivityName, oldActivityName);
        verify(scheduledActivityDao, times(1)).save(any(ScheduledActivity.class));
    }

    /**
     * Tests updating the activity of a scheduled activity with a null activity ->
     * Fail
     */
    @Test
    public void testUpdateScheduledActivityActivityNullActivity() {
        String oldActivityName = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getActivity().getName();
        String newActivityName = null;

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityActivity(
                    SCHEDULED_ACTIVITY_KEY, newActivityName, oldActivityName);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Scheduled Activity cannot be empty!", error);
    }

    @Test
    public void testUpdateScheduledActivityNullEndTime() {
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(10, 0, 0);
        LocalTime newEndTime = null;

        LocalDate oldDate = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getDate();
        LocalTime oldStartTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getEndTime();

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityByDateAndTime(
                    scheduledActivityId, newDate, oldDate, newStartTime, oldStartTime, newEndTime, oldEndTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("End time cannot be empty!", error);
    }

    @Test
    public void testUpdateScheduledActivityNullInstructor() {
        int oldScheduledActivityId = 1;
        int newScheduledActivityId = 2;

        int oldAccountRoleId = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(oldScheduledActivityId)
                .getSupervisor().getAccountRoleId();
        int newAccountRoleId = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(newScheduledActivityId)
                .getSupervisor().getAccountRoleId();

        String error = null;
        // when(activityService.(instructorId)).thenReturn(true);//this checks if the
        // instructor exists, if not, it will return false which will throw an exception
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityInstructor(
                    oldScheduledActivityId, newAccountRoleId, oldAccountRoleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor cannot be empty!", error);
    }

    @Test
    public void testUpdateScheduledActivityNullActivity() {
        int oldScheduledActivityId = 1;
        int newScheduledActivityId = 2;

        String oldActivityName = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(oldScheduledActivityId)
                .getActivity().getName();
        String newActivityName = null;

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityActivity(
                    oldScheduledActivityId, newActivityName, oldActivityName);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Scheduled Activity cannot be empty!", error);
    }

    @Test
    public void testUpdateScheduledActivityNullStartTimeAfterEndTime() {
        int scheduledActivityId = 1;
        LocalDate newDate = LocalDate.of(2025, 3, 19);
        LocalTime newStartTime = LocalTime.of(20, 0, 0);
        LocalTime newEndTime = LocalTime.of(18, 0, 0);

        LocalTime oldStartTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getEndTime();
        LocalDate oldDate = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getDate();

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityByDateAndTime(
                    scheduledActivityId, newDate, oldDate, newStartTime, oldStartTime, newEndTime, oldEndTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be after end time!", error);
    }

    @Test
    public void testUpdateScheduledActivityCapacity() {
        int scheduledActivityId = 1;
        int newCapacity = 20;

        int oldCapacity = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getCapacity();

        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId))
                .thenReturn(null);// this checks if the activity already exists, and if it does, it will return
                                  // null

        when(scheduledActivityRepository.save(any(ScheduledActivity.class))).thenAnswer((invocation) -> {
            ScheduledActivity scheduledActivity = new ScheduledActivity();
            scheduledActivity.setCapacity(newCapacity);
            return scheduledActivity;
        });

        ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityCapacity(
                scheduledActivityId, newCapacity, oldCapacity);
        verify(scheduledActivityRepository, times(1)).save(any(ScheduledActivity.class));

        assertNotNull(updatedScheduledActivity);
        assertEquals(newCapacity, updatedScheduledActivity.getCapacity());
    }

    @Test
    public void testUpdateScheduledActivityInvalidCapacity() {
        int scheduledActivityId = 1;
        int newCapacity = -20;

        int oldCapacity = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId)
                .getCapacity();

        String error = null;
        try {
            ScheduledActivity updatedScheduledActivity = scheduledActivityService.updateScheduledActivityCapacity(
                    scheduledActivityId, newCapacity, oldCapacity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Capacity cannot be negative!", error);
    }

    @Test
    public void testDeleteScheduledActivity() {
        int scheduledActivityId = 1;

        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId))
                .thenReturn(new ScheduledActivity());
        // when(scheduledActivityRepository.checkAccountInstructor(instructorId)).thenReturn(true);//this
        // checks if the instructor exists, if not, it will return false which will
        // throw an exception

        scheduledActivityService.deleteScheduledActivity(scheduledActivityId);
        verify(scheduledActivityRepository, times(1)).delete(any(ScheduledActivity.class));

        // assertNotNull(deletedScheduledActivity());
        // assertEquals(scheduledActivityId,
        // deletedScheduledActivity.getScheduledActivityId());
    }

    @Test
    public void testDeleteScheduledActivityInvalidId() {
        int scheduledActivityId = -1;

        String error = null;
        when(scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId))
                .thenReturn(null);
        try {
            scheduledActivityService.deleteScheduledActivity(scheduledActivityId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Scheduled Activity does not exist!", error);
    }

}
