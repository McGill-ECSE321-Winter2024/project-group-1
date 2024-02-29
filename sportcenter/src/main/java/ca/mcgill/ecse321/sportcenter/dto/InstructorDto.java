package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.*;
import java.util.*;
import java.util.stream.Collectors;



public class InstructorDto {
    public enum InstructorStatus { Active, Inactive, Fired, Suspended }
    private int accountRoleId;
    private InstructorStatus status;
    private String description;
    private String profilePicURL;
    private AccountDto account;

    public InstructorDto(int accountRoleId, InstructorStatus status, String description, String profilePicURL, AccountDto account) {
        this.accountRoleId = accountRoleId;
        this.status = status;
        this.description = description;
        this.profilePicURL = profilePicURL;
        this.account = account;
    }

    
}