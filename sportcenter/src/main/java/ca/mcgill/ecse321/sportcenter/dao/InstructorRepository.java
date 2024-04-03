package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Instructor;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub) and Patrick Zakaria
 *         dao and CRUD for Instructor
 */
public interface InstructorRepository extends CrudRepository<Instructor, Integer> {

    public Instructor findInstructorByAccountRoleId(int accountRoleId); // Account role is parent class. Primary Key
                                                                        // accountRoleId

    public Instructor findInstructorByAccountUsername(String username); // Instructor is a child class of AccountRole.
                                                                        // Username
    // is a field in AccountRole

}