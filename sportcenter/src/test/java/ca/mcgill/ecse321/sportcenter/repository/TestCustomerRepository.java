package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportcenter.model.Customer;

@SpringBootTest
public class TestCustomerRepository {
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCustomer() {
        String name = "testName";
        String email = "testEmail";
        String phoneNumber = "testPhoneNumber";
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customerRepository.save(customer);

        customer = customerRepository.findCustomerByName(name);
        assertNotNull(customer);
        assertEquals(name, customer.getName());
        assertEquals(email, customer.getEmail());
        assertEquals(phoneNumber, customer.getPhoneNumber());
    }

    @Test
    public void testPersistAndLoadCustomerDto() {
        String name = "testName";
        String email = "testEmail";
        String phoneNumber = "testPhoneNumber";
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customerRepository.save(customer);

        CustomerDto customerDto = new CustomerDto(name, email, phoneNumber);
        customerDto.setName(name);
        customerDto.setEmail(email);
        customerDto.setPhoneNumber(phoneNumber);
    }
}
