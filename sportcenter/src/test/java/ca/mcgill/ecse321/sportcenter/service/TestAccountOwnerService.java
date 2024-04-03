package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Owner;

/**
 * Test class for Owner Class
 * 
 * @author Patrick Zakaria
 */
@ExtendWith(MockitoExtension.class)
public class TestAccountOwnerService {
    @InjectMocks
    private AccountManagementService accountManagementService;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private AccountRepository accountRepository;

    // account keys
    private static final int OWNER_ACCOUNT_KEY = 1;
    private static final int NON_OWNER_ACCOUNT_KEY = 2;

    // account credentials
    private static final String OWNER_USERNAME = "owner";
    private static final String OWNER_PASSWORD = "password";

    private static final String INVALID_USERNAME = "inva lid";

    // account role keys
    private static final int OWNER_ROLE_KEY = 1;

    @SuppressWarnings("null")
    @BeforeEach
    public void setMockOutput() {
        lenient().when(accountRepository.findAccountByAccountId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(OWNER_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setAccountId(OWNER_ACCOUNT_KEY);
                account.setUsername(OWNER_USERNAME);
                account.setPassword(OWNER_PASSWORD);
                return account;
            } else if (invocation.getArgument(0).equals(NON_OWNER_ACCOUNT_KEY)) {
                Account account = new Account();
                account.setAccountId(NON_OWNER_ACCOUNT_KEY);
                account.setUsername("customer");
                account.setPassword("password");
                return account;
            } else {
                return null;
            }
        });

        lenient().when(accountRepository.findAccountByUsername(any(String.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(OWNER_USERNAME)) {
                        Account account = new Account();
                        account.setAccountId(OWNER_ACCOUNT_KEY);
                        account.setUsername(OWNER_USERNAME);
                        account.setPassword(OWNER_PASSWORD);
                        return account;
                    } else if (invocation.getArgument(0).equals("customer")) {
                        Account account = new Account();
                        account.setAccountId(NON_OWNER_ACCOUNT_KEY);
                        account.setUsername("customer");
                        account.setPassword("password");
                        return account;
                    } else {
                        return null;
                    }
                });

        lenient().when(ownerRepository.findOwnerByAccountRoleId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(OWNER_ROLE_KEY)) {
                Owner owner = new Owner();
                owner.setAccount(accountRepository.findAccountByUsername(OWNER_USERNAME));
                owner.setAccountRoleId(OWNER_ROLE_KEY);
                return owner;
            } else {
                return null;
            }
        });

        lenient().when(ownerRepository.findOwnerByAccountUsername(any(String.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(OWNER_USERNAME)) {
                        Owner owner = new Owner();
                        owner.setAccount(accountRepository.findAccountByUsername(OWNER_USERNAME));
                        owner.setAccountRoleId(OWNER_ROLE_KEY);
                        return owner;
                    } else {
                        return null;
                    }
                });

        lenient().when(ownerRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<Owner> owners = new ArrayList<>();
            return owners;
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(accountRepository.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(ownerRepository.save(any(Owner.class))).thenAnswer(returnParameterAsAnswer);
    }

    /**
     * Test for creating an owner account -> Success
     */
    @Test
    public void testCreateOwnerAccount() {
        Owner owner = null;
        try {
            owner = accountManagementService.createOwner(OWNER_USERNAME);
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }
        assertNotNull(owner);
        assertEquals(OWNER_USERNAME, owner.getAccount().getUsername());
    }

    /**
     * Test for creating an owner account with invalid username -> Fail
     */
    @Test
    public void testCreateOwnerAccountInvalidUsername() {
        Owner owner = null;
        String error = null;
        try {
            owner = accountManagementService.createOwner(INVALID_USERNAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(owner);
        assertEquals("Username cannot be null, empty or contain spaces!", error);
    }

    /**
     * Test for getting an owner account by username -> Success
     */
    @Test
    public void testGetOwnerAccountByUsername() {
        Owner owner = null;
        try {
            owner = accountManagementService.getOwnerByUsername(OWNER_USERNAME);
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        }
        assertNotNull(owner);
        assertEquals(OWNER_USERNAME, owner.getAccount().getUsername());
    }

    /**
     * Test for getting an owner account by username with invalid username -> Fail
     */
    @Test
    public void testGetOwnerAccountByInvalidUsername() {
        Owner owner = null;
        String error = null;
        try {
            owner = accountManagementService.getOwnerByUsername(INVALID_USERNAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(owner);
        assertEquals("Username cannot be null, empty and spaces!", error);
    }

    /**
     * Test for getting an owner account by account username that doesn't exist ->
     * Fail
     */
    @Test
    public void testGetOwnerAccountByNonExistingAccount() {
        Owner owner = null;
        String error = null;
        try {
            owner = accountManagementService.getOwnerByUsername("nonexisting");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(owner);
        assertEquals("Account does not exist!", error);
    }

    /**
     * Test for getting an owner account by that is not an owner account -> Fail
     */
    @Test
    public void testGetOwnerAccountByNonOwnerAccount() {
        Owner owner = null;
        String error = null;
        try {
            owner = accountManagementService.getOwnerByUsername("customer");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(owner);
        assertEquals("The account is not an owner!", error);
    }

}