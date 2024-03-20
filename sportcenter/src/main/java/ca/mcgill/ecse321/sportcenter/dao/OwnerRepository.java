package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Owner;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub) and Patrick Zakaria
 *         dao and CRUD for Owner
 */
public interface OwnerRepository extends CrudRepository<Owner, Integer> {

    public Owner findAccountRoleByAccountRoleId(int accountRoleId); // Account role is parent class. Primary Key
                                                                    // accountRoleId

    public Owner findOwnerByAccountRoleId(int accountRoleId); // Account role is parent class. Primary Key accountRoleId

}
