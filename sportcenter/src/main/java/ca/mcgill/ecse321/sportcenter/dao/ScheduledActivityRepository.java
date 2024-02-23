package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub) and Patrick Zakaria
 * dao and CRUD for ScheduledActivity
 */
public interface ScheduledActivityRepository extends CrudRepository<ScheduledActivity, Integer>{

    public ScheduledActivity findScheduledActivityByScheduledActivityId(int scheduledActivityId); //scheduledActivityId is the Primary Key
    
}
