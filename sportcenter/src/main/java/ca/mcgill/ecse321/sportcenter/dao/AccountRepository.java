package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Account;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub) and Patrick Zakaria
 * dao and CRUD for Account
 */
public interface AccountRepository extends CrudRepository<Account, Integer>{
    
    public Account findAccountByAccountId(int accoundId); //accoundId is the Primary Key
    
    public Account findAccountByUsername(String username);//check with TA if we want an accountId or username to be the primary key

}