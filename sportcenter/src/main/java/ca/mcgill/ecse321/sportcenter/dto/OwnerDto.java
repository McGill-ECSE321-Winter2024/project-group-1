package ca.mcgill.ecse321.sportcenter.dto;

public class OwnerDto {
    private AccountDto account;

    public OwnerDto() {
    }

    public OwnerDto(AccountDto aAccount) {
        this.account = aAccount;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto aAccount) {
        this.account = aAccount;
    }

}