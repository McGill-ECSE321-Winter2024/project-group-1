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
    public ResponseEntity<?> getCustomer(@PathVariable("accountRoleId") int accountRoleId) {
        try {
            Customer customer = customerService.getCustomer(accountRoleId);
            return ResponseEntity.ok(convertToDto(customer));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete a customer by its accountRoleId
     * 
     * @param accountRoleId
     * @return
     */
    @DeleteMapping(value = { "/deleteCustomer/{accountRoleId}", "/deleteCustomer/{accountRoleId}/" })
    public ResponseEntity<?> deleteCustomer(@PathVariable("accountRoleId") int accountRoleId) {
        try {
            customerService.deleteCustomer(accountRoleId);
            return ResponseEntity.ok("Customer deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get all customers
     * 
     * @return List<CustomerDto>
     */
    @GetMapping(value = { "/getAllCustomers", "/getAllCustomers/" })
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return ResponseEntity.ok(convertToDto(customers));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete a customer by its username
     * 
     * @param username
     * @return
     */
    @DeleteMapping(value = { "/deleteCustomer/{username}", "/deleteCustomer/{username}/" })
    public ResponseEntity<?> deleteCustomerByUsername(@PathVariable("username") String username) {
        try {
            customerService.deleteCustomerByUsername(username);
            return ResponseEntity.ok("Customer deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Update a customer's username
     * 
     * @param accountRoleId
     * @param username
     * @return CustomerDto
     */

    @PutMapping(value = { "/updateCustomer/{accountRoleId}", "/updateCustomer/{accountRoleId}/" })
    public ResponseEntity<?> updateCustomer(@PathVariable("accountRoleId") int accountRoleId,
            @RequestParam("username") String username) {
        try {
            Customer customer = customerService.updateCustomer(accountRoleId, username);
            return ResponseEntity.ok(convertToDto(customer));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get a customer by its username
     * 
     * @param username
     * @return CustomerDto
     */
    @GetMapping(value = { "/getCustomer{username}", "/getCustomer/{username}/" })
    public ResponseEntity<?> getCustomerByUsername(@PathVariable("username") String username) {
        try {
            Customer customer = customerService.getCustomerByUsername(username);
            return ResponseEntity.ok(convertToDto(customer));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete all customers
     * 
     * @return
     */
    @DeleteMapping(value = { "/deleteAllCustomers", "/deleteAllCustomers/" })
    public ResponseEntity<?> deleteAllCustomers() {
        try {
            customerService.deleteAllCustomers();
            return ResponseEntity.ok("Customers deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
