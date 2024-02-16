package ca.mcgill.ecse321.sportcenter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Instructor;

@SpringBootTest
public class TestInstructorPersistence {
    @Autowired
    private InstructorRepository instructorRepository;

    @AfterEach
    public void clearDatabase() {
        instructorRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadInstructor() {
        
        Instructor instructor = new Instructor();
        String name = "John";
        String email = "john@gmail.com";
        String password = "password";
        
        instructor.setName(name);
        instructor.setEmail(email);
        instructor.setPassword(password);
        instructorRepository.save(instructor);

        instructor = null;
        instructor = instructorRepository.findInstructorByEmail(email);//could be by id

        assertNotNull(instructor);
        assertEquals(name, instructor.getName());
        assertEquals(email, instructor.getEmail());
        assertEquals(password, instructor.getPassword());
    }
}
