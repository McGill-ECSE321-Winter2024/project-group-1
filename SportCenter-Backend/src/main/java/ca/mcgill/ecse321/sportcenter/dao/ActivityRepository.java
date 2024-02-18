package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;

public interface ActivityRepository extends CrudRepository<Activity, String>{
    
    public Activity findActivity(String name); //name is the PK

}
