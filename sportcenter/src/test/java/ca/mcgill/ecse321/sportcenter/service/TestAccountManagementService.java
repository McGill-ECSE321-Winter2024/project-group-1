package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.invocation.InvocationOnMock;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.service.AccountManagementService;

public class TestAccountManagementService {
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

    @BeforeEach
    public void setAccount() {
        Account account = new Account();
        account.setUsername("Person1");
        account.setPassword("Password1");
        accountRepository.save(account);

        Instructor instructor = new Instructor();
        instructor.setAccount(account);
        instructorRepository.save(instructor);

        Owner owner = new Owner();
        owner.setAccount(account);
        ownerRepository.save(owner);

        Customer customer = new Customer();
        customer.setAccount(account);
        customerRepository.save(customer);
    }

    @Test
    public void testCreateAccount() {
        String username = "username";
        String password = "password";
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account createdAccount = accountService.createAccount(username, password);

        assertNotNull(createdAccount);
        assertEquals(username, createdAccount.getUsername());
        assertEquals(password, createdAccount.getPassword());
        verify(accountRepository, times(1)).save(account1);
    }

    @Test
    public void testCreateAccountNull() {
        String username = null;
        String password = null;
        String error = null;
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account createdAccount = accountService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Username cannot be empty!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testCreateAccountEmpty() {
        String username = "";
        String password = "";
        String error = null;
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account createdAccount = accountService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testCreateAccountSpaces() {
        String username = " ";
        String password = " ";
        String error = null;
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account createdAccount = accountService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test ////////////////////////////
    public void testCreateAccountUsernameExists() {
        String username1 = "username";
        String username2 = "username";
        String password = "password";
        String error = null;
        Account account1 = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.findAccountByUsername(username1)).thenReturn(account1);

        try {
            Account createdAccount = accountService.createAccount(username1, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username already exists!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testCreateCustomer() {
        String username = "testUsername";

        // will be returned, if not,
        // null will be returned
        when(customerRepository.save(any(Customer.class))).thenAnswer((invocation) -> invocation.getArgument(0));
        Customer customer = null;
        try {
            customer = accountService.createCustomer(username);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            assertEquals("Username cannot be empty!", e.getMessage());
        }
        // check model in memory
        assertNotNull(customer);
    }

    @Test
    public void testCreateCustomerNull() {
        String username = null;
        String error = null;
        Customer customer = null;
        try {
            customer = accountService.createCustomer(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
        // check model in memory
        assertEquals(null, customer);
    }

    @Test
    public void testCreateCustomerEmpty() {
        String username = "";
        String error = null;
        Customer customer = null;
        try {
            customer = accountService.createCustomer(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
        // check model in memory
        assertEquals(null, customer);
    }

    @Test
    public void testCreateCustomerSpaces() {
        String username = " ";
        String error = null;
        Customer customer = null;
        try {
            customer = accountService.createCustomer(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
        // check model in memory
        assertEquals(null, customer);
    }

    @Test
    public void testUpdateAccountUsername(String oldUsername, String username) {
        Account account1 = accountRepository.findAccountByUsername(oldUsername);

        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = accountService.updateAccountUsername(oldUsername, username);

        assertNotNull(updatedAccount);
        assertEquals(username, updatedAccount.getUsername());
        verify(accountRepository, times(1)).save(account1);

    }

    @Test
    public void testUpdateAccountPassword(String username, String oldPassword, String newPassword) {
        Account account1 = accountRepository.findAccountByUsername(username);
        when(accountRepository.findAccountByUsername(username)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account updatedAccount = accountService.updateAccountPassword(username, oldPassword, newPassword);

        assertNotNull(updatedAccount);
        assertEquals(newPassword, updatedAccount.getPassword());
        verify(accountRepository, times(1)).save(account1);
    }

    @Test
    public void testUpdateAccountUsernameNull() {
        String oldUsername = "Person1";
        String username = null;
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccountUsername(oldUsername, username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountPasswordNull() {
        String oldUsername = "Person1";
        String oldPassword = null;

        String newPassword = null;
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        assertEquals("Username cannot be empty! Password cannot be empty! ", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountUsernameEmpty() {
        String oldUsername = "Person1";
        String username = "";
        String error = null;

        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccountUsername(oldUsername, username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountPasswordEmpty() {
        String oldUsername = "Person1";
        String password = "";
        String newPassword = "";
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccountPassword(oldUsername, password, newPassword);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty! Password cannot be empty! ", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountUsernameSpaces() {
        String oldUsername = "Person1";
        String username = " ";
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccountUsername(oldUsername, username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty! Password cannot be empty! ", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountPasswordSpaces() {
        String oldUsername = "Person1";
        String password = " ";
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccountPassword(oldUsername, password, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty! Password cannot be empty! ", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testUpdateAccountUsernameExists() {
        String oldUsername = "oldUsername";
        String username = "Person1";
        String error = null;
        Account account1 = accountRepository.findAccountByUsername(oldUsername);
        when(accountRepository.findAccountByUsername(oldUsername)).thenReturn(account1);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        when(accountRepository.findAccountByUsername(username)).thenReturn(account1);

        try {
            Account updatedAccount = accountService.updateAccountUsername(oldUsername, username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username already exists!", error);
        verify(accountRepository, times(0)).save(account1);
    }

    @Test
    public void testDeleteAccount() {
        int accountId = 1;
        Account account1 = new Account();
        when(accountRepository.findAccountByAccountId(accountId)).thenReturn(account1);

        accountService.deleteAccount(accountId);

        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    public void testDeleteAccountNull() {
        int accountId = 1;
        Account account1 = null;
        String error = null;
        when(accountRepository.findAccountByAccountId(accountId)).thenReturn(account1);

        try {
            accountService.deleteAccount(accountId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account does not exist", error);
        verify(accountRepository, times(0)).deleteById(accountId);
    }

    @Test
    public void testGetAccount() {
        int accountId = 1;
        Account account1 = new Account();
        when(accountRepository.findAccountByAccountId(accountId)).thenReturn(account1);

        Account account = accountService.getAccountByAccountId(accountId);

        assertNotNull(account);
        assertEquals(account1, account);
    }

    @Test
    public void testGetAccountNull() {
        int accountId = 1;
        Account account1 = null;
        String error = null;
        when(accountRepository.findAccountByAccountId(accountId)).thenReturn(account1);

        try {
            Account account = accountService.getAccountByAccountId(accountId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account does not exist", error);
    }

    @Test
    public void testGetAllAccounts() {
        Account account1 = new Account();
        Account account2 = new Account();
        when(accountRepository.findAll()).thenReturn(java.util.Arrays.asList(account1, account2));

        java.util.List<Account> accounts = accountService.getAllAccounts();

        assertNotNull(accounts);
        assertEquals(2, accounts.size());
    }

    @Test
    public void testDeleteCustomer() {
        String username = "testUsername";
        when(customerRepository.findAccountRoleByUsername(username)).thenReturn(new Customer());
        when(customerRepository.save(any(Customer.class))).thenAnswer((invocation) -> invocation.getArgument(0));
        try {
            accountService.deleteCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            assertEquals("Username cannot be empty!", e.getMessage());
        }
        verify(customerRepository, times(1)).delete(any(Customer.class));
    }

    @Test
    public void testDeleteCustomerNull() {
        String username = null;
        String error = null;
        try {
            accountService.deleteCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testDeleteCustomerEmpty() {
        String username = "";
        String error = null;
        try {
            accountService.deleteCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testDeleteCustomerSpaces() {
        String username = " ";
        String error = null;
        try {
            accountService.deleteCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testGetCustomer() {
        String username = "testUsername";
        when(customerRepository.findAccountRoleByUsername(username)).thenReturn(new Customer());
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            assertEquals("Username cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void testGetCustomerNull() {
        String username = null;
        String error = null;
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testGetCustomerEmpty() {
        String username = "";
        String error = null;
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testGetCustomerSpaces() {
        String username = " ";
        String error = null;
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testGetCustomerDoesNotExist() {
        String username = "testUsername";
        when(customerRepository.findAccountRoleByUsername(username)).thenReturn(null);
        String error = null;
        try {
            accountService.getCustomerByUsername(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Customer does not exist!", error);
    }

    @Test
    public void testDeleteAllCustomers() {
        accountService.deleteAllCustomers();
        verify(customerRepository, times(1)).deleteAll();
    }

    @Test
    public void testGetAllCustomers() {
        accountService.getAllCustomers();
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void getCustomerByRoleId() {
        int roleId = 1;
        when(customerRepository.findAccountRoleByAccountRoleId(roleId)).thenReturn(new Customer());
        try {
            accountService.getCustomerByAccountRoleId(roleId);
        } catch (IllegalArgumentException e) {
            // Check that no error occurred
            assertEquals("Role ID cannot be empty!", e.getMessage());
        }
    }

    @Test
    public void getCustomerByRoleIdDoesNotExist() {
        int roleId = 1;
        when(customerRepository.findAccountRoleByAccountRoleId(roleId)).thenReturn(null);
        String error = null;
        try {
            accountService.getCustomerByAccountRoleId(roleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Customer does not exist!", error);
    }

    @Test ///////////////
    public void getCustomerByRoleIdNull() {
        int roleId = -1;
        String error = null;
        try {
            accountService.getCustomerByAccountRoleId(roleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("AccountRoleId is not valid!", error);
    }

    @Test
    public void getAllCustomers() {
        accountService.getAllCustomers();
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void deleteAllCustomers() {
        accountService.deleteAllCustomers();
        verify(customerRepository, times(1)).deleteAll();
    }

    @Test
    public void testGetOwnerByAccountRoleId() {
        String username = "testname";
        String password = "testpassword";

        ownerRepository.save(new Owner(new Account(username, password)));
        int id = 1;
        Owner owner = accountService.getOwnerByAccountRoleId(id);

        try {
            owner = accountService.getOwnerByAccountRoleId(id);
        } catch (IllegalArgumentException e) {
            assertEquals("AccountRoleId cannot be found!", e.getMessage()); // TODO: Find the exact message
        }

        assertNotNull(owner);
    }

    @Test
    public void testGetOwnerByAccountRoleIdZero() {
        int accountRoleId = 0;
        String error = null;
        Owner owner = null;

        try {
            owner = accountService.getOwnerByAccountRoleId(accountRoleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("AccountRoleId cannot be negative!", error); // TODO: Modify the message
        assertEquals(null, owner);
    }

    @Test
    public void testGetOwnerByAccountRoleIdNegative() {
        int accountRoleId = -1;
        String error = null;
        Owner owner = null;

        try {
            owner = accountService.getOwnerByAccountRoleId(accountRoleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("AccountRoleId cannot be negative!", error); // TODO: Modify the message
        assertEquals(null, owner);
    }

    @Test
    public void testGetOwnerByAccountRoleIdDoesNotExist() {
        int accountRoleId = 1;
        when(ownerRepository.findAccountRoleByAccountRoleId(accountRoleId)).thenReturn(null);
        String error = null;
        Owner owner = null;

        try {
            owner = accountService.getOwnerByAccountRoleId(accountRoleId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        // check error
        assertEquals("Owner does not exist!", error); // TODO: Modify the message
        assertEquals(null, owner);

    }

    @Test
    public void testCreateInstructor() {

        when(instructorRepository.save(any(Instructor.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        final String username = "gumball";
        final String password = "fiddlesticks";
        final String description = "He can beat Goku";
        final String image = "image";

        Account account = new Account();
        Instructor instructor = null;

        try {

            // note this method also create the account.
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);

        } catch (IllegalArgumentException e) {

            fail("IllegalArgumentException not expected here");

        }

        assertNotNull(instructor);
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
        assertEquals(description, instructor.getDescription());
        assertEquals(image, instructor.getProfilePicURL());

        // check if all was added
        verify(instructorRepository, times(1)).save(instructor);

    }

    @Test
    public void testCreateInstructorNull() { // make an empty username

        when(instructorRepository.save(any(Instructor.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        final String username = "";
        final String password = "fiddlesticks";
        final String description = "He can beat Goku";
        final String image = "image";

        // Account account = new Account();
        Instructor instructor = null;

        try {

            // note this method also create the account.
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);
            fail("IllegalArgumentException expected!");

        } catch (IllegalArgumentException e) {

            // expected here!
            assertNull(instructor);

        }

        // check if all was added
        verify(instructorRepository, times(0)).save(instructor);

    }

    @Test
    public void testCreateInstructorSpaces() { // make an empty username
        String username = " ";
        String password = "fiddlesticks";
        String description = "He can beat Goku";
        String image = "image";
        String error = null;

        when(instructorRepository.save(any(Instructor.class)))
                .thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Account account = new Account();
        Instructor instructor = null;

        try {

            // note this method also create the account.
            instructor = accountService.createInstructor(username, InstructorStatus.Active, description,
                    image);
            fail("IllegalArgumentException expected!");
        } catch (IllegalArgumentException e) {

            // expected here!
            assertNull(instructor);

        }
    }

    // RETRIEVE TESTING
    // These tests assume that creation works.

    @Test
    public void testGetInstructorById() {

        // again we now assume that creating will work now
        final String username = "Shakira";
        final String password = "elo111";
        final String description = "Made hips don't lie";
        final String image = "image1";

        Instructor sampleInstructor = accountService.createInstructor(username, InstructorStatus.Active,
                description, image);
        Instructor verifyInstructor = new Instructor();

        Account sampleAccount = sampleInstructor.getAccount();
        int sampleInstructorID = sampleAccount.getAccountId();

        when(instructorRepository.findAccountRoleByAccountRoleId(sampleInstructorID))
                .thenAnswer((InvocationOnMock invocation) -> sampleInstructor);

        try {

            verifyInstructor = accountService.getAccountByAccountId(sampleInstructorID);

        } catch (IllegalArgumentException e) {

            fail("IllegalArgumentException not expected here");

        }

        Account sampleAccount2 = verifyInstructor.getAccount();

        assertNotNull(verifyInstructor);
        assertEquals(sampleInstructorID, sampleAccount2.getAccountId()); // verify if ID match up
        assertEquals(sampleAccount, sampleAccount2);
        assertEquals(sampleInstructor, verifyInstructor);

    }

    // REMOVE TESTING

    @Test
    public void testCreateAndDeleteInstructor() {

        // again we now assume that creating will work now
        final String username = "Beatrix Kiddo";
        final String password = "Kill Bill";
        final String description = "Okinawa Katana Expert";
        final String image = "image67";

        Instructor instructor = accountService.createInstructor(username, InstructorStatus.Active,
                description, image);
        Account account = instructor.getAccount();
        int instructorID = account.getAccountId();

        when(instructorRepository.findAccountRoleByAccountRoleId(instructorID))
                .thenAnswer((InvocationOnMock invocation) -> instructor);

        try {

            accountService.deleteInstructor(instructorID);

        } catch (IllegalArgumentException e) {

            fail("IllegalArgumentException not expected here");

        }

        verify(instructorRepository, times(0)).save(instructor);

    }
}
//

// TODO 2 - checkAccountOwner
// @Test
// public void testCheckAccountOwner() {
// int id = 1;
// // ownerRepository.Owner o = new Owner();
// when(ownerRepository.findAccountRoleByAccountRoleId(id)).thenReturn(new
// Owner());
// assertTrue(accountService.checkAccountOwner(id));
// }

// @Test
// public void testCheckAccountOwnerNegative() {
// int id = -1;
// when(ownerRepository.findAccountRoleByAccountRoleId(id)).thenReturn(new
// Owner());
// assertTrue(accountService.checkAccountOwner(id));
// }

// @Test
// public void testCheckAccountOwnerZero() {
// int id = 0;
// when(ownerRepository.findAccountRoleByAccountRoleId(id)).thenReturn(new
// Owner());
// assertTrue(accountService.checkAccountOwner(id));
// }

// @Test
// public void testCheckAccountOwnerDoesNotExist() {
// int id = 1;
// when(ownerRepository.findAccountRoleByAccountRoleId(id)).thenReturn(null);
// assertFalse(accountService.checkAccountOwner(id));
// }
