package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class OwnerDto {
    private int accountRoleId;

    public OwnerDto(int accountRoleId) {
        this.accountRoleId = accountRoleId;
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

}