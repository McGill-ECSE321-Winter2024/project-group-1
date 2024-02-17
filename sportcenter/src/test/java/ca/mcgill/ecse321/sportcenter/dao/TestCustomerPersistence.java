package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.User;


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
        User user = new User();
        int accountId = 123;
        String username = "John";
        String password = "password";
        user.setUsername(username);
        user.setPassword(password);
        customer.setAccountId(accountId);
        customerRepository.save(customer);

        customer = null;
        Customer foundCustomer = customerRepository.findAccount(accountId);

        assertNotNull(foundCustomer);
        assertEquals(username, foundCustomer.getUser().getUsername());
        assertEquals(password, foundCustomer.getUser().getPassword());
        assertEquals(accountId, foundCustomer.getAccountId());
    }
}

