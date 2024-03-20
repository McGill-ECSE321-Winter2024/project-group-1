package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class OwnerDto extends AccountRoleDto {
    private int accountRoleId;

    public OwnerDto(int accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public OwnerDto(String name, String email, String phoneNumber) {
        // TODO Auto-generated constructor stub
    }

    public int getAccountRoleId() {
        return accountRoleId;
    }

    public void setAccountRoleId(int accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public static OwnerDto convertToDto(Owner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("There is no owner!");
        }
        return new OwnerDto(owner.getAccountRoleId());
    }

    // SETTERS
    public boolean setUserame(String name) {
        boolean wasSet = false;
        name = aName;
        wasSet = true;
        return wasSet;
    }

    public boolean setPhoneNumber(String phoneNumber) {
        boolean wasSet = false;
        phoneNumber = aPhoneNumber;
        wasSet = true;
        return wasSet;
    }

    public boolean setEmail(String email) {
        boolean wasSet = false;
        email = aEmail;
        wasSet = true;
        return wasSet;
    }

    // GETTERS
    public String getUsername() {
        return username;
    }

    public static Account getWithUsername(String aUsername) {
        return accountsByUsername.get(aUsername);
    }

    public static boolean hasWithUsername(String aUsername) {
        return getWithUsername(aUsername) != null;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountId() {
        return accountId;
    }
}