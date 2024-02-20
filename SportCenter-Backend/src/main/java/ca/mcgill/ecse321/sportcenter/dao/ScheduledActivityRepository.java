package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

/*
 * @author Anslean Albert Jeyaras (GumballGB on GitHub)
 * dao and CRUD for ScheduledActivity
 */
public interface ScheduledActivityRepository extends CrudRepository<ScheduledActivity, Integer>{

    public ScheduledActivity findScheduledActivityByscheduledActivityId(Integer scheduledActivityId); //scheduledActivityId is the Primary Key
    
}
