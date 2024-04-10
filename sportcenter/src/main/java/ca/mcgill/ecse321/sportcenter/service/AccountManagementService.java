package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Owner;
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
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null!");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null!");
        }

        if (username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty!");
        }

        if (password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }

        if (username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot contain spaces!");
        }

        if (password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain spaces!");
        }

        if (!(accountRepository.findAccountByUsername(username) == null)) {
            throw new IllegalArgumentException("Account already exists!");
        }
        Account account = new Account();
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
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null!");
        }

        if (username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty!");
        }

        if (username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot contain spaces!");
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
    public Instructor createInstructor(String username, String description,
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
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Instructor instructor = instructorRepository.findInstructorByAccountUsername(account.getUsername());
        if (instructor != null) {
            throw new IllegalArgumentException("Instructor already exists!");
        }

        instructor = new Instructor(InstructorStatus.Pending, description, profilePicURL, account);
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
            throw new IllegalArgumentException("Username cannot be null, empty or contain spaces!");
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
    public Account login(String username, String password, String role) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }
        if (password == null || password.trim().isEmpty() || password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (role == null || role.trim().isEmpty() || role.contains(" ")) {
            throw new IllegalArgumentException("Role cannot be empty");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }

        // If role is customer, check if account has customer role
        if (role.equals("customer")) {
            if (customerRepository.findCustomerByAccountRoleId(account.getAccountId()) == null) {
                throw new IllegalArgumentException("Account does not have customer role");
            }
        }

        // If role is instructor, check if account has instructor role
        if (role.equals("instructor")) {
            if (instructorRepository.findInstructorByAccountRoleId(account.getAccountId()) == null) {
                throw new IllegalArgumentException("Account does not have instructor role");
            }
        }

        // If role is owner, check if account has owner role
        if (role.equals("owner")) {
            if (ownerRepository.findOwnerByAccountRoleId(account.getAccountId()) == null) {
                throw new IllegalArgumentException("Account does not have owner role");
            }
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
        if (accountId <= 0) {
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
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null!");
        }

        if (username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty!");
        }

        if (username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot contain spaces!");
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

        Customer customer = customerRepository.findCustomerByAccountRoleId(accountRoleId);
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

        Customer customer = customerRepository.findCustomerByAccountRoleId(accountRoleId);
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

        Customer customer = customerRepository.findCustomerByAccountRoleId(accountId);
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

        if (username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot contain spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Customer customer = customerRepository.findCustomerByAccountUsername(username);
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

        Instructor instructor = instructorRepository.findInstructorByAccountRoleId(accountRoleId);
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
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Instructor instructor = instructorRepository.findInstructorByAccountRoleId(account.getAccountId());
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

        Owner owner = ownerRepository.findOwnerByAccountRoleId(accountRoleId);
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
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Owner owner = ownerRepository.findOwnerByAccountUsername(username);
        if (owner == null) {
            throw new IllegalArgumentException("The account is not an owner!");
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
        if (oldUsername == null) {
            throw new IllegalArgumentException("Old Username cannot be null");
        }

        if (newUsername == null) {
            throw new IllegalArgumentException("New Username cannot be null");
        }

        if (oldUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Old Username cannot be empty");
        }

        if (newUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("New Username cannot be empty");
        }

        if (oldUsername.contains(" ")) {
            throw new IllegalArgumentException("Old Username cannot contain spaces");
        }

        if (newUsername.contains(" ")) {
            throw new IllegalArgumentException("New Username cannot contain spaces");
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

        Instructor instructor = instructorRepository.findInstructorByAccountRoleId(id);
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

        Instructor instructor = instructorRepository.findInstructorByAccountRoleId(id);
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
    public Instructor updateInstructor(String username, String description, String picture) {
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
        Instructor instructor = instructorRepository.findInstructorByAccountUsername(username);
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        }

        instructor.setDescription(description);
        instructor.setProfilePicURL(picture);
        instructorRepository.save(instructor);
        return instructor;
    }

    // Delete functions

    /**
     * Delete an account by its account ID (primary key)
     * 
     * @param accountId
     * @return boolean
     */
    @Transactional
    public boolean deleteAccount(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("Account ID must be greater than 0");
        }
        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }



        accountRepository.deleteById(accountId);
        return true;
    }

    /**
     * Delete a customer by its accountRoleId (primary key)
     * 
     * @param accountRoleId
     * @return boolean
     */
    @Transactional
    public boolean deleteCustomerByAccountRoleId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }

        Customer customer = customerRepository.findCustomerByAccountRoleId(accountRoleId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }

        customerRepository.delete(customer);
        return true;
    }

    /**
     * Delete customer by accountId
     * 
     * @param accountId
     * @return boolean
     */
    @Transactional
    public boolean deleteCustomerByAccountId(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("AccountId cannot be negative!");
        }

        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Customer customer = customerRepository.findCustomerByAccountAccountId(accountId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }

        customerRepository.delete(customer);
        return true;
    }

    /**
     * Delete customer by username
     * 
     * @param username
     */
    @Transactional
    public boolean deleteCustomerByUsername(String username) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Customer customer = customerRepository.findCustomerByAccountUsername(username);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }

        customerRepository.delete(customer);
        return true;
    }

    /**
     * Delete a instructor by its accountRoleId (primary key).
     * 
     * @param accountRoleId
     * @return boolean
     * @author Anslean AJ
     */
    @Transactional
    public boolean deleteInstructorByAccountRoleId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }

        Instructor instructor = instructorRepository.findInstructorByAccountRoleId(accountRoleId);
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        }

        instructorRepository.delete(instructor);
        return true;
    }

    /**
     * Delete instructor by username
     * 
     * @param username
     * @return boolean
     * @author Anslean AJ
     */
    @Transactional
    public boolean deleteInstructorByUsername(String username) {
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Instructor instructor = instructorRepository.findInstructorByAccountUsername(username);
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        }

        instructorRepository.delete(instructor);
        return true;
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
            throw new IllegalArgumentException("Account ID must be greater than 0");
        }

        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Customer customer = customerRepository.findCustomerByAccountAccountId(accountId);
        
        if (customer == null) {
            return false;
        }
        else {
            return true;
        }
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
            throw new IllegalArgumentException("Account ID must be greater than 0");
        }

        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Instructor instructor = instructorRepository.findInstructorByAccountAccountId(accountId);
        if (instructor == null) {
            return false;
        }
        else {
            return true;
        }
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
            throw new IllegalArgumentException("Account ID must be greater than 0");
        }

        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        Owner owner = ownerRepository.findOwnerByAccountAccountId(accountId);
        if (owner == null) {
            return false;
        }
        else {
            return true;
        }
    }
}