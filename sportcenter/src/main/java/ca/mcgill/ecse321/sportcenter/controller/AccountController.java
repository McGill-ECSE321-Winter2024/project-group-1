package ca.mcgill.ecse321.sportcenter.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.AccountDto;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.service.AccountService;

/**
 * Controller class for the Account entity
 * 
 * @Author Andrew Nemr
 */
@CrossOrigin(origins = "*")
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * Create an account
     * 
     * @param username
     * @param password
     * @return AccountDto
     */
    @PostMapping(value = { "/createAccount", "/createAccount/" })
    public AccountDto createAccount(@PathVariable("username") String username,
            @PathVariable("password") String password) throws IllegalArgumentException {
        Account account = accountService.createAccount(username, password);
        return convertAccountDto(account);
    }

    /**
     * Update an account
     * 
     * @param username
     * @param password
     * @return AccountDto
     */
    @PutMapping(value = { "/account/update/{oldUsername}/{newUsername}/{newPassword}",
            "/account/update/{oldUsername}/{newUsername}/{newPassword}/" })
    public AccountDto updateAccount(@PathVariable("oldUsername") String oldUsername,
            @PathVariable("newUsername") String newUsername, @PathVariable("newPassword") String newPassword)
            throws IllegalArgumentException {
        Account account = accountService.updateAccount(oldUsername, newUsername, newPassword);
        return convertAccountDto(account);
    }

    /**
     * Get an account by its account ID
     * 
     * @param accountId
     * @return AccountDto
     */
    @GetMapping(value = { "/account/{accountId}", "/account/{accountId}/" })
    public AccountDto getAccountById(@PathVariable("accountId") int accountId) throws IllegalArgumentException {
        Account account = accountService.getAccountById(accountId);
        return convertAccountDto(account);
    }

    /**
     * Get an account by its username
     * 
     * @param username
     * @return AccountDto
     */
    @GetMapping(value = { "/account/{username}", "/account/{username}/" })
    public AccountDto findAccountByUsername(@PathVariable("username") String username) throws IllegalArgumentException {
        Account account = accountService.getAccountByUsername(username);
        return convertAccountDto(account);
    }

    /**
     * Get all accounts
     * 
     * @return List<AccountDto>
     */
    @GetMapping(value = { "/accounts/login/getAll", "/accounts/login/getAll/" })
    public List<AccountDto> getAllAccounts() throws IllegalArgumentException {
        List<Account> accounts = accountService.getAllAccounts();
        return convertAccountsToDto(accounts);
    }

    /**
     * Delete an account by its account ID
     * 
     * @param accountId
     */
    @DeleteMapping(value = { "/account/delete/{accountId}", "/account/delete/{accountId}/" })
    public void deleteAccount(@PathVariable("accountId") int accountId) throws IllegalArgumentException {
        accountService.deleteAccount(accountId);
    }

    /**
     * Delete all accounts
     */
    @DeleteMapping(value = { "/accounts/login/deleteAll", "/accounts/login/deleteAll/" })
    public void deleteAllAccounts() throws IllegalArgumentException {
        accountService.deleteAllAccounts();
    }

    /**
     * Login
     * 
     * @param username
     * @param password
     * 
     * @return boolean
     */
    @PostMapping(value = { "/login", "/login/" })
    public boolean login(@RequestParam("username") String username,
            @RequestParam("password") String password) throws IllegalArgumentException {
        return accountService.login(username, password);
    }

    /**
     * Convert an account to an account DTO
     * 
     * @param account
     * @return
     */
    private AccountDto convertAccountDto(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("There is no account to convert");
        }
        return new AccountDto(account.getUsername(), account.getPassword());
    }

    /**
     * Convert a list of accounts to a list of account DTOs
     * 
     * @param accounts
     * @return
     */
    private List<AccountDto> convertAccountsToDto(List<Account> accounts) {
        List<AccountDto> accountDtos = new ArrayList<AccountDto>(accounts.size());

        for (Account account : accounts) {
            accountDtos.add(new AccountDto(account.getUsername(), account.getPassword()));
        }
        return accountDtos;
    }
}
