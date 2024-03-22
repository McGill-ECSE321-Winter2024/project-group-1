package ca.mcgill.ecse321.sportcenter.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub) and Patrick Zakaria
 *         dao and CRUD for ScheduledActivity
 */
public interface ScheduledActivityRepository extends CrudRepository<ScheduledActivity, Integer> {

    public ScheduledActivity findScheduledActivityByScheduledActivityId(int scheduledActivityId); // scheduledActivityId
                                                                                                  // is the Primary Key

    public List<ScheduledActivity> findScheduledActivityByDate(LocalDate date); // date is the date of the scheduled
                                                                                // activity

    public List<ScheduledActivity> findScheduledActivityBySupervisorAccountRoleId(int accountRoleId); // supervisorId is
                                                                                                      // the
    // instructorId
}
