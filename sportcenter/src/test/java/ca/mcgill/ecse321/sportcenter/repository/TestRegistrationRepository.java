package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRegistrationRepository {
    @Autowired
    private RegistrationRepository registrationRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        registrationRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadRegistration() {
        String name = "testName";
        String email = "testEmail";
        String phoneNumber = "testPhoneNumber";
        Registration registration = new Registration();
        registration.setName(name);
        registration.setEmail(email);
        registration.setPhoneNumber(phoneNumber);
        registrationRepository.save(registration);

        registration = registrationRepository.findRegistrationByName(name);
        assertNotNull(registration);
        assertEquals(name, registration.getName());
        assertEquals(email, registration.getEmail());
        assertEquals(phoneNumber, registration.getPhoneNumber());
    }

    @Test
    public void testPersistAndLoadRegistrationDto() {
        String name = "testName";
        String email = "testEmail";
        String phoneNumber = "testPhoneNumber";
        Registration registration = new Registration();
        registration.setName(name);
        registration.setEmail(email);
        registration.setPhoneNumber(phoneNumber);
        registrationRepository.save(registration);

        RegistrationDto registrationDto = new RegistrationDto(name, email, phoneNumber);
        registrationDto.setName(name);
        registrationDto.setEmail(email);
        registrationDto.setPhoneNumber(phoneNumber);
    }
}
