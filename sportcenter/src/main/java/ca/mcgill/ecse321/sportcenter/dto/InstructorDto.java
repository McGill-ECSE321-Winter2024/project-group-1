package ca.mcgill.ecse321.sportcenter.dto;

public class InstructorDto {
    public enum InstructorStatus {
        Active, Inactive, Fired, Suspended
    }

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

    public InstructorDto(int accountRoleId, InstructorStatus status, String description, String profilePicURL,
            AccountDto account) {
        this.accountRoleId = accountRoleId;
        this.status = status;
        this.description = description;
        this.profilePicURL = profilePicURL;
        this.account = account;
    }

}