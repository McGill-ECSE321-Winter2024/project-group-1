package ca.mcgill.ecse321.sportcenter.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestOwnerIntegration {
    @Autowired
    private TestRestTemplate client; // Always stays the same

    @Autowired
    private OwnerRepository ownerRepository; // TODO: Make sure it's the good type

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        ownerRepository.deleteAll(); // TODO: Make sure it's the good type
    }

    // TODO: Insert your constants here

    @Test
    @Order(1)
    public void testCreateOwner() {
        // Create a new owner
        // Send a POST request to /owners with the owner’s name
        // Use client.postForEntity()
        // Check that the owner was created by sending a GET request to
        // /owners/{ownerId}
        // Use client.getForEntity()
        // Check that the owner’s name is correct
        // Use assertEquals()
    }

    // TODO: Continue the tests here

}
