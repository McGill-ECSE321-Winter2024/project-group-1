package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;


public interface AccountRepository extends CrudRepository<Account, Integer> {

    public Account findAccount(Integer iD); //iD is the PM

}