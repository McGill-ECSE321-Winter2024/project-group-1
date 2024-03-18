package ca.mcgill.ecse321.sportcenter.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import static org.mockito.Mockito.times;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;

import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Account;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestInstructorService {

    //mocking a database thanks to Mockito
    @Mock private ActivityRepository activityRepository;
    @Mock private AccountRepository accountRepository;
    @Mock private OwnerRepository ownerRepository;
    @Mock private InstructorRepository instructorRepository;
    @Mock private CustomerRepository customerRepository;
    @Mock private RegistrationRepository registrationRepository;
    @Mock private ScheduledActivityRepository scheduledActivityRepository;

    @InjectMocks private InstructorService service;

    @Test
    public void testCreateInstructor() {

        when(instructorRepository.save(any(Instructor.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        final String username = "gumball";
        Instructor instructor = null;
        Account account = null;
        
        try {
            instructor = service.createInstructor(username);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        //now check if an account was actually created

        try {
           account = account.getWithUsername(username);
        } catch (IllegalArgumentException e) {
            fail(); //account was not found!
        }

        assertNotNull(instructor);
        assertEquals(username, account.getUsername());

        //check if all was added
        verify(instructorRepository, times(1)).save(instructor);

    }

    @Test
    public void testCreateInstructorNull() { //make an empty username

        when(instructorRepository.save(any(Instructor.class))).thenThrow(IllegalArgumentException.class);

        //this system should not allow null usernames right??
        String username = null;
        String error = null;

        Instructor instructor = null;

        try {
            instructor = service.createInstructor(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        } 

        assertNull(instructor);
        //check error
        assertEquals("Instructor name can not be empty!", error); //error won't happen

        verify(instructorRepository, never()).save(any(Instructor.class));
 
    }

    @Test
    public void testCreateInstructorSpaces() { //make an empty username

        when(instructorRepository.save(any(Instructor.class))).thenThrow(IllegalArgumentException.class);

        //this system should not allow null usernames right??
        String username = " ";
        String error = null;

        Instructor instructor = null;

        try {
            instructor = service.createInstructor(username);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        } 

        assertNull(instructor);
        //check error
        assertEquals("Instructor name can not be empty!", error); //error won't happen


        verify(instructorRepository, never()).save(any(Instructor.class));
 
    }

    //TEST THE REST.





    
}
