package ca.mcgill.ecse321.sportcenter.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.client.TestRestTemplate;

import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestInstructorIntegration {
    // @Autowired
    // private TestRestTemplate client; // Always stays the same

    @Autowired
    private InstructorRepository instructorRepository; // TODO: Make sure it's the good type

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        instructorRepository.deleteAll(); // TODO: Make sure it's the good type
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
