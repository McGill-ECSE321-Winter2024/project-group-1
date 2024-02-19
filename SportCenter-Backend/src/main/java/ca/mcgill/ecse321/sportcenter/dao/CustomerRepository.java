package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Customer;

/*
 * @author Anslean Albert Jeyaras (GumballGB on GitHub)
 * dao and CRUD for Customer
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    
    public Customer findAccountRole(Integer accountRoleId); //Account role is parent class. Primary Key accountRoleId

}
