package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{
    
    public User findAccount(Integer userId); //iD is the PM

}
