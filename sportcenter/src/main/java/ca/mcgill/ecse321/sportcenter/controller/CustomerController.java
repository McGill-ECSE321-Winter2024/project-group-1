package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.service.CustomerService;
import ca.mcgill.ecse321.sportcenter.dto.AccountDto;
import ca.mcgill.ecse321.sportcenter.dto.CustomerDto;

/**
 * Rest controller for the Customer entity
 * 
 * @Author Andrew Nemr
 */
@CrossOrigin(origins = "*")
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * Create a customer from accountId
     * 
     * @param accountId
     * @return CustomerDto
     */
    @PostMapping(value = { "/createCustomer/{accountId}", "/createCustomer/{accountId}/" })
    public CustomerDto createCustomer(@PathVariable("accountId") int accountId) throws IllegalArgumentException {
        Customer customer = customerService.createCustomer(accountId);
        return convertCustomerDto(customer);
    }

    /**
     * Create a customer from account username
     * 
     * @param username
     * @return CustomerDto
     */
    @PostMapping(value = { "/createCustomer", "/createCustomer/" })
    public CustomerDto createCustomer(@RequestParam("username") String username) throws IllegalArgumentException {
        Customer customer = customerService.createCustomer(username);
        return convertCustomerDto(customer);
    }

    /**
     * Get a customer by its accountRoleId
     * 
     * @param accountRoleId
     * @return CustomerDto
     */
    @GetMapping(value = { "/getCustomer/{accountRoleId}", "/getCustomer/{accountRoleId}/" })
    public CustomerDto getCustomer(@PathVariable("accountRoleId") int accountRoleId) throws IllegalArgumentException {
        Customer customer = customerService.getCustomerByAccountRoleId(accountRoleId);
        return convertCustomerDto(customer);
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
        Customer customer = customerService.getCustomerByAccountId(accountId);
        return convertCustomerDto(customer);
    }

    /**
     * Get a customer by its username
     * 
     * @param username
     * @return CustomerDto
     */
    @GetMapping(value = { "/getCustomer/{username}", "/getCustomer/{username}/" })
    public CustomerDto getCustomer(@PathVariable("username") String username) throws IllegalArgumentException {
        Customer customer = customerService.getCustomerByUsername(username);
        return convertCustomerDto(customer);
    }

    /**
     * Get all customers
     * 
     * @return List<CustomerDto>
     */
    @GetMapping(value = { "/getAllCustomers", "/getAllCustomers/" })
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return convertCustomersDto(customers);
    }

    /**
     * Get account by accountRoleId
     * 
     * @param accountRoleId
     * @return AccountDto
     */
    @GetMapping(value = { "/getAccount/{accountRoleId}", "/getAccount/{accountRoleId}/" })
    public AccountDto getAccount(@PathVariable("accountRoleId") int accountRoleId) throws IllegalArgumentException {
        Customer customer = customerService.getCustomerByAccountRoleId(accountRoleId);
        Account account = customer.getAccount();
        return AccountController.convertAccountDto(account);
    }

    /**
     * Update a customer account with a new accountId
     * 
     * @param accountRoleId
     * @return CustomerDto
     */
    @PostMapping(value = { "/updateCustomer/{accountRoleId}", "/updateCustomer/{accountRoleId}/" })
    public CustomerDto updateCustomer(@PathVariable("accountRoleId") int accountRoleId,
            @RequestParam("newAccountId") int newAccountId) throws IllegalArgumentException {
        Customer customer = customerService.updateCustomer(accountRoleId, newAccountId);
        return convertCustomerDto(customer);
    }

    /**
     * Update a customer account with a new username
     * 
     * @param accountRoleId
     * @return CustomerDto
     */
    @PostMapping(value = { "/updateCustomer/{accountRoleId}", "/updateCustomer/{accountRoleId}/" })
    public CustomerDto updateCustomer(@PathVariable("accountRoleId") int accountRoleId,
            @RequestParam("newUsername") String newUsername) throws IllegalArgumentException {
        Customer customer = customerService.updateCustomer(accountRoleId, newUsername);
        return convertCustomerDto(customer);
    }

    /**
     * Delete a customer by its accountRoleId
     * 
     * @param accountRoleId
     * @return
     */
    @DeleteMapping(value = { "/deleteCustomer/{accountRoleId}", "/deleteCustomer/{accountRoleId}/" })
    public void deleteCustomer(@PathVariable("accountRoleId") int accountRoleId)
            throws IllegalArgumentException {
        customerService.deleteCustomerByAccountRoleId(accountRoleId);
    }

    /**
     * Delete a customer by its accountId
     * 
     * @param username
     * @return CustomerDto
     */
    @DeleteMapping(value = { "/deleteCustomerByAccountId/{accountId}", "/deleteCustomerByAccountId/{accountId}/" })
    public void deleteCustomerByAccountId(@PathVariable("accountId") int accountId) throws IllegalArgumentException {
        customerService.deleteCustomerByAccountId(accountId);
    }

    /**
     * Delete a customer by its username
     * 
     * @param username
     * @return
     */
    @DeleteMapping(value = { "/deleteCustomer/{username}", "/deleteCustomer/{username}/" })
    public void deleteCustomer(@PathVariable("username") String username) throws IllegalArgumentException {
        customerService.deleteCustomerByUsername(username);
    }

    /**
     * Delete all customers
     * 
     * @return
     */
    @DeleteMapping(value = { "/deleteAllCustomers", "/deleteAllCustomers/" })
    public void deleteAllCustomers() {
        customerService.deleteAllCustomers();
    }

    /**
     * Check if account is a customer
     * 
     * @param accountRoleId
     * @return boolean
     */
    @GetMapping(value = { "/isCustomer/{accountRoleId}", "/isCustomer/{accountRoleId}/" })
    public boolean isCustomer(@PathVariable("accountRoleId") int accountRoleId) {
        return customerService.isCustomer(accountRoleId);
    }

    /**
     * Convert a Customer entity to a CustomerDto
     * 
     * @param customer
     * @return CustomerDto
     */
    public static CustomerDto convertCustomerDto(Customer customer) {
        return new CustomerDto(customer.getAccountRoleId());
    }

    /**
     * Convert a list of Customer entities to a list of CustomerDtos
     * 
     * @param customers
     * @return List<CustomerDto>
     */
    public static List<CustomerDto> convertCustomersDto(List<Customer> customers) {
        List<CustomerDto> customerDto = new ArrayList<CustomerDto>(customers.size());
        for (Customer customer : customers) {
            customerDto.add(convertCustomerDto(customer));
        }
        return customerDto;
    }

}
