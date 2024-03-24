package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import ca.mcgill.ecse321.sportcenter.dto.ErrorDto;
import ca.mcgill.ecse321.sportcenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportcenter.dto.InstructorDto;
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
    private static final int ACCOUNTID = 1;
    private static final int INVALID_ACCOUNTID = -1;
    private static final int ACCOUNTROLEID = 1;
    private static final String INVALID_USERNAME = "test Username Not Valid";
    private static final String INVALID_PASSWORD = "testPassword Not Valid";
    private static final int INVALID_ACCOUNTROLEID = -1;

    private static final String OLD_USERNAME = "testUsername_old";
    private static final String OLD_PASSWORD = "testPassword_old";

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        accountRepository.deleteAll();
        customerRepository.deleteAll();
        instructorRepository.deleteAll();
        ownerRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateAccount() {
        AccountDto accountDto = new AccountDto(ACCOUNTID, USERNAME, PASSWORD);

        ResponseEntity<AccountDto> response = account.postForEntity("/createAccount/" + USERNAME + "/" + PASSWORD,
                accountDto, AccountDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        AccountDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(USERNAME, responseAccount.getUsername());
        assertEquals(PASSWORD, responseAccount.getPassword());
        assertEquals(ACCOUNTID, responseAccount.getAccountId());
    }

    @Test
    @Order(2)
    public void testGetAccountByAccountId() {
        AccountDto accountDto = new AccountDto(ACCOUNTID, USERNAME, PASSWORD);

        ResponseEntity<AccountDto> response = account.getForEntity("/account/" + ACCOUNTID, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccountDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(USERNAME, responseAccount.getUsername());
        assertEquals(PASSWORD, responseAccount.getPassword());
        assertEquals(ACCOUNTID, responseAccount.getAccountId());
    }

    @Test
    @Order(3)
    public void testGetAccountByAccountIdInvalid() {
        ResponseEntity<ErrorDto> response = account.getForEntity("/account/" + INVALID_ACCOUNTID, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(1, responseAccount.getErrors().size());
        assertEquals("Account not found", responseAccount.getErrors().get(0));
    }

    @Test
    @Order(4)
    public void testGetAccountByUsername() {
        AccountDto accountDto = new AccountDto(ACCOUNTID, USERNAME, PASSWORD);

        ResponseEntity<AccountDto> response = account.getForEntity("/account/" + USERNAME, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccountDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(USERNAME, responseAccount.getUsername());
        assertEquals(PASSWORD, responseAccount.getPassword());
        assertEquals(ACCOUNTID, responseAccount.getAccountId());
    }

    @Test
    @Order(5)
    public void testGetAccountByUsernameInvalid() {
        ResponseEntity<ErrorDto> response = account.getForEntity("/account/" + INVALID_USERNAME, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(1, responseAccount.getErrors().size());
        assertEquals("Account not found", responseAccount.getErrors().get(0));
    }

    @Test
    @Order(6)
    public void testGetAllAccounts() {
        AccountDto accountDto = new AccountDto(ACCOUNTID, USERNAME, PASSWORD);

        ResponseEntity<AccountDto[]> response = account.getForEntity("/accounts", AccountDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccountDto[] responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(1, responseAccount.length);
        assertEquals(USERNAME, responseAccount[0].getUsername());
        assertEquals(PASSWORD, responseAccount[0].getPassword());
        assertEquals(ACCOUNTID, responseAccount[0].getAccountId());
    }

    @Test
    @Order(7)
    public void testCreateAccountInvalid() {
        AccountDto accountDto = new AccountDto(ACCOUNTID, INVALID_USERNAME, INVALID_PASSWORD);

        ResponseEntity<ErrorDto> response = account.postForEntity("/account/create", accountDto, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(1, responseAccount.getErrors().size());
        assertEquals("Incorrect input data", responseAccount.getErrors().get(0));
    }

    @Test
    @Order(8)
    public void testUpdateAccountUsername() {
        AccountDto accountDto = new AccountDto(ACCOUNTID, OLD_USERNAME, OLD_PASSWORD);

        ResponseEntity<AccountDto> response = account.postForEntity("/updateAccountUsername/" + OLD_USERNAME + "/"
                + USERNAME, accountDto, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccountDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(USERNAME, responseAccount.getUsername());
        assertEquals(OLD_PASSWORD, responseAccount.getPassword());
        assertEquals(ACCOUNTID, responseAccount.getAccountId());
    }

    @Test
    @Order(9)
    public void testUpdateAccountUsernameInvalid() {
        AccountDto accountDto = new AccountDto(ACCOUNTID, OLD_USERNAME, OLD_PASSWORD);

        ResponseEntity<ErrorDto> response = account.postForEntity("/updateAccountUsername/" + OLD_USERNAME + "/"
                + INVALID_USERNAME, accountDto, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(1, responseAccount.getErrors().size());
        assertEquals("Incorrect input data", responseAccount.getErrors().get(0));
    }

    @Test
    @Order(10)
    public void testUpdateAccountPassword() {
        AccountDto accountDto = new AccountDto(ACCOUNTID, USERNAME, OLD_PASSWORD);

        ResponseEntity<AccountDto> response = account.postForEntity("/updateAccountPassword/" + USERNAME + "/"
                + PASSWORD, accountDto, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccountDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(USERNAME, responseAccount.getUsername());
        assertEquals(PASSWORD, responseAccount.getPassword());
        assertEquals(ACCOUNTID, responseAccount.getAccountId());
    }

    @Test
    @Order(11)
    public void testUpdateAccountPasswordInvalid() {
        AccountDto accountDto = new AccountDto(ACCOUNTID, USERNAME, OLD_PASSWORD);

        ResponseEntity<ErrorDto> response = account.postForEntity("/updateAccountPassword/" + USERNAME + "/"
                + INVALID_PASSWORD, accountDto, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(1, responseAccount.getErrors().size());
        assertEquals("Incorrect input data", responseAccount.getErrors().get(0));
    }

    @Test
    @Order(12)
    public void testDeleteAccountByAccountId() {
        AccountDto accountDto = new AccountDto(ACCOUNTID, USERNAME, PASSWORD);

        ResponseEntity<AccountDto> response = account.postForEntity("/deleteAccount/" + ACCOUNTID, accountDto,
                AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccountDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(USERNAME, responseAccount.getUsername());
        assertEquals(PASSWORD, responseAccount.getPassword());
        assertEquals(ACCOUNTID, responseAccount.getAccountId());
    }

    @Test
    @Order(13)
    public void testDeleteAccountByAccountIdInvalid() {
        AccountDto accountDto = new AccountDto(INVALID_ACCOUNTID, USERNAME, PASSWORD);

        ResponseEntity<ErrorDto> response = account.postForEntity("/deleteAccount/" + INVALID_ACCOUNTID, accountDto,
                ErrorDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(1, responseAccount.getErrors().size());
        assertEquals("Account not found", responseAccount.getErrors().get(0));
    }

    @Test
    @Order(14)
    public void testDeleteAllAccounts() {
        AccountDto accountDto = new AccountDto(ACCOUNTID, USERNAME, PASSWORD);

        ResponseEntity<AccountDto[]> response = account.getForEntity("/accounts/deleteAll/", AccountDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccountDto[] responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(0, responseAccount.length);
    }

    @Test
    @Order(15)
    public void testCreateCustomerAccount() {

}