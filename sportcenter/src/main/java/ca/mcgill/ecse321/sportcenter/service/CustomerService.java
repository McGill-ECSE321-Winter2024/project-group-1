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

    public List<Customer> getCustomers() {
        return toList(customerRepository.findAll());
    }

    /**
     * Converts an Iterable to a List.
     * 
     * @param iterable the Iterable to convert
     * @return a List containing the elements of the Iterable
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
    /**
     * Get a customer by its accountRole Id (primary key)
     * @param accountRoleId
     * @return Customer
     * 
     */

    @Transactional
    public Customer getCustomer(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }
        return customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
    }
}
