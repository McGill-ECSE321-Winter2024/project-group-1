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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

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

    private static final Integer INSTRUCTOR_KEY = Integer.valueOf(1);

    @BeforeEach
    public void setMockOutput() {
        //check if the instructor exists. ID = 1
        lenient()
            .when(instructorRepository.findById(INSTRUCTOR_KEY))
            .thenAnswer(
                
                (InvocationOnMock invocation) -> {
                    
                    if(invocation.getArgument(0).equals(INSTRUCTOR_KEY)) {
                        
                        Instructor instructor = new Instructor();
                        instructor.setAccountRoleId(INSTRUCTOR_KEY);
                        return instructor;
                    }
                    else {
                        return null;
                    }
                }
            );
            // Whenever anything is saved, just return the parameter object
		    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
        lenient().when(instructorRepository.save(any(Instructor.class))).thenAnswer(returnParameterAsAnswer);
        
    }

    @Test
    public void testCreateInstructor() { //testing the creating of an instructor

        assertEquals(0, service.getAllInstructors().size()); //checks if no instructors

        String username = "Dwayne";
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

    }




    
}
