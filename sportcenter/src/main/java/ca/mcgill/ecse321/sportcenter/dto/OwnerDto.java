package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.*;
import java.util.*;

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
        return new OwnerDto(owner.getAccountRoleId());
    }

    public static List<OwnerDto> convertToDto(List<Owner> owners) {
        List<OwnerDto> ownerDto = new ArrayList<OwnerDto>(owners.size());
        for (Owner owner : owners) {
            ownerDto.add(OwnerDto.convertToDto(owner));
        }
        return ownerDto;
    }
}