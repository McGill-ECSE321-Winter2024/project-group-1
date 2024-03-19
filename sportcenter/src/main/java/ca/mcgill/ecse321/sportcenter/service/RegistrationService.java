package ca.mcgill.ecse321.sportcenter.service;

import org.springframework.stereotype.Service;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;
import ca.mcgill.ecse321.sportcenter.dto.CustomerDto;
import ca.mcgill.ecse321.sportcenter.dto.RegistrationDto;
import ca.mcgill.ecse321.sportcenter.dto.ScheduledActivityDto;
import ca.mcgill.ecse321.sportcenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Registration;

@Service
public class RegistrationService {

    @Autowired
    ScheduledActivityRepository scheduledActivityRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RegistrationRepository registrationRepository;

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * Create an registration
     * 
     * @param accountRoleId
     * @param scheduledActivityId
     * @return Regitration
     * @author Emilie Ruel
     */
    @Transactional
    public Registration register(int accountRoleId, int scheduledActivityId) {
        Registration registration = new Registration();
        if (accountRoleId <= 0 || scheduledActivityId <= 0) {
            throw new IllegalArgumentException("Id is not a customer account role");
        }
        ScheduledActivity scheduledActivity = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled activity does not exist");
        }
        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (customer == null) {
            throw new IllegalArgumentException("Id is not a customer account role");
        }

        Registration existingRegistration = getRegistrationByCustomerAndScheduledActivity(accountRoleId,
                scheduledActivityId);
        if (existingRegistration != null) {
            throw new IllegalArgumentException("Registration already exists");

        }
        Date date = new Date(System.currentTimeMillis());
        // check if date is in the future
        if (scheduledActivity.getDate().isBefore(date.toLocalDate())) {
            throw new IllegalArgumentException("Scheduled activity is in the past");
        }
        if (scheduledActivity.getDate().equals(date.toLocalDate())) {
            Time time = new Time(System.currentTimeMillis());
            // check if time is in the future
            if (scheduledActivity.getStartTime().isBefore(time.toLocalTime())) {
                throw new IllegalArgumentException("Scheduled activity is in the past");
            }
        }
        int size = getRegistrationByScheduledActivityId(scheduledActivityId).size();
        // check if capacity allows it
        if (size >= scheduledActivity.getCapacity()) {
            throw new IllegalArgumentException("Scheduled activity is full");
        }

        registration.setRegistrationId(accountRoleId * scheduledActivityId);
        registration.setCustomer(customer);
        registration.setScheduledActivity(scheduledActivity);

        registrationRepository.save(registration);

        return registration;
    }

    /**
     * Get a registration by a registration ID
     * 
     * @param registrationId
     * @return Registration
     * @author Emilie Ruel
     */
    @Transactional
    public Registration getRegistrationById(Integer registrationId) {
        Registration registration = registrationRepository.findRegistrationByRegId(registrationId);
        if (registration == null) {
            throw new IllegalArgumentException("Registartion does not exist");
        }
        return registration;

    }

    /**
     * Get a list of all registrations
     * 
     * @return List<Registration>
     * @author Emilie Ruel
     */
    @Transactional
    public List<Registration> getAllRegistrations() {
        return toList(registrationRepository.findAll());
    }

    /**
     * Get all registrations of a costumer bu its accountRoleId
     * 
     * @param accountRoleId
     * @return List<Registration>
     * @author Emilie Ruel
     */
    @Transactional
    public List<Registration> getRegistrationByCostumerId(int accountRoleId) {
        List<Registration> scheduledActivitiesAttendedByCustomer = new ArrayList<>();
        for (Registration r : registrationRepository.findAll()) {
            if (r.getCustomer().getAccountRoleId() == accountRoleId) {
                scheduledActivitiesAttendedByCustomer.add(r);
            }
        }
        return scheduledActivitiesAttendedByCustomer;
    }

    /**
     * Get all registrations of a scheduled activity by scheduled activity Id
     * 
     * @param scheduledActivityId
     * @return List<Registration>
     * @author Emilie Ruel
     */
    @Transactional
    public List<Registration> getRegistrationByScheduledActivityId(int scheduledActivityId) {
        List<Registration> customersAttendingScheduledActivity = new ArrayList<>();
        for (Registration r : registrationRepository.findAll()) {
            if (r.getScheduledActivity().getScheduledActivityId() == scheduledActivityId) {
                customersAttendingScheduledActivity.add(r);
            }

        }
        return customersAttendingScheduledActivity;
    }

    /**
     * Get a registration by its scheduled activity and customer
     * 
     * @param accountRoleId
     * @param scheduledActivityId
     * @return Registration
     */
    @Transactional
    public Registration getRegistrationByCustomerAndScheduledActivity(int accountRoleId, int scheduledActivityId) {
        Registration registration;
        for (Registration r : registrationRepository.findAll()) {
            if (r.getCustomer().getAccountRoleId() == accountRoleId) {
                if (r.getScheduledActivity().getScheduledActivityId() == scheduledActivityId) {
                    registration = r;
                    return registration;
                }
            }
        }
        return null;
    }

    /**
     * Delete a registration by its registration ID (primary key)
     * 
     * @param registrationId
     * @autor Emilie Ruel
     */
    @Transactional
    public void deleteRegistration(int registrationId) {
        if (registrationId < 0) {
            throw new IllegalArgumentException("Id not valid!");
        }
        registrationRepository.deleteById(registrationId);
    }

    /**
     * Delete all registrations
     * 
     * @return void
     * @Author Emilie Ruel
     */
    @Transactional
    public void deleteAllRegistrations() {
        registrationRepository.deleteAll();
    }

    // converts an Registration to an RegistrationDto
    public static RegistrationDto convertToDto(Registration registration) {
        if (registration == null) {
            throw new IllegalArgumentException("There is no registration to convert");
        }
        return new RegistrationDto(CustomerDto.convertToDto(registration.getCustomer()),
                ScheduledActivityDto.convertToDto(registration.getScheduledActivity()),
                registration.getRegistrationId());
    }

    public static List<RegistrationDto> convertToDto(List<Registration> registrations) {
        List<RegistrationDto> registrationDto = new ArrayList<RegistrationDto>(registrations.size());

        for (Registration registration : registrations) {
            registrationDto.add(RegistrationService.convertToDto(registration));
        }
        return registrationDto;
    }
}
