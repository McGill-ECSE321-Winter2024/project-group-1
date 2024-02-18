package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.AccountRole;


public interface AccountRoleRepository extends CrudRepository<AccountRole, Integer> {

    public AccountRole findAccountRole(Integer iD); //iD is the PK

}