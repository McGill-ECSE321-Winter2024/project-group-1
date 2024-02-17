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
    private UserRepository userRepository;

    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCustomer() {

        User user = new User();
        int accountId = 123;
        String username = "John";
        String password = "password";

        Customer customer = new Customer();
        customer.setUser(user);

        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);

        customerRepository.save(customer);

        customer = null;
        Customer foundCustomer = customerRepository.findAccount(accountId);



        assertNotNull(foundCustomer);
        assertEquals(username, foundCustomer.getUser().getUsername());
        assertEquals(password, foundCustomer.getUser().getPassword());
        assertEquals(accountId, foundCustomer.getAccountId());
    }
}

