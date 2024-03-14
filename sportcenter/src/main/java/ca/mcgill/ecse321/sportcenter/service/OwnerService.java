package ca.mcgill.ecse321.sportcenter.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.model.Owner;

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
     * 
     */
    public static Owner convertOwnerDto(Owner owner) {
        if (owner == null){
            throw new IllegalArgumentException("There is no owner to convert");
        }
        return new Owner(owner.getOwnerName(), owner.getOwnerEmail(), owner.getOwnerPassword());
    }

    public static List<Owner> convertOwnerDto(List<Owner> owners) {
        List<Owner> ownerDto = new ArrayList<Owner>(owners.size());

        for (Owner owner : owners) {
            ownerDto.add(OwnerService.convertOwnerDto(owner));
        }
        return ownerDto;
    }
    
}
