package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
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

    // account keys
    private static final int CUSTOMER_ACCOUNT_KEY = 1;
    private static final int APPROVED_INSTRUCTOR_ACCOUNT_KEY = 2;
    private static final int DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY = 3;
    private static final int NEW_CUSTOMER_ACCOUNT_KEY = 4;

    @InjectMocks
    private AccountManagementService accountManagementService;

    @Mock
    private ScheduledActivityManagementService scheduledActivityManagementService;

    @Mock
    private ActivityManagementService activityManagementService;

    @Mock
    private ScheduledActivityManagementService scheduledActivityService;

    @SuppressWarnings("null")
    @BeforeEach
    void setMockOutput() {
        lenient().when(accountRepository.findAccountByAccountId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(CUSTOMER_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername("customer");
                account.setPassword("password");
                account.setAccountId(CUSTOMER_ACCOUNT_KEY);
                return account;
            } else if (invocation.getArgument(0).equals(APPROVED_INSTRUCTOR_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername("approvedInstructor");
                account.setPassword("password");
                account.setAccountId(APPROVED_INSTRUCTOR_ACCOUNT_KEY);
                return account;
            } else if (invocation.getArgument(0).equals(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername("disapprovedInstructor");
                account.setPassword("password");
                account.setAccountId(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY);
                return account;
            } else if (invocation.getArgument(0).equals(NEW_CUSTOMER_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setUsername("newCustomer");
                account.setPassword("password");
                account.setAccountId(NEW_CUSTOMER_ACCOUNT_KEY);
                return account;
            } else {
                return null;
            }
        });

        lenient().when(accountRepository.findAccountByUsername(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals("customer")) {
                        Account account = new Account();
                        account.setUsername("customer");
                        account.setPassword("password");
                        account.setAccountId(CUSTOMER_ACCOUNT_KEY);
                        return account;
                    } else if (invocation.getArgument(0).equals("approvedInstructor")) {
                        Account account = new Account();
                        account.setUsername("approvedInstructor");
                        account.setPassword("password");
                        account.setAccountId(APPROVED_INSTRUCTOR_ACCOUNT_KEY);
                        return account;
                    } else if (invocation.getArgument(0).equals("disapprovedInstructor")) {
                        Account account = new Account();
                        account.setUsername("disapprovedInstructor");
                        account.setPassword("password");
                        account.setAccountId(DISAPPROVED_INSTRUCTOR_ACCOUNT_KEY);
                        return account;
                    } else if (invocation.getArgument(0).equals("newCustomer")) {
                        Account account = new Account();
                        account.setUsername("newCustomer");
                        account.setPassword("password");
                        account.setAccountId(NEW_CUSTOMER_ACCOUNT_KEY);
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

    // CREATE
    // test for creating a new account-> success
    @Test
    public void testCreateAccount() {
        String username = "newCustomer";
        String password = "password";
        Account account = null;
        try {
            account = accountManagementService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(account);
        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
    }

    // test for creating a new account null -> fail
    @Test
    public void testCreateAccountNull() {
        String username = null;
        String password = null;
        String error = null;
        Account account = null;
        try {
            account = accountManagementService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals("Username cannot be empty! Password cannot be empty!", error);
    }

    // test for creating a new account with empty username -> fail
    @Test
    public void testCreateAccountEmptyUsername() {
        String username = "";
        String password = "password";
        String error = null;
        Account account = null;
        try {
            account = accountManagementService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals("Username cannot be empty!", error);
    }

    // test for creating a new account with empty password -> fail
    @Test
    public void testCreateAccountEmptyPassword() {
        String username = "newCustomer";
        String password = "";
        String error = null;
        Account account = null;
        try {
            account = accountManagementService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals("Password cannot be empty!", error);
    }

    // test for creating a new account with whitespace username -> fail
    @Test
    public void testCreateAccountWhitespaceUsername() {
        String username = " ";
        String password = "password";
        String error = null;
        Account account = null;
        try {
            account = accountManagementService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals("Username cannot be empty!", error);
    }

    // test for creating a new account with whitespace password -> fail
    @Test
    public void testCreateAccountWhitespacePassword() {
        String username = "newCustomer";
        String password = " ";
        String error = null;
        Account account = null;
        try {
            account = accountManagementService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals("Password cannot be empty!", error);
    }

    // test for creating a new account with username already taken -> fail
    @Test
    public void testCreateAccountUsernameAlreadyTaken() {
        String username = "customer";
        String password = "password";
        String error = null;
        Account account = null;
        try {
            account = accountManagementService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        try {
            account = accountManagementService.createAccount(username, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals("Username already taken!", error);
    }

    // GET

    // test get account by id -> success
    @Test
    public void testGetAccountById() {
        String username = "customer";
        String password = "password";
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
        Account account = null;
        try {
            account = accountManagementService.getAccountByAccountId(1000);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals("Account not found!", error);
    }

    // test get account by id, invalid -> fail
    @Test
    public void testGetAccountByIdInvalid() {
        String error = null;
        Account account = null;
        try {
            account = accountManagementService.getAccountByAccountId(-1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals("Account not found!", error);
    }

    // test get account by username -> success
    @Test
    public void testGetAccountByUsername() {
        String username = "customer";
        String password = "password";
        Account account = null;
        try {
            account = accountManagementService.getAccountByUsername(username);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(account);
        assertEquals(username, account.getUsername());
    }

    // test get account by username, non existing -> fail
    @Test
    public void testGetAccountByUsernameNonExisting() {
        String error = null;
        Account account = null;
        try {
            account = accountManagementService.getAccountByUsername("nonExisting");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals("Account not found!", error);
    }

    // test get account by username, null -> fail
    @Test
    public void testGetAccountByUsernameNull() {
        String error = null;
        Account account = null;
        try {
            account = accountManagementService.getAccountByUsername(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals("Username cannot be empty!", error);
    }

    // test get account by username, whitespace -> fail
    @Test
    public void testGetAccountByUsernameWhitespace() {
        String error = null;
        Account account = null;
        try {
            account = accountManagementService.getAccountByUsername(" ");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals("Username cannot be empty!", error);
    }

    // test get account by username, empty -> fail
    @Test
    public void testGetAccountByUsernameEmpty() {
        String error = null;
        Account account = null;
        try {
            account = accountManagementService.getAccountByUsername("");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(account);
        assertEquals("Username cannot be empty!", error);
    }

    // test get all accounts -> success
    @Test
    public void testGetAllAccounts() {
        List<Account> accounts = accountManagementService.getAllAccounts();
        assertNotNull(accounts);
        assertEquals(4, accounts.size());

    }

    // UPDATE

    // test update account username -> success
    @Test
    public void testUpdateAccountUsername() {
        String username = "newCustomer";
        String password = "password";
        Account account = null;
        account = accountManagementService.createAccount(username, password);
        try {
            account = accountManagementService.updateAccountUsername(username, "newUsername");
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertEquals("newUsername", account.getUsername());
    }

    // test update account username, null -> fail
    @Test
    public void testUpdateAccountUsernameNull() {
        String username = "newCustomer";
        String password = "password";
        String error = null;
        Account account = null;
        account = accountManagementService.createAccount(username, password);
        try {
            account = accountManagementService.updateAccountUsername(username, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(account.getUsername(), username);
        assertEquals("Username cannot be empty!", error);
    }

    // test update account username, empty -> fail
    @Test
    public void testUpdateAccountUsernameEmpty() {
        String username = "newCustomer";
        String password = "password";
        String error = null;
        Account account = null;
        account = accountManagementService.createAccount(username, password);
        try {
            account = accountManagementService.updateAccountUsername(username, "");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(account.getUsername(), username);
        assertEquals("Username cannot be empty!", error);
    }

    // test update account username, whitespace -> fail
    @Test
    public void testUpdateAccountUsernameWhitespace() {
        String username = "newCustomer";
        String password = "password";
        String error = null;
        Account account = null;
        account = accountManagementService.createAccount(username, password);
        try {
            account = accountManagementService.updateAccountUsername(username, " ");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(account.getUsername(), username);
        assertEquals("Username cannot be empty!", error);
    }

    // test update account username, already taken -> fail
    @Test
    public void testUpdateAccountUsernameAlreadyTaken() {
        String username = "newCustomer";
        String username2 = "customer";
        String password = "password";
        String error = null;
        Account account = null;
        Account account2 = null;
        account = accountManagementService.createAccount(username, password);
        account2 = accountManagementService.createAccount(username2, password);
        try {
            account = accountManagementService.updateAccountUsername(username, username2);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(account.getUsername(), username);
        assertEquals("Username already taken!", error);
    }

    // test update account password -> success
    @Test
    public void testUpdateAccountPassword() {
        String username = "newCustomer";
        String password = "password";
        Account account = null;
        account = accountManagementService.createAccount(username, password);
        try {
            account = accountManagementService.updateAccountPassword(username, password, "newPassword");
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(account);
        assertEquals("newPassword", account.getPassword());
    }

    // test update account password, same password -> fail
    @Test
    public void testUpdateAccountPasswordInvalid() {
        String username = "newCustomer";
        String password = "password";
        String error = null;
        Account account = null;
        account = accountManagementService.createAccount(username, password);
        try {
            account = accountManagementService.updateAccountPassword(username, password, password);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Password cannot be empty!", error);
        assertEquals(account.getPassword(), password);
    }

    // test update account password, wrong password -> fail
    @Test
    public void testUpdateAccountPasswordWrongPassword() {
        String username = "newCustomer";
        String password = "password";
        String error = null;
        Account account = null;
        account = accountManagementService.createAccount(username, password);
        try {
            account = accountManagementService.updateAccountPassword(username, "wrongPassword", "newPassword");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Wrong password!", error);
        assertEquals(account.getPassword(), password);
    }

    // test update account password, null -> fail
    @Test
    public void testUpdateAccountPasswordNull() {
        String username = "newCustomer";
        String password = "password";
        String error = null;
        Account account = null;
        account = accountManagementService.createAccount(username, password);
        try {
            account = accountManagementService.updateAccountPassword(username, password, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Password cannot be empty!", error);
        assertEquals(account.getPassword(), password);
    }

    // test update account password, empty -> fail
    @Test
    public void testUpdateAccountPasswordEmpty() {
        String username = "newCustomer";
        String password = "password";
        String error = null;
        Account account = null;
        account = accountManagementService.createAccount(username, password);
        try {
            account = accountManagementService.updateAccountPassword(username, password, "");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Password cannot be empty!", error);
        assertEquals(account.getPassword(), password);
    }

    // test update account password, whitespace -> fail
    @Test
    public void testUpdateAccountPasswordWhitespace() {
        String username = "newCustomer";
        String password = "password";
        String error = null;
        Account account = null;
        account = accountManagementService.createAccount(username, password);
        try {
            account = accountManagementService.updateAccountPassword(username, password, " ");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Password cannot be empty!", error);
        assertEquals(account.getPassword(), password);
    }

    // DELETE

    // test delete account -> success
    @Test
    public void testDeleteAccount() {
        String username = "newCustomer";
        String password = "password";
        Account account = null;
        account = accountManagementService.createAccount(username, password);
        try {
            accountManagementService.deleteAccount(account.getAccountId());
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNull(accountManagementService.getAccountByUsername(username));
    }

    // test delete account, invalid -> fail
    @Test
    public void testDeleteAccountNull() {
        String error = null;
        try {
            accountManagementService.deleteAccount(-1);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Account not found!", error);
    }

}