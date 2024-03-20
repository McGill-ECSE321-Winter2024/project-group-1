package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.AccountRole;
import ca.mcgill.ecse321.sportcenter.model.Instructor;

import ca.mcgill.ecse321.sportcenter.model.Activity;

public class OwnerService {
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    InstructorRepository instructorRepository;

    /**
     * Get the owner
     * 
     * @param <T>
     * @param iterable
     * @return
     * @author Mathias Pacheco Lemina
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * Get owner by accountRoleId
     * 
     * @param accountRoleId
     * @return
     * @author Mathias Pacheco Lemina
     */
    @Transactional
    public Owner getOwnerByAccountRoleId(int accountRoleId) {
        if (accountRoleId <= 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be empty!");
        } else if (ownerRepository.findAccountRoleByAccountRoleId(accountRoleId) == null) {
            throw new IllegalArgumentException("Owner does not exist!");
        } else {
            return ownerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        }
    }

    /**
     * Check if accountRoleId is an owner
     * 
     * @param accountRoleId
     * @return
     * @author Mathias Pacheco Lemina
     */
    @Transactional
    public boolean checkAccountOwner(int accountRoleId) {
        if (accountRoleId <= 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be empty!");
        } else if (ownerRepository.findAccountRoleByAccountRoleId(accountRoleId) == null) {
            throw new IllegalArgumentException("Owner does not exist!");
        } else {
            AccountRole role = ownerRepository.findAccountRoleByAccountRoleId(accountRoleId);
            return role instanceof Owner;
        }
    }

    /**
     * Set isApproved of activity to true
     * 
     * @param activityName
     * @author Mathias Pacheco Lemina
     */
    @Transactional
    public void approveActivity(String activityName) {
        Activity activity = activityRepository.findActivityByName(activityName);

        if (activityName == "" || activityName == null) {
            throw new IllegalArgumentException("Activity name cannot be empty!");
        } else if (activity == null) {
            System.out.println("Activity does not exist!");
        } else if (activity.getIsApproved()) {
            throw new IllegalArgumentException("Activity is already approved!");
        } else {
            activity.setIsApproved(true);
            activityRepository.save(activity);
        }
    }

    /**
     * Set isApproved of activity to false
     * 
     * @param activityName
     * @author Mathias Pacheco Lemina
     */
    @Transactional
    public void disapproveActivity(String activityName) {
        Activity activity = activityRepository.findActivityByName(activityName);

        if (activityName.trim() == "" || activityName == null) {
            throw new IllegalArgumentException("Activity name cannot be empty!");
        } else if (activityRepository.findActivityByName(activityName) == null) {
            System.out.println("Activity does not exist!");
        } else if (!activity.getIsApproved()) {
            throw new IllegalArgumentException("Activity is already not approved!");
        } else {
            activity.setIsApproved(false);
            activityRepository.save(activity);
        }
    }

    /*
     * Approve instructor based on the id given
     * 
     * @author Mathias Pacheco Lemina
     */
    @Transactional
    public boolean approveInstructor(int id) {
        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(id);

        if (id <= 0) {
            throw new IllegalArgumentException("Instructor id cannot be empty!");
        } else if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        } else if (instructor.getStatus() == Instructor.InstructorStatus.Active) {
            throw new IllegalArgumentException("Instructor is already approved!");
        } else {
            boolean success = instructor.setStatus(Instructor.InstructorStatus.Active);
            instructorRepository.save(instructor);
            return success;
        }
    }

    // @Transactional
    // public void approveInstructor(String username) {
    // Account account = accountRepository.findAccountByUsername(username);
    // }

    /*
     * Disapprove instructor based on the id given
     * 
     * @author Mathias Pacheco Lemina
     */
    @Transactional
    public boolean disapproveInstructor(int id, Instructor.InstructorStatus status) {
        Instructor instructor = instructorRepository.findAccountRoleByAccountRoleId(id);

        if (id <= 0) {
            throw new IllegalArgumentException("Instructor id cannot be empty!");
        } else if (instructor == null) {
            throw new IllegalArgumentException("Instructor does not exist!");
        } else if (instructor.getStatus() == Instructor.InstructorStatus.Inactive
                && status == Instructor.InstructorStatus.Inactive) {
            throw new IllegalArgumentException("Instructor is already not approved!");
        } else if (instructor.getStatus() == Instructor.InstructorStatus.Fired
                && status == Instructor.InstructorStatus.Fired) {
            throw new IllegalArgumentException("Instructor is already fired!");
        } else if (instructor.getStatus() == Instructor.InstructorStatus.Suspended
                && status == Instructor.InstructorStatus.Suspended) {
            throw new IllegalArgumentException("Instructor is already suspended!");
        } else {
            boolean success = instructor.setStatus(status);
            instructorRepository.save(instructor);
            return success;
        }
    }

    // @Transactional
    // public void disapproveInstructor(String username) {
    // Account account = accountRepository.findAccountByUsername(username);
    // }
}
