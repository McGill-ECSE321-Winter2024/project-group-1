package ca.mcgill.ecse321.sportcenter.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.sportcenter.dto.*;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.service.RegistrationService;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    /**
     * Get all registrations
     * 
     * @return
     * @Author Emilie Ruel
     */
    @GetMapping(value = {"/regitrations/getAll", "/registrations/getAll/"})
    public RegistrationDto getAllRegistrations() throws IllegalArgumentException{
        List<Registration> registrations = registrationService.getAllRegistrations();
        return RegistrationDto.convertToDto(registrations);


     /**
     * Get an registration by its id
     * @param registrationId
     * @return
     * @Author Emilie Ruel
     */
    @GetMapping(value = { "/registration/{registrationId}", "/registration/{registrationId}/" })
    public ResponseEntity<?> getRegistrationById(@PathVariable("registrationId") int registrationId) {
        try {
            Registration registration = registrationService.getRegistrationById(registrationId);
            return ResponseEntity.ok(RegistrationDto.convertToDto(registration));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get registrations of customer by accountRoleId
     * 
     * @param registrationId
     * @return
     * @Author Emilie Ruel
     */
    @GetMapping(value = { "/registration/{accountRoleId}", "/registration/{accountRoleId}/" })
    public ResponseEntity<?> getRegistrationByCustomerId(@PathVariable("accountRoleId") int accountRoleId) {
        try {
            List<Registration> registrations = registrationService.getRegistrationByCostumerId(accountRoleId);
            return ResponseEntity.ok(RegistrationDto.convertToDto(registrations));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * get registrations of scheduled activity by its id
     * 
     * @param registrationId
     * @return
     */
    @GetMapping(value = { "/registration/{scheduledActivityId}", "/registration/{scheduledActivityId}/" })
    public ResponseEntity<?> getRegistration(@PathVariable("schedulesActivityId") int scheduledActivityId) {
        try {
            List<Registration> registrations = registrationService
                    .getRegistrationByScheduledActivityId(scheduledActivityId);
            return ResponseEntity.ok(RegistrationDto.convertToDto(registrations));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * get registration by scheduled activity id and costumer id
     * 
     * @param registrationId
     * @return
     * @Author Emilie Ruel
     */
    @GetMapping(value = { "/registration/{scheduledActivityId}/{accountRoleId}",
            "/registration/{scheduledActivityId}/{accountRoleId}/" })
    public ResponseEntity<?> getRegistration(@PathVariable("schedulesActivityId") int scheduledActivityId,
            @PathVariable("accountRoleId") int accountRoleId) {
        try {
            Registration registrations = registrationService
                    .getRegistrationByCustomerAndScheduledActivity(accountRoleId, scheduledActivityId);
            return ResponseEntity.ok(RegistrationDto.convertToDto(registrations));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // create new registration
    /**
     * Create an account
     * 
     * @param username
     * @param password
     * @return
     * @Author Emilie Ruel
     */
    @PostMapping(value = { "/register/{scheduledActivityId}/{accountRoleId}",
            "/register/{scheduledActivityId}/{accountRoleId}/" })
    public ResponseEntity<?> register(@PathVariable("schedulesActivityId") int scheduledActivityId,
            @PathVariable("accountRoleId") int accountRoleId) {
        try {
            Registration registration = registrationService.register(accountRoleId, scheduledActivityId);
            return ResponseEntity.ok(RegistrationDto.convertToDto(registration));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete a registration by its ID
     * 
     * @param registrationId
     * @return
     * @Author Emilie Ruel
     */
    @DeleteMapping(value = { "/registration/delete/{registrationId}", "/registration/delete/{registrationId}/" })
    public ResponseEntity<?> deleteRegistration(@PathVariable("registrationId") int registrationId) {
        try {
            registrationService.deleteRegistration(registrationId);
            return ResponseEntity.ok("Registration deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Delete all registration
     * 
     * @return
     * @Author Emilie Ruel
     */
    @DeleteMapping(value = { "/registrations/delete", "/registrations/delete/" })
    public ResponseEntity<?> deleteAllRegistrations() {
        try {
            registrationService.deleteAllRegistrations();
            return ResponseEntity.ok("Registrations deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}