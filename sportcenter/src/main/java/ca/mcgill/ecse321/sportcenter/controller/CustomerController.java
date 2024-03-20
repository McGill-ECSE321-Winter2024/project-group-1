package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.service.CustomerService;
import ca.mcgill.ecse321.sportcenter.dto.CustomerDto;
//import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * Create a customer
     * 
     * @param username
     * @return CustomerDto
     * @Author Andrew Nemr
     */

    @PostMapping(value = { "/createCustomer", "/createCustomer/" })
    public CustomerDto createCustomer(@RequestParam("username") String username) throws IllegalArgumentException {
        Customer customer = customerService.createCustomer(username);
        return convertToDto(customer);
    }

    /**
     * Get a customer by its accountRoleId
     * 
     * @param accountRoleId
     * @return CustomerDto
     */
    @GetMapping(value = { "/getCustomer/{accountRoleId}", "/getCustomer/{accountRoleId}/" })
    public CustomerDto getCustomer(@PathVariable("accountRoleId") int accountRoleId) throws IllegalArgumentException {
        Customer customer = customerService.getCustomer(accountRoleId);
        return convertToDto(customer);
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
     * Get all customers
     * 
     * @return List<CustomerDto>
     */
    @GetMapping(value = { "/getAllCustomers", "/getAllCustomers/" })
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return convertToDto(customers);
    }

    /**
     * Get a customer by its username
     * 
     * @param username
     * @return CustomerDto
     */
    @GetMapping(value = { "/getCustomer{username}", "/getCustomer/{username}/" })
    public CustomerDto getCustomer(@PathVariable("username") String username) throws IllegalArgumentException {
        Customer customer = customerService.getCustomerByUsername(username);
        return convertToDto(customer);
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
     * Get customers attending a scheduled activity
     * 
     * @param scheduledActivity
     * @return List<CustomerDto>
     */
    /**
     * check what to do to make this work ????????????????????????????????
     * 
     * @GetMapping(value =
     *                   {"/getCustomersAttendingScheduledActivity/{scheduledActivity}",
     *                   "/getCustomersAttendingScheduledActivity/{scheduledActivity}/"})
     *                   public ResponseEntity<?>
     *                   getCustomersAttendingScheduledActivity(@PathVariable("scheduledActivity")
     *                   ScheduledActivity scheduledActivity) {
     *                   try {
     *                   List<Customer> customers =
     *                   customerService.getCustomersAttendingScheduledActivity(scheduledActivity);
     *                   return
     *                   ResponseEntity.ok(CustomerDto.convertToDto(customers));
     *                   } catch (IllegalArgumentException e) {
     *                   return ResponseEntity.badRequest().body(e.getMessage());
     *                   }
     *                   }
     */

    public static CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(customer.getAccountRoleId());
    }

    public static List<CustomerDto> convertToDto(List<Customer> customers) {
        List<CustomerDto> customerDto = new ArrayList<CustomerDto>(customers.size());
        for (Customer customer : customers) {
            customerDto.add(convertToDto(customer));
        }
        return customerDto;
    }

}
