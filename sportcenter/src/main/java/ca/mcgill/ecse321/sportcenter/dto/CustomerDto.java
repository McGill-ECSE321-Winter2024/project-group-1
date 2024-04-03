package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.controller.AccountManagementController;
import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.model.*;
import java.util.*;

public class CustomerDto {
    private int accountRoleId;
    private AccountDto account;
    CustomerRepository customerRepository;

    public CustomerDto() {
    }

    public CustomerDto(int accountRoleId) {
        this.accountRoleId = accountRoleId;
        this.account = AccountManagementController
                .convertAccountToDto(customerRepository.findCustomerByAccountRoleId(accountRoleId).getAccount());
    }

    public int getAccountRoleId() {
        return accountRoleId;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccountRoleId(int accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    public static CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(customer.getAccountRoleId());
    }

    public static List<CustomerDto> convertToDto(List<Customer> customers) {
        List<CustomerDto> customerDto = new ArrayList<CustomerDto>(customers.size());
        for (Customer customer : customers) {
            customerDto.add(CustomerDto.convertToDto(customer));
        }
        return customerDto;
    }
}