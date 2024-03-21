package ca.mcgill.ecse321.sportcenter.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.AccountDto;
import ca.mcgill.ecse321.sportcenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportcenter.dto.InstructorDto;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.service.AccountManagementService;

/**
 * Controller class for the AccountManagement
 * 
 * @Author Andrew Nemr
 */
@CrossOrigin(origins = "*")
@RestController
public class AccountManagementController {
    @Autowired
    private AccountManagementService accountService;

    /**
     * Create an account
     * 
     * @param username
     * @param password
     * @return AccountDto
     */
    @PostMapping(value = { "/createAccount", "/createAccount/" })
    public AccountDto createAccount(@PathVariable("username") String username,
            @PathVariable("password") String password) throws IllegalArgumentException {
        Account account = accountService.createAccount(username, password);
        return convertAccountDto(account);
    }

    /**
     * Create a customer from accountId
     * 
     * @param accountId
     * @return CustomerDto
     */
    @PostMapping(value = { "/createCustomer/{accountId}", "/createCustomer/{accountId}/" })
    public CustomerDto createCustomer(@PathVariable("accountId") int accountId) throws IllegalArgumentException {
        Customer customer = accountService.createCustomer(accountId);
        return convertCustomerDto(customer);
    }

    /**
     * Create an intructor
     * 
     * @param username
     * @param status
     * @param description
     * @param profilePicture
     * @return InstructorDto
     */
    @PostMapping(value = { "/createInstructor/{username}/{status}/{description}/{profilePicture}",
            "/createInstructor/{username}/{status}/{description}/{profilePicture}/" })
    public InstructorDto createInstructor(@PathVariable("username") String username,
            @PathVariable("status") InstructorStatus status,
            @PathVariable("description") String description, @PathVariable("profilePicture") String profilePicture) {
        Instructor instructor = accountService.createInstructor(username, status, description, profilePicture);
        return convertToDto(instructor);
    }

    /**
     * Login
     * 
     * @param username
     * @param password
     * 
     * @return boolean
     */
    @PostMapping(value = { "/login", "/login/" })
    public boolean login(@RequestParam("username") String username,
            @RequestParam("password") String password) throws IllegalArgumentException {
        return accountService.login(username, password);
    }

    /**
     * Get all accounts
     * 
     * @return List<AccountDto>
     */
    @GetMapping(value = { "/accounts/login/getAll", "/accounts/login/getAll/" })
    public List<AccountDto> getAllAccounts() throws IllegalArgumentException {
        List<Account> accounts = accountService.getAllAccounts();
        return convertAccountsToDto(accounts);
    }

    /**
     * Get an account by its account ID
     * 
     * @param accountId
     * @return AccountDto
     */
    @GetMapping(value = { "/account/{accountId}", "/account/{accountId}/" })
    public AccountDto getAccountById(@PathVariable("accountId") int accountId) throws IllegalArgumentException {
        Account account = accountService.getAccountById(accountId);
        return convertAccountDto(account);
    }

    /**
     * Get an account by its username
     * 
     * @param username
     * @return AccountDto
     */
    @GetMapping(value = { "/account/{username}", "/account/{username}/" })
    public AccountDto findAccountByUsername(@PathVariable("username") String username) throws IllegalArgumentException {
        Account account = accountService.getAccountByUsername(username);
        return convertAccountDto(account);
    }

    /**
     * Get a customer by its accountRoleId
     * 
     * @param accountRoleId
     * @return CustomerDto
     */
    @GetMapping(value = { "/getCustomer/{accountRoleId}", "/getCustomer/{accountRoleId}/" })
    public CustomerDto getCustomer(@PathVariable("accountRoleId") int accountRoleId) throws IllegalArgumentException {
        Customer customer = accountService.getCustomerByAccountRoleId(accountRoleId);
        return convertCustomerDto(customer);
    }

    /**
     * Get a customer by its accountId
     * 
     * @param accountId
     * @return CustomerDto
     */
    @GetMapping(value = { "/getCustomerByAccountId/{accountId}", "/getCustomerByAccountId/{accountId}/" })
    public CustomerDto getCustomerByAccountId(@PathVariable("accountId") int accountId)
            throws IllegalArgumentException {
        Customer customer = accountService.getCustomerByAccountId(accountId);
        return convertCustomerDto(customer);
    }

    /**
     * Get a customer by its username
     * 
     * @param username
     * @return CustomerDto
     */
    @GetMapping(value = { "/getCustomer/{username}", "/getCustomer/{username}/" })
    public CustomerDto getCustomer(@PathVariable("username") String username) throws IllegalArgumentException {
        Customer customer = accountService.getCustomerByUsername(username);
        return convertCustomerDto(customer);
    }

