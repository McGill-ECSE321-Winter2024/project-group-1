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

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
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
     * Get a customer by its accountRole Id (primary key)
     * 
     * @param accountRoleId
     * @return Customer
     */

    @Transactional
    public Customer getCustomerByAccountRoleId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }
        return customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
    }

    /**
     * Get a customer by its account username
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
        return customerRepository.findAccountRoleByAccountRoleId(account.getAccountId());
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
     * Delete a customer by its accountRoleId (primary key)
     * 
     * @param accountRoleId
     */
    @Transactional
    public void deleteCustomerByAccountRoleId(int accountRoleId) {
        customerRepository.deleteById(accountRoleId);
    }

    /**
     * Delete customer by username
     * 
     * @param username
     * @return
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
     * Get a customer by its accountRoleId (primary key)
     * 
     * @param accountRoleId
     * @return Customer
     */
    @Transactional
    public Customer getCustomerByRoleId(int accountRoleId) {
        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        if (accountRoleId < 0) {// to check if accountRolle is null?????
            throw new IllegalArgumentException("AccountRoleId is not valid!");
        }
        return customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
    }

    /**
     * Update a custormer username ??????????????????
     * 
     * @param accountRoleId
     * @param username
     * @return Customer
     */
    @Transactional
    public Customer updateCustomer(int accountRoleId, String username) {
        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        Account account = accountRepository.findAccountByUsername(username);

        if (account == null) { // if (account != null) { - CORRECTED
            throw new IllegalArgumentException("Account does not exist!");
        }

        account.setUsername(username);
        accountRepository.save(account);
        customer.setAccount(account);
        return customer;
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
