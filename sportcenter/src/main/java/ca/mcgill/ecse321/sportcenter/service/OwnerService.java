package ca.mcgill.ecse321.sportcenter.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.dao.ActivityRepository;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.AccountRole;

import ca.mcgill.ecse321.sportcenter.model.Activity;

public class OwnerService {
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    ActivityRepository activityRepository;

    /**
     * Get the owner
     * @param <T>
     * @param iterable
     * @return
     */
    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * Get owner by accountRoleId
     * @param accountRoleId
     * @return
     * @author Andrew Nemr
     */
    @Transactional
    public Owner getOwnerByAccountRoleId(int accountRoleId) {
        return ownerRepository.findAccountRoleByAccountRoleId(accountRoleId);
    }


    /**
     * Check if accountRoleId is an owner
     * @param accountRoleId
     * @return
     */
    @Transactional
    public boolean checkAccountOwner(int accountRoleId) {
        AccountRole role = ownerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        return role instanceof Owner;
    }

    /**
     * Set isApproved of activity to true
     * @param activityName
     */
    @Transactional
    public void approveActivity(String activityName) {
        Activity activity = activityRepository.findActivityByName(activityName);
        if (activity == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        }
        activity.setIsApproved(true);
        activityRepository.save(activity);
    }

    /**
     * Set isApproved of activity to false
     * @param activityName
     */
    @Transactional
    public void disapproveActivity(String activityName) {
        Activity activity = activityRepository.findActivityByName(activityName);
        if (activity == null) {
            throw new IllegalArgumentException("Activity does not exist!");
        }
        activity.setIsApproved(false);
        activityRepository.save(activity);
    }
    
}
