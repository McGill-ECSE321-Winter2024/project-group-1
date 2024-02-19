package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;

/*
 * @author Anslean Albert Jeyaras (GumballGB on GitHub)
 * dao and CRUD for SportCenter. Empty since there is 1 sport center
 */
public interface SportCenterRepository extends CrudRepository<SportCenter, Integer>{
    
    
}
