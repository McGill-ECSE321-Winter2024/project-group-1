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

public class TestAccountOwnerService {
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
}