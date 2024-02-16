package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Customer;

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
        String name = "John";
        String email = "john@gmail.com";
        String password = "password";

        
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        customerRepository.save(customer);

        customer = null;
        customer = customerRepository.findCustomerByEmail(email);//could be by id

        assertNotNull(customer);
        assertEquals(name, customer.getName());
        assertEquals(email, customer.getEmail());
        assertEquals(password, customer.getPassword());
    }
}

