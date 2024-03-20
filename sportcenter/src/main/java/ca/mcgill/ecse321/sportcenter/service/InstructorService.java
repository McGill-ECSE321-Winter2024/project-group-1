package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.AccountRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
//import ca.mcgill.ecse321.sportcenter.dto.InstructorDto.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Instructor.InstructorStatus;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.AccountRole;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Instructor service is responsible for CRUD services. Handles the business
 * logic.
 */
public class InstructorService {

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    AccountRepository accountRepository;

    /**
     * Get an instructor by its accountRole Id (primary key).
     * 
     * @param accountRoleId
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public Instructor getInstructor(int accountRoleId) {

        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }

        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);

        if (instructor == null) {
            return null;
        }

        return instructor;
    }

    /**
     * Creates an instructor and account if the username is valid.
     * 
     * @param username
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public Instructor createInstructor(String username, String password, InstructorStatus status, String description,
            String profilePicURL) {

        // Check if the username is null or consists only of whitespace characters
        if (username == null || username.trim().isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot be null, empty and spaces!");
        }

        // Check if the password is null or consists only of whitespace characters
        if (password == null || password.trim().isEmpty() || password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot be null, empty and spaces!");
        }

        // Check if the description is null or consists only of whitespace characters
        if (description == null || description.trim().isEmpty() || description.contains(" ")) {
            throw new IllegalArgumentException("Description cannot be null, empty and spaces!");
        }

        // Check if the profilePicURL is null or consists only of whitespace characters
        if (profilePicURL == null || profilePicURL.trim().isEmpty() || profilePicURL.contains(" ")) {
            throw new IllegalArgumentException("ProfilePic URL cannot be null, empty and spaces!");
        }

        // check if the account already exists
        Account verifyAccount = accountRepository.findAccountByUsername(username);
        if (verifyAccount != null) {
            throw new IllegalArgumentException("Account already exists!");
        }

        // All checks are made now.

        Account account = new Account(username, password);

        Instructor instructor = new Instructor(InstructorStatus.Active, description, profilePicURL, account);

        instructor.setAccount(account);

        instructorRepository.save(instructor);

        return instructor;
    }

    /**
     * Get a instructor by its accountRoleId (primary key)
     * 
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
     * Returns the list of all intructors in the repository.
     * toList is required since .findAll() will return an Iterable.
     * 
     * @return List of Instructor
     * @author Anslean AJ
     */
    public List<Instructor> getInstructors() {

        return toList(instructorRepository.findAll());
    }

    /**
     * Returns the list of all intructors in the repository.
     * 
     * @return List of Instructor
     * @author Anslean AJ
     */
    private List<Instructor> toList(Iterable<Instructor> iterable) {

        List<Instructor> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    /**
     * Delete a instructor by its accountRoleId (primary key). If Id is valid.
     * 
     * @param accountRoleId
     * @author Anslean AJ
     */
    @Transactional
    public void deleteInstructor(int accountRoleId) {

        Instructor instructor = null;

        instructor = instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);

        if (instructor == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        instructorRepository.deleteById(accountRoleId);
    }

    /**
     * delete instructor by username
     * 
     * @param username
     * @author Anslean AJ
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
     * Get all instructors
     * 
     * @return List of Instructor
     * @author Anslean AJ
     */
    @Transactional
    public List<Instructor> getAllInstructors() {

        return toList(instructorRepository.findAll());

    }

    /**
     * Get a instructor by its account username
     * 
     * @param username
     * @return Instructor
     * @author Anslean AJ
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
     * This method allows to update the instructor's username.
     * 
     * @param accountRoleId
     * @param username
     * @param newUsername
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public void updateInstructorUsername(int accountRoleId, String username, String newUsername) {

        // Check if the username is null or consists only of whitespace characters
        if (newUsername == null || newUsername.trim().isEmpty() || newUsername.contains(" ")) {
            throw new IllegalArgumentException("New username cannot be null, empty and spaces!");
        }

        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        account.setUsername(newUsername);
        instructor.setAccount(account);

        accountRepository.save(account);
    }

    /**
     * This method allows to update the instructor's username.
     * 
     * @param accountRoleId
     * @param username
     * @param newUsername
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public void updateInstructorPassword(int accountRoleId, String password, String newPassword) {

        // Check if the username is null or consists only of whitespace characters
        if (newPassword == null || newPassword.trim().isEmpty() || newPassword.contains(" ")) {
            throw new IllegalArgumentException("New password cannot be null, empty and spaces!");
        }

        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);
        Account account = accountRepository.findAccountByUsername(password);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        account.setUsername(newPassword);
        accountRepository.save(account);
        instructor.setAccount(account);
    }

    /**
     * This method allows to update the instructor's description.
     * 
     * @param accountRoleId
     * @param username
     * @param description
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public void updateInstructorDescription(int accountRoleId, String username, String description) {

        // Check if the username is null or consists only of whitespace characters
        if (description == null || description.trim().isEmpty() || description.contains(" ")) {
            throw new IllegalArgumentException("Description cannot be null, empty and spaces!");
        }

        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        // find the instructor and update it
        instructor.setDescription(description);
        accountRepository.save(account);
        instructor.setAccount(account);
    }

    /**
     * This method allows to update the instructor's description.
     * 
     * @param accountRoleId
     * @param username
     * @param picture
     * @return Instructor
     * @author Anslean AJ
     */
    @Transactional
    public void updateInstructorProfilePic(int accountRoleId, String username, String picture) {

        // Check if the username is null or consists only of whitespace characters
        if (picture == null || picture.trim().isEmpty() || picture.contains(" ")) {
            throw new IllegalArgumentException("Profile pictures cannot be null, empty and spaces!");
        }

        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist!");
        }

        // find the instructor and update it
        instructor.setProfilePicURL(picture);
        accountRepository.save(account);
        instructor.setAccount(account);
    }

    /**
     * Delete all instructors
     * 
     * @return void
     * @author Anslean AJ
     */
    @Transactional
    public void deleteAllInstructors() {

        instructorRepository.deleteAll();

    }

    /**
     * Check if account is an instructor
     * 
     * @param accountRoleId
     * @return boolean
     */
    @Transactional
    public boolean checkAccountInstructor(int accountRoleId) {
        AccountRole role = instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);
        return role instanceof Instructor;
    }

    // UNIQUE METHODS

    /**
     * Propose an acitvity to the owner.
     * 
     * @return Activity
     * @author Anslean AJ
     */
    @Transactional
    public Activity proposeActivity(String name, String description, ClassCategory subcategory) {

        ActivityService activityService = new ActivityService();

        try {

            return activityService.createActivity(name, description, subcategory);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid inputs!");
        }
    }

    /**
     * Make a new scheduled activity to the system.
     * 
     * @return ScheduledActivity
     * @author Anslean AJ
     */
    @Transactional
    public ScheduledActivity makeScheduledActivity(LocalDate date, LocalDate startTime, LocalTime endTime,
            Instructor instructor, Activity activity, int capacity) {

        ScheduledActivityService scheduledActivityService = new ScheduledActivityService();

        try {

            return scheduledActivityService.createScheduledActivity(date, endTime, endTime, instructor, activity,
                    capacity);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid inputs!");
        }

    }

}