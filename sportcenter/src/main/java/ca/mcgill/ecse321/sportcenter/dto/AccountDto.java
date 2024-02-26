package ca.mcgill.ecse321.sportcenter.dto;

public class AccountDto {
    public enum AccountStatus{ACTIVE, INACTIVE}
    private int accountRoleId;
    private String email;
    private String password;
    private AccountStatus accountStatus;
    
}