package ca.mcgill.ecse321.sportcenter.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.sportcenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportcenter.dto.InstructorDto;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.service.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    //CLASSIC CRUD SERVICES

    /**
     * Create an intructor
     * @param username
     * @return InstructorDto
     * @author Anslean AJ
     */
     @PostMapping(value = {"getInstructor/{instructorRoleId}", "getInstructor/{accountRoleId}"})
     public ResponseEntity<?> createInstructor(@RequestParam("username") String username) {

        try {
            Instructor instructor = instructorService.createInstructor(username);
            return ResponseEntity.ok(InstructorDto.convertToDto(instructor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //something went bad, get request
        }
     }

     /**
      * Get a instructor by its accountRoleId
        * @param accountRoleId
        * @return instructorDto
      */
    @GetMapping(value = {"/getInstructor/{accountRoleId}", "/getInstructor/{accountRoleId}/"})
    public ResponseEntity<?> getInstructor(@PathVariable("accountRoleId") int accountRoleId) {
        try {
            Instructor instructor = instructorService.getInstructor(accountRoleId);
            return ResponseEntity.ok(InstructorDto.convertToDto(instructor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //something went bad, get request
        }
    }


    /**
      * Delete a instructor by its accountRoleId
      * @param accountRoleId
      * @return
    */
    @DeleteMapping(value = {"/deleteInstructor/{accountRoleId}", "/deleteInstructor/{accountRoleId}/"})
    public ResponseEntity<?> deleteInstructor(@PathVariable("accountRoleId") int accountRoleId) {
        try {
            instructorService.deleteInstructor(accountRoleId);
            return ResponseEntity.ok("Instructor deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //something went bad, get request
        }
    }


    /**
     * Update a instructor's username
     * @param accountRoleId
     * @param username
     * @return InstructorDto
     */
    @PutMapping(value = {"/updateInstructor/{accountRoleId}", "/updateInstructor/{accountRoleId}/"})
    public ResponseEntity<?> updateInstructor(@PathVariable("accountRoleId") int accountRoleId, @RequestParam("username") String username, @RequestParam("newUsername") String newUsername) {
        try {
            Instructor instructor = instructorService.updateInstructorUsername(accountRoleId, username, newUsername);
            return ResponseEntity.ok(InstructorDto.convertToDto(instructor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
     }

    //MORE CONTROLLERS

    /**
     * Get all instructors
     * @return List<InstructorDto>
     */
    @GetMapping(value = {"/getAllInstructors", "/getAllInstructors/"})
    public ResponseEntity<?> getAllInstructors() {
        try {
            List<Instructor> instructors = instructorService.getAllInstructors();
            return ResponseEntity.ok(InstructorDto.convertToDto(instructors));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //something went bad, get request
        }
    }

    /**
    * Get a instructor by its username
    * @param username
    * @return InstructorDto
    */
    @GetMapping(value = {"/getInstructor{username}", "/getInstructor/{username}/"})
    public ResponseEntity<?> getInstructorByUsername(@PathVariable("username") String username) {
        try {
            Instructor instructor = instructorService.getInstructorByUsername(username);
            return ResponseEntity.ok(InstructorDto.convertToDto(instructor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
    * Delete all instructors
    * @return 
    */
    @DeleteMapping(value = {"/deleteAllInstructors", "/deleteAllInstructors/"})
    public ResponseEntity<?> deleteAllInstructors() {
        try {
            instructorService.deleteAllInstructors();
            return ResponseEntity.ok("Instructors deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }









        





     










    
}
