package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;

/**
 * Service class for the Account entity
 * 
 * @author Andrew Nemr
 */
@Service
public class AccountManagementService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    OwnerRepository ownerRepository;

    /**
     * Helper function to turn an iterable into a list
     * 
     * @param Iterable<T>
     * @return List<T>
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * Create an account
     * 
     * @param username
     * @param password
     * @return Account
     */
    @Transactional
    public Account createAccount(String username, String password) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }
        if (password == null || password.trim().isEmpty() || password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account != null) {
            throw new IllegalArgumentException("Username already exists!");
        }

        account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);
        return account;
    }

    /**
     * Create a customer from username
     * 
     * @param username
     * @return Customer
     */
    @Transactional
    public Customer createCustomer(String username) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Customer customer = new Customer();
        customer.setAccount(account);
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Creates an instructor from username
     * 
     * @param username
     * @param status
     * @param description
     * @param profilePicURL
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public Instructor createInstructor(String username, InstructorStatus status, String description,
            String profilePicURL) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null, empty and spaces!");
        }
        if (profilePicURL == null || profilePicURL.trim().isEmpty() || profilePicURL.contains(" ")) {
            throw new IllegalArgumentException("ProfilePic URL cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account != null) {
            throw new IllegalArgumentException("Account already exists!");
        }

        Instructor instructor = new Instructor(InstructorStatus.Pending, description, profilePicURL, account);
        instructorRepository.save(instructor);
        return instructor;
    }

    /**
     * Create an owner from username
     * 
     * @param username
     * @return Owner
     */
    @Transactional
    public Owner createOwner(String username) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        // Can only have 1 Owner
        if (ownerRepository.findAll().iterator().hasNext()) {
            throw new IllegalArgumentException("Owner already exists!");
        }

        Owner owner = new Owner();
        owner.setAccount(account);
        ownerRepository.save(owner);
        return owner;
    }

    /**
     * Login function
     * 
     * @param username
     * @param password
     * @return Account
     */
    public Account login(String username, String password) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }
        if (password == null || password.trim().isEmpty() || password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        return account;
    }

    /**
     * Get an account by its account ID (primary key)
     * 
     * @param accountId
     * @return Account
     */
    @Transactional
    public Account getAccountByAccountId(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("Account ID must be greater than 0");
        }

        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        return account;
    }

    /**
     * Get an account by its username
     * 
     * @param username
     * @return Account
     */
    @Transactional
    public Account getAccountByUsername(String username) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        return account;
    }

    /**
     * Get account by accountRoleId
     * 
     * @param accountRoleId
     * @return Account
     */
    @Transactional
    public Account getAccountByAccountRoleId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }

        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        return customer.getAccount();
    }

    /**
     * Get all accounts
     * 
     * @return List<Account>
     * @author Andrew Nemr
     */
    @Transactional
    public List<Account> getAllAccounts() {
        return toList(accountRepository.findAll());
    }

    /**
     * Get a customer by its accountRoleId (primary key)
     * 
     * @param accountRoleId
     * @return Customer
     */
    @Transactional
    public Customer getCustomerByAccountRoleId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }

        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        return customer;
    }

    /**
     * Get a customer by accountId
     * 
     * @param accountId
     * @return Customer
     */
    @Transactional
    public Customer getCustomerByAccountId(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("AccountId cannot be negative!");
        }

        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        return customer;
    }

    /**
     * Get a customer by username
     * 
     * @param username
     * @return Customer
     */
    @Transactional
    public Customer getCustomerByUsername(String username) {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Customer customer = customerRepository.findAccountRoleByAccountRoleId(account.getAccountId());
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        return customer;
    }

    /**
     * Get all customers
     * 
     * @return List<Customer>
     */
    @Transactional
    public List<Customer> getAllCustomers() {
        return toList(customerRepository.findAll());
    }

    /**
     * Get an instructor by its accountRole Id (primary key).
     * 
     * @param accountRoleId
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public Instructor getInstructorByAccountRoleId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }

        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        }
        return instructor;
    }

    /**
     * Get a instructor by its account username
     * 
     * @param username
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public Instructor getInstructorByUsername(String username) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) { // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(account.getAccountId());
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        }
        return instructor;
    }

    /**
     * Get all instructors
     * 
     * @return List of Instructor
     * @author Anslean AJ
     */
    @Transactional
    public List<Instructor> getAllInstructors() {
        return toList(instructorRepository.findAll());
    }

    /**
     * Get an owner by its accountRoleId (primary key)
     * 
     * @param accountRoleId
     * @return Owner
     */
    @Transactional
    public Owner getOwnerByAccountRoleId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }

        Owner owner = ownerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (owner == null) {
            throw new IllegalArgumentException("Owner does not exist!");
        }
        return owner;
    }

    /**
     * Get an owner by its account username
     * 
     * @param username
     * @return Owner
     */
    @Transactional
    public Owner getOwnerByUsername(String username) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) { // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Owner owner = ownerRepository.findAccountRoleByAccountRoleId(account.getAccountId());
        if (owner == null) {
            throw new IllegalArgumentException("Owner does not exist!");
        }
        return owner;
    }

    /**
     * Update an account's username
     * 
     * @param accountId
     * @param newUsername
     * @param newPassword
     * @return Account
     */
    @Transactional
    public Account updateAccountUsername(String oldUsername, String newUsername) {
        if (oldUsername == null || oldUsername.trim().isEmpty() || oldUsername.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (newUsername == null || newUsername.trim().isEmpty() || newUsername.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        Account account = accountRepository.findAccountByUsername(oldUsername);
        if (account == null) {
            throw new IllegalArgumentException("Account with the old Username does not exist");
        }
        if (accountRepository.findAccountByUsername(newUsername) != null) {
            throw new IllegalArgumentException("Account with the new Username already exists");
        }

        account.setUsername(newUsername);
        accountRepository.save(account);
        return account;
    }

    /**
     * Owner approves an instructor
     * 
     * @param id
     * @return boolean
     */
    @Transactional
    public boolean approveInstructor(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative");
        }

        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(id);
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist");
        }
        if (instructor.getStatus() == InstructorStatus.Active) {
            throw new IllegalArgumentException("Instructor is already approved");
        }

        instructor.setStatus(InstructorStatus.Active);
        instructorRepository.save(instructor);
        return true;

    }

    /**
     * Owner disapproves an instructor
     * 
     * @param id
     * @return boolean
     */
    @Transactional
    public boolean disapproveInstructor(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative");
        }

        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(id);
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist");
        }
        if (instructor.getStatus() == InstructorStatus.Inactive) {
            throw new IllegalArgumentException("Instructor is already disapproved");
        }

        instructor.setStatus(InstructorStatus.Inactive);
        instructorRepository.save(instructor);
        return true;
    }

    /**
     * Update an account's password
     * 
     * @param username
     * @param oldPassword
     * @param newPassword
     * @return Account
     */
    @Transactional
    public Account updateAccountPassword(String username, String oldPassword, String newPassword) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (oldPassword == null || oldPassword.trim().isEmpty() || oldPassword.contains(" ")) {
            throw new IllegalArgumentException("Old password cannot be empty");
        }
        if (newPassword == null || newPassword.trim().isEmpty() || newPassword.contains(" ")) {
            throw new IllegalArgumentException("New password cannot be empty");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        if (!account.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        if (account.getPassword().equals(newPassword)) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }

        account.setPassword(newPassword);
        accountRepository.save(account);
        return account;
    }

    /**
     * Update the instructor's profile information.
     * 
     * @param accountRoleId
     * @param username
     * @param picture
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public void updateInstructor(String username, String description, String picture) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }
        if (description == null || description.trim().isEmpty() || description.contains(" ")) {
            throw new IllegalArgumentException("Description cannot be null, empty and spaces!");
        }
        if (picture == null || picture.trim().isEmpty() || picture.contains(" ")) {
            throw new IllegalArgumentException("ProfilePic URL cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }
        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(account.getAccountId());
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        }

        instructor.setDescription(description);
        instructor.setProfilePicURL(picture);
        instructorRepository.save(instructor);
    }

    // Delete functions

    /**
     * Delete an account by its account ID (primary key)
     * 
     * @param accountId
     */
    @Transactional
    public void deleteAccount(int accountId) { // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        if (accountId < 0) {
            throw new IllegalArgumentException("Account ID must be greater than 0");
        }
        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }

        // Delete all associated account roles
        deleteCustomerByAccountId(accountId);
        deleteInstructorByUsername(account.getUsername());

        accountRepository.deleteById(accountId);
    }

    /**
     * Delete all accounts
     * 
     * @return void
     */
    @Transactional
    public void deleteAllAccounts() {
        for (Account account : accountRepository.findAll()) {
            deleteAccount(account.getAccountId());
        }
    }

    /**
     * Delete a customer by its accountRoleId (primary key)
     * 
     * @param accountRoleId
     */
    @Transactional
    public void deleteCustomerByAccountRoleId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }

        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }

        // Delete all associated registrations
        RegistrationManagementService registrationManagementService = new RegistrationManagementService();
        registrationManagementService.deleteRegistrationsByAccountRoleId(customer.getAccountRoleId());

        customerRepository.delete(customer);
    }

    /**
     * Delete customer by accountId
     * 
     * @param accountId
     */
    @Transactional
    public void deleteCustomerByAccountId(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("AccountId cannot be negative!");
        }

        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }

        // Delete all associated registrations
        RegistrationManagementService registrationManagementService = new RegistrationManagementService();
        registrationManagementService.deleteRegistrationsByAccountRoleId(customer.getAccountRoleId());

        customerRepository.delete(customer);
    }

    /**
     * Delete customer by username
     * 
     * @param username
     */
    @Transactional
    public void deleteCustomerByUsername(String username) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) { // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Customer customer = customerRepository.findAccountRoleByAccountRoleId(account.getAccountId());
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }

        // Delete all associated registrations
        RegistrationManagementService registrationManagementService = new RegistrationManagementService();
        registrationManagementService.deleteRegistrationsByAccountRoleId(customer.getAccountRoleId());

        customerRepository.delete(customer);
    }

    /**
     * Delete all customers
     * 
     * @return void
     */
    @Transactional
    public void deleteAllCustomers() {
        for (Customer customer : customerRepository.findAll()) {
            deleteCustomerByAccountRoleId(customer.getAccountRoleId());
        }
    }

    /**
     * Delete a instructor by its accountRoleId (primary key).
     * 
     * @param accountRoleId
     * @author Anslean AJ
     */
    @Transactional
    public void deleteInstructorByAccountRoleId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }

        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (instructor == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        // Delete all associated scheduled activities
        ScheduledActivityManagementService scheduledActivityManagementService = new ScheduledActivityManagementService();
        scheduledActivityManagementService.deleteAllScheduledActivitiesByInstructorId(accountRoleId);

        instructorRepository.deleteById(accountRoleId);
    }

    /**
     * Delete instructor by username
     * 
     * @param username
     * @author Anslean AJ
     */
    @Transactional
    public void deleteInstructorByUsername(String username) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) { // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(account.getAccountId());
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        }

        // Delete all associated scheduled activities
        ScheduledActivityManagementService scheduledActivityManagementService = new ScheduledActivityManagementService();
        scheduledActivityManagementService.deleteAllScheduledActivitiesByInstructorId(instructor.getAccountRoleId());

        instructorRepository.delete(instructor);
    }

    /**
     * Delete all instructors
     * 
     * @return void
     * @author Anslean AJ
     */
    @Transactional
    public void deleteAllInstructors() {
        for (Instructor instructor : instructorRepository.findAll()) {
            deleteInstructorByAccountRoleId(instructor.getAccountRoleId());
        }
    }

    // Extra functions

    /**
     * Check if account has customer role
     * 
     * @param accountId
     * @return boolean
     */
    @Transactional
    public boolean checkAccountHasCustomerRole(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("Account ID must be greater than 0"); // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        }

        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountId);
        return customer == null;
    }

    /**
     * Check if account has instructor role
     * 
     * @param accountId
     * @return boolean
     */
    @Transactional
    public boolean checkAccountHasInstructorRole(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("Account ID must be greater than 0"); // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        }

        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(accountId);
        return instructor == null;
    }

    /**
     * Check if account has owner role
     * 
     * @param accountId
     * @return boolean
     */
    @Transactional
    public boolean checkAccountHasOwnerRole(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("Account ID must be greater than 0"); // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        }

        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Owner owner = ownerRepository.findAccountRoleByAccountRoleId(accountId);
        return owner == null;
    }
}