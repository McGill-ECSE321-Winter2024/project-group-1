package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.model.Instructor;

public class InstructorService {
    @Autowired
    InstructorRepository instructorRepository;

    public List<Instructor> getInstructors() {
        return toList(instructorRepository.findAll());
    }

    private List<Instructor> toList(Iterable<Instructor> iterable) {
        List<Instructor> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
    /**
     * Get an instructor by its accountRole Id (primary key)
     * @param accountRoleId
     * @return Instructor
     * 
     */
    @Transactional
    public Instructor getInstructor(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("AccountRoleId cannot be negative!");
        }
        return instructorRepository.findAccountRoleByAccountRoleId(accountRoleId);
    }
    
}
