package ca.mcgill.ecse321.sportcenter.dto;

/**
 * Data transfer object for the Account entity
 * 
 * @Author Andrew Nemr
 */
public class AccountDto {

    private int accountId;
    private String username;
    private String password;

    public AccountDto(int accountId, String username, String password) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
