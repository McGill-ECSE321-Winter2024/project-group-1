package ca.mcgill.ecse321.sportcenter.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
 */
@CrossOrigin(origins = "*")
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
    public AccountDto createAccount(@PathVariable("username") String username,
            @PathVariable("password") String password) throws IllegalArgumentException {
        Account account = accountService.createAccount(username, password);
        return convertAccountToDto(account);
    }

    /**
     * Create a customer from accountId
     * 
     * @param accountId
     * @return CustomerDto
     *
     * @PostMapping(value = { "/createCustomer/{accountId}",
     *                    "/createCustomer/{accountId}/" })
     *                    public CustomerDto
     *                    createCustomer(@PathVariable("accountId") int accountId)
     *                    throws IllegalArgumentException {
     *                    Customer customer =
     *                    accountService.createCustomer(accountId);
     *                    return convertCustomerDto(customer);
     *                    }
     */

    /**
     * Create a customer from username
     * 
     * @param username
     * @return CustomerDto
     */
    @PostMapping(value = { "/createCustomer/{username}", "/createCustomer/{username}/" })
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
    @PostMapping(value = { "/createInstructor/{username}/{status}/{description}/{profilePicURL}",
            "/createInstructor/{username}/{status}/{description}/{profilePicURL}/" })
    public InstructorDto createInstructor(@PathVariable("username") String username,
            @PathVariable("status") InstructorStatus status,
            @PathVariable("description") String description, @PathVariable("profilePicture") String profilePicURL)
            throws IllegalArgumentException {
        Instructor instructor = accountService.createInstructor(username, status, description, profilePicURL);
        return convertInstructorToDto(instructor);
    }

    /**
     * Create an owner from username
     * 
     * @param username
     * @return OwnerDto
     */
    @PostMapping(value = { "/createOwner/{username}", "/createOwner/{username}/" })
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
    @GetMapping(value = { "/login/{username}/{password}", "/login/{username}/{password}/" })
    public AccountDto login(@PathVariable("username") String username, @PathVariable("password") String password)
            throws IllegalArgumentException {
        Account account = accountService.login(username, password);
        return convertAccountToDto(account);
    }

    /**
     * Get an account by its account ID
     * 
     * @param accountId
     * @return AccountDto
     */
    @GetMapping(value = { "/account/{accountId}", "/account/{accountId}/" })
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
    @GetMapping(value = { "/account/{username}", "/account/{username}/" })
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
    @GetMapping(value = { "/accounts", "/accounts/" })
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
    @GetMapping(value = { "/customer/{accountRoleId}", "/customer/{accountRoleId}/" })
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
    @GetMapping(value = { "/customer/{username}", "/customer/{username}/" })
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
    @GetMapping(value = { "/getInstructor/{accountRoleId}", "/getInstructor/{accountRoleId}/" })
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
    @GetMapping(value = { "/getInstructor/{username}", "/getInstructor/{username}/" })
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

    // Update

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
    @PutMapping(value = { "/updateInstructor/{username}/{description}/{picture}/",
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
     * Delete all accounts
     */
    @DeleteMapping(value = { "/accounts/deleteAll", "/accounts/deleteAll/" })
    public void deleteAllAccounts() throws IllegalArgumentException {
        accountService.deleteAllAccounts();
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

    /**
     * Delete all instructors
     */
    @DeleteMapping(value = { "/deleteAllInstructors", "/deleteAllInstructors/" })
    public void deleteAllInstructors() {
        accountService.deleteAllInstructors();
    }

    // Account Role Checkers

    /**
     * Check if account has customer role
     * 
     * @param accountRoleId
     * @return boolean
     */
    @GetMapping(value = { "/checkAccountHasCustomerRole/{accountRoleId}",
            "/checkAccountHasCustomerRole/{accountRoleId}/" })
    public boolean checkAccountHasCustomerRole(@PathVariable("accountRoleId") int accountRoleId)
            throws IllegalArgumentException {
        return accountService.checkAccountHasCustomerRole(accountRoleId);
    }

    /**
     * Check if account has instructor role
     * 
     * @param accountRoleId
     * @return boolean
     */
    @GetMapping(value = { "/checkAccountHasInstructorRole/{accountRoleId}",
            "/checkAccountHasInstructorRole/{accountRoleId}/" })
    public boolean checkAccountHasInstructorRole(@PathVariable("accountRoleId") int accountRoleId)
            throws IllegalArgumentException {
        return accountService.checkAccountHasInstructorRole(accountRoleId);
    }

    /**
     * Check if account has owner role
     * 
     * @param accountRoleId
     * @return boolean
     */
    @GetMapping(value = { "/checkAccountHasOwnerRole/{accountRoleId}",
            "/checkAccountHasOwnerRole/{accountRoleId}/" })
    public boolean checkAccountHasOwnerRole(@PathVariable("accountRoleId") int accountRoleId)
            throws IllegalArgumentException {
        return accountService.checkAccountHasOwnerRole(accountRoleId);
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
        return new InstructorDto(instructor.getAccountRoleId());
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
        return new CustomerDto(customer.getAccountRoleId());
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