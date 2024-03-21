package ca.mcgill.ecse321.sportcenter.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.sportcenter.dto.*;
import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.service.RegistrationService;

/**
 * Rest controller for the Registration entity
 * 
 * @author Emilie Ruel
 */
@CrossOrigin(origins = "*")
@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    /**
     * Create a registration
     * 
     * @param customerId
     * @param scheduledActivityId
     * @return RegistrationDto
     */
    @PostMapping(value = { "/register/{customerId}/{scheduledActivityId}" })
    public RegistrationDto register(@PathVariable("customerId") int customerId,
            @PathVariable("scheduledActivityId") int scheduledActivityId) throws IllegalArgumentException {
        Registration registration = registrationService.createRegistration(customerId, scheduledActivityId);
        return convertToDto(registration);
    }

    /**
     * Get a registration by a registration id
     * 
     * @param registrationId
     * @return RegistrationDto
     */
    @GetMapping(value = { "/registration/{registrationId}" })
    public RegistrationDto getRegistrationByRegId(@PathVariable("registrationId") int registrationId)
            throws IllegalArgumentException {
        Registration registration = registrationService.getRegistrationByRegId(registrationId);
        return convertToDto(registration);
    }

    /**
     * Get a registration by its scheduled activity id and customer account role id
     * 
     * @param scheduledActivityId
     * @param accountRoleId
     * @return RegistrationDto
     */
    @GetMapping(value = { "/registration/{scheduledActivityId}/{accountRoleId}" })
    public RegistrationDto getRegistrationByScheduledActivityIdAndAccountRoleId(
            @PathVariable("scheduledActivityId") int scheduledActivityId,
            @PathVariable("accountRoleId") int accountRoleId) throws IllegalArgumentException {
        Registration registration = registrationService
                .getRegistrationByAccountRoleIdAndScheduledActivityId(scheduledActivityId, accountRoleId);
        return convertToDto(registration);
    }

    /**
     * Get all registrations
     * 
     * @return List<RegistrationDto>
     */
    @GetMapping(value = { "/registrations", "/registrations/" })
    public List<RegistrationDto> getAllRegistrations() throws IllegalArgumentException {
        return convertToDto(registrationService.getAllRegistrations());
    }

    /**
     * Get all registrations of a costumer account role Id
     * 
     * @param accountRoleId
     * @return List<RegistrationDto>
     */
    @GetMapping(value = { "/registrations/{accountRoleId}" })
    public List<RegistrationDto> getRegistrationsByAccountRoleId(@PathVariable("accountRoleId") int accountRoleId)
            throws IllegalArgumentException {
        return convertToDto(registrationService.getRegistrationByAccountRoleId(accountRoleId));
    }

    /**
     * Get all registrations of a scheduled activity by scheduledActivityId
     * 
     * @param scheduledActivityId
     * @return List<RegistrationDto>
     */
    @GetMapping(value = { "/registrations/scheduledActivity/{scheduledActivityId}" })
    public List<RegistrationDto> getRegistrationsByScheduledActivityId(
            @PathVariable("scheduledActivityId") int scheduledActivityId) throws IllegalArgumentException {
        return convertToDto(registrationService.getRegistrationByScheduledActivityId(scheduledActivityId));
    }

    /**
     * Get all costumers attending a scheduled activity by scheduledActivityId
     * 
     * @param scheduledActivityId
     * @return List<RegistrationDto>
     */
    @GetMapping(value = { "/registrations/scheduledActivity/{scheduledActivityId}" })
    public List<RegistrationDto> getCustomersByScheduledActivityId(
            @PathVariable("scheduledActivityId") int scheduledActivityId) throws IllegalArgumentException {
        return convertToDto(registrationService.getRegistrationByScheduledActivityId(scheduledActivityId));
    }

    /**
     * Get all scheduled activities attended by a customer by its accountRoleId
     * 
     * @param accountRoleId
     * @return List<RegistrationDto>
     */
    @GetMapping(value = { "/registrations/customer/{accountRoleId}" })
    public List<RegistrationDto> getScheduledActivitiesByCustomer(@PathVariable("accountRoleId") int accountRoleId)
            throws IllegalArgumentException {
        return convertToDto(registrationService.getRegistrationByAccountRoleId(accountRoleId));
    }

    /**
     * Delete a registration by its registrationId
     * 
     * @param registrationId
     */
    @DeleteMapping(value = { "/registration/{registrationId}" })
    public void deleteRegistration(@PathVariable("registrationId") int registrationId) throws IllegalArgumentException {
        registrationService.deleteRegistration(registrationId);
    }

    /**
     * Delete all registrations
     */
    @DeleteMapping(value = { "/registrations", "/registrations/" })
    public void deleteAllRegistrations() throws IllegalArgumentException {
        registrationService.deleteAllRegistrations();
    }

    //

    /**
     * Convert Registration to RegistrationDto
     * 
     * @param registration
     * @return RegistrationDto
     */
    public static RegistrationDto convertToDto(Registration registration) {
        if (registration == null) {
            throw new IllegalArgumentException("There is no registration");
        }
        return new RegistrationDto(AccountManagementController.convertCustomerToDto(registration.getCustomer()),
                ScheduledActivityController.convertOwnerToDto(registration.getScheduledActivity()),
                registration.getRegistrationId());
    }

    /**
     * Convert a list of Registration to a list of RegistrationDto
     * 
     * @param registrations
     * @return List<RegistrationDto>
     */
    public static List<RegistrationDto> convertToDto(List<Registration> registrations) {
        List<RegistrationDto> registrationDtos = new ArrayList<RegistrationDto>();
        for (Registration registration : registrations) {
            registrationDtos.add(convertToDto(registration));
        }
        return registrationDtos;
    }
}