package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Customer;
//import ca.mcgill.ecse321.sportcenter.model.User;


@SpringBootTest
public class TestCustomerPersistence {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired

    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCustomer() {
        
        Customer customer = new Customer();
        int accountId = 123;
        String username = "John";
        String password = "password";


        
        customer.setAccountId(accountId);
        customer.setUsername(username);
        customer.setPassword(password);
        customerRepository.save(customer);

        customer = null;
        Customer foundCustomer = customerRepository.findAccount(accountId);

        assertNotNull(foundCustomer);
        assertEquals(accountId, customer.getAccountId());
        assertEquals(username, customer.getUsername());
        assertEquals(password, customer.getPassword());
    }
}

