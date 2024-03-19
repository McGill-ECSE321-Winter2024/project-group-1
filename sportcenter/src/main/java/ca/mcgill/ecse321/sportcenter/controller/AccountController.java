package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


public class AccountController {
    @Autowired
    private AccountService accountService;
    
    /**
     * Create an account
     * @param username
     * @param password
     * @return
     * @Author Andrew Nemr
     */
    @PostMapping(value = {"/createAccount", "/createAccount/"})
    public ResponseEntity<?> createAccount(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            Account account = accountService.createAccount(username, password);
            return ResponseEntity.ok(AccountDto.convertAccountDto(account));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Update an account
     * @param username
     * @param password
     * @return
     */

    @PutMapping(value = {"/account/update/{oldUsername}/{newUsername}/{newPassword}", "/account/update/{oldUsername}/{newUsername}/{newPassword}/"})
    public ResponseEntity<?> updateAccount(@PathVariable("oldUsername") String oldUsername, @PathVariable("newUsername") String newUsername, @PathVariable("newPassword") String newPassword) {
        try {
            Account account = accountService.updateAccount(oldUsername, newUsername, newPassword);
            return ResponseEntity.ok(AccountDto.convertAccountDto(account));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get an account by its account ID
     * @param accountId
     * @return
     */
    @GetMapping(value = {"/account/{accountId}", "/account/{accountId}/"})
    public ResponseEntity<?> getAccountById(@PathVariable("accountId") int accountId) {
        try {
            Account account = accountService.getAccountById(accountId);
            return ResponseEntity.ok(AccountDto.convertAccountDto(account));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get an account by its username
     * @param username
     * @return
     */
    @GetMapping(value = {"/account/{username}", "/account/{username}/"})
    public ResponseEntity<?> findAccountByUsername(@PathVariable("username") String username) {
        try {
            Account account = accountService.getAccountByUsername(username);
            return ResponseEntity.ok(AccountDto.convertAccountDto(account));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get all accounts
     * @return
     */
    @GetMapping(value = {"/accounts/login/getAll", "/accounts/login/getAll/"})
    public ResponseEntity<?> getAllAccounts() {
        try {
            List<Account> accounts = accountService.getAllAccounts();
            return ResponseEntity.ok(AccountDto.convertAccountDto(accounts));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete an account by its account ID
     * @param accountId
     * @return
     */
    @DeleteMapping(value = {"/account/delete/{accountId}", "/account/delete/{accountId}/"})
    public ResponseEntity<?> deleteAccount(@PathVariable("accountId") int accountId) {
        try {
            accountService.deleteAccount(accountId);
            return ResponseEntity.ok("Account deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete all accounts
     * @return
     */
    @DeleteMapping(value = {"/accounts/login/deleteAll", "/accounts/login/deleteAll/"})
    public ResponseEntity<?> deleteAllAccounts() {
        try {
            accountService.deleteAllAccounts();
            return ResponseEntity.ok("All accounts deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Login
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = {"/login", "/login/"})
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            boolean login = accountService.login(username, password);
            return ResponseEntity.ok(login);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
