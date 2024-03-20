package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;

@SpringBootTest
public class TestInstructorRepository {
    @Autowired
    private InstructorRepository instructorRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        instructorRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadInstructor() {
        String name = "testName";
        String email = "testEmail";
        String phoneNumber = "testPhoneNumber";
        Instructor instructor = new Instructor();
        instructor.setName(name);
        instructor.setEmail(email);
        instructor.setPhoneNumber(phoneNumber);
        instructorRepository.save(instructor);

        instructor = instructorRepository.findInstructorByName(name);
        assertNotNull(instructor);
        assertEquals(name, instructor.getName());
        assertEquals(email, instructor.getEmail());
        assertEquals(phoneNumber, instructor.getPhoneNumber());
    }

    @Test
    public void testPersistAndLoadInstructorDto() {
        String name = "testName";
        String email = "testEmail";
        String phoneNumber = "testPhoneNumber";
        Instructor instructor = new Instructor();
        instructor.setName(name);
        instructor.setEmail(email);
        instructor.setPhoneNumber(phoneNumber);
        instructorRepository.save(instructor);

        InstructorDto instructorDto = new InstructorDto(name, email, phoneNumber);
        instructorDto.setName(name);
        instructorDto.setEmail(email);
        instructorDto.setPhoneNumber(phoneNumber);
    }
}
