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
