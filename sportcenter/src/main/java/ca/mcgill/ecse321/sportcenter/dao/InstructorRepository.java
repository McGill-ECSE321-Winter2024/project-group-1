package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Instructor;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub) and Patrick Zakaria
 *         dao and CRUD for Instructor
 */
public interface InstructorRepository extends CrudRepository<Instructor, Integer> {

    public Instructor findAccountRoleByAccountRoleId(int accountRoleId); // Account role is parent class. Primary Key
                                                                         // accountRoleId

}