package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    
    public Customer findAccount(Integer iD);

}
