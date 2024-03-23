package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
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

import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;

import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;

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

    // Account Role keys

    // Instructor keys
    private static final int APPROVED_INSTRUCTOR_KEY = 2;
    private static final int DISAPPROVED_INSTRUCTOR_KEY = 3;

    // Activity keys
    private static final String APPROVED_ACTIVITY_KEY = "ApprovedActivity";
    private static final String DISAPPROVED_ACTIVITY_KEY = "DisapprovedActivity";

    // Scheduled Activity keys
    private static final int SCHEDULED_ACTIVITY_KEY = 1;
    private static final LocalDate DATE = LocalDate.of(2025, 3, 19);
    private static final LocalTime START_TIME = LocalTime.of(10, 0, 0);
    private static final LocalTime END_TIME = LocalTime.of(12, 0, 0);
    private static final int CAPACITY = 20;

    @InjectMocks
    private ScheduledActivityManagementService scheduledActivityService;

    @SuppressWarnings("null")
    @BeforeEach
    void setMockOutput() {
        lenient().when(accountDao.findAccountByAccountId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
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

        lenient().when(instructorDao.findInstructorByAccountRoleId(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(APPROVED_INSTRUCTOR_KEY)) {
                        Instructor instructor = new Instructor();
                        instructor.setAccount(accountDao.findAccountByAccountId(APPROVED_INSTRUCTOR_ACCOUNT_KEY));
                        instructor.setStatus(InstructorStatus.Active);
                        instructor.setDescription("description");
                        instructor.setProfilePicURL("pictureURL");
                        return instructor;
                    } else if (invocation.getArgument(0).equals(DISAPPROVED_INSTRUCTOR_KEY)) {
                        Instructor instructor = new Instructor();
                        instructor.setAccount(accountDao.findAccountByAccountId(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY));
                        instructor.setStatus(InstructorStatus.Pending);
                        instructor.setDescription("description");
                        instructor.setProfilePicURL("pictureURL");
                        return instructor;
                    } else {
                        return null;
                    }
                });

        lenient().when(activityDao.findActivityByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
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

        lenient().when(scheduledActivityDao.findScheduledActivityByScheduledActivityId(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(SCHEDULED_ACTIVITY_KEY)) {
                        ScheduledActivity scheduledActivity = new ScheduledActivity();
                        scheduledActivity.setDate(LocalDate.now());
                        scheduledActivity.setStartTime(LocalTime.now());
                        scheduledActivity.setEndTime(LocalTime.of(12, 0));
                        scheduledActivity.setSupervisor(
                                instructorDao.findInstructorByAccountRoleId(
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

        // MATHIAS DELETED THIS
        lenient().when(scheduledActivityDao.save(any(ScheduledActivity.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(accountDao.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(instructorDao.save(any(Instructor.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(activityDao.save(any(Activity.class))).thenAnswer(returnParameterAsAnswer);

    }

    @SuppressAjWarnings("null")

    /**
     * Tests the creation of a scheduled activity with a null date -> Fail
     */
    @Test
    public void testCreateScheduledActivityNullDate() {
        String error = null;
        try {
            scheduledActivityService.createScheduledActivity(null, START_TIME,
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
            scheduledActivityService.createScheduledActivity(DATE, null, END_TIME,
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
            scheduledActivityService.createScheduledActivity(DATE, START_TIME,
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
            scheduledActivityService.createScheduledActivity(DATE, START_TIME,
                    END_TIME, -1, APPROVED_ACTIVITY_KEY, CAPACITY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor Id must be greater than 0", error);
    }

    /**
     * Tests the creation of a scheduled activity with a null activity -> Fail
     */
    @Test
    public void testCreateScheduledActivityNullActivity() {
        String error = null;
        try {
            scheduledActivityService.createScheduledActivity(DATE, START_TIME,
                    END_TIME, APPROVED_INSTRUCTOR_KEY, null, CAPACITY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    /**
     * Tests the creation of a scheduled activity with a start time after the end
     * time -> Fail
     */
    @Test
    public void testCreateScheduledActivityStartTimeAfterEndTime() {
        String error = null;
        try {
            scheduledActivityService.createScheduledActivity(DATE, END_TIME,
                    START_TIME, APPROVED_INSTRUCTOR_KEY, APPROVED_ACTIVITY_KEY, CAPACITY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be after end time!", error);
    }

    /**
     * Tests updating the date and time of a scheduled activity -> Success
     */
    @SuppressWarnings("null")
    @Test
    public void testUpdateScheduledActivityByDateAndTime() {
        LocalDate oldDate = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getDate();
        LocalTime oldStartTime = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getEndTime();

        scheduledActivityService.updateScheduledActivityByDateAndTime(
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
            scheduledActivityService.updateScheduledActivityByDateAndTime(
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
            scheduledActivityService.updateScheduledActivityByDateAndTime(
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
            scheduledActivityService.updateScheduledActivityByDateAndTime(
                    SCHEDULED_ACTIVITY_KEY, DATE, oldDate, START_TIME, oldStartTime, null, oldEndTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("End time cannot be empty!", error);
    }

    /**
     * Tests updating the instructor of a scheduled activity -> Success
     */
    @SuppressWarnings("null")
    @Test
    public void testUpdateScheduledActivityInstructor() {
        int oldAccountRoleId = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getSupervisor().getAccountRoleId();
        int newAccountRoleId = APPROVED_INSTRUCTOR_KEY;

        scheduledActivityService.updateScheduledActivityInstructor(
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
            scheduledActivityService.updateScheduledActivityInstructor(
                    SCHEDULED_ACTIVITY_KEY, newAccountRoleId, oldAccountRoleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Instructor Id cannot be negative!", error);
    }

    /**
     * Tests updating the activity of a scheduled activity -> Success
     */
    @Test
    public void testUpdateScheduledActivityActivity() {
        // String oldActivityName =
        // scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
        // .getActivity().getName();
        // String newActivityName = APPROVED_ACTIVITY_KEY;

        // scheduledActivityService.updateScheduledActivityActivity(
        // SCHEDULED_ACTIVITY_KEY, newActivityName, oldActivityName);
        // verify(scheduledActivityDao, times(1)).save(any(ScheduledActivity.class));
        ScheduledActivity scheduledActivity = null;

        try {
            scheduledActivity = scheduledActivityService.updateScheduledActivityActivity(
                    SCHEDULED_ACTIVITY_KEY, APPROVED_ACTIVITY_KEY, DISAPPROVED_ACTIVITY_KEY);
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }

        assertNotNull(scheduledActivity);
        assertEquals(APPROVED_ACTIVITY_KEY, scheduledActivity.getActivity().getName());
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
            scheduledActivityService.updateScheduledActivityActivity(
                    SCHEDULED_ACTIVITY_KEY, newActivityName, oldActivityName);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    /**
     * Tests updating the date and time of a scheduled activity with a start time
     * after the end time -> Fail
     */
    @Test
    public void testUpdateScheduledActivityStartTimeAfterEndTime() {
        LocalDate oldDate = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getDate();
        LocalTime oldStartTime = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getStartTime();
        LocalTime oldEndTime = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getEndTime();

        String error = null;
        try {
            scheduledActivityService.updateScheduledActivityByDateAndTime(
                    SCHEDULED_ACTIVITY_KEY, DATE, oldDate, END_TIME, oldStartTime, START_TIME, oldEndTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Start time cannot be after end time!", error);
    }

    /**
     * Tests updating the capacity of a scheduled activity -> Success
     */
    @SuppressWarnings("null")
    @Test
    public void testUpdateScheduledActivityCapacity() {
        int oldCapacity = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getCapacity();
        int newCapacity = 20;

        scheduledActivityService.updateScheduledActivityCapacity(
                SCHEDULED_ACTIVITY_KEY, newCapacity, oldCapacity);
        verify(scheduledActivityDao, times(1)).save(any(ScheduledActivity.class));
    }

    /**
     * Tests updating the capacity of a scheduled activity with a null capacity ->
     * Fail
     */
    @Test
    public void testUpdateScheduledActivityInvalidCapacity() {
        int oldCapacity = scheduledActivityDao.findScheduledActivityByScheduledActivityId(SCHEDULED_ACTIVITY_KEY)
                .getCapacity();
        int newCapacity = -20;

        String error = null;
        try {
            scheduledActivityService.updateScheduledActivityCapacity(
                    SCHEDULED_ACTIVITY_KEY, newCapacity, oldCapacity);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Capacity must be greater than 0!", error);
    }

    /**
     * Tests updating the activity of a scheduled activity with a null activity ->
     * Fail
     */
    @Test
    public void testUpdateScheduledActivityNullActivity() {
        String error = null;
        try {
            scheduledActivityService.updateScheduledActivityActivity(
                    SCHEDULED_ACTIVITY_KEY, null, DISAPPROVED_ACTIVITY_KEY);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Activity name cannot be empty!", error);
    }

    /**
     * Tests deleting a scheduled activity -> Success
     */
    @SuppressWarnings("null")
    @Test
    public void testDeleteScheduledActivity() {
        int scheduledActivityId = SCHEDULED_ACTIVITY_KEY;

        when(scheduledActivityDao.findScheduledActivityByScheduledActivityId(scheduledActivityId))
                .thenReturn(new ScheduledActivity());

        try {
            scheduledActivityService.deleteScheduledActivity(scheduledActivityId);
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }
        scheduledActivityService.deleteScheduledActivity(scheduledActivityId);
        verify(scheduledActivityDao, times(1)).delete(any(ScheduledActivity.class));
    }

}
