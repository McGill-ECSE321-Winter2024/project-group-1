package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.controller.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.controller.CustomerDto;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Registration;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub) and Patrick Zakaria
 * dao and CRUD for Registration
 */
public interface RegistrationRepository extends CrudRepository<Registration, Integer>{

    public Registration findRegistrationByRegId(int regId); //regId is the Primary Key

    public Registration findRegistrationByCustomerAndScheduledActivity(Customer customer, ScheduledActivity scheduledActivity);

    public Registration findBySchuledActivity(ScheduledActivity scheduledActivity);

    public Registration findByCustomer(Customer customer);
} 