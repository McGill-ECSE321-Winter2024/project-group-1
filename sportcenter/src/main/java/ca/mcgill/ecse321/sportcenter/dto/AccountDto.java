package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.*;
import java.util.*;
import java.util.stream.Collectors;

import org.glassfish.jaxb.core.annotation.OverrideAnnotationOf;

public class AccountDto {
    public enum AccountStatus{ACTIVE, INACTIVE}
    private int accountRoleId;
    private String username;
    private String password;
    private AccountStatus accountStatus;

    public AccountDto(int accountRoleId, String username, String password, AccountStatus accountStatus) {
        this.accountRoleId = accountRoleId;
        this.username = username;
        this.password = password;
        this.accountStatus = accountStatus;
    }

    public static AccountDto convertAccountDto(Account account) {
        AccountDto accountDto = null;

        if (account instanceof Customer) {
            Customer customer = (Customer) account;
            accountDto = CustomerDto.convertCustomerDto(customer);
        } else if (account instanceof Instructor) {
            Instructor instructor = (Instructor) account;
            accountDto = InstructorDto.convertInstructorDto(instructor);
        } else if (account instanceof Manager) {
            Manager manager = (Manager) account;
            accountDto = ManagerDto.convertManagerDto(manager);
        }

        
        return accountDto;
    }

    public static List<AccountDto> convertAccountDto(List<Account> accounts) {
        return accounts.stream().map(x -> convertAccountDto(x)).collect(Collectors.toList());
    }

    public int getAccountRoleId() {
        return accountRoleId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountRoleId(int accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof AccountDto)){
            return false;
        }
        AccountDto accountDto = (AccountDto) obj;
        if(accountDto.getAccountRoleId() != this.accountRoleId){//if the accountRoleId is different then the accounts are different sp return false
            return false;
        }
        return true;
    }


}
