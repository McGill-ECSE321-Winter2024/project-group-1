package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Account;


@SpringBootTest
public class TestCustomerPersistence {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCustomer() {

        Account account = new Account();
        int accountRoleId = 123;
        String username = "John";
        String password = "password";
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);
        
        Customer customer = new Customer();
        customer.setAccount(account);
        customerRepository.save(customer);

        customer = null;
        Customer foundCustomer = customerRepository.findAccountRole(accountRoleId);



        assertNotNull(foundCustomer);
        assertEquals(username, foundCustomer.getAccount().getUsername());
        assertEquals(password, foundCustomer.getAccount().getPassword());
        assertEquals(accountRoleId, foundCustomer.getAccountRoleId());
    }
}
