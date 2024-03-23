package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import ca.mcgill.ecse321.sportcenter.service.AccountManagementService;
import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;

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
    private static final int NEW_ACCOUNT_ACCOUNT_KEY = 4;

    // USERNAMES
    private static final String CUSTOMER_USERNAME = "customer";
    private static final String APPROVED_INSTRUCTOR_USERNAME = "approvedInstructor";
    private static final String DISAPPROVED_INSTRUCTOR_USERNAME = "disapprovedInstructor";
    private static final String NEW_ACCOUNT_USERNAME = "newAccount";

    // PASSWORDS
    private static final String CUSTOMER_PASSWORD = "customerPassword";
    private static final String APPROVED_INSTRUCTOR_PASSWORD = "approvedInstructorPassword";
    private static final String DISAPPROVED_INSTRUCTOR_PASSWORD = "disapprovedInstructorPassword";
    private static final String NEW_ACCOUNT_PASSWORD = "newAccountPassword";

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

    // CREATE
    // test for creating a new account-> success
    // TODO: BUGGY
    @Test
    public void testCreateAccount() {
        Account account = null;

        try {
            account = accountManagementService.createAccount(NEW_ACCOUNT_USERNAME, NEW_ACCOUNT_PASSWORD);
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }

        assertNotNull(account);
        assertEquals(NEW_ACCOUNT_USERNAME, account.getUsername());
        assertEquals(NEW_ACCOUNT_PASSWORD, account.getPassword());
    }

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

    @Test
    public void testCreateCustomer() {
        Customer customer = null;

        try {
            customer = accountManagementService.createCustomer(CUSTOMER_USERNAME);
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }

        assertNotNull(customer);
        assertEquals(CUSTOMER_USERNAME, customer.getAccount().getUsername());
        assertEquals(CUSTOMER_PASSWORD, customer.getAccount().getPassword());
    }

    @Test
    public void testCreateCustomerNull() {
        String error = null;

        try {
            accountManagementService.createCustomer(null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be null!", error);
    }

    @Test
    public void testCreateCustomerEmpty() {
        String error = null;

        try {
            accountManagementService.createCustomer("");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testCreateCustomerWhitespace() {
        String error = null;

        try {
            accountManagementService.createCustomer("a b c");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Username cannot contain spaces!", error);
    }

    @Test
    public void testCreateCustomerAlreadyExists() {
        String error = null;

        try {
            accountManagementService.createCustomer(CUSTOMER_USERNAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account does not exist!", error);
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
            accountManagementService.getAccountByAccountId(5);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Account does not exist!", error);
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

        assertEquals("Account does not exist!", error);
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