    /**
     * Get all customers
     * 
     * @return List<CustomerDto>
     */
    @GetMapping(value = { "/getAllCustomers", "/getAllCustomers/" })
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = accountService.getAllCustomers();
        return convertCustomersDto(customers);
    }

    /**
     * Get account by accountRoleId
     * 
     * @param accountRoleId
     * @return AccountDto
     */
    @GetMapping(value = { "/getAccount/{accountRoleId}", "/getAccount/{accountRoleId}/" })
    public AccountDto getAccount(@PathVariable("accountRoleId") int accountRoleId) throws IllegalArgumentException {
        Customer customer = accountService.getCustomerByAccountRoleId(accountRoleId);
        Account account = customer.getAccount();
        return convertAccountDto(account);
    }

    /**
     * Get all instructors
     * 
     * @return List<InstructorDto>
     */
    @GetMapping(value = { "/getAllInstructors", "/getAllInstructors/" })
    public ResponseEntity<?> getAllInstructors() {
        try {
            List<Instructor> instructors = accountService.getAllInstructors();
            return ResponseEntity.ok(convertToDto(instructors));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // something went bad, get request
        }
    }

    /**
     * Get a instructor by its username
     * 
     * @param username
     * @return InstructorDto
     */
    @GetMapping(value = { "/getInstructor{username}", "/getInstructor/{username}/" })
    public ResponseEntity<?> getInstructorByUsername(@PathVariable("username") String username) {
        try {
            Instructor instructor = accountService.getInstructorByUsername(username);
            return ResponseEntity.ok(convertToDto(instructor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get a instructor by its accountRoleId
     * 
     * @param accountRoleId
     * @return instructorDto
     */
    @GetMapping(value = { "/getInstructor/{accountRoleId}", "/getInstructor/{accountRoleId}/" })
    public InstructorDto getInstructor(@PathVariable("accountRoleId") int accountRoleId) {
        Instructor instructor = accountService.getInstructorByAccountRoleId(accountRoleId);
        return convertToDto(instructor);
    }

    /**
     * Update an account
     * 
     * @param username
     * @param password
     * @return AccountDto
     */
    @PutMapping(value = { "/account/update/{oldUsername}/{newUsername}/{newPassword}",
            "/account/update/{oldUsername}/{newUsername}/{newPassword}/" })
    public AccountDto updateAccount(@PathVariable("oldUsername") String oldUsername,
            @PathVariable("newUsername") String newUsername, @PathVariable("newPassword") String newPassword)
            throws IllegalArgumentException {
        Account account = accountService.updateAccount(oldUsername, newUsername, newPassword);
        return convertAccountDto(account);
    }

    /**
     * Update an instructor
     * 
     * @param String username
     * @param String description
     * @param String picture
     * @param String InstructorStatus
     */
    @PutMapping(value = { "/updateInstructor/{username}/{description}/{picture}/",
            "/updateInstructor/{username}/{description}/{picture}/" })
    public void updateInstructor(@PathVariable("username") String username,
            @PathVariable("description") String description,
            @PathVariable("picture") String picture) {
        accountService.updateInstructor(username, description, picture);
    }

    /**
     * Delete an account by its account ID
     * 
     * @param accountId
     */
    @DeleteMapping(value = { "/account/delete/{accountId}", "/account/delete/{accountId}/" })
    public void deleteAccount(@PathVariable("accountId") int accountId) throws IllegalArgumentException {
        accountService.deleteAccount(accountId);
    }

    /**
     * Delete all accounts
     */
    @DeleteMapping(value = { "/accounts/login/deleteAll", "/accounts/login/deleteAll/" })
    public void deleteAllAccounts() throws IllegalArgumentException {
        accountService.deleteAllAccounts();
    }

    /**
     * Delete a customer by its accountRoleId
     * 
     * @param accountRoleId
     * @return
     */
    @DeleteMapping(value = { "/deleteCustomer/{accountRoleId}", "/deleteCustomer/{accountRoleId}/" })
    public void deleteCustomer(@PathVariable("accountRoleId") int accountRoleId)
            throws IllegalArgumentException {
        accountService.deleteCustomerByAccountRoleId(accountRoleId);
    }

    /**
     * Delete a customer by its accountId
     * 
     * @param username
     * @return CustomerDto
     */
    @DeleteMapping(value = { "/deleteCustomerByAccountId/{accountId}", "/deleteCustomerByAccountId/{accountId}/" })
    public void deleteCustomerByAccountId(@PathVariable("accountId") int accountId) throws IllegalArgumentException {
        accountService.deleteCustomerByAccountId(accountId);
    }

    /**
     * Delete a customer by its username
     * 
     * @param username
     * @return
     */
    @DeleteMapping(value = { "/deleteCustomer/{username}", "/deleteCustomer/{username}/" })
    public void deleteCustomer(@PathVariable("username") String username) throws IllegalArgumentException {
        accountService.deleteCustomerByUsername(username);
    }

    /**
     * Delete all customers
     * 
     * @return
     */
    @DeleteMapping(value = { "/deleteAllCustomers", "/deleteAllCustomers/" })
    public void deleteAllCustomers() {
        accountService.deleteAllCustomers();
    }

    /**
     * Delete a instructor by its accountRoleId
     * 
     * @param accountRoleId
     * @return
     */
    @DeleteMapping(value = { "/deleteInstructor/{accountRoleId}", "/deleteInstructor/{accountRoleId}/" })
    public void deleteInstructor(@PathVariable("accountRoleId") int accountRoleId) {
        accountService.deleteInstructorByAccountRoleId(accountRoleId);
    }

    // MORE CONTROLLERS

    /**
     * Delete all instructors
     * 
     * @return
     */
    @DeleteMapping(value = { "/deleteAllInstructors", "/deleteAllInstructors/" })
    public ResponseEntity<?> deleteAllInstructors() {
        try {
            accountService.deleteAllInstructors();
            return ResponseEntity.ok("Instructors deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public static InstructorDto convertToDto(Instructor instructor) {
        return new InstructorDto(instructor.getAccountRoleId());
    }

    public static List<InstructorDto> convertToDto(List<Instructor> instructors) {
        List<InstructorDto> instructorDto = new ArrayList<InstructorDto>(instructors.size());
        for (Instructor instructor : instructors) {
            instructorDto.add(convertToDto(instructor));
        }
        return instructorDto;
    }

    /**
     * Convert a list of accounts to a list of account DTOs
     * 
     * @param accounts
     * @return
     */
    static List<AccountDto> convertAccountsToDto(List<Account> accounts) {
        List<AccountDto> accountDtos = new ArrayList<AccountDto>(accounts.size());

        for (Account account : accounts) {
            accountDtos.add(new AccountDto(account.getAccountId(), account.getUsername(), account.getPassword()));
        }
        return accountDtos;
    }

    /**
     * Convert a Customer entity to a CustomerDto
     * 
     * @param customer
     * @return CustomerDto
     */
    public static CustomerDto convertCustomerDto(Customer customer) {
        return new CustomerDto(customer.getAccountRoleId());
    }

    /**
     * Convert a list of Customer entities to a list of CustomerDtos
     * 
     * @param customers
     * @return List<CustomerDto>
     */
    public static List<CustomerDto> convertCustomersDto(List<Customer> customers) {
        List<CustomerDto> customerDto = new ArrayList<CustomerDto>(customers.size());
        for (Customer customer : customers) {
            customerDto.add(convertCustomerDto(customer));
        }
        return customerDto;
    }

    /**
     * Convert an account to an account DTO
     * 
     * @param account
     * @return
     */
    static AccountDto convertAccountDto(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("There is no account to convert");
        }
        return new AccountDto(account.getAccountId(), account.getUsername(), account.getPassword());
    }

    /**
     * Check if account is a customer
     * 
     * @param accountRoleId
     * @return boolean
     */
    @GetMapping(value = { "/isCustomer/{accountRoleId}", "/isCustomer/{accountRoleId}/" })
    public boolean isCustomer(@PathVariable("accountRoleId") int accountRoleId) {
        return accountService.isCustomer(accountRoleId);
    }

    /**
     * Check if account is an instructor
     * 
     * @param accountRoleId
     * @return boolean
     */
    @GetMapping(value = { "/isInstructor/{accountRoleId}", "/isInstructor/{accountRoleId}/" })
    public boolean isInstructor(@PathVariable("accountRoleId") int accountRoleId) {
        return accountService.checkAccountInstructor(accountRoleId);
    }

    /**
     * Check if account is an owner
     * 
     * @param accountRoleId
     * @return boolean
     */

    @GetMapping(value = { "/isOwner/{accountRoleId}", "/isOwner/{accountRoleId}/" })
    public boolean isOwner(@PathVariable("accountRoleId") int accountRoleId) {
        return accountService.checkAccountOwner(accountRoleId);
    }
}