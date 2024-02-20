package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub)
 * dao and CRUD for Account
 */
public interface AccountRepository extends CrudRepository<Account, Integer>{
    
    public Account findAccountByaccountId(Integer accoundId); //accoundId is the Primary Key

}
