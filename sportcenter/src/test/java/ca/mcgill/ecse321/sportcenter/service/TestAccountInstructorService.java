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
        lenient().when(accountRepository.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(instructorRepository.save(any(Instructor.class))).thenAnswer(returnParameterAsAnswer);
    }

    @SuppressAjWarnings("null")
    @Test
    public void testCreateInstructor() {
        Instructor instructor = null;

        try {
            instructor = accountService.createInstructor(USERNAME_NONDUPLICATE, InstructorStatus.Pending, "description",
                    "image");
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
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
            assertEquals("Instructor already exists!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorUsernameEmpty() { // make an empty username
        Instructor instructor = null;
        try {
            instructor = accountService.createInstructor("", InstructorStatus.Pending, "description", "image");
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorUsernameSpaces() { // make an empty username
        Instructor instructor = null;
        try {
            instructor = accountService.createInstructor(" ", InstructorStatus.Pending, "description", "image");
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorDescriptionNull() { // make an empty username
        Instructor instructor = null;
        try {
            instructor = accountService.createInstructor(USERNAME_NONDUPLICATE, InstructorStatus.Pending, null,
                    "image");
        } catch (IllegalArgumentException e) {
            assertEquals("Description cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    /// missing like empty and space testing for all attributes of instructor
    @Test
    public void testCreateInstructorDescriptionEmpty() { // make an empty username
        Instructor instructor = null;
        try {
            instructor = accountService.createInstructor(USERNAME_NONDUPLICATE, InstructorStatus.Pending, "",
                    "image");
        } catch (IllegalArgumentException e) {
            assertEquals("Description cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorDescriptionSpaces() { // make an empty username
        Instructor instructor = null;
        try {
            instructor = accountService.createInstructor(USERNAME_NONDUPLICATE, InstructorStatus.Pending, " ",
                    "image");
        } catch (IllegalArgumentException e) {
            assertEquals("Description cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorStatusNull() { // make an empty username
        Instructor instructor = null;
        try {
            instructor = accountService.createInstructor(USERNAME_NONDUPLICATE, null, "description", "image");
        } catch (IllegalArgumentException e) {
            assertEquals("Instructor already exists!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorStatusEmpty() { // make an empty username
        Instructor instructor = null;
        try {
            instructor = accountService.createInstructor(USERNAME_NONDUPLICATE, InstructorStatus.Pending, "description",
                    "image");
        } catch (IllegalArgumentException e) {
            assertEquals("Instructor already exists!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorStatusSpaces() { // make an empty username
        Instructor instructor = null;
        try {
            instructor = accountService.createInstructor(USERNAME_NONDUPLICATE, InstructorStatus.Pending, "description",
                    "image");
        } catch (IllegalArgumentException e) {
            assertEquals("Instructor already exists!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorImageNull() { // make an empty username
        Instructor instructor = null;
        try {
            instructor = accountService.createInstructor(USERNAME_NONDUPLICATE, InstructorStatus.Pending, "description",
                    null);
        } catch (IllegalArgumentException e) {
            assertEquals("ProfilePic URL cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorImageEmpty() { // make an empty username
        Instructor instructor = null;
        try {
            instructor = accountService.createInstructor(USERNAME_NONDUPLICATE, InstructorStatus.Pending, "description",
                    "");
        } catch (IllegalArgumentException e) {
            assertEquals("ProfilePic URL cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorImageSpaces() { // make an empty username
        Instructor instructor = null;
        try {
            instructor = accountService.createInstructor(USERNAME_NONDUPLICATE, InstructorStatus.Pending, "description",
                    " ");
        } catch (IllegalArgumentException e) {
            assertEquals("ProfilePic URL cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testCreateInstructorAlreadyExists() {
        Instructor instructor = null;
        try {
            instructor = accountService.createInstructor(USERNAME, InstructorStatus.Pending, "description", "image");
        } catch (IllegalArgumentException e) {
            assertEquals("Instructor already exists!", e.getMessage());
            assertNull(instructor);
        }
    }

    // RETRIEVE TESTING
    // These tests assume that creation works.

    @Test
    public void testGetInstructorByAccountRoleId() {
        Instructor instructor = null;
        try {
            instructor = accountService.getInstructorByAccountRoleId(Approved_AccountRoleId);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(instructor);
        assertEquals(USERNAME, instructor.getAccount().getUsername());
    }

    @Test
    public void testGetInstructorByAccountRoleIdInvalid() {
        Instructor instructor = null;
        try {
            instructor = accountService.getInstructorByAccountRoleId(4);
        } catch (IllegalArgumentException e) {
            assertEquals("Instructor does not exist!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testGetInstructorByUsername() {
        Instructor instructor = null;
        try {
            instructor = accountService.getInstructorByUsername(USERNAME);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(instructor);
        assertEquals(USERNAME, instructor.getAccount().getUsername());
    }

    @Test
    public void testGetInstructorByUsernameInvalid() {
        Instructor instructor = null;
        try {
            instructor = accountService.getInstructorByUsername("Goku");
        } catch (IllegalArgumentException e) {
            assertEquals("Account does not exist!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testGetInstructorByUsernameNull() {
        Instructor instructor = null;
        try {
            instructor = accountService.getInstructorByUsername(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testGetInstructorByUsernameEmpty() {
        Instructor instructor = null;
        try {
            instructor = accountService.getInstructorByUsername("");
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    @Test
    public void testGetInstructorByUsernameSpaces() {
        Instructor instructor = null;
        try {
            instructor = accountService.getInstructorByUsername(" ");
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    // REMOVE TESTING

    @SuppressWarnings("null")
    @Test
    public void testDeleteInstructorByAccountRoleId() {
        try {
            accountService.deleteInstructorByAccountRoleId(Approved_AccountRoleId);
        } catch (IllegalArgumentException e) {
            fail();
        }
        verify(instructorRepository, times(1)).delete(any(Instructor.class));
    }

    @Test
    public void testDeleteInstructorByAccountRoleIdInvalid() {
        try {
            accountService.deleteInstructorByAccountRoleId(4);
        } catch (IllegalArgumentException e) {
            assertEquals("Instructor does not exist!", e.getMessage());
        }
    }

    @SuppressWarnings("null")
    @Test
    public void testDeleteInstructorByUsername() {
        try {
            accountService.deleteInstructorByUsername(USERNAME);
        } catch (IllegalArgumentException e) {
            fail();
        }
        verify(instructorRepository, times(1)).delete(any(Instructor.class));
    }

    @Test
    public void testDeleteInstructorByUsernameInvalid() {
        try {
            accountService.deleteInstructorByUsername("Gokull");
        } catch (IllegalArgumentException e) {
            assertEquals("Account does not exist!", e.getMessage());
        }

    }

    @Test
    public void testDeleteInstructorByUsernameNull() {
        try {
            accountService.deleteInstructorByUsername(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be null, empty and spaces!", e.getMessage());
        }
    }

    @Test
    public void testDeleteInstructorByUsernameEmpty() {
        try {
            accountService.deleteInstructorByUsername("");
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be null, empty and spaces!", e.getMessage());
        }
    }

    @Test
    public void testDeleteInstructorByUsernameSpaces() {
        try {
            accountService.deleteInstructorByUsername(" ");
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be null, empty and spaces!", e.getMessage());
        }
    }

    /**
     * Tests for updating an instructor's description and profile picture
     */
    @Test
    public void testUpdateInstructorDescription() {
        Instructor instructor = null;
        try {
            instructor = accountService.updateInstructor(USERNAME, "new_description", "new_profilePicURL");
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(instructor);
        assertEquals("new_description", instructor.getDescription());
        assertEquals("new_profilePicURL", instructor.getProfilePicURL());
    }

    /**
     * Tests for updating an instructor's with an invalid username
     */
    @Test
    public void testUpdateInstructorDescriptionInvalidUsername() {
        Instructor instructor = null;
        try {
            instructor = accountService.updateInstructor("Goku", "new_description", "profilePicURL");
        } catch (IllegalArgumentException e) {
            assertEquals("Account does not exist!", e.getMessage());
            assertNull(instructor);
        }
    }

    /**
     * Tests for updating an instructor's profile with an invalid description
     */
    @Test
    public void testUpdateInstructorDescriptionInvalidDescription() {
        Instructor instructor = null;
        try {
            instructor = accountService.updateInstructor(USERNAME, null, "profilePicURL");
        } catch (IllegalArgumentException e) {
            assertEquals("Description cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    /**
     * Tests for updating an instructor's profile with an invalid profile picture
     * URL
     */
    @Test
    public void testUpdateInstructorDescriptionInvalidProfilePicURL() {
        Instructor instructor = null;
        try {
            instructor = accountService.updateInstructor(USERNAME, "description", null);
        } catch (IllegalArgumentException e) {
            assertEquals("ProfilePic URL cannot be null, empty and spaces!", e.getMessage());
            assertNull(instructor);
        }
    }

    /**
     * Tests for updating an instructor's profile with an account that does not
     * exist
     */
    @Test
    public void testUpdateInstructorDescriptionInvalidAccount() {
        Instructor instructor = null;
        try {
            instructor = accountService.updateInstructor("Goku", "description", "profilePicURL");
        } catch (IllegalArgumentException e) {
            assertEquals("Account does not exist!", e.getMessage());
            assertNull(instructor);
        }
    }

    /**
     * Tests for updating an instructor's profile with an instructor that does not
     * exist
     */
    @Test
    public void testUpdateInstructorDescriptionInvalidInstructor() {
        Instructor instructor = null;
        try {
            instructor = accountService.updateInstructor(USERNAME_NONDUPLICATE, "description", "profilePicURL");
        } catch (IllegalArgumentException e) {
            assertEquals("Instructor does not exist!", e.getMessage());
            assertNull(instructor);
        }
    }

}