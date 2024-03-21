package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.checkerframework.checker.units.qual.A;
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

import ca.mcgill.ecse321.sportcenter.dto.AccountDto;
import ca.mcgill.ecse321.sportcenter.model.Account;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TestAccountIntegration {
    @Autowired
    private TestRestTemplate account;

    @Autowired
    private AccountRepository accountRepository;

    private static final String USERNAME = "testUsername";
    private static final String PASSWORD = "testPassword";
    private static final int ACCOUNTROLEID = 1;
    private static final int ACCOUNTID = 2;
    private static final String INVALID_USERNAME = "testUsername";
    private static final String INVALID_PASSWORD = "testPassword";
    private static final int INVALID_ACCOUNTROLEID = 5;
    private static final int INVALID_ACCOUNTID = 6;

    @Test
    @Order(1)
    public void testCreateAccount() {
        ResponseEntity<AccountDto> response = account.postForEntity("/account/createAccount?username=" + USERNAME + "&password=" + PASSWORD, null, AccountDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(2)
    public void testCreateAccountInvalid() {
        ResponseEntity<AccountDto> response = account.postForEntity("/account/createAccount?username=" + INVALID_USERNAME + "&password=" + INVALID_PASSWORD, null, AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void testGetAccount() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount?accountRoleId=" + ACCOUNTROLEID, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(4)
    public void testGetAccountInvalid() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount?accountRoleId=" + INVALID_ACCOUNTROLEID, AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testDeleteAccount() {
        ResponseEntity<AccountDto> response = account.postForEntity("/account/deleteAccount?accountRoleId=" + ACCOUNTROLEID, null, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeleteAccountInvalid() {
        ResponseEntity<AccountDto> response = account.postForEntity("/account/deleteAccount?accountRoleId=" + INVALID_ACCOUNTROLEID, null, AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(7)
    public void testGetAccountAfterDelete() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount?accountRoleId=" + ACCOUNTROLEID, AccountDto.class);
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
        ResponseEntity<AccountDto> response = account.postForEntity("/account/updateAccount?accountRoleId=" + ACCOUNTROLEID + "&username=" + USERNAME + "&password=" + PASSWORD, null, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(11)
    public void testUpdateAccountInvalid() {
        ResponseEntity<AccountDto> response = account.postForEntity("/account/updateAccount?accountRoleId=" + INVALID_ACCOUNTROLEID + "&username=" + INVALID_USERNAME + "&password=" + INVALID_PASSWORD, null, AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(12)
    public void testGetAccountAfterUpdate() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount?accountRoleId=" + ACCOUNTROLEID, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(13)
    public void testGetAccountAfterUpdateInvalid() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount?accountRoleId=" + INVALID_ACCOUNTROLEID, AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(14)
    public void testDeleteAccountAfterUpdate() {
        ResponseEntity<AccountDto> response = account.postForEntity("/account/deleteAccount?accountRoleId=" + ACCOUNTROLEID, null, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(15)
    public void testDeleteAccountAfterUpdateInvalid() {//when you try to delete an account that does not exist, it should return a bad request and update
        ResponseEntity<AccountDto> response = account.postForEntity("/account/deleteAccount?accountRoleId=" + INVALID_ACCOUNTROLEID, null, AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(16)
    public void testGetAccountAfterDeleteAfterUpdate() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount?accountRoleId=" + ACCOUNTROLEID, AccountDto.class);
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
        ResponseEntity<AccountDto> response = account.postForEntity("/account/createAccount?username=" + USERNAME + "&password=" + PASSWORD, null, AccountDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(19)
    public void testCreateAccountInvalidAfterDeleteAfterUpdate() {
        ResponseEntity<AccountDto> response = account.postForEntity("/account/createAccount?username=" + INVALID_USERNAME + "&password=" + INVALID_PASSWORD, null, AccountDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(20)
    public void testGetAccountAfterCreateAfterDeleteAfterUpdate() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount?accountRoleId=" + ACCOUNTROLEID, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto accountDto = response.getBody();
        assertNotNull(accountDto);
        assertEquals(USERNAME, accountDto.getUsername());
        assertEquals(PASSWORD, accountDto.getPassword());
    }

    @Test
    @Order(21)
    public void testGetAccountAfterCreateInvalidAfterDeleteAfterUpdate() {
        ResponseEntity<AccountDto> response = account.getForEntity("/account/getAccount?accountRoleId=" + INVALID_ACCOUNTROLEID, AccountDto.class);
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
}
