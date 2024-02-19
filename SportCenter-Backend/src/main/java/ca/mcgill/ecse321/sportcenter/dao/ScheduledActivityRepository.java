package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

public interface ScheduledActivityRepository extends CrudRepository<ScheduledActivity, Integer>{

    public ScheduledActivity findActivity(Integer scheduledActivityId); //iD is the PM
    
}
