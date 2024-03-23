package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.List;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;

/*
 * Tests class for AccountManagerService
 *
 * 
 *@author Emilie Ruel and  Mathias 
 */
@ExtendWith(MockitoExtension.class)
public class TestAccountService {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private OwnerRepository ownerRepository;

    // ACCOUNT KEYS
    private static final int CUSTOMER_ACCOUNT_KEY = 1;
    private static final int APPROVED_INSTRUCTOR_ACCOUNT_KEY = 2;
    private static final int DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY = 3;
    // USERNAMES
    private static final String CUSTOMER_USERNAME = "customer";
    private static final String APPROVED_INSTRUCTOR_USERNAME = "approvedInstructor";
    private static final String DISAPPROVED_INSTRUCTOR_USERNAME = "disapprovedInstructor";
    private static final String NEW_ACCOUNT_USERNAME = "newAccount";
    private static final String NEW_CUSTOMER_USERNAME = "newCustomer";

    // PASSWORDS
    private static final String CUSTOMER_PASSWORD = "customerPassword";
    private static final String APPROVED_INSTRUCTOR_PASSWORD = "approvedInstructorPassword";
    private static final String DISAPPROVED_INSTRUCTOR_PASSWORD = "disapprovedInstructorPassword";
    private static final String NEW_ACCOUNT_PASSWORD = "newAccountPassword";
    private static final String NEW_CUSTOMER_PASSWORD = "newCustomerPassword";

    @InjectMocks
    private AccountManagementService accountManagementService;

