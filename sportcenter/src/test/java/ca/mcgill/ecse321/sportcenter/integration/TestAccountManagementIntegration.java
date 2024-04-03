package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
import ca.mcgill.ecse321.sportcenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportcenter.dto.ErrorDto;
import ca.mcgill.ecse321.sportcenter.dto.InstructorDto;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Owner;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TestAccountManagementIntegration {

        @Autowired
        private TestRestTemplate client;

        @Autowired
        private AccountRepository accountRepository;

        @Autowired
        private CustomerRepository customerRepository;

        @Autowired
        private InstructorRepository instructorRepository;

        @Autowired
        private OwnerRepository ownerRepository;

        private int validAccountId;
        private int validCustomerAccountRoleId;

        private static final String USERNAME = "testUsername";
        private static final String PASSWORD = "testPassword";
        private static final int ACCOUNTID = 1;
        private static final int INVALID_ACCOUNTID = -1;
        private static final int ACCOUNTROLEID = 1;
        private static final String INVALID_USERNAME = "testUsername NotValid";
        private static final String INVALID_PASSWORD = "testPassword NotValid";
        private static final int INVALID_ACCOUNTROLEID = -1;

        private static final String OLD_USERNAME = "testUsername_old";
        private static final String OLD_PASSWORD = "testPassword_old";

        // instructor attributes
        private static final String INSTRUCTOR_DESCRIPRION = "testDescription";
        private static final String INSTRUCTOR_PICTURE = "testPicture";
        private static final InstructorStatus INSTRUCTOR_STATUS = InstructorStatus.Pending;

        @BeforeAll
        @AfterAll
        public void clearDatabase() {
                customerRepository.deleteAll();
                instructorRepository.deleteAll();
                ownerRepository.deleteAll();
                accountRepository.deleteAll();
        }

        @Test
        @Order(1)
        public void testCreateAccount() {
                // Set up
                String url = "/createAccount/" + USERNAME + "/" + PASSWORD;

                // Act
                ResponseEntity<AccountDto> response = client.postForEntity(url, null, AccountDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());

                AccountDto responseAccount = response.getBody();
                assertNotNull(responseAccount);
                assertEquals(USERNAME, responseAccount.getUsername());
                assertEquals(PASSWORD, responseAccount.getPassword());
                this.validAccountId = responseAccount.getAccountId();
                assertTrue(validAccountId > 0, "Account ID should be greater than 0");
                assertNotNull(accountRepository.findAccountByUsername(USERNAME));
        }

        @Test
        @Order(2)
        public void testGetAccountById() {
                // Set up
                String url = "/getAccountById/" + this.validAccountId;

                // Act
                ResponseEntity<AccountDto> response = client.getForEntity(url, AccountDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());

                AccountDto responseAccount = response.getBody();

                assertNotNull(responseAccount);
                assertEquals(USERNAME, responseAccount.getUsername());
                assertEquals(PASSWORD, responseAccount.getPassword());
                assertTrue(responseAccount.getAccountId() > 0, "Account ID should be greater than 0");
        }

        @Test
        @Order(3)
        public void testGetAccountByUsername() {

                // Set up
                String url = "/getAccountByUsername/" + USERNAME;

                // Act
                ResponseEntity<AccountDto> response = client.getForEntity(url, AccountDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                AccountDto responseAccount = response.getBody();
                assertNotNull(responseAccount);
                assertEquals(USERNAME, responseAccount.getUsername());
                assertEquals(PASSWORD, responseAccount.getPassword());
                assertTrue(responseAccount.getAccountId() > 0);
        }

        @Test
        @Order(4)
        public void testGetAccountByAccountIdInvalid() {
                // Set up
                String url = "/getAccountById/" + INVALID_ACCOUNTID;

                // Act
                ResponseEntity<ErrorDto> response = client.getForEntity(url, ErrorDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

                // ErrorDto responseAccount = response.getBody();
                // assertNotNull(responseAccount);
                // assertEquals(1, responseAccount.getErrors().size());
                // assertEquals("Account ID must be greater than 0",
                // responseAccount.getErrors().get(0));
        }

        @Test
        @Order(5)
        public void testGetAccountByUsernameInvalid() {
                // Set up
                String url = "/account/" + INVALID_USERNAME;

                // Act
                ResponseEntity<ErrorDto> response = client.getForEntity(url, ErrorDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                // ErrorDto responseAccount = response.getBody();
                // assertNotNull(responseAccount);
                // assertEquals(1, responseAccount.getErrors().size());
                // assertEquals("Account not found", responseAccount.getErrors().get(0));
        }

        @Test
        @Order(6)
        public void testGetAllAccounts() {

                // Set up
                // Add a second account
                String username1 = "testUsername2";
                String password1 = "testPassword2";
                String url = "/createAccount/" + username1 + "/" + password1;
                ResponseEntity<AccountDto> response1 = client.postForEntity(url, null, AccountDto.class);

                // Act
                ResponseEntity<AccountDto[]> response = client.getForEntity("/accounts",
                                AccountDto[].class);
                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());

                AccountDto[] responseAccount = response.getBody();
                assertNotNull(responseAccount);
                assertEquals(2, responseAccount.length);
                assertEquals(USERNAME, responseAccount[0].getUsername());
                assertEquals(PASSWORD, responseAccount[0].getPassword());
                assertEquals(this.validAccountId, responseAccount[0].getAccountId());
                assertEquals("testUsername2", responseAccount[1].getUsername());
                assertEquals("testPassword2", responseAccount[1].getPassword());
                assertEquals(response1.getBody().getAccountId(), responseAccount[1].getAccountId());
        }

        @Test
        @Order(7)
        public void testCreateAccountInvalid() {
                // Set up
                String url = "/createAccount/" + INVALID_USERNAME + "/" + PASSWORD;

                // Act
                ResponseEntity<ErrorDto> response = client.postForEntity(url, null, ErrorDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                // ErrorDto responseAccount = response.getBody();
                // assertNotNull(responseAccount);
                // assertEquals(1, responseAccount.getErrors().size());
                // assertEquals("Account already exists", responseAccount.getErrors().get(0));
        }

        @Test
        @Order(8)
        public void testUpdateAccountUsername() {
                // Set up
                String url_setup = "/createAccount/" + OLD_USERNAME + "/" + OLD_PASSWORD;
                ResponseEntity<AccountDto> response1 = client.postForEntity(url_setup, null, AccountDto.class);

                String url_test = "/updateAccountUsername/" + OLD_USERNAME + "/" + USERNAME;

                // Act
                ResponseEntity<AccountDto> response = client.postForEntity(url_test, null, AccountDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(USERNAME, response.getBody().getUsername());
        }

        @Test
        @Order(9)
        public void testUpdateAccountUsernameInvalid() {
                // Set up
                String url = "/updateAccountUsername/" + OLD_USERNAME + "/" + USERNAME;

                // Act
                ResponseEntity<ErrorDto> response = client.postForEntity(url, null, ErrorDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                // assertNotNull(response.getBody());
                // assertEquals(1, response.getBody().getErrors().size());
        }

        @Test
        @Order(10)
        public void testUpdateAccountPassword() {
                // Set up
                String url_setup = "/updateAccountPassword/" + USERNAME + "/" + PASSWORD + "/" + OLD_PASSWORD;

                // Act
                ResponseEntity<AccountDto> response = client.postForEntity(url_setup, null, AccountDto.class);

                // Assert
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response);
                assertEquals(PASSWORD, response.getBody().getPassword());
        }

        @Test
        @Order(11)
        public void testUpdateAccountPasswordInvalid() {
                // Set up
                String url = "/updateAccountPassword/" + OLD_USERNAME + "/" + OLD_PASSWORD + "/" + PASSWORD;

                // Act
                ResponseEntity<ErrorDto> response = client.postForEntity(url, null, ErrorDto.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                // assertEquals(1, response.getBody().getErrors().size());
                // assertEquals("Password not valid", response.getBody().getErrors().get(0));
        }

        @Test
        @Order(12)
        public void testDeleteAccountByAccountId() {
                // Set up
                String deleteTestUsername = "deleteTest";
                String deleteTestPassword = "deleteTest";
                int deleteTestAccountId;

                String url_setup = "/createAccount/" + deleteTestUsername + "/" + deleteTestPassword;
                ResponseEntity<AccountDto> response1 = client.postForEntity(url_setup, null, AccountDto.class);
                deleteTestAccountId = response1.getBody().getAccountId();

                String url_test = "/deleteAccount/" + deleteTestAccountId;

                // Act
                ResponseEntity<String> response = client.exchange(url_test, org.springframework.http.HttpMethod.DELETE,
                                null, String.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNull(accountRepository.findAccountByAccountId(deleteTestAccountId));
        }

        @Test
        @Order(13)
        public void testDeleteAccountByAccountIdInvalid() {
                // Set up
                String deleteTestUsername = "deleteTest";
                String deleteTestPassword = "deleteTest";
                int deleteTestAccountId;

                String url_setup = "/createAccount/" + deleteTestUsername + "/" + deleteTestPassword;
                ResponseEntity<AccountDto> response1 = client.postForEntity(url_setup, null, AccountDto.class);
                deleteTestAccountId = response1.getBody().getAccountId();

                String url_test = "/deleteAccount/" + -1;

                // Act
                ResponseEntity<String> response = client.exchange(url_test, org.springframework.http.HttpMethod.DELETE,
                                null, String.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertNotNull(accountRepository.findAccountByAccountId(deleteTestAccountId));
        }

        @Test
        @Order(14)
        public void testCreateCustomerAccount() {
                // Set up
                String url = "/createCustomer/" + USERNAME;

                // Act
                ResponseEntity<CustomerDto> response = client.postForEntity(url, null, CustomerDto.class);
                this.validCustomerAccountRoleId = response.getBody().getAccountRoleId();

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(USERNAME, response.getBody().getAccount().getUsername());
                assertEquals(PASSWORD, response.getBody().getAccount().getPassword());
        }

        @Test
        @Order(15)
        public void testDeleteCustomerByAccountRoleId() {
                // Set up
                String url = "/deleteCustomer/" + this.validCustomerAccountRoleId;

                // Act
                ResponseEntity<String> response = client.exchange(url, org.springframework.http.HttpMethod.DELETE,
                                null, String.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNull(customerRepository.findCustomerByAccountRoleId(this.validCustomerAccountRoleId));
        }

        @Test
        @Order(16)
        public void testDeleteCustomerByAccountRoleIdInvalid() {
                // Set up
                String url = "/deleteCustomer/" + INVALID_ACCOUNTROLEID;

                // Act
                ResponseEntity<String> response = client.exchange(url, org.springframework.http.HttpMethod.DELETE,
                                null, String.class);

                // Assert
                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertNotNull(response.getBody());
                // check message
        }

        @Test
        @Order(17)
        public void testCreateInstructorAccount() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
                instructorRepository.save(instructor);

                ResponseEntity<InstructorDto[]> response = client.postForEntity(
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
        @Order(18)
        public void createInstructorAccountInvalidDescription() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Instructor instructor = new Instructor(INSTRUCTOR_STATUS, "",
                                INSTRUCTOR_PICTURE, account1);
                instructorRepository.save(instructor);

                ResponseEntity<ErrorDto> response = client.postForEntity(
                                "/createInstructor/" + USERNAME + "/" + PASSWORD,
                                instructor, ErrorDto.class);
                assertNotNull(response);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                assertEquals("Description not valid", response.getBody().getErrors().get(0));
        }

        @Test
        @Order(19)
        public void createInstructorAccountInvalidPicture() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                                INSTRUCTOR_DESCRIPRION, "", account1);
                instructorRepository.save(instructor);

                ResponseEntity<ErrorDto> response = client.postForEntity(
                                "/createInstructor/" + USERNAME + "/" + PASSWORD,
                                instructor, ErrorDto.class);
                assertNotNull(response);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                assertEquals("Picture not valid", response.getBody().getErrors().get(0));
        }

        @Test
        @Order(20)
        public void getInstructorByAccountRoleId() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
                instructor.setAccountRoleId(ACCOUNTROLEID);
                instructorRepository.save(instructor);

                ResponseEntity<InstructorDto> response = client.getForEntity("/getinstructor/" + ACCOUNTROLEID,
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
        @Order(21)
        public void getInstructorByAccountRoleIdInvalid() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
                instructor.setAccountRoleId(INVALID_ACCOUNTROLEID);
                instructorRepository.save(instructor);

                ResponseEntity<ErrorDto> response = client.getForEntity("/getinstructor/" +
                                INVALID_ACCOUNTROLEID,
                                ErrorDto.class);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(1, response.getBody().getErrors().size());
                assertEquals("Instructor not found", response.getBody().getErrors().get(0));
        }

        @Test
        @Order(22)
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

                ResponseEntity<InstructorDto[]> response = client.getForEntity("/instructors", InstructorDto[].class);
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());

                InstructorDto[] responseInstructor = response.getBody();
                assertNotNull(responseInstructor);
                assertEquals(2, responseInstructor.length);
        }

        @Test
        @Order(23)
        public void testUpdateInstructorAccount() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
                instructor.setDescription("newDescription");
                instructor.setProfilePicURL("newPicture");

                ResponseEntity<InstructorDto> response = client
                                .postForEntity("/updateInstructorAccount/" + USERNAME + "/"
                                                + PASSWORD, instructor, InstructorDto.class);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response);
                assertEquals("newDescription", response.getBody().getDescription());
                assertEquals("newPicture", response.getBody().getProfilePicURL());
        }

        @Test
        @Order(24)
        public void testUpdateInstructorAccountInvalid() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
                instructor.setDescription("");
                instructor.setProfilePicURL("");

                ResponseEntity<ErrorDto> response = client.postForEntity("/updateInstructorAccount/" + USERNAME + "/"
                                + PASSWORD, instructor, ErrorDto.class);

                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                assertNotNull(response);
                assertEquals(2, response.getBody().getErrors().size());
                assertEquals("Description not valid", response.getBody().getErrors().get(0));
                assertEquals("Picture not valid", response.getBody().getErrors().get(1));
        }

        @Test
        @Order(25)
        public void testDeleteInstructorByAccountRoleId() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
                instructor.setAccountRoleId(ACCOUNTROLEID);
                instructorRepository.save(instructor);

                ResponseEntity<InstructorDto> response = client.getForEntity(
                                "/deleteInstructorAccount/" + ACCOUNTROLEID,
                                InstructorDto.class);
                assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
                assertNotNull(response.getBody());
                assertNull(instructorRepository.findInstructorByAccountRoleId(ACCOUNTROLEID));
        }

        @Test
        @Order(26)
        public void testDeleteInstructorByAccountRoleIdInvalid() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
                instructor.setAccountRoleId(INVALID_ACCOUNTROLEID);
                instructorRepository.save(instructor);

                ResponseEntity<ErrorDto> response = client.getForEntity(
                                "/deleteInstructorAccount/" + INVALID_ACCOUNTROLEID,
                                ErrorDto.class);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(1, response.getBody().getErrors().size());
                assertEquals("Instructor not found", response.getBody().getErrors().get(0));
        }

        @Test
        @Order(27)
        public void testDeleteInstructorByUsername() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
                instructor.setAccountRoleId(ACCOUNTROLEID);
                instructorRepository.save(instructor);

                ResponseEntity<InstructorDto> response = client.getForEntity("/deleteInstructorAccount/" + USERNAME,
                                InstructorDto.class);
                assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
                assertNotNull(response.getBody());
                assertNull(instructorRepository.findInstructorByAccountRoleId(ACCOUNTROLEID));
        }

        @Test
        @Order(28)
        public void testDeleteInstructorByUsernameInvalid() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Instructor instructor = new Instructor(INSTRUCTOR_STATUS,
                                INSTRUCTOR_DESCRIPRION, INSTRUCTOR_PICTURE, account1);
                instructor.setAccountRoleId(INVALID_ACCOUNTROLEID);
                instructorRepository.save(instructor);

                ResponseEntity<ErrorDto> response = client.getForEntity("/deleteInstructorAccount/" + INVALID_USERNAME,
                                ErrorDto.class);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(1, response.getBody().getErrors().size());
                assertEquals("Instructor not found", response.getBody().getErrors().get(0));
        }

        @Test
        @Order(29)
        public void testCreateOwnerAccount() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Owner owner = new Owner(account1);
                ownerRepository.save(owner);

                ResponseEntity<AccountDto[]> response = client.postForEntity(
                                "/createOwner/" + USERNAME + "/" + PASSWORD,
                                owner, AccountDto[].class);
                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }

        @Test
        @Order(30)
        public void testGetOwnerAccountByAccountRoleId() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Owner owner = new Owner(account1);
                owner.setAccountRoleId(ACCOUNTROLEID);
                ownerRepository.save(owner);

                ResponseEntity<AccountDto> response = client.getForEntity("/getowner/" +
                                ACCOUNTROLEID,
                                AccountDto.class);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(USERNAME, response.getBody().getUsername());
                assertEquals(PASSWORD, response.getBody().getPassword());
        }

        @Test
        @Order(31)
        public void testGetOwnerAccountByAccountRoleIdInvalid() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);
                Owner owner = new Owner(account1);
                owner.setAccountRoleId(INVALID_ACCOUNTROLEID);
                ownerRepository.save(owner);

                ResponseEntity<ErrorDto> response = client.getForEntity("/getowner/" +
                                INVALID_ACCOUNTROLEID,
                                ErrorDto.class);
                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(1, response.getBody().getErrors().size());
                assertEquals("Owner not found", response.getBody().getErrors().get(0));
        }

        @Test
        @Order(32)
        public void testLogin() {
                Account account1 = new Account(USERNAME, PASSWORD);
                accountRepository.save(account1);

                ResponseEntity<AccountDto> response = client.getForEntity("/login/" +
                                USERNAME + "/" + PASSWORD,
                                AccountDto.class);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(USERNAME, response.getBody().getUsername());
                assertEquals(PASSWORD, response.getBody().getPassword());
        }
}
