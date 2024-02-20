package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Owner;

/*
 * @author Anslean Albert Jeyaras (GumballGB on GitHub)
 * dao and CRUD for Owner
 */
public interface OwnerRepository extends CrudRepository<Owner, Integer>{
 
    public Owner findAccountRoleByaccountRoleId(Integer accountRoleId); //Account role is parent class. Primary Key accountRoleId

}
