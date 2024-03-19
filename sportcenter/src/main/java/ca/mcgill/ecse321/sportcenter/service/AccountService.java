package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * Create an account
     * @param username
     * @param password
     * @return Account
     * @author Andrew Nemr
     */
    @Transactional
    public Account createAccount(String username, String password) {

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);
        return account;
    }
    /**
     * Get an account by its account ID (primary key)
     * @param accountId
     * @return Account
     * 
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
     * Validate an account's username and password
     * @param username
     * @param password
     */

   public void validateAccount(String username, String password) {

        Account account = accountRepository.findAccountByUsername(username);
        if (account != null) {
            throw new IllegalArgumentException("Username already exists!");
        }
        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("Password is incorrect");
        }
        String error = "";
        if (username == null || username.trim().length() == 0) {
            error = error + "Username cannot be empty! ";
        }
        if (password == null || password.trim().length() == 0) {
            error = error + "Password cannot be empty! ";
        }
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
    }
    /**
     * Get all accounts
     * @return List<Account>
     * 
     * @Author Andrew Nemr
     */
    @Transactional
    public List<Account> getAllAccounts() {
        return toList(accountRepository.findAll());
    }

    /**
     * Delete an account by its account ID (primary key)
     * @param accountId
     */
    @Transactional
    public void deleteAccount(int accountId) {
        accountRepository.deleteById(accountId);
    }
    /**
     * Update an account's username and password
     * @param accountId
     * @param username
     * @param password
     */
    @Transactional
    public Account updateAccount(String oldUsername, String username, String password) {
        Account account = accountRepository.findAccountByUsername(oldUsername);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        validateAccount(username, password);
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);
        return account;
    }
    /**
     * Get an account by its username
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
     * Delete all accounts
     * @return void
     */
    @Transactional
    public void deleteAllAccounts() {
        accountRepository.deleteAll();
    }

    // check group 14 of winter 2022 good ideas for checking for stuff

    //check for email uniqueness
    public boolean checkUsernameUniqueness(String username) {
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    //check for password correctness
    public boolean checkPasswordCorrectness(String username, String password) {
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                if (account.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    //check for account existence
    public boolean checkAccountExistence(String username) {
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    //check all input parameters for account creation
    public boolean checkAccountCreation(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            return false;
        }
        if (password == null || password.trim().length() == 0) {
            return false;
        }
        return true;
    }

}