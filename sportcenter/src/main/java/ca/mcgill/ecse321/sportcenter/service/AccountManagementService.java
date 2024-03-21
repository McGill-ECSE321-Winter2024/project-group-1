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
import ca.mcgill.ecse321.sportcenter.model.AccountRole;
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
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty!");
        }
        if (password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }
        Account account;
        if (accountRepository.findAccountByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists!");
        }
        account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);
        return account;
    }

    // /**
    // * Create a customer from accountId
    // *
    // * @param accountId
    // * @return Customer
    // */
    // @Transactional
    // public Customer createCustomer(int accountId) {
    // if (accountId < 0) {
    // throw new IllegalArgumentException("AccountId cannot be negative!");
    // }
    // Account account = accountRepository.findAccountByAccountId(accountId);
    // if (account == null) {
    // throw new IllegalArgumentException("Account does not exist!");
    // }
    // Customer customer = new Customer();
    // customer.setAccount(account);
    // customerRepository.save(customer);
    // return customer;
    // }

    /**
     * Create a customer from username
     * 
     * @param username
     * @return Customer
     */
    @Transactional
    public Customer createCustomer(String username) {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty!");
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
     * Creates an instructor and account if the username is valid.
     * 
     * @param username
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public Instructor createInstructor(String username, InstructorStatus status, String description,
            String profilePicURL) {

        // Check if the username is null or consists only of whitespace characters
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        // Check if the description is null or consists only of whitespace characters
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null, empty and spaces!");
        }

        // Check if the profilePicURL is null or consists only of whitespace characters
        if (profilePicURL == null || profilePicURL.trim().isEmpty() || profilePicURL.contains(" ")) {
            throw new IllegalArgumentException("ProfilePic URL cannot be null, empty and spaces!");
        }

        // check if the account already exists
        Account verifyAccount = accountRepository.findAccountByUsername(username);
        if (verifyAccount != null) {
            throw new IllegalArgumentException("Account already exists!");
        }

        // All checks are made now.

        Account account = accountRepository.findAccountByUsername(username);

        Instructor instructor = new Instructor(InstructorStatus.Pending, description, profilePicURL, account);
        instructor.setAccount(account);
        instructorRepository.save(instructor);

        return instructor;
    }

    // /**
    // * Create an owner from accountId
    // *
    // * @param accountId
    // * @return Owner
    // */
    // @Transactional
    // public Owner createOwner(int accountId) {
    // if (accountId < 0) {
    // throw new IllegalArgumentException("AccountId cannot be negative!");
    // }
    // Account account = accountRepository.findAccountByAccountId(accountId);
    // if (account == null) {
    // throw new IllegalArgumentException("Account does not exist!");
    // }
    // Owner owner = new Owner();
    // owner.setAccount(account);
    // ownerRepository.save(owner);
    // return owner;
    // }

    /**
     * Create an owner from username
     * 
     * @param username
     * @return Owner
     */
    @Transactional
    public Owner createOwner(String username) {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty!");
        }
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
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
    public boolean login(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        return account.getPassword().equals(password);
    }

    /**
     * Get an account by its account ID (primary key)
     * 
     * @param accountId
     * @return Account
     */
    @Transactional
    public Account getAccountById(int accountId) {
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
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        return account;
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
            return null;
        }

        return instructor;
    }

    /**
     * Returns the list of all intructors in the repository.
     * toList is required since .findAll() will return an Iterable.
     * 
     * @return List of Instructor
     * @author Anslean AJ
     */
    public List<Instructor> getInstructors() {

        return toList(instructorRepository.findAll());
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
     * Get a instructor by its account username
     * 
     * @param username
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public Instructor getInstructorByUsername(String username) {

        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }
        return instructorRepository.findAccountRoleByAccountRoleId(account.getAccountId());
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
     * Update an account's username and password
     * 
     * @param accountId
     * @param newUsername
     * @param newPassword
     * @return Account
     */
    @Transactional
    public Account updateAccount(String oldUsername, String newUsername, String newPassword) {
        if (oldUsername == null || oldUsername.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (newUsername == null || newUsername.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (newPassword == null || newPassword.trim().length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        Account account = accountRepository.findAccountByUsername(oldUsername);
        if (account == null) {
            throw new IllegalArgumentException("Account with the old Username does not exist");
        }
        if (accountRepository.findAccountByUsername(newUsername) != null) {
            throw new IllegalArgumentException("Account with the new Username already exists");
        }
        account.setUsername(newUsername);
        if (!account.getPassword().equals(newPassword)) {
            throw new IllegalArgumentException("Password is incorrect");
        }

        account.setPassword(newPassword);
        accountRepository.save(account);
        return account;
    }

    /**
     * This method allows to update the instructor's description.
     * 
     * @param accountRoleId
     * @param username
     * @param picture
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public void updateInstructor(String username, String description, String picture) {

        // Check if the description is null or consists only of whitespace characters
        if (description == null || description.trim().isEmpty() || description.contains(" ")) {
            throw new IllegalArgumentException("Description cannot be null, empty and spaces!");
        }

        // Check if the profilePicURL is null or consists only of whitespace characters
        if (picture == null || picture.trim().isEmpty() || picture.contains(" ")) {
            throw new IllegalArgumentException("ProfilePic URL cannot be null, empty and spaces!");
        }

        Instructor instructor = instructorRepository.findInstructorByUsername(username);
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        instructor.setDescription(description);
        instructor.setProfilePicURL(picture);
        instructorRepository.save(instructor);
    }

    /**
     * Delete an account by its account ID (primary key)
     * 
     * @param accountId
     */
    @Transactional
    public void deleteAccount(int accountId) {
        accountRepository.deleteById(accountId);
    }

    /**
     * Delete all accounts
     * 
     * @return void
     */
    @Transactional
    public void deleteAllAccounts() {
        accountRepository.deleteAll();
    }

    /**
     * Delete a customer by its accountRoleId (primary key)
     * 
     * @param accountRoleId
     */
    @Transactional
    public void deleteCustomerByAccountRoleId(int accountRoleId) {
        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        customerRepository.delete(customer);
    }

    /**
     * Delete customer by accountId
     * 
     * @param accountId
     */
    @Transactional
    public void deleteCustomerByAccountId(int accountId) {
        Customer customer = new Customer();
        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }
        customer.setAccount(account);
        customerRepository.delete(customer);
    }

    /**
     * Delete customer by username
     * 
     * @param username
     */
    @Transactional
    public void deleteCustomerByUsername(String username) {
        Customer customer = new Customer();
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }
        customer.setAccount(account);
        customerRepository.delete(customer);
    }

    /**
     * Delete all customers
     * 
     * @return void
     */
    @Transactional
    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    /**
     * Delete a instructor by its accountRoleId (primary key). If Id is valid.
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

        instructorRepository.deleteById(accountRoleId);
    }

    /**
     * delete instructor by username
     * 
     * @param username
     * @author Anslean AJ
     */
    @Transactional
    public void deleteInstructorByUsername(String username) {

        Instructor instructor = new Instructor();
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        instructor.setAccount(account);
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

        instructorRepository.deleteAll();

    }

    /**
     * Check if account is a customer
     * 
     * @param accountRoleId
     * @return boolean
     */
    @Transactional
    public boolean isCustomer(int accountRoleId) {
        AccountRole role = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        return role instanceof Customer;
        // return customerRepository.existsById(accountRoleId);

    }

    /**
     * Check if account is an instructor
     * 
     * @param accountRoleId
     * @return boolean
     */
    @Transactional
    public boolean checkAccountInstructor(int accountRoleId) {
        AccountRole role = instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);
        return role instanceof Instructor;
    }

    /**
     * Check if account is an owner
     * 
     * @param accountRoleId
     * @return boolean
     */
    @Transactional
    public boolean checkAccountOwner(int accountRoleId) {
        AccountRole role = ownerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        return role instanceof Owner;
    }
}