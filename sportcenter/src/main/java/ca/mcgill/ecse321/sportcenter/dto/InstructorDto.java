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

    public InstructorDto(int accountRoleId) {
        this.accountRoleId = accountRoleId;
    }


    public int getAccountRoleId() {
        return accountRoleId;
    }

    public void setAccountRoleId(int accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    
    public static InstructorDto convertToDto(Instructor instructor) {
        return new InstructorDto(instructor.getAccountRoleId());
    }

    public static List<InstructorDto> convertToDto(List<Instructor> instructors) {
        List<InstructorDto> instructorDto = new ArrayList<InstructorDto>(instructors.size());
        for (Instructor instructor : instructors) {
            instructorDto.add(InstructorDto.convertToDto(instructor));
        }
        return instructorDto;
    }


    public InstructorDto(int accountRoleId, InstructorStatus status, String description, String profilePicURL, AccountDto account) {
        this.accountRoleId = accountRoleId;
        this.status = status;
        this.description = description;
        this.profilePicURL = profilePicURL;
        this.account = account;
    }



    
}