    @SuppressWarnings("null")
    @BeforeEach
    void setMockOutput() {
        lenient().when(accountRepository.findAccountByAccountId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(CUSTOMER_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername(CUSTOMER_USERNAME);
                account.setPassword(CUSTOMER_PASSWORD);
                account.setAccountId(CUSTOMER_ACCOUNT_KEY);
                return account;
            } else if (invocation.getArgument(0).equals(APPROVED_INSTRUCTOR_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername(APPROVED_INSTRUCTOR_USERNAME);
                account.setPassword(APPROVED_INSTRUCTOR_PASSWORD);
                account.setAccountId(APPROVED_INSTRUCTOR_ACCOUNT_KEY);
                return account;
            } else if (invocation.getArgument(0).equals(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername(DISAPPROVED_INSTRUCTOR_USERNAME);
                account.setPassword(DISAPPROVED_INSTRUCTOR_PASSWORD);
                account.setAccountId(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY);
                return account;
            } else {
                return null;
            }
        });

        lenient().when(accountRepository.findAccountByUsername(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(CUSTOMER_USERNAME)) {
                        Account account = new Account();
                        account.setUsername(CUSTOMER_USERNAME);
                        account.setPassword(CUSTOMER_PASSWORD);
                        account.setAccountId(CUSTOMER_ACCOUNT_KEY);
                        return account;
                    } else if (invocation.getArgument(0).equals(APPROVED_INSTRUCTOR_USERNAME)) {
                        Account account = new Account();
                        account.setUsername(APPROVED_INSTRUCTOR_USERNAME);
                        account.setPassword(APPROVED_INSTRUCTOR_PASSWORD);
                        account.setAccountId(APPROVED_INSTRUCTOR_ACCOUNT_KEY);
                        return account;
                    } else if (invocation.getArgument(0).equals(DISAPPROVED_INSTRUCTOR_USERNAME)) {
                        Account account = new Account();
                        account.setUsername(DISAPPROVED_INSTRUCTOR_USERNAME);
                        account.setPassword(DISAPPROVED_INSTRUCTOR_PASSWORD);
                        account.setAccountId(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY);
                        return account;
                    } else {
                        return null;
                    }
                });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(accountRepository.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
    }

    @SuppressAjWarnings("null")

    // test for creating a new account null -> fail
    @Test
    public void testCreateAccountUsernameNull() {
        String error = null;

        try {
            accountManagementService.createAccount(null, NEW_ACCOUNT_PASSWORD);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be null!", error);
    }

    @Test
    public void testCreateAccountPasswordNull() {
        String error = null;

        try {
            accountManagementService.createAccount(NEW_ACCOUNT_USERNAME, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Password cannot be null!", error);
    }

    // test for creating a new account with empty username -> fail
    @Test
    public void testCreateAccountUsernameEmpty() {
        String error = null;

        try {
            accountManagementService.createAccount("", NEW_ACCOUNT_PASSWORD);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty!", error);
    }

    // test for creating a new account with empty password -> fail
    @Test
    public void testCreateAccountEmptyPassword() {
        String error = null;

        try {
            accountManagementService.createAccount(NEW_ACCOUNT_USERNAME, "");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Password cannot be empty!", error);
    }

    // test for creating a new account with whitespace username -> fail
    @Test
    public void testCreateAccountUsernameWhitespace() {
        String error = null;

        try {
            accountManagementService.createAccount("a b c", NEW_ACCOUNT_PASSWORD);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot contain spaces!", error);
    }

    // test for creating a new account with whitespace password -> fail
    @Test
    public void testCreateAccountPasswordWhitespace() {
        String error = null;

        try {
            accountManagementService.createAccount(NEW_ACCOUNT_USERNAME, "a b c");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Password cannot contain spaces!", error);
    }

    // test for creating a new account with username already taken -> fail
    @Test
    public void testCreateAccountUsernameAlreadyTaken() {
        String error = null;

        try {
            accountManagementService.createAccount(CUSTOMER_USERNAME, NEW_ACCOUNT_PASSWORD);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account already exists!", error);
    }

    // GET

    // test get account by id -> success
    @Test
    public void testGetAccountById() {
        Account account = null;

        try {
            account = accountManagementService.getAccountByAccountId(CUSTOMER_ACCOUNT_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(account);
        assertEquals(CUSTOMER_ACCOUNT_KEY, account.getAccountId());
    }

    // test get account by id, non existing -> fail
    @Test
    public void testGetAccountByIdNonExisting() {
        String error = null;

        try {
            accountManagementService.getAccountByAccountId(1000);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account does not exist", error);
    }

    // test get account by id, invalid -> fail
    @Test
    public void testGetAccountByIdInvalid() {
        String error = null;

        try {
            accountManagementService.getAccountByAccountId(-1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account ID must be greater than 0", error);
    }

    // test get account by username -> success
    @Test
    public void testGetAccountByUsername() {
        Account account = null;

        try {
            account = accountManagementService.getAccountByUsername(CUSTOMER_USERNAME);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(account);
        assertEquals(CUSTOMER_USERNAME, account.getUsername());
    }

    // test get account by username, non existing -> fail
    @Test
    public void testGetAccountByUsernameNonExisting() {
        String error = null;

        try {
            accountManagementService.getAccountByUsername("nonExisting");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account does not exist", error);
    }

    // test get account by username, null -> fail
    @Test
    public void testGetAccountByUsernameNull() {
        String error = null;

        try {
            accountManagementService.getAccountByUsername(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be null!", error);
    }

    // test get account by username, whitespace -> fail
    @Test
    public void testGetAccountByUsernameWhitespace() {
        String error = null;

        try {
            accountManagementService.getAccountByUsername(" a b c");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot contain spaces!", error);
    }

    // test get account by username, empty -> fail
    @Test
    public void testGetAccountByUsernameEmpty() {
        String error = null;

        try {
            accountManagementService.getAccountByUsername("");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty!", error);
    }

    // test get all accounts -> success
    @Test
    public void testGetAllAccounts() {
        List<Account> accounts = accountManagementService.getAllAccounts();
        assertNotNull(accounts);
        assertEquals(3, accounts.size());
    }

    // UPDATE

    // test update account username -> success
    @Test
    public void testUpdateAccountUsername() {
        Account account = null;

        try {
            account = accountManagementService.updateAccountUsername(CUSTOMER_USERNAME, NEW_CUSTOMER_USERNAME);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(account);
        assertEquals(NEW_CUSTOMER_USERNAME, account.getUsername());
    }

    // test update account username, null -> fail
    @Test
    public void testUpdateAccountNewUsernameNull() {
        String error = null;

        try {
            accountManagementService.updateAccountUsername(CUSTOMER_USERNAME, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New Username cannot be null", error);
    }

    @Test
    public void testUpdateAccountNewUsernameEmpty() {
        String error = null;

        try {
            accountManagementService.updateAccountUsername(CUSTOMER_USERNAME, "");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New Username cannot be empty", error);
    }

    @Test
    public void testUpdateAccountNewUsernameWhitespace() {
        String error = null;

        try {
            accountManagementService.updateAccountUsername(CUSTOMER_USERNAME, "a b c");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New Username cannot contain spaces", error);
    }

    @Test
    public void testUpdateAccountNewUsernameAlreadyExists() {
        String error = null;

        try {
            accountManagementService.updateAccountUsername(CUSTOMER_USERNAME, CUSTOMER_USERNAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account with the new Username already exists", error);
    }

    @Test
    public void testUpdateAccountOldUsernameNull() {
        String error = null;

        try {
            accountManagementService.updateAccountUsername(null, NEW_CUSTOMER_USERNAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Old Username cannot be null", error);
    }

    @Test
    public void testUpdateAccountOldUsernameEmpty() {
        String error = null;

        try {
            accountManagementService.updateAccountUsername("", NEW_CUSTOMER_USERNAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Old Username cannot be empty", error);
    }

    @Test
    public void testUpdateAccountOldUsernameWhitespace() {
        String error = null;

        try {
            accountManagementService.updateAccountUsername("a b c", NEW_CUSTOMER_USERNAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Old Username cannot contain spaces", error);
    }

    @Test
    public void testUpdateAccountOldUsernameNonExisting() {
        String error = null;

        try {
            accountManagementService.updateAccountUsername("nonExisting", NEW_CUSTOMER_USERNAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account with the old Username does not exist", error);
    }

    @Test
    public void testUpdateAccountPassword() {
        Account account = null;

        try {
            account = accountManagementService.updateAccountPassword(CUSTOMER_USERNAME, CUSTOMER_PASSWORD,
                    NEW_CUSTOMER_PASSWORD);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(account);
        assertEquals(NEW_CUSTOMER_PASSWORD, account.getPassword());
    }

    @Test
    public void testUpdateAccountNewPasswordNull() {
        String error = null;

        try {
            accountManagementService.updateAccountPassword(CUSTOMER_USERNAME, CUSTOMER_PASSWORD, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New password cannot be empty", error);
    }

    @Test
    public void testUpdateAccountNewPasswordEmpty() {
        String error = null;

        try {
            accountManagementService.updateAccountPassword(CUSTOMER_USERNAME, CUSTOMER_PASSWORD, "");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New password cannot be empty", error);
    }

    @Test
    public void testUpdateAccountNewPasswordWhitespace() {
        String error = null;

        try {
            accountManagementService.updateAccountPassword(CUSTOMER_USERNAME, CUSTOMER_PASSWORD, "a b c");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("New password cannot be empty", error);
    }

    @Test
    public void testUpdateAccountOldPasswordNull() {
        String error = null;

        try {
            accountManagementService.updateAccountPassword(CUSTOMER_USERNAME, null, NEW_CUSTOMER_PASSWORD);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Old password cannot be empty", error);
    }

    @Test
    public void testUpdateAccountOldPasswordEmpty() {
        String error = null;

        try {
            accountManagementService.updateAccountPassword(CUSTOMER_USERNAME, "", NEW_CUSTOMER_PASSWORD);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Old password cannot be empty", error);
    }

    @Test
    public void testUpdateAccountOldPasswordWhitespace() {
        String error = null;

        try {
            accountManagementService.updateAccountPassword(CUSTOMER_USERNAME, "a b c", NEW_CUSTOMER_PASSWORD);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Old password cannot be empty", error);
    }

    @Test
    public void testUpdateAccountOldPasswordNotValid() {
        String error = null;

        try {
            accountManagementService.updateAccountPassword(CUSTOMER_USERNAME, "nonExisting", NEW_CUSTOMER_PASSWORD);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Old password is incorrect", error);
    }

    @Test
    public void testUpdateAccountPasswordNotFound() {
        String error = null;

        try {
            accountManagementService.updateAccountPassword("nonExisting", CUSTOMER_PASSWORD, NEW_CUSTOMER_PASSWORD);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account does not exist", error);
    }

    // DELETE

    // test delete account -> success
    @Test
    public void testDeleteAccount() {
        try {
            accountManagementService.deleteAccount(CUSTOMER_ACCOUNT_KEY);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertTrue(accountManagementService.getAccountByAccountId(CUSTOMER_ACCOUNT_KEY).equals(null));
    }
}