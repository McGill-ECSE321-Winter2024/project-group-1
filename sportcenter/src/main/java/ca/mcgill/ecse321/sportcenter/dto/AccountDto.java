package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class AccountDto {
    public enum AccountStatus{ACTIVE, INACTIVE}
    private int accountId;
    private String username;
    private String password;

    public AccountDto(int accountId, String username, String password) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
    }

    public static AccountDto convertAccountDto(Account account) {
        return new AccountDto(account.getAccountId(), account.getUsername(), account.getPassword());
    }
    public static List<AccountDto> convertAccountDto(List<Account> accounts) {
        return accounts.stream().map(x -> convertAccountDto(x)).collect(Collectors.toList());
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

    public void setAccountId(int accountRoleId) {
        this.accountId = accountRoleId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof AccountDto)){
            return false;
        }
        AccountDto accountDto = (AccountDto) obj;
        if(accountDto.getAccountId() != this.accountId){//if the accountRoleId is different then the accounts are different sp return false
            return false;
        }
        return true;
    }


}
