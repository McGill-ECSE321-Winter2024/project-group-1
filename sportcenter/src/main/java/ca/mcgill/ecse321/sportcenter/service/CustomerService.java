package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.AccountRole;
import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;

public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    /**
     * Convert an iterable to a list
     * 
     * @param Iterable<T>
     * @return List<T>
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    /**
     * Create a customer from accountId
     * 
     * @param accountId
     * @return Customer
     */
    @Transactional
    public Customer createCustomer(int accountId) {
        if (accountId < 0) {
            throw new IllegalArgumentException("AccountId cannot be negative!");
        }
        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }
        Customer customer = new Customer();
        customer.setAccount(account);
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Create a customer from account username
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
     * Update a customer account with a new accountId
     * 
     * @param accountRoleId
     * @param newAccountId
     * @return Customer
     */
    @Transactional
    public Customer updateCustomer(int accountRoleId, int newAccountId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }
        if (newAccountId < 0) {
            throw new IllegalArgumentException("NewAccountId cannot be negative!");
        }
        Account newAccount = accountRepository.findAccountByAccountId(newAccountId);
        if (newAccount == null) {
            throw new IllegalArgumentException("New account does not exist!");
        }
        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        customer.setAccount(newAccount);
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Update a customer account with a new username
     * 
     * @param accountRoleId
     * @param newUsername
     * @return Customer
     */
    @Transactional
    public Customer updateCustomer(int accountRoleId, String newUsername) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }
        if (newUsername == null || newUsername.trim().length() == 0) {
            throw new IllegalArgumentException("New username cannot be empty!");
        }
        Account newAccount = accountRepository.findAccountByUsername(newUsername);
        if (newAccount == null) {
            throw new IllegalArgumentException("New account does not exist!");
        }
        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        customer.setAccount(newAccount);
        customerRepository.save(customer);
        return customer;
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
}
