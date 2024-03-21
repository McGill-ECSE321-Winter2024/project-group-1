package ca.mcgill.ecse321.sportcenter.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;

public class TestSchedueledActivityIntegration {
    @Autowired
    private TestRestTemplate client; // Always stays the same

    @Autowired
    private ScheduledActivityRepository scheduledActivityRepository; // TODO: Make sure it's the good type

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        scheduledActivityRepository.deleteAll(); // TODO: Make sure it's the good type
    }

    // TODO: Insert your constants here

    @Test
    @Order(1)
    public void testABC() {
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
