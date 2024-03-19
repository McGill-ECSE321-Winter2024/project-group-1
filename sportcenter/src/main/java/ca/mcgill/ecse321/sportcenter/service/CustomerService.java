package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
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
     * Get a customer by its accountRole Id (primary key)
     * @param accountRoleId
     * @return Customer
     * @Author Andrew Nemr
     */

    @Transactional
    public Customer getCustomer(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }
        return customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
    }

    /**
     * Create a customer from account username
     * @param username
     * @return Customer
     */

     @Transactional
     public Customer createCustomer(String username) {
         Customer customer = new Customer();
         Account account = accountRepository.findAccountByUsername(username);
         if (account == null) {
             throw new IllegalArgumentException("Account does not exist!");
         }
         customer.setAccount(account);
         customerRepository.save(customer);
         return customer;
     }

    /**
     * Get all customers
     * @return List<Customer>
     */
    @Transactional
    public List<Customer> getAllCustomers() {
        return toList(customerRepository.findAll());
    }

    /**
     * Delete a customer by its accountRoleId (primary key)
     * @param accountRoleId
     */
    @Transactional
    public void deleteCustomer(int accountRoleId) {
        customerRepository.deleteById(accountRoleId);
    }

    /**
     * delete customer by username
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
     * @param accountRoleId
     * @return Customer
     */
    @Transactional
    public Customer getCustomerByRoleId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }
        return customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
    }

    /**
     * Get a customer by its account username
     * @param username
     * @return Customer
     */
    @Transactional
    public Customer getCustomerByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }
        return customerRepository.findAccountRoleByAccountRoleId(account.getAccountId());
    }

    /**
     * Update a custormer username ??????????????????
     * @param accountRoleId
     * @param username
     * @return Customer
     */
    @Transactional
    public Customer updateCustomer(int accountRoleId, String username) {
        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        Account account = accountRepository.findAccountByUsername(username);
        if (account != null) {
            throw new IllegalArgumentException("Account does not exist!");
        }
        account.setUsername(username);
        accountRepository.save(account);
        customer.setAccount(account);
        return customer;
    }

    /**
     * Delete all customers
     * @return void
     */
    @Transactional
    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    /**
     * Check if account is a customer
     * @param accountRoleId
     * @return boolean
     */
    @Transactional
    public boolean isCustomer(int accountRoleId) {
        AccountRole role = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        return role instanceof Customer;
        //return customerRepository.existsById(accountRoleId);
    }
}
