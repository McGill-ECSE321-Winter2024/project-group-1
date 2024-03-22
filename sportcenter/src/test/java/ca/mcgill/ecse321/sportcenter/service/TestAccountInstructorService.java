package ca.mcgill.ecse321.sportcenter.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.times;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;

import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestAccountInstructorService {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private AccountManagementService accountService;

    @Test
    public void testCreateInstructor() {
        String username = "Shakira";
        String password = "anastasia";
        String description = "Made hips don't lie";
        String image = "image1";

        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);

        assertNotNull(instructor);
        assertEquals(username, instructor.getAccount().getUsername());
        assertEquals(password, instructor.getAccount().getPassword());
        assertEquals(description, instructor.getDescription());
        assertEquals(image, instructor.getProfilePicURL());
    }

    @Test
    public void testCreateInstructorUsernameNull() { // make an empty username
        String username = null;
        String password = "anastasia";
        String description = "Made hips don't lie";
        String image = "image1";

        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Instructor instructor = null;

        try {
            instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorUsernameEmpty() { // make an empty username
        String username = "";
        String password = "fiddlesticks";
        String description = "He can beat Goku";
        String image = "image";
        String error = null;

        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = null;

        try {
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertNull(instructor);

        }
    }

    @Test
    public void testCreateInstructorUsernameSpaces() { // make an empty username
        String username = " ";
        String password = "fiddlesticks";
        String description = "He can beat Goku";
        String image = "image";
        String error = null;

        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = null;

        try {
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertNull(instructor);

        }
    }

    @Test
    public void testCreateInstructorDescriptionNull() { // make an empty username
        String username = "Goku";
        String password = "fiddlesticks";
        String description = null;
        String image = "image";

        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = null;

        try {
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);
        } catch (IllegalArgumentException e) {
            assertEquals("Description cannot be empty!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorImageNull() { // make an empty username
        String username = "Goku";
        String password = "fiddlesticks";
        String description = "He can beat Superman";
        String image = null;
        String error = null;

        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = null;

        try {
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);
        } catch (IllegalArgumentException e) {
            assertEquals("Image cannot be empty!", e.getMessage());
            assertNull(instructor);

        }
    }

    /// missing like empty and space testing for all attributes of instructor
    @Test
    public void testCreateInstructorDescriptionEmpty() { // make an empty username
        String username = "Goku";
        String password = "fiddlesticks";
        String description = "";
        String image = "image";
        String error = null;

        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = null;

        try {
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);
        } catch (IllegalArgumentException e) {
            assertEquals("Description cannot be empty!", e.getMessage());
            assertNull(instructor);

        }
    }

    @Test
    public void testCreateInstructorDescriptionSpaces() { // make an empty username
        String username = "Goku";
        String password = "fiddlesticks";
        String description = " ";
        String image = "image";
        String error = null;

        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = null;

        try {
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);
        } catch (IllegalArgumentException e) {
            assertEquals("Description cannot be empty!", e.getMessage());
            assertNull(instructor);

        }
    }

    @Test
    public void testCreateInstructorImageEmpty() { // make an empty username
        String username = "Goku";
        String password = "fiddlesticks";
        String description = "He can beat Superman";
        String image = "";
        String error = null;

        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = null;

        try {
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);
        } catch (IllegalArgumentException e) {
            assertEquals("Image cannot be empty!", e.getMessage());
            assertNull(instructor);

        }
    }

    @Test
    public void testCreateInstructorImageSpaces() { // make an empty username
        String username = "Goku";
        String password = "fiddlesticks";
        String description = "He can beat Superman";
        String image = " ";
        String error = null;

        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = null;

        try {
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);
        } catch (IllegalArgumentException e) {
            assertEquals("Image cannot be empty!", e.getMessage());
            assertNull(instructor);

        }
    }

    @Test
    public void testCreateInstructorAlreadyExists() {
        String username = "Shakira";
        String password = "anastasia";
        String description = "Made hips don't lie";
        String image = "image1";

        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);

        assertNotNull(instructor);
        assertEquals(username, instructor.getAccount().getUsername());
        assertEquals(password, instructor.getAccount().getPassword());
        assertEquals(description, instructor.getDescription());
        assertEquals(image, instructor.getProfilePicURL());

        Instructor instructor2 = null;
        try {
            instructor2 = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        } catch (IllegalArgumentException e) {
            assertEquals("Instructor already exists", e.getMessage());
            assertNull(instructor2);
        }
    }

    // RETRIEVE TESTING
    // These tests assume that creation works.

    @Test
    public void testGetInstructorByAccountRoleId() {

        // again we now assume that creating will work now
        final String username = "Shakira";
        final String password = "elo111";
        final String description = "Made hips don't lie";
        final String image = "image1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        int roleId = 1;
        when(instructorRepository.findAccountRoleByAccountRoleId(roleId))
                .thenReturn(instructor);

        Instructor retrievedInstructor = accountService.getInstructorByAccountRoleId(roleId);
        assertNotNull(retrievedInstructor);
        assertEquals(username, retrievedInstructor.getAccount().getUsername());
        assertEquals(password, retrievedInstructor.getAccount().getPassword());
        assertEquals(description, retrievedInstructor.getDescription());
        assertEquals(image, retrievedInstructor.getProfilePicURL());

    }

    @Test
    public void testGetInstructorByAccountRoleIdInvalid() {

        // again we now assume that creating will work now
        final String username = "Shakira";
        final String password = "elo111";
        final String description = "Made hips don't lie";
        final String image = "image1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        int roleId = 1;
        when(instructorRepository.findAccountRoleByAccountRoleId(roleId))
                .thenReturn(instructor);

        Instructor retrievedInstructor = null;
        try {
            retrievedInstructor = accountService.getInstructorByAccountRoleId(2);
        } catch (IllegalArgumentException e) {
            assertEquals("Instructor not found", e.getMessage());
            assertNull(retrievedInstructor);
        }
    }

    @Test
    public void testGetInstructorByUsername() {

        // again we now assume that creating will work now
        final String username = "Shakira";
        final String password = "elo111";
        final String description = "Made hips don't lie";
        final String image = "image1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        when(instructorRepository.findInstructorByUsername(username))
                .thenReturn(instructor);

        Instructor retrievedInstructor = accountService.getInstructorByUsername(username);
        assertNotNull(retrievedInstructor);
        assertEquals(username, retrievedInstructor.getAccount().getUsername());
        assertEquals(password, retrievedInstructor.getAccount().getPassword());
        assertEquals(description, retrievedInstructor.getDescription());
        assertEquals(image, retrievedInstructor.getProfilePicURL());
    }

    @Test
    public void testGetInstructorByUsernameInvalid() {

        // again we now assume that creating will work now
        final String username = "Shakira";
        final String password = "elo111";
        final String description = "Made hips don't lie";
        final String image = "image1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        when(instructorRepository.findInstructorByUsername(username))
                .thenReturn(instructor);

        Instructor retrievedInstructor = null;
        try {
            retrievedInstructor = accountService.getInstructorByUsername("Goku");
        } catch (IllegalArgumentException e) {
            assertEquals("Instructor not found", e.getMessage());
            assertNull(retrievedInstructor);
        }
    }

    @Test
    public void testGetInstructorByUsernameNull() {

        // again we now assume that creating will work now
        final String username = "Shakira";
        final String password = "elo111";
        final String description = "Made hips don't lie";
        final String image = "image1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        when(instructorRepository.findInstructorByUsername(username))
                .thenReturn(instructor);

        Instructor retrievedInstructor = null;
        try {
            retrievedInstructor = accountService.getInstructorByUsername(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertNull(retrievedInstructor);
        }
    }

    @Test
    public void testGetInstructorByUsernameEmpty() {

        // again we now assume that creating will work now
        final String username = "Shakira";
        final String password = "elo111";
        final String description = "Made hips don't lie";
        final String image = "image1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        when(instructorRepository.findInstructorByUsername(username))
                .thenReturn(instructor);

        Instructor retrievedInstructor = null;
        try {
            retrievedInstructor = accountService.getInstructorByUsername("");
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertNull(retrievedInstructor);
        }
    }

    @Test
    public void testGetInstructorByUsernameSpaces() {

        // again we now assume that creating will work now
        final String username = "Shakira";
        final String password = "elo111";
        final String description = "Made hips don't lie";
        final String image = "image1";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        when(instructorRepository.findInstructorByUsername(username))
                .thenReturn(instructor);

        Instructor retrievedInstructor = null;
        try {
            retrievedInstructor = accountService.getInstructorByUsername(" ");
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
            assertNull(retrievedInstructor);
        }
    }

    // REMOVE TESTING

    @Test
    public void testDeleteInstructorByAccountRoleId() {

        // again we now assume that creating will work now
        final String username = "Beatrix Kiddo";
        final String password = "Kill Bill";
        final String description = "Okinawa Katana Expert";
        final String image = "image67";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        int roleId = 1;
        when(instructorRepository.findAccountRoleByAccountRoleId(roleId))
                .thenReturn(instructor);

        accountService.deleteInstructorByAccountRoleId(roleId);
        verify(instructorRepository, times(1)).delete(instructor);
    }

    @Test
    public void testDeleteInstructorByAccountRoleIdInvalid() {

        // again we now assume that creating will work now
        final String username = "Beatrix Kiddo";
        final String password = "Kill Bill";
        final String description = "Okinawa Katana Expert";
        final String image = "image67";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        int roleId = 1;
        when(instructorRepository.findAccountRoleByAccountRoleId(roleId))
                .thenReturn(instructor);

        try {
            accountService.deleteInstructorByAccountRoleId(2);
        } catch (IllegalArgumentException e) {
            assertEquals("Instructor not found", e.getMessage());
        }
    }

    @Test
    public void testDeleteInstructorByUsername() {

        // again we now assume that creating will work now
        final String username = "Beatrix Kiddo";
        final String password = "Kill Bill";
        final String description = "Okinawa Katana Expert";
        final String image = "image67";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        when(instructorRepository.findInstructorByUsername(username))
                .thenReturn(instructor);

        accountService.deleteInstructorByUsername(username);
        verify(instructorRepository, times(1)).delete(instructor);
    }

    @Test
    public void testDeleteInstructorByUsernameInvalid() {

        // again we now assume that creating will work now
        final String username = "Beatrix Kiddo";
        final String password = "Kill Bill";
        final String description = "Okinawa Katana Expert";
        final String image = "image67";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        when(instructorRepository.findInstructorByUsername(username))
                .thenReturn(instructor);

        try {
            accountService.deleteInstructorByUsername("Goku");
        } catch (IllegalArgumentException e) {
            assertEquals("Instructor not found", e.getMessage());
        }
    }

    @Test
    public void testDeleteInstructorByUsernameNull() {

        // again we now assume that creating will work now
        final String username = "Beatrix Kiddo";
        final String password = "Kill Bill";
        final String description = "Okinawa Katana Expert";
        final String image = "image67";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        when(instructorRepository.findInstructorByUsername(username))
                .thenReturn(instructor);

        try {
            accountService.deleteInstructorByUsername(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void testDeleteInstructorByUsernameEmpty() {

        // again we now assume that creating will work now
        final String username = "Beatrix Kiddo";
        final String password = "Kill Bill";
        final String description = "Okinawa Katana Expert";
        final String image = "image67";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        when(instructorRepository.findInstructorByUsername(username))
                .thenReturn(instructor);

        try {
            accountService.deleteInstructorByUsername("");
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void testDeleteInstructorByUsernameSpaces() {

        // again we now assume that creating will work now
        final String username = "Beatrix Kiddo";
        final String password = "Kill Bill";
        final String description = "Okinawa Katana Expert";
        final String image = "image67";
        Account account = new Account(username, password);
        when(accountRepository.save(any(Account.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Pending, description, image);
        when(instructorRepository.findInstructorByUsername(username))
                .thenReturn(instructor);

        try {
            accountService.deleteInstructorByUsername(" ");
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
        }
    }

}