package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub) and Patrick Zakaria
 * dao and CRUD for Activity
 */
public interface ActivityRepository extends CrudRepository<Activity, String>{
    
    public Activity findActivityByName(String name);
}
