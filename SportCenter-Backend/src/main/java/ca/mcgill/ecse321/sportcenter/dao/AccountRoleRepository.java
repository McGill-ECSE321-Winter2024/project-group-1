package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.AccountRole;

/*
 * @author Anslean Albert Jeyaras (GumballGB on GitHub)
 * dao and CRUD for AccountRole
 */
public interface AccountRoleRepository extends CrudRepository<AccountRole, Integer> {

    public AccountRole findAccountRole(Integer iD); //iD is the Primary Key

}