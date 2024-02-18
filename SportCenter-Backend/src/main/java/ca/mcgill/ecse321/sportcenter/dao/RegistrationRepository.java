package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Registration;

public interface RegistrationRepository extends CrudRepository<Registration, Integer>{

    public Registration findAccount(Integer regId); //iD is the PM
} 