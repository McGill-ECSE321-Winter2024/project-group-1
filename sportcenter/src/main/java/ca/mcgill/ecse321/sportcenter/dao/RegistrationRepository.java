package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Registration;

/*
 * @author Anslean Albert Jeyaras (GumballGB on GitHub)
 * dao and CRUD for Registration
 */
public interface RegistrationRepository extends CrudRepository<Registration, Integer>{

    public Registration findRegistrationByRegId(Integer regId); //regId is the Primary Key
} 