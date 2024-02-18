package ca.mcgill.ecse321.sportcenter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Instructor;

public interface InstructorRepository extends CrudRepository<Instructor, Integer> {

    public Instructor findAccountRole(Integer iD);

}