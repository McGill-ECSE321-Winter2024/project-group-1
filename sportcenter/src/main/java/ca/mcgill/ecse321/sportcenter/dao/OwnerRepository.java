package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Integer>{
 
    public Owner findAccount(Integer iD);

}
