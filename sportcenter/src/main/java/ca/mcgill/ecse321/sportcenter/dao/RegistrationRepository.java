package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.Registration;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub) and Patrick Zakaria
 *         dao and CRUD for Registration
 */
public interface RegistrationRepository extends CrudRepository<Registration, Integer> {

    public Registration findRegistrationByRegId(int regId); // regId is the Primary Key

    public void findAccountRoleByAccountRoleId(int accountRoleId); // accountRoleId is the Foreign Key

    public void findScheduledActivityByScheduledActivityId(int scheduledActivityId); // scheduledActivityId is the
                                                                                     // Foreign Key

}