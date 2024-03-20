package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class TestSchedueledActivityRepository {
    @Autowired
    private SchedueledActivityRepository schedueledActivityRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        schedueledActivityRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadSchedueledActivity() {
        String name = "testName";
        String email = "testEmail";
        String phoneNumber = "testPhoneNumber";
        SchedueledActivity schedueledActivity = new SchedueledActivity();
        schedueledActivity.setName(name);
        schedueledActivity.setEmail(email);
        schedueledActivity.setPhoneNumber(phoneNumber);
        schedueledActivityRepository.save(schedueledActivity);

        schedueledActivity = schedueledActivityRepository.findSchedueledActivityByName(name);
        assertNotNull(schedueledActivity);
        assertEquals(name, schedueledActivity.getName());
        assertEquals(email, schedueledActivity.getEmail());
        assertEquals(phoneNumber, schedueledActivity.getPhoneNumber());
    }

    @Test
    public void testPersistAndLoadSchedueledActivityDto() {
        String name = "testName";
        String email = "testEmail";
        String phoneNumber = "testPhoneNumber";
        SchedueledActivity schedueledActivity = new SchedueledActivity();
        schedueledActivity.setName(name);
        schedueledActivity.setEmail(email);
        schedueledActivity.setPhoneNumber(phoneNumber);
        schedueledActivityRepository.save(schedueledActivity);

        SchedueledActivityDto schedueledActivityDto = new SchedueledActivityDto(name, email, phoneNumber);
        schedueledActivityDto.setName(name);
        schedueledActivityDto.setEmail(email);
        schedueledActivityDto.setPhoneNumber(phoneNumber);
    }
}
