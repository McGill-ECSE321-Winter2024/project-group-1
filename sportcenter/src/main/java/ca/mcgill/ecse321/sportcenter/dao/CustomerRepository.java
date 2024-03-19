package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Customer;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub) and Patrick Zakaria
 * dao and CRUD for Customer
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    
    public Customer findAccountRoleByAccountRoleId(int accountRoleId); //Account role is parent class. Primary Key accountRoleId

    public Customer findCustomerByUserName(String username); //Username is unique

}
