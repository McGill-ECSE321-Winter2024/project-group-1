package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.controller.ScheduledController;
import ca.mcgill.ecse321.sportcenter.controller.CustomerController;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub) and Patrick Zakaria
 * dao and CRUD for Registration
 */
public interface RegistrationRepository extends CrudRepository<Registration, Integer>{

    public Registration findRegistrationByRegId(int regId); //regId is the Primary Key

    //by emilie; to review

    public Registration findRegistrationByCustomerAndScheduledActivity(Customer customer, ScheduledActivity scheduledActivity);

    public Registration findBySchuledActivity(ScheduledActivity scheduledActivity);

    public Registration findByCustomer(Customer customer);
} 