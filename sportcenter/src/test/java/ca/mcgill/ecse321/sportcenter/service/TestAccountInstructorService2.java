package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestAccountInstructorService2 {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private InstructorRepository instructorRepository;

    // Account AccountId
    private static final int Approved_AccountId = 1;
    private static final int Pending_AccountId = 2;
    private static final int Disaproved_AccountId = 3;

    // AccountRole AccountRoleId
    private static final int Approved_AccountRoleId = 1;
    private static final int Pending_AccountRoleId = 2;
    private static final int Disaproved_AccountRoleId = 3;

    @InjectMocks
    private AccountManagementService accountService;

    @SuppressWarnings("null")
    @BeforeEach
    void setMockOutput() {
        lenient().when(accountRepository.findAccountByAccountId(any(Integer.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(Approved_AccountId)) {
                        Account account = new Account();
                        account.setUsername("approved_username");
                        account.setPassword("password");
                        return account;
                    }
                    if (invocation.getArgument(0).equals(Pending_AccountId)) {
                        Account account = new Account();
                        account.setUsername("pending_username");
                        account.setPassword("password");
                        return account;
                    }
                    if (invocation.getArgument(0).equals(Disaproved_AccountId)) {
                        Account account = new Account();
                        account.setUsername("disapproved_username");
                        account.setPassword("password");
                        return account;
                    } else {
                        return null;
                    }
                });

        lenient().when(instructorRepository.findInstructorByAccountRoleId(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(Approved_AccountRoleId)) {
                        Instructor instructor = new Instructor();
                        instructor.setAccount(accountRepository.findAccountByAccountId(Approved_AccountId));
                        instructor.setDescription("approved_description");
                        instructor.setStatus(InstructorStatus.Active);
                        instructor.setProfilePicURL("profilePicURL");
                        return instructor;
                    }
                    if (invocation.getArgument(0).equals(Pending_AccountRoleId)) {
                        Instructor instructor = new Instructor();
                        instructor.setAccount(accountRepository.findAccountByAccountId(Pending_AccountId));
                        instructor.setDescription("pending_description");
                        instructor.setStatus(InstructorStatus.Pending);
                        instructor.setProfilePicURL("profilePicURL");
                        return instructor;
                    }
                    if (invocation.getArgument(0).equals(Disaproved_AccountRoleId)) {
                        Instructor instructor = new Instructor();
                        instructor.setAccount(accountRepository.findAccountByAccountId(Disaproved_AccountId));
                        instructor.setDescription("disapproved_description");
                        instructor.setStatus(InstructorStatus.Inactive);
                        instructor.setProfilePicURL("profilePicURL");
                        return instructor;
                    } else {
                        return null;
                    }
                });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        when(accountRepository.save(any(Account.class))).thenAnswer(returnParameterAsAnswer);
        when(instructorRepository.save(any(Instructor.class))).thenAnswer(returnParameterAsAnswer);
    }

}
