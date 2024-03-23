package ca.mcgill.ecse321.sportcenter.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Account;

@SpringBootTest
public class TestAccountCustomerDto {
    @Autowired
    private CustomerRepository repo;

    @Autowired
    private AccountRepository accountRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
        accountRepo.deleteAll();
    }

    @Test
    public void createAndReadCustomerAccount() {
        // Create an Account
        String username = "username";
        String password = "password";
        Account account = new Account(username, password);
        account = accountRepo.save(account);
        Customer customer = new Customer(account);

        // Save the Account in the database
        customer = repo.save(customer);
        int accountRoleId = customer.getAccountRoleId();
        int accountId = customer.getAccount().getAccountId();

        // Load the Account from the database
        Customer customerFromDatabase = repo.findCustomerByAccountRoleId(accountRoleId);

        // Check the attributes
        assertNotNull(customerFromDatabase);
        assertEquals(accountRoleId, customerFromDatabase.getAccountRoleId());
        assertEquals(accountId, customerFromDatabase.getAccount().getAccountId());
        assertEquals(username, customerFromDatabase.getAccount().getUsername());
        assertEquals(password, customerFromDatabase.getAccount().getPassword());
    }

}
