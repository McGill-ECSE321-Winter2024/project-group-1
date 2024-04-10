package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;

public class InstructorDto {

    private int accountRoleId;
    private InstructorStatus status;
    private String description;
    private String profilePicURL;
    private AccountDto account;

    public InstructorDto(Instructor instructor) {
        this.accountRoleId = instructor.getAccountRoleId();
        this.status = instructor.getStatus();
        this.description = instructor.getDescription();
        this.profilePicURL = instructor.getProfilePicURL();
        this.account = new AccountDto(instructor.getAccount());
    }

    public InstructorDto(int accountRoleId, InstructorStatus status, String description, String profilePicURL,
            AccountDto account) {
        this.accountRoleId = accountRoleId;
        this.status = status;
        this.description = description;
        this.profilePicURL = profilePicURL;
        this.account = account;
    }

    // Getters
    public int getAccountRoleId() {
        return accountRoleId;
    }

    public InstructorStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public AccountDto getAccount() {
        return account;
    }

    // Setters

    public void setAccountRoleId(int accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public void setStatus(InstructorStatus status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

}