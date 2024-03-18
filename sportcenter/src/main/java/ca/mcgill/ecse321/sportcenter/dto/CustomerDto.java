package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerDto{
    private int accountRoleId;

    public CustomerDto(int accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public int getAccountRoleId() {
        return accountRoleId;
    }

    public void setAccountRoleId(int accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public static CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(customer.getAccountRoleId());
    }

    public static List<CustomerDto> convertToDto(List<Customer> customers) {
        List <CustomerDto> customerDto = new ArrayList<CustomerDto>(customers.size());
        for (Customer customer : customers) {
            customerDto.add(CustomerDto.convertToDto(customer));
        }
        return customerDto;
        }
}