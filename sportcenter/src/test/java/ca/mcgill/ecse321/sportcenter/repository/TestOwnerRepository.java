package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Customer;

/**
 * @author Mathias Pacheco Lemina
 */
@SpringBootTest
public class TestOwnerRepository {
    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        ownerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadOwner() {
        String name = "testName";
        String email = "testEmail";
        String phoneNumber = "testPhoneNumber";
        Owner owner = new Owner();
        owner.setName(name);
        owner.setEmail(email);
        owner.setPhoneNumber(phoneNumber);
        ownerRepository.save(owner);

        owner = ownerRepository.findOwnerByName(name);
        assertNotNull(owner);
        assertEquals(name, owner.getName());
        assertEquals(email, owner.getEmail());
        assertEquals(phoneNumber, owner.getPhoneNumber());
    }

    @Test
    public void testPersistAndLoadOwnerDto() {
        String name = "testName";
        String email = "testEmail";
        String phoneNumber = "testPhoneNumber";
        Owner owner = new Owner();
        owner.setName(name);
        owner.setEmail(email);
        owner.setPhoneNumber(phoneNumber);
        ownerRepository.save(owner);

        OwnerDto ownerDto = new OwnerDto(name, email, phoneNumber);
        ownerDto.setName(name);
        ownerDto.setEmail(email);
        ownerDto.setPhoneNumber(phoneNumber);
    }
}
