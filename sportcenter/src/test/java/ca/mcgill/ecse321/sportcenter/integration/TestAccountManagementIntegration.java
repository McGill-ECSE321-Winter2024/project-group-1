package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.dto.AccountDto;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Owner;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TestAccountManagementIntegration {

    @Autowired
    private TestRestTemplate account;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    private static final String USERNAME = "testUsername";
    private static final String PASSWORD = "testPassword";
    private static final int ACCOUNTROLEID = 1;
    private static final int ACCOUNTID = 2;
    private static final String INVALID_USERNAME = "testUsernameNotValid";
    private static final String INVALID_PASSWORD = "testPasswordNotValid";
    private static final int INVALID_ACCOUNTROLEID = 5;
    private static final int INVALID_ACCOUNTID = 6;

    @Test
    @Order(1)
    public void testCreateAccount() {
        ResponseEntity<AccountDto> response = account.postForEntity(
                "/account/createAccount", null, AccountDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(2)
    public void testCreateAccountInvalid() {
        ResponseEntity<AccountDto> response = account.postForEntity(
                "/account/createAccount", null,
                AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetAccount() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount",
                AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(4)
    public void testGetAccountInvalid() {
        ResponseEntity<AccountDto> response = account
                .getForEntity("/account/getAccount", AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testDeleteAccount() {
        ResponseEntity<AccountDto> response = account
                .postForEntity("/account/deleteAccount", null, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeleteAccountInvalid() {
        ResponseEntity<AccountDto> response = account
                .postForEntity("/account/deleteAccount", null, AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(7)
    public void testGetAccountAfterDelete() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount",
                AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(8)
    public void testGetAllAccounts() {
        ResponseEntity<AccountDto[]> response = account.getForEntity("/account/getAllAccounts", AccountDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto[] accountDtos = response.getBody();
        assertNotNull(accountDtos);
        assertTrue(accountDtos.length > 0);
    }

    @Test
    @Order(9)
    public void testGetAllAccountsAfterDelete() {
        ResponseEntity<AccountDto[]> response = account.getForEntity("/account/getAllAccounts", AccountDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto[] accountDtos = response.getBody();
        assertNotNull(accountDtos);
        assertTrue(accountDtos.length > 0);
    }

    @Test
    @Order(10)
    public void testUpdateAccount() {
        ResponseEntity<AccountDto> response = account.postForEntity("/account/updateAccount?accountRoleId=", null,
                AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(11)
    public void testUpdateAccountInvalid() {
        ResponseEntity<AccountDto> response = account.postForEntity("/account/updateAccount?accountRoleId=", null,
                AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(12)
    public void testGetAccountAfterUpdate() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount?accountRoleId=",
                AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(13)
    public void testGetAccountAfterUpdateInvalid() {
        ResponseEntity<AccountDto> response = account
                .getForEntity("/account/getAccount?accountRoleId=", AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(14)
    public void testDeleteAccountAfterUpdate() {
        ResponseEntity<AccountDto> response = account
                .postForEntity("/account/deleteAccount?accountRoleId=", null, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(15)
    public void testDeleteAccountAfterUpdateInvalid() {// when you try to delete an account that does not exist, it
                                                       // should return a bad request and update
        ResponseEntity<AccountDto> response = account
                .postForEntity("/account/deleteAccount?accountRoleId=", null, AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(16)
    public void testGetAccountAfterDeleteAfterUpdate() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount?accountRoleId=",
                AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(17)
    public void testGetAllAccountsAfterDeleteAfterUpdate() {
        ResponseEntity<AccountDto[]> response = account.getForEntity("/account/getAllAccounts", AccountDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto[] accountDtos = response.getBody();
        assertNotNull(accountDtos);
        assertTrue(accountDtos.length > 0);
    }

    @Test
    @Order(18)
    public void testCreateAccountAfterDeleteAfterUpdate() {
        ResponseEntity<AccountDto> response = account.postForEntity(
                "/account/createAccount", null, AccountDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(19)
    public void testCreateAccountInvalidAfterDeleteAfterUpdate() {
        ResponseEntity<AccountDto> response = account.postForEntity(
                "/account/createAccount", null,
                AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(20)
    public void testGetAccountAfterCreateAfterDeleteAfterUpdate() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount",
                AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(21)
    public void testGetAccountAfterCreateInvalidAfterDeleteAfterUpdate() {
        ResponseEntity<AccountDto> response = account
                .getForEntity("/account/getAccount", AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(22)
    public void testGetAllAccountsAfterCreateAfterDeleteAfterUpdate() {
        ResponseEntity<AccountDto[]> response = account.getForEntity("/account/getAllAccounts", AccountDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto[] accountDtos = response.getBody();
        assertNotNull(accountDtos);
        assertTrue(accountDtos.length > 0);
    }

    @Test
    @Order(23)
    public void testCreateCustomer() {
        ResponseEntity<AccountDto> response = account.postForEntity(
                "/account/createAccount", null, AccountDto.class);
        Account account = new Account();
        account.setUsername(USERNAME);
        account.setPassword(PASSWORD);
        accountRepository.save(account);
        Customer customer = new Customer();
        customer.setAccountRoleId(ACCOUNTROLEID);
        customerRepository.save(customer);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(24)
    public void testCreateInstructor() {
        ResponseEntity<AccountDto> response = account.postForEntity(
                "/account/createAccount", null, AccountDto.class);
        Account account = new Account();
        account.setUsername(USERNAME);
        account.setPassword(PASSWORD);
        accountRepository.save(account);
        Instructor instructor = new Instructor();
        instructor.setAccountRoleId(ACCOUNTROLEID);
        instructorRepository.save(instructor);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(25)
    public void testCreateOwner() {
        ResponseEntity<AccountDto> response = account.postForEntity(
                "/account/createAccount", null, AccountDto.class);
        Account account = new Account();
        account.setUsername(USERNAME);
        account.setPassword(PASSWORD);
        accountRepository.save(account);
        Owner owner = new Owner();
        owner.setAccountRoleId(ACCOUNTROLEID);
        ownerRepository.save(owner);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(26)
    public void testCreateCustomerInvalid() {
        ResponseEntity<AccountDto> response = account.postForEntity(
                "/account/createAccount", null,
                AccountDto.class);
        Account account = new Account();
        account.setUsername(INVALID_USERNAME);
        account.setPassword(INVALID_PASSWORD);
        accountRepository.save(account);
        Customer customer = new Customer();
        customer.setAccountRoleId(INVALID_ACCOUNTROLEID);
        customerRepository.save(customer);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(27)
    public void testCreateInstructorInvalid() {
        ResponseEntity<AccountDto> response = account.postForEntity(
                "/account/createAccount", null,
                AccountDto.class);
        Account account = new Account();
        account.setUsername(INVALID_USERNAME);
        account.setPassword(INVALID_PASSWORD);
        accountRepository.save(account);
        Instructor instructor = new Instructor();
        instructor.setAccountRoleId(INVALID_ACCOUNTROLEID);
        instructorRepository.save(instructor);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(28)
    public void testCreateOwnerInvalid() {
        ResponseEntity<AccountDto> response = account.postForEntity(
                "/account/createAccount", null,
                AccountDto.class);
        Account account = new Account();
        account.setUsername(INVALID_USERNAME);
        account.setPassword(INVALID_PASSWORD);
        accountRepository.save(account);
        Owner owner = new Owner();
        owner.setAccountRoleId(INVALID_ACCOUNTROLEID);
        ownerRepository.save(owner);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(29)
    public void testGetAccountCustomer() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount",
                AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(30)
    public void testGetAccountInstructor() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount",
                AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(31)
    public void testGetAccountOwner() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount",
                AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(32)
    public void testGetAccountCustomerInvalid() {
        ResponseEntity<AccountDto> response = account
                .getForEntity("/account/getAccount", AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(33)
    public void testGetAccountInstructorInvalid() {
        ResponseEntity<AccountDto> response = account
                .getForEntity("/account/getAccount", AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(34)
    public void testGetAccountOwnerInvalid() {
        ResponseEntity<AccountDto> response = account
                .getForEntity("/account/getAccount", AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(35)
    public void testGetAllAccountsCustomer() {
        ResponseEntity<AccountDto[]> response = account.getForEntity("/account/getAllAccounts", AccountDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto[] accountDtos = response.getBody();
        assertNotNull(accountDtos);
        assertTrue(accountDtos.length > 0);
    }

    @Test
    @Order(36)
    public void testGetAllAccountsInstructor() {
        ResponseEntity<AccountDto[]> response = account.getForEntity("/account/getAllAccounts", AccountDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto[] accountDtos = response.getBody();
        assertNotNull(accountDtos);
        assertTrue(accountDtos.length > 0);
    }

    // @Test
    // @Order(37)
    // public void testGetAllAccountsOwner() {
    // ResponseEntity<AccountDto[]> response =
    // account.getForEntity("/account/getAllAccounts", AccountDto[].class);
    // assertEquals(HttpStatus.OK, response.getStatusCode());
    // AccountDto[] accountDtos = response.getBody();
    // assertNotNull(accountDtos);
    // assertTrue(accountDtos.length > 0);
    // }

    @Test
    @Order(37)
    public void testGetAllAccountsCustomerInvalid() {
        ResponseEntity<AccountDto[]> response = account.getForEntity("/account/getAllAccounts", AccountDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto[] accountDtos = response.getBody();
        assertNotNull(accountDtos);
        assertTrue(accountDtos.length > 0);
    }

    @Test
    @Order(38)
    public void testGetAllAccountsInstructorInvalid() {
        ResponseEntity<AccountDto[]> response = account.getForEntity("/account/getAllAccounts", AccountDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto[] accountDtos = response.getBody();
        assertNotNull(accountDtos);
        assertTrue(accountDtos.length > 0);
    }

    @Test
    @Order(39)
    public void testGetAllAccountsOwnerInvalid() {
        ResponseEntity<AccountDto[]> response = account.getForEntity("/account/getAllAccounts", AccountDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto[] accountDtos = response.getBody();
        assertNotNull(accountDtos);
        assertTrue(accountDtos.length > 0);
    }

    @Test
    @Order(40)
    public void testUpdateAccountInstructor() {
        ResponseEntity<AccountDto> response = account.postForEntity("/account/updateAccount", null, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(41)
    public void testDeleteAccountInstructor() {
        ResponseEntity<AccountDto> response = account
                .postForEntity("/account/deleteAccount", null, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(42)
    public void testDeleteAccountInstructorInvalid() {
        ResponseEntity<AccountDto> response = account
                .postForEntity("/account/deleteAccount", null, AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(43)
    public void testDeleteAccountOwner() {
        ResponseEntity<AccountDto> response = account
                .postForEntity("/account/deleteAccount", null, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(44)
    public void testDeleteAccountOwnerInvalid() {
        ResponseEntity<AccountDto> response = account
                .postForEntity("/account/deleteAccount", null, AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
