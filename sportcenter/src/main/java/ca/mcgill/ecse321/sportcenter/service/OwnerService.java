package ca.mcgill.ecse321.sportcenter.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.AccountRole;

import ca.mcgill.ecse321.sportcenter.model.Activity;

public class OwnerService {
    @Autowired
    OwnerRepository ownerRepository;

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
     */
    @Transactional
    public Owner getOwnerByAccountRoleId(int accountRoleId) {
        return ownerRepository.findAccountRoleByAccountRoleId(accountRoleId);
    }

    /**
    * Get owner by accountRoleId
    * @return
    */
    @Transactional
    public List<Owner> getOwners() {
        return toList(ownerRepository.findAll());
    }

    /**
     * Check if accountRoleId is an owner
     * @param accountRoleId
     * @return
     */
    @Transactional
    public Owner checkAccountOwner(int accountRoleId) {
        AccountRole role = ownerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (role instanceof Owner) {//check if role is an instance of Owner
            throw new IllegalArgumentException("AccountRole does not exist!");
        }
        return (Owner) role;

        
    }

}
