package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.*;
import java.util.*;

public class CustomerDto {
    private int accountRoleId;
    private AccountDto account;

    public CustomerDto() {
    }

    public CustomerDto(Customer customer) {
        this.accountRoleId = customer.getAccountRoleId();
        this.account = new AccountDto(customer.getAccount());
    }

    public int getAccountRoleId() {
        return accountRoleId;
    }

    public AccountDto getAccount() {
        return account;
    }

    public static List<CustomerDto> convertToDto(List<Customer> customers) {
        List<CustomerDto> customerDto = new ArrayList<CustomerDto>(customers.size());
        for (Customer customer : customers) {
            customerDto.add(new CustomerDto(customer));
        }
        return customerDto;
    }
}