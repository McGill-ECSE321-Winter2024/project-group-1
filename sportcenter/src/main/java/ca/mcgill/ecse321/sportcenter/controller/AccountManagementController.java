package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.AccountDto;
import ca.mcgill.ecse321.sportcenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportcenter.dto.InstructorDto;
import ca.mcgill.ecse321.sportcenter.dto.OwnerDto;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.service.AccountManagementService;

/**
 * Controller class for the AccountManagement
 * 
 * @author Mathias Lamina & Patrick Zacharia & Andrew Nemr
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AccountManagementController {
    @Autowired
    private AccountManagementService accountService;

    // Create

    /**
     * Create an account
     * 
     * @param username
     * @param password
     * @return AccountDto
     */
    @PostMapping(value = { "/createAccount/{username}/{password}", "/createAccount/{username}/{password}/" })
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@PathVariable("username") String username,
            @PathVariable("password") String password)
            throws IllegalArgumentException {
        Account account = accountService.createAccount(username, password);
        return convertAccountToDto(account);
    }

    /**
     * Create a customer from username
     * 
     * @param username
     * @return CustomerDto
     */
    @PostMapping(value = { "/createCustomer/{username}", "/createCustomer/{username}/" })
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@PathVariable("username") String username) throws IllegalArgumentException {
        Customer customer = accountService.createCustomer(username);
        return convertCustomerToDto(customer);
    }

    /**
     * Create an intructor from username
     * 
     * @param username
     * @param status
     * @param description
     * @param profilePicURL
     * @return InstructorDto
     */
    @PostMapping(value = { "/createInstructor/{username}/{description}/{profilePicURL}",
            "/createInstructor/{username}/{description}/{profilePicURL}/" })
    @ResponseStatus(HttpStatus.CREATED)
    public InstructorDto createInstructor(@PathVariable("username") String username,
            @PathVariable("description") String description, @PathVariable("profilePicURL") String profilePicURL)
            throws IllegalArgumentException {
        Instructor instructor = accountService.createInstructor(username, description, profilePicURL);
        return convertInstructorToDto(instructor);
    }

    /**
     * Create an owner from username
     * 
     * @param username
     * @return OwnerDto
     */
    @PostMapping(value = { "/createOwner/{username}", "/createOwner/{username}/" })
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerDto createOwner(@PathVariable("username") String username) throws IllegalArgumentException {
        Owner owner = accountService.createOwner(username);
        return convertOwnerToDto(owner);
    }

    // Get

    /**
     * Login
     * 
     * @param username
     * @param password
     * 
     * @return AccountDto
     */
    @GetMapping(value = { "/login/{username}/{password}/{role}", "/login/{username}/{password}/{role}/" })
    public AccountDto login(@PathVariable("username") String username, @PathVariable("password") String password,
            @PathVariable("role") String role) throws IllegalArgumentException {
        Account account = accountService.login(username, password, role);
        return convertAccountToDto(account);
    }

    /**
     * Get an account by its account ID
     * 
     * @param accountId
     * @return AccountDto
     */
    @GetMapping(value = { "/getAccountById/{accountId}", "/getAccountById/{accountId}/" })
    public AccountDto getAccountById(@PathVariable("accountId") int accountId) throws IllegalArgumentException {
        Account account = accountService.getAccountByAccountId(accountId);
        return convertAccountToDto(account);
    }

    /**
     * Get an account by its username
     * 
     * @param username
     * @return AccountDto
     */
    @GetMapping(value = { "/getAccountByUsername/{username}", "/getAccountByUsername/{username}/" })
    public AccountDto getAccountByUsername(@PathVariable("username") String username) throws IllegalArgumentException {
        Account account = accountService.getAccountByUsername(username);
        return convertAccountToDto(account);
    }

    /**
     * Get account by accountRoleId
     * 
     * @param accountRoleId
     * @return AccountDto
     */
    @GetMapping(value = { "/getAccountByAccountRoleId/{accountRoleId}", "/getAccountByAccountRoleId/{accountRoleId}/" })
    public AccountDto getAccountByAccountRoleId(@PathVariable("accountRoleId") int accountRoleId)
            throws IllegalArgumentException {
        Account account = accountService.getAccountByAccountRoleId(accountRoleId);
        return convertAccountToDto(account);
    }

    /**
     * Get all accounts
     * 
     * @return List<AccountDto>
     */
    @GetMapping(value = { "/getAllAccounts", "/getAllAccounts/" })
    public List<AccountDto> getAllAccounts() throws IllegalArgumentException {
        List<Account> accounts = accountService.getAllAccounts();
        return convertAccountsToDto(accounts);
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
        return convertCustomerToDto(customer);
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
        return convertCustomerToDto(customer);
    }

    /**
     * Get a customer by its username
     * 
     * @param username
     * @return CustomerDto
     */
    @GetMapping(value = { "/getCustomerByUsername/{username}", "/getCustomerByUsername/{username}/" })
    public CustomerDto getCustomer(@PathVariable("username") String username) throws IllegalArgumentException {
        Customer customer = accountService.getCustomerByUsername(username);
        return convertCustomerToDto(customer);
    }

    /**
     * Get all customers
     * 
     * @return List<CustomerDto>
     */
    @GetMapping(value = { "/getAllCustomers", "/getAllCustomers/" })
    public List<CustomerDto> getAllCustomers() throws IllegalArgumentException {
        List<Customer> customers = accountService.getAllCustomers();
        return convertCustomersToDto(customers);
    }

    /**
     * Get a instructor by its accountRoleId
     * 
     * @param accountRoleId
     * @return instructorDto
     */
    @GetMapping(value = { "/getInstructorByAccountRoleId/{accountRoleId}",
            "/getInstructorByAccountRoleId/{accountRoleId}/" })
    public InstructorDto getInstructor(@PathVariable("accountRoleId") int accountRoleId)
            throws IllegalArgumentException {
        Instructor instructor = accountService.getInstructorByAccountRoleId(accountRoleId);
        return convertInstructorToDto(instructor);
    }

    /**
     * Get a instructor by its username
     * 
     * @param username
     * @return InstructorDto
     */
    @GetMapping(value = { "/getInstructorByUsername/{username}", "/getInstructorByUsername/{username}/" })
    public InstructorDto getInstructorByUsername(@PathVariable("username") String username)
            throws IllegalArgumentException {
        Instructor instructor = accountService.getInstructorByUsername(username);
        return convertInstructorToDto(instructor);
    }

    /**
     * Get all instructors
     * 
     * @return List<InstructorDto>
     */
    @GetMapping(value = { "/getAllInstructors", "/getAllInstructors/" })
    public List<InstructorDto> getAllInstructors() throws IllegalArgumentException {
        List<Instructor> instructors = accountService.getAllInstructors();
        return convertInstructorsToDto(instructors);
    }

    /**
     * Get all instructors by status
     * 
     * @param status
     * @return List<InstructorDto>
     */
    @GetMapping(value = { "/getAllInstructorsByStatus/{status}", "/getAllInstructorsByStatus/{status}/" })
    public List<InstructorDto> getAllInstructorsByStatus(@PathVariable("status") String status)
            throws IllegalArgumentException {
        List<Instructor> instructors = accountService.getAllInstructorsByStatus(InstructorStatus.valueOf(status));
        return convertInstructorsToDto(instructors);
    }

    /**
     * Get an owner by its accountRoleId
     * 
     * @param accountRoleId
     * @return OwnerDto
     */
    @GetMapping(value = { "/getOwnerByAccountRoleId/{accountRoleId}", "/getOwnerByAccountRoleId/{accountRoleId}/" })
    public OwnerDto getOwnerByAccountRoleId(@PathVariable("accountRoleId") int accountRoleId)
            throws IllegalArgumentException {
        Owner owner = accountService.getOwnerByAccountRoleId(accountRoleId);
        return convertOwnerToDto(owner);
    }

    /**
     * Get an owner by its username
     * 
     * @param username
     * @return OwnerDto
     */
    @GetMapping(value = { "/getOwnerByUsername/{username}", "/getOwnerByUsername/{username}/" })
    public OwnerDto getOwnerByUsername(@PathVariable("username") String username) throws IllegalArgumentException {
        Owner owner = accountService.getOwnerByUsername(username);
        return convertOwnerToDto(owner);
    }

    /**
     * Get accountId by username
     * 
     * @param username
     * @return int
     */
    @GetMapping(value = { "/getAccountIdByUsername/{username}", "/getAccountIdByUsername/{username}/" })
    public int getAccountIdByUsername(@PathVariable("username") String username) throws IllegalArgumentException {
        return accountService.getAccountIdByUsername(username);
    }

    /**
     * Get customer accountRoleId by username
     * 
     * @param username
     * @return int
     */
    @GetMapping(value = { "/getCustomerAccountRoleIdByUsername/{username}",
            "/getCustomerAccountRoleIdByUsername/{username}/" })
    public int getCustomerAccountRoleIdByUsername(@PathVariable("username") String username)
            throws IllegalArgumentException {
        return accountService.getCustomerAccountRoleIdByUsername(username);
    }

    /**
     * Get instructor accountRoleId by username
     * 
     * @param username
     * @return int
     */
    @GetMapping(value = { "/getInstructorAccountRoleIdByUsername/{username}",
            "/getInstructorAccountRoleIdByUsername/{username}/" })
    public int getInstructorAccountRoleIdByUsername(@PathVariable("username") String username)
            throws IllegalArgumentException {
        return accountService.getInstructorAccountRoleIdByUsername(username);
    }

    // Update

    /**
     * Approve an instructor
     * 
     * @param username
     * @return InstructorDto
     */
    @PutMapping(value = { "/approveInstructor/{username}", "/approveInstructor/{username}/" })
    public InstructorDto approveInstructor(@PathVariable("username") String username) throws IllegalArgumentException {
        Instructor instructor = accountService.approveInstructor(username);
        return convertInstructorToDto(instructor);
    }

    /**
     * Disapprove an instructor
     * 
     * @param username
     * @return InstructorDto
     */
    @PutMapping(value = { "/disapproveInstructor/{username}", "/disapproveInstructor/{username}/" })
    public InstructorDto disapproveInstructor(@PathVariable("username") String username)
            throws IllegalArgumentException {
        Instructor instructor = accountService.disapproveInstructor(username);
        return convertInstructorToDto(instructor);
    }

    /**
     * Update an account username
     * 
     * @param oldUsername
     * @param newUsername
     * @return AccountDto
     */
    @PutMapping(value = { "/updateAccountUsername/{oldUsername}/{newUsername}",
            "/updateAccountUsername/{oldUsername}/{newUsername}/" })
    public AccountDto updateAccountUsername(@PathVariable("oldUsername") String oldUsername,
            @PathVariable("newUsername") String newUsername) throws IllegalArgumentException {
        Account account = accountService.updateAccountUsername(oldUsername, newUsername);
        return convertAccountToDto(account);
    }

    /**
     * Update an account password
     * 
     * @param username
     * @param oldPassword
     * @param newPassword
     * @return AccountDto
     */
    @PutMapping(value = { "/updateAccountPassword/{username}/{oldPassword}/{newPassword}",
            "/updateAccountPassword/{username}/{oldPassword}/{newPassword}/" })
    public AccountDto updateAccountPassword(@PathVariable("username") String username,
            @PathVariable("oldPassword") String oldPassword, @PathVariable("newPassword") String newPassword)
            throws IllegalArgumentException {
        Account account = accountService.updateAccountPassword(username, oldPassword, newPassword);
        return convertAccountToDto(account);
    }

    /**
     * Update an instructor's profile information
     * 
     * @param String username
     * @param String description
     * @param String picture
     * @param String InstructorStatus
     */
    @PutMapping(value = { "/updateInstructor/{username}/{description}/{picture}",
            "/updateInstructor/{username}/{description}/{picture}/" })
    public void updateInstructor(@PathVariable("username") String username,
            @PathVariable("description") String description,
            @PathVariable("picture") String picture) throws IllegalArgumentException {
        accountService.updateInstructor(username, description, picture);
    }

    // Delete

    /**
     * Delete an account by its account ID
     * 
     * @param accountId
     */
    @DeleteMapping(value = { "/deleteAccount/{accountId}", "/deleteAccount/{accountId}/" })
    public void deleteAccount(@PathVariable("accountId") int accountId) throws IllegalArgumentException {
        accountService.deleteAccount(accountId);
    }

    /**
     * Delete a customer by its accountRoleId
     * 
     * @param accountRoleId
     * @return
     */
    @DeleteMapping(value = { "/deleteCustomer/{accountRoleId}",
            "/deleteCustomer/{accountRoleId}/" })
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
    @DeleteMapping(value = { "/deleteCustomerByUsername/{username}", "/deleteCustomerByUsername/{username}/" })
    public void deleteCustomer(@PathVariable("username") String username) throws IllegalArgumentException {
        accountService.deleteCustomerByUsername(username);
    }

    /**
     * Delete a instructor by its accountRoleId
     * 
     * @param accountRoleId
     * @return
     */
    @DeleteMapping(value = { "/deleteInstructorByAccountRoleId/{accountRoleId}",
            "/deleteInstructorByAccountRoleId/{accountRoleId}/" })
    public void deleteInstructorByAccountRoleId(@PathVariable("accountRoleId") int accountRoleId)
            throws IllegalArgumentException {
        accountService.deleteInstructorByAccountRoleId(accountRoleId);
    }

    /**
     * Delete a instructor by its username
     * 
     * @param username
     * @return
     */
    @DeleteMapping(value = { "/deleteInstructorByUsername/{username}", "/deleteInstructorByUsername/{username}/" })
    public void deleteInstructorByUsername(@PathVariable("username") String username) throws IllegalArgumentException {
        accountService.deleteInstructorByUsername(username);
    }

    // Account Role Checkers

    /**
     * Check if account has customer role
     * 
     * @param accountId
     * @return boolean
     */
    @GetMapping(value = { "/checkAccountHasCustomerRole/{accountId}",
            "/checkAccountHasCustomerRole/{accountId}/" })
    public boolean checkAccountHasCustomerRole(@PathVariable("accountId") int accountId)
            throws IllegalArgumentException {
        return accountService.checkAccountHasCustomerRole(accountId);
    }

    /**
     * Check if account has instructor role
     * 
     * @param accountId
     * @return boolean
     */
    @GetMapping(value = { "/checkAccountHasInstructorRole/{accountId}",
            "/checkAccountHasInstructorRole/{accountId}/" })
    public boolean checkAccountHasInstructorRole(@PathVariable("accountId") int accountId)
            throws IllegalArgumentException {
        return accountService.checkAccountHasInstructorRole(accountId);
    }

    /**
     * Check if account has owner role
     * 
     * @param accountId
     * @return boolean
     */
    @GetMapping(value = { "/checkAccountHasOwnerRole/{accountId}",
            "/checkAccountHasOwnerRole/{accountId}/" })
    public boolean checkAccountHasOwnerRole(@PathVariable("accountId") int accountId)
            throws IllegalArgumentException {
        return accountService.checkAccountHasOwnerRole(accountId);
    }

    // Converters

    /**
     * Convert an account to an account DTO
     * 
     * @param account
     * @return AccountDto
     */
    public static AccountDto convertAccountToDto(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("There is no account to convert");
        }
        return new AccountDto(account.getAccountId(), account.getUsername(), account.getPassword());
    }

    /**
     * Convert a list of accounts to a list of account DTOs
     * 
     * @param accounts
     * @return List<AccountDto>
     */
    public static List<AccountDto> convertAccountsToDto(List<Account> accounts) {
        List<AccountDto> accountDtos = new ArrayList<AccountDto>(accounts.size());

        for (Account account : accounts) {
            accountDtos.add(new AccountDto(account.getAccountId(), account.getUsername(), account.getPassword()));
        }
        return accountDtos;
    }

    /**
     * Convert an instructor to an instructor DTO
     * 
     * @param instructor
     * @return InstructorDto
     */
    public static InstructorDto convertInstructorToDto(Instructor instructor) {
        return new InstructorDto(instructor);
    }

    /**
     * Convert a list of instructors to a list of instructor DTOs
     * 
     * @param instructors
     * @return List<InstructorDto>
     */
    public static List<InstructorDto> convertInstructorsToDto(List<Instructor> instructors) {
        List<InstructorDto> instructorDto = new ArrayList<InstructorDto>(instructors.size());
        for (Instructor instructor : instructors) {
            instructorDto.add(convertInstructorToDto(instructor));
        }
        return instructorDto;
    }

    /**
     * Convert a Customer entity to a CustomerDto
     * 
     * @param customer
     * @return CustomerDto
     */
    public static CustomerDto convertCustomerToDto(Customer customer) {
        return new CustomerDto(customer);
    }

    /**
     * Convert a list of Customer entities to a list of CustomerDtos
     * 
     * @param customers
     * @return List<CustomerDto>
     */
    public static List<CustomerDto> convertCustomersToDto(List<Customer> customers) {
        List<CustomerDto> customerDto = new ArrayList<CustomerDto>(customers.size());
        for (Customer customer : customers) {
            customerDto.add(convertCustomerToDto(customer));
        }
        return customerDto;
    }

    /**
     * Convert an owner to an owner DTO
     * 
     * @param owner
     * @return OwnerDto
     */
    public static OwnerDto convertOwnerToDto(Owner owner) {
        return new OwnerDto(convertAccountToDto(owner.getAccount()));
    }

}