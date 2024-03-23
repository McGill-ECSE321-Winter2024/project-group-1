package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestAccountInstructorService {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private InstructorRepository instructorRepository;

    // Account AccountId
    private static final String USERNAME = "Shakira";
    private static final String USERNAME_NONDUPLICATE = "ShakiraBella";

    // AccountRole AccountRoleId
    private static final int Approved_AccountRoleId = 1;
    private static final int Pending_AccountRoleId = 2;
    private static final int Disaproved_AccountRoleId = 3;

    @InjectMocks
    private AccountManagementService accountService;

    @SuppressWarnings("null")
    @BeforeEach
    void setMockOutput() {
        lenient().when(accountRepository.findAccountByUsername(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(USERNAME)) {
                        Account account = new Account();
                        account.setUsername(USERNAME);
                        account.setPassword("password");
                        account.setAccountId(1);
                        return account;
                    } else if (invocation.getArgument(0).equals(USERNAME_NONDUPLICATE)) {
                        Account account = new Account();
                        account.setUsername(USERNAME_NONDUPLICATE);
                        account.setPassword("password");
                        account.setAccountId(2);
                        return account;
                    } else {
                        return null;
                    }
                });

        lenient().when(instructorRepository.findInstructorByAccountRoleId(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(Approved_AccountRoleId)) {
                        Instructor instructor = new Instructor();
                        instructor.setAccount(accountRepository.findAccountByUsername(USERNAME));
                        instructor.setDescription("approved_description");
                        instructor.setStatus(InstructorStatus.Active);
                        instructor.setProfilePicURL("profilePicURL");
                        instructor.setAccountRoleId(Approved_AccountRoleId);
                        return instructor;
                    }
                    if (invocation.getArgument(0).equals(Pending_AccountRoleId)) {
                        Instructor instructor = new Instructor();
                        instructor.setAccount(accountRepository.findAccountByUsername(USERNAME));
                        instructor.setDescription("pending_description");
                        instructor.setStatus(InstructorStatus.Pending);
                        instructor.setProfilePicURL("profilePicURL");
                        instructor.setAccountRoleId(Pending_AccountRoleId);
                        return instructor;
                    }
                    if (invocation.getArgument(0).equals(Disaproved_AccountRoleId)) {
                        Instructor instructor = new Instructor();
                        instructor.setAccount(accountRepository.findAccountByUsername(USERNAME));
                        instructor.setDescription("disapproved_description");
                        instructor.setStatus(InstructorStatus.Inactive);
                        instructor.setProfilePicURL("profilePicURL");
                        instructor.setAccountRoleId(Disaproved_AccountRoleId);
                        return instructor;
                    } else {
                        return null;
                    }
                });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        when(accountRepository.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
        when(instructorRepository.save(any(Instructor.class))).thenAnswer(returnParameterAsAnswer);
    }

    @SuppressAjWarnings("null")
    @Test
    public void testCreateInstructor() {
        Instructor instructor = null;

        try {
            instructor = accountService.createInstructor(USERNAME_NONDUPLICATE, InstructorStatus.Pending, "description",
                    "image");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(instructor);
        assertEquals(USERNAME_NONDUPLICATE, instructor.getAccount().getUsername());
        assertEquals("description", instructor.getDescription());
        assertEquals("image", instructor.getProfilePicURL());
    }

    @Test
    public void testCreateInstructorUsernameNull() { // make an empty username
        Instructor instructor = null;

        try {
            instructor = accountService.createInstructor(USERNAME, InstructorStatus.Pending, "description", "image");
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
        when(instructorRepository.findInstructorByAccountRoleId(roleId))
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
        when(instructorRepository.findInstructorByAccountRoleId(roleId))
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
        when(instructorRepository.findInstructorByAccountUsername(username))
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
        when(instructorRepository.findInstructorByAccountUsername(username))
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
        when(instructorRepository.findInstructorByAccountUsername(username))
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
        when(instructorRepository.findInstructorByAccountUsername(username))
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
        when(instructorRepository.findInstructorByAccountUsername(username))
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
        when(instructorRepository.findInstructorByAccountRoleId(roleId))
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
        when(instructorRepository.findInstructorByAccountRoleId(roleId))
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
        when(instructorRepository.findInstructorByAccountUsername(username))
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
        when(instructorRepository.findInstructorByAccountUsername(username))
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
        when(instructorRepository.findInstructorByAccountUsername(username))
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
        when(instructorRepository.findInstructorByAccountUsername(username))
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
        when(instructorRepository.findInstructorByAccountUsername(username))
                .thenReturn(instructor);

        try {
            accountService.deleteInstructorByUsername(" ");
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty!", e.getMessage());
        }
    }

}