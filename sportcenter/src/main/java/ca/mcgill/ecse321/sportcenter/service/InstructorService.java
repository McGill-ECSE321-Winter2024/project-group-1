package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;

public class InstructorService {
    
    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    AccountRepository accountRepository;


    /**
     * Get an instructor by its accountRole Id (primary key)
     * @param accountRoleId
     * @return Instructor
     * 
     */
    @Transactional
    public Instructor getInstructor(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }
        return instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);
    }

    @Transactional
    public Instructor createInstructor(String username) {
        Instructor instructor = new Instructor();
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }
        instructor.setAccount(account);
        instructorRepository.save(instructor);
        return instructor;
    }

    public List<Instructor> getInstructors() {
        return toList(instructorRepository.findAll());
    }

    private List<Instructor> toList(Iterable<Instructor> iterable) {
        List<Instructor> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    /**
     * Delete a instructor by its accountRoleId (primary key)
     * @param accountRoleId
     */
    @Transactional
    public void deleteInstructor(int accountRoleId) {
        instructorRepository.deleteById(accountRoleId);
    }



    /**
     * delete instructor by username
     * @param username
     * @return
     */
    @Transactional
    public void deleteInstructorByUsername(String username) {
        Instructor instructor = new Instructor();
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }
        instructor.setAccount(account);
        instructorRepository.delete(instructor);
    }

    /**
     * Get a instructor by its accountRoleId (primary key)
     * @param accountRoleId
     * @return Instructor
     */
    @Transactional
    public Instructor getInstructorByRoleId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }
        return instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);
    }


    /**
    * Get all instructors
    * @return List<Instructor>
    */
    @Transactional
    public List<Instructor> getAllInstructors() {
        return toList(instructorRepository.findAll());
    }



    /**
     * Get a instructor by its account username
     * @param username
     * @return Instructor
     */
    @Transactional
    public Instructor getInstructorByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }
        return instructorRepository.findAccountRoleByAccountRoleId(account.getAccountId());
    }

    /**
     * Update an instructor username (copied off, so unclear) TO BE REVIEWED
     * @param accountRoleId
     * @param username
     * @return Instructor
     */
    @Transactional
    public Instructor updateInstructor(int accountRoleId, String username) {
        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);
        Account account = accountRepository.findAccountByUsername(username);
        if (account != null) {
            throw new IllegalArgumentException("Account does not exist!");
        }
        account.setUsername(username);
        accountRepository.save(account);
        instructor.setAccount(account);
        return instructor;
    }

    /**
     * Delete all instructors
     * @return void
     */
    @Transactional
    public void deleteAllInstructors() {
        instructorRepository.deleteAll();
    }

    
}
