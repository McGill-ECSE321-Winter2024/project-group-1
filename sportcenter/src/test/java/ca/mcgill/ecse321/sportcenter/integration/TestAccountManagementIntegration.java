package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Owner;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@SuppressWarnings("null")
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

    // instructor attributes
    private static final String INSTRUCTOR_DESCRIPRION = "testDescription";
    private static final String INSTRUCTOR_PICTURE = "testPicture";
    private static final InstructorStatus INSTRUCTOR_STATUS = InstructorStatus.Pending;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
        instructorRepository.deleteAll();
        ownerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testCreateAccount() {
        AccountDto accountDto = new AccountDto(ACCOUNTID, USERNAME, PASSWORD);

        ResponseEntity<AccountDto> response = account.postForEntity("/createAccount",
                accountDto, AccountDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        AccountDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(USERNAME, responseAccount.getUsername());
        assertEquals(PASSWORD, responseAccount.getPassword());
        // assertEquals(ACCOUNTID, responseAccount.getAccountId());
        assertNotNull(accountRepository.findAccountByUsername(USERNAME));
    }

    @Test
    public void testGetAccountById() {
        Account accountDto = new Account(USERNAME, PASSWORD);
        // accountDto.setAccountId(ACCOUNTID);
        accountRepository.save(accountDto);

        String url = "/account/" + ACCOUNTID;

        ResponseEntity<AccountDto> response = account.getForEntity(url, AccountDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccountDto responseAccount = response.getBody();

        assertNotNull(responseAccount);
        assertEquals(USERNAME, responseAccount.getUsername());
        assertEquals(PASSWORD, responseAccount.getPassword());
        assertEquals(ACCOUNTID, responseAccount.getAccountId());
    }

    @Test
    public void testGetAccountByUsername() {
        Account accountDto = new Account(USERNAME, PASSWORD);
        // accountDto.setAccountId(ACCOUNTID);
        accountRepository.save(accountDto);

        ResponseEntity<AccountDto> response = account.getForEntity("/account/" +
                USERNAME, AccountDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(USERNAME, responseAccount.getUsername());
        assertEquals(PASSWORD, responseAccount.getPassword());
        assertEquals(ACCOUNTID, responseAccount.getAccountId());
    }

    @Test
    public void testGetAccountByAccountIdInvalid() {
        Account accountDto = new Account(USERNAME, PASSWORD);
        accountRepository.save(accountDto);

        ResponseEntity<ErrorDto> response = account.getForEntity("/account/" +
                INVALID_ACCOUNTID, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(1, responseAccount.getErrors().size());
        assertEquals("Account not found", responseAccount.getErrors().get(0));
    }

    @Test
    public void testGetAccountByUsernameInvalid() {
        Account accountDto = new Account(USERNAME, PASSWORD);
        accountRepository.save(accountDto);

        ResponseEntity<ErrorDto> response = account.getForEntity("/account/" +
                INVALID_USERNAME, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(1, responseAccount.getErrors().size());
        assertEquals("Account not found", responseAccount.getErrors().get(0));
    }

    @Test
    public void testGetAllAccounts() {
        Account accountDto = new Account(USERNAME, PASSWORD);
        Account accountDto2 = new Account("testUsername2", "testPassword2");
        accountRepository.save(accountDto);
        accountRepository.save(accountDto2);

        ResponseEntity<AccountDto[]> response = account.getForEntity("/accounts",
                AccountDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        AccountDto[] responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(2, responseAccount.length);
        assertEquals(USERNAME, responseAccount[0].getUsername());
        assertEquals(PASSWORD, responseAccount[0].getPassword());
        assertEquals(ACCOUNTID, responseAccount[0].getAccountId());
        assertEquals("testUsername2", responseAccount[1].getUsername());
        assertEquals("testPassword2", responseAccount[1].getPassword());
        assertEquals(2, responseAccount[1].getAccountId());
    }

    @Test
    public void testCreateAccountInvalid() {
        Account accountDto = new Account(INVALID_USERNAME, PASSWORD);
        accountRepository.save(accountDto);

        ResponseEntity<ErrorDto> response = account.postForEntity("/createAccount/" +
                INVALID_USERNAME + "/" + PASSWORD,
                accountDto, ErrorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDto responseAccount = response.getBody();
        assertNotNull(responseAccount);
        assertEquals(1, responseAccount.getErrors().size());
        assertEquals("Account already exists", responseAccount.getErrors().get(0));
    }

    @Test
    @Order(8)
    public void testUpdateAccountUsername() {
        Account account1 = new Account(OLD_USERNAME, OLD_PASSWORD);
        account1.setUsername(USERNAME);
        AccountDto accountDto = new AccountDto(ACCOUNTID, USERNAME, OLD_PASSWORD);

        ResponseEntity<AccountDto> response = account
                .postForEntity("/updateAccountUsername/" + OLD_USERNAME + "/"
                        + USERNAME, accountDto, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(USERNAME, response.getBody().getUsername());
    }

    @Test
    public void testUpdateAccountUsernameInvalid() {
        Account account1 = new Account(OLD_USERNAME, OLD_PASSWORD);
        account1.setUsername(INVALID_USERNAME);
        AccountDto accountDto = new AccountDto(ACCOUNTID, INVALID_USERNAME,
                OLD_PASSWORD);

        ResponseEntity<ErrorDto> response = account.postForEntity("/updateAccountUsername/" + OLD_USERNAME + "/"
                + INVALID_USERNAME, accountDto, ErrorDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());// could be

        assertNotNull(response);
        assertEquals(1, response.getBody().getErrors().size());
    }

    @Test
    public void testUpdateAccountPassword() {
        Account account1 = new Account(USERNAME, OLD_PASSWORD);
        account1.setPassword(PASSWORD);
        AccountDto accountDto = new AccountDto(ACCOUNTID, USERNAME, PASSWORD);

        ResponseEntity<AccountDto> response = account.postForEntity("/updateAccountPassword/" + USERNAME + "/"
                + PASSWORD, accountDto, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    public void testUpdateAccountPasswordInvalid() {
        Account account1 = new Account(USERNAME, OLD_PASSWORD);
        account1.setPassword(INVALID_PASSWORD);
        AccountDto accountDto = new AccountDto(ACCOUNTID, USERNAME,
                INVALID_PASSWORD);

        ResponseEntity<ErrorDto> response = account.postForEntity("/updateAccountPassword/" + PASSWORD + "/"
                + INVALID_PASSWORD, accountDto, ErrorDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());// could be

        assertNotNull(response);
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals("Password not valid", response.getBody().getErrors().get(0));
    }

    @Test
    public void testDeleteAccountByAccountId() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);

        ResponseEntity<AccountDto> response = account.getForEntity("/deleteAccount/"
                + ACCOUNTID, AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteAccountByAccountIdInvalid() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);

        ResponseEntity<ErrorDto> response = account.getForEntity("/deleteAccount/" +
                INVALID_ACCOUNTID, ErrorDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals("Account not found", response.getBody().getErrors().get(0));
    }

    @Test
    public void testCreateCustomerAccount() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Customer customer = new Customer(account1);
        customerRepository.save(customer);

        ResponseEntity<CustomerDto[]> response = account.postForEntity(
                "/createCustomerAccount/" + USERNAME + "/" + PASSWORD,
                customer, CustomerDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Order(17)
    public void testDeleteCustomerByAccountRoleId() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Customer customer = new Customer(account1);
        customer.setAccountRoleId(ACCOUNTROLEID);
        customerRepository.save(customer);

        ResponseEntity<CustomerDto> response = account.getForEntity("/deleteCustomerAccount/" + ACCOUNTROLEID,
                CustomerDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteCustomerByAccountRoleIdInvalid() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Customer customer = new Customer(account1);
        customer.setAccountRoleId(INVALID_ACCOUNTROLEID);
        customerRepository.save(customer);

        ResponseEntity<ErrorDto> response = account.getForEntity(
                "/deleteCustomerAccount/" + INVALID_ACCOUNTROLEID,
                ErrorDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals("Customer not found", response.getBody().getErrors().get(0));
    }

    @Test
    public void testCreateInstructorAccount() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
        instructorRepository.save(instructor);

        ResponseEntity<InstructorDto[]> response = account.postForEntity(
                "/createInstructor/" + USERNAME + "/" + PASSWORD,
                instructor, InstructorDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(USERNAME, response.getBody()[0].getAccount().getUsername());
        assertEquals(PASSWORD, response.getBody()[0].getAccount().getPassword());
        assertEquals(INSTRUCTOR_DESCRIPRION, response.getBody()[0].getDescription());
        assertEquals(INSTRUCTOR_PICTURE, response.getBody()[0].getProfilePicURL());
        assertEquals(INSTRUCTOR_STATUS, response.getBody()[0].getStatus());
    }

    @Test
    public void createInstructorAccountInvalidDescription() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS, "",
                INSTRUCTOR_PICTURE, account1);
        instructorRepository.save(instructor);

        ResponseEntity<ErrorDto> response = account.postForEntity(
                "/createInstructor/" + USERNAME + "/" + PASSWORD,
                instructor, ErrorDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Description not valid", response.getBody().getErrors().get(0));
    }

    @Test
    public void createInstructorAccountInvalidPicture() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                INSTRUCTOR_DESCRIPRION, "", account1);
        instructorRepository.save(instructor);

        ResponseEntity<ErrorDto> response = account.postForEntity(
                "/createInstructor/" + USERNAME + "/" + PASSWORD,
                instructor, ErrorDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Picture not valid", response.getBody().getErrors().get(0));
    }

    @Test
    public void getInstructorByAccountRoleId() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
        instructor.setAccountRoleId(ACCOUNTROLEID);
        instructorRepository.save(instructor);

        ResponseEntity<InstructorDto> response = account.getForEntity("/getinstructor/" + ACCOUNTROLEID,
                InstructorDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(USERNAME, response.getBody().getAccount().getUsername());
        assertEquals(PASSWORD, response.getBody().getAccount().getPassword());
        assertEquals(INSTRUCTOR_DESCRIPRION, response.getBody().getDescription());
        assertEquals(INSTRUCTOR_PICTURE, response.getBody().getProfilePicURL());
        assertEquals(INSTRUCTOR_STATUS, response.getBody().getStatus());
    }

    @Test
    public void getInstructorByAccountRoleIdInvalid() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
        instructor.setAccountRoleId(INVALID_ACCOUNTROLEID);
        instructorRepository.save(instructor);

        ResponseEntity<ErrorDto> response = account.getForEntity("/getinstructor/" +
                INVALID_ACCOUNTROLEID,
                ErrorDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals("Instructor not found", response.getBody().getErrors().get(0));
    }

    @Test
    public void testGetAllInstructorAccount() {
        Account account1 = new Account(USERNAME, PASSWORD);
        Account account2 = new Account("testUsername2", "testPassword2");
        accountRepository.save(account1);
        accountRepository.save(account2);
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
        Instructor instructor2 = new Instructor(INSTRUCTOR_STATUS,
                "testDescription2", "testPicture2", account2);
        instructorRepository.save(instructor);
        instructorRepository.save(instructor2);

        ResponseEntity<InstructorDto[]> response = account.getForEntity("/instructors", InstructorDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        InstructorDto[] responseInstructor = response.getBody();
        assertNotNull(responseInstructor);
        assertEquals(2, responseInstructor.length);
    }

    @Test
    public void testUpdateInstructorAccount() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
        instructor.setDescription("newDescription");
        instructor.setProfilePicURL("newPicture");

        ResponseEntity<InstructorDto> response = account
                .postForEntity("/updateInstructorAccount/" + USERNAME + "/"
                        + PASSWORD, instructor, InstructorDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
        assertEquals("newDescription", response.getBody().getDescription());
        assertEquals("newPicture", response.getBody().getProfilePicURL());
    }

    @Test
    public void testUpdateInstructorAccountInvalid() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
        instructor.setDescription("");
        instructor.setProfilePicURL("");

        ResponseEntity<ErrorDto> response = account.postForEntity("/updateInstructorAccount/" + USERNAME + "/"
                + PASSWORD, instructor, ErrorDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response);
        assertEquals(2, response.getBody().getErrors().size());
        assertEquals("Description not valid", response.getBody().getErrors().get(0));
        assertEquals("Picture not valid", response.getBody().getErrors().get(1));
    }

    @Test
    public void testDeleteInstructorByAccountRoleId() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
        instructor.setAccountRoleId(ACCOUNTROLEID);
        instructorRepository.save(instructor);

        ResponseEntity<InstructorDto> response = account.getForEntity(
                "/deleteInstructorAccount/" + ACCOUNTROLEID,
                InstructorDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(instructorRepository.findInstructorByAccountRoleId(ACCOUNTROLEID));
    }

    @Test
    public void testDeleteInstructorByAccountRoleIdInvalid() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
        instructor.setAccountRoleId(INVALID_ACCOUNTROLEID);
        instructorRepository.save(instructor);

        ResponseEntity<ErrorDto> response = account.getForEntity(
                "/deleteInstructorAccount/" + INVALID_ACCOUNTROLEID,
                ErrorDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals("Instructor not found", response.getBody().getErrors().get(0));
    }

    @Test

    public void testDeleteInstructorByUsername() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
        instructor.setAccountRoleId(ACCOUNTROLEID);
        instructorRepository.save(instructor);

        ResponseEntity<InstructorDto> response = account.getForEntity("/deleteInstructorAccount/" + USERNAME,
                InstructorDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(instructorRepository.findInstructorByAccountRoleId(ACCOUNTROLEID));
    }

    @Test
    public void testDeleteInstructorByUsernameInvalid() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
        instructor.setAccountRoleId(INVALID_ACCOUNTROLEID);
        instructorRepository.save(instructor);

        ResponseEntity<ErrorDto> response = account.getForEntity("/deleteInstructorAccount/" + INVALID_USERNAME,
                ErrorDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals("Instructor not found", response.getBody().getErrors().get(0));
    }

    @Test
    public void testCreateOwnerAccount() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Owner owner = new Owner(account1);
        ownerRepository.save(owner);

        ResponseEntity<AccountDto[]> response = account.postForEntity(
                "/createOwner/" + USERNAME + "/" + PASSWORD,
                owner, AccountDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetOwnerAccountByAccountRoleId() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Owner owner = new Owner(account1);
        owner.setAccountRoleId(ACCOUNTROLEID);
        ownerRepository.save(owner);

        ResponseEntity<AccountDto> response = account.getForEntity("/getowner/" +
                ACCOUNTROLEID,
                AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(USERNAME, response.getBody().getUsername());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    public void testGetOwnerAccountByAccountRoleIdInvalid() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);
        Owner owner = new Owner(account1);
        owner.setAccountRoleId(INVALID_ACCOUNTROLEID);
        ownerRepository.save(owner);

        ResponseEntity<ErrorDto> response = account.getForEntity("/getowner/" +
                INVALID_ACCOUNTROLEID,
                ErrorDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals("Owner not found", response.getBody().getErrors().get(0));
    }

    @Test
    public void testLogin() {
        Account account1 = new Account(USERNAME, PASSWORD);
        accountRepository.save(account1);

        ResponseEntity<AccountDto> response = account.getForEntity("/login/" +
                USERNAME + "/" + PASSWORD,
                AccountDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(USERNAME, response.getBody().getUsername());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }
}
