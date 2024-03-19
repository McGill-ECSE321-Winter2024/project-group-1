package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;

/**
 * Service class for the Account entity
 * 
 * @author Andrew Nemr
 */
@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    /**
     * Create an account
     * 
     * @param username
     * @param password
     * @return Account
     */
    @Transactional
    public Account createAccount(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty!");
        }
        if (password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }
        Account account;
        if (accountRepository.findAccountByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists!");
        }
        account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);
        return account;
    }

    /**
     * Get an account by its account ID (primary key)
     * 
     * @param accountId
     * @return Account
     */
    @Transactional
    public Account getAccountById(int accountId) {
        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        return account;
    }

    /**
     * Get an account by its username
     * 
     * @param username
     * @return Account
     */
    @Transactional
    public Account getAccountByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        return account;
    }

    /**
     * Helper function to turn an iterable into a list
     * 
     * @param Iterable<T>
     * @return List<T>
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * Get all accounts
     * 
     * @return List<Account>
     * @author Andrew Nemr
     */
    @Transactional
    public List<Account> getAllAccounts() {
        return toList(accountRepository.findAll());
    }

    /**
     * Update an account's username and password
     * 
     * @param accountId
     * @param newUsername
     * @param newPassword
     * @return Account
     */
    @Transactional
    public Account updateAccount(String oldUsername, String newUsername, String newPassword) {
        if (oldUsername == null || oldUsername.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (newUsername == null || newUsername.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (newPassword == null || newPassword.trim().length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        Account account = accountRepository.findAccountByUsername(oldUsername);
        if (account == null) {
            throw new IllegalArgumentException("Account with the old Username does not exist");
        }
        if (accountRepository.findAccountByUsername(newUsername) != null) {
            throw new IllegalArgumentException("Account with the new Username already exists");
        }
        account.setUsername(newUsername);
        if (!account.getPassword().equals(newPassword)) {
            throw new IllegalArgumentException("Password is incorrect");
        }

        account.setPassword(newPassword);
        accountRepository.save(account);
        return account;
    }

    /**
     * Delete an account by its account ID (primary key)
     * 
     * @param accountId
     */
    @Transactional
    public void deleteAccount(int accountId) {
        accountRepository.deleteById(accountId);
    }

    /**
     * Delete all accounts
     * 
     * @return void
     */
    @Transactional
    public void deleteAllAccounts() {
        accountRepository.deleteAll();
    }

    /**
     * Login function
     * 
     * @param username
     * @param password
     * @return Account
     */
    public boolean login(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        return account.getPassword().equals(password);
    }
}