package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;

public interface SportCenterRepository extends CrudRepository<SportCenter, Integer>{
    
    
}
