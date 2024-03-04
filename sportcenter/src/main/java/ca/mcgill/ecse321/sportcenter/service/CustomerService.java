package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.model.Customer;

public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;

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
     * Create a customer
     * @param accountRoleId
     * @return Customer
     */
    @Transactional
    public Customer createCustomer(int accountRoleId) {
        Customer customer = new Customer();
        customer.setAccountRoleId(accountRoleId);
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
     * Update a customer's accountRoleId
     * @param accountRoleId
     * @param newAccountRoleId
     * @return Customer
     */
    @Transactional
    public Customer updateCustomer(int accountRoleId, int newAccountRoleId) {
        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        customer.setAccountRoleId(newAccountRoleId);
        customerRepository.save(customer);
        return customer;
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
}
