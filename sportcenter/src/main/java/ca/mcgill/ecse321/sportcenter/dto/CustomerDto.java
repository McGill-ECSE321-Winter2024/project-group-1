package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerDto{
    private int accountRoleId;

    public CustomerDto(int accountRoleId) {
        this.accountRoleId = accountRoleId;
    }


}