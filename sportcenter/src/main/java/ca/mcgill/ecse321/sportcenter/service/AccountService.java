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
    /**
     * Get an account by its account ID (primary key)
     * @param accountId
     * @return Account
     * 
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

    @Transactional
    public Account getAccountById(int accountId) {
        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        return account;
    }

    private void validateAccount(String username, String password) {
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

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
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
    public Account updateAccount(int accountId, String username, String password) {
        Account account = accountRepository.findAccountByAccountId(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        validateAccount(username, password);
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);
        return account;
    }
}