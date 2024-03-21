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
import ca.mcgill.ecse321.sportcenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Registration;

/**
 * Service class for the Registration entity
 * 
 * @Author Emilie Ruel
 */
@Service
public class RegistrationService {

    @Autowired
    ScheduledActivityRepository scheduledActivityRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RegistrationRepository registrationRepository;

    /**
     * Convert a list of iterable to a list
     * 
     * @param Iterable<T>
     * @return List<T>
     */
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
     */
    @Transactional
    public Registration createRegistration(int accountRoleId, int scheduledActivityId) {
        Registration registration = new Registration();
        ScheduledActivity scheduledActivity = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled activity does not exist");
        }
        Customer customer = customerRepository.findAccountRoleByAccountRoleId(accountRoleId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist");
        }

        Registration existingRegistration = getRegistrationByAccountRoleIdAndScheduledActivityId(accountRoleId,
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
     */
    @Transactional
    public Registration getRegistrationByRegId(Integer registrationId) {
        Registration registration = registrationRepository.findRegistrationByRegId(registrationId);
        if (registration == null) {
            throw new IllegalArgumentException("Registartion does not exist");
        }
        if (registrationId < 0) {
            throw new IllegalArgumentException("Id not valid!");
        }
        return registration;
    }

    /**
     * Get a list of all registrations
     * 
     * @return List<Registration>
     */
    @Transactional
    public List<Registration> getAllRegistrations() {
        return toList(registrationRepository.findAll());
    }

    /**
     * Get all registrations of a costumer by its accountRoleId
     * 
     * @param accountRoleId
     * @return List<Registration>
     */
    @Transactional
    public List<Registration> getRegistrationByAccountRoleId(int accountRoleId) {
        List<Registration> scheduledActivitiesAttendedByCustomer = new ArrayList<>();
        for (Registration registration : registrationRepository.findAll()) {
            if (registration.getCustomer().getAccountRoleId() == accountRoleId) {
                scheduledActivitiesAttendedByCustomer.add(registration);
            }
        }
        return scheduledActivitiesAttendedByCustomer;
    }

    /**
     * Get all registrations of a scheduled activity by scheduled activity Id
     * 
     * @param scheduledActivityId
     * @return List<Registration>
     */
    @Transactional
    public List<Registration> getRegistrationByScheduledActivityId(int scheduledActivityId) {
        List<Registration> customersAttendingScheduledActivity = new ArrayList<>();
        if (scheduledActivityId < 0) {
            throw new IllegalArgumentException("Id not valid!");
        }
        for (Registration registration : registrationRepository.findAll()) {
            if (registration.getScheduledActivity().getScheduledActivityId() == scheduledActivityId) {
                customersAttendingScheduledActivity.add(registration);
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
    public Registration getRegistrationByAccountRoleIdAndScheduledActivityId(int accountRoleId,
            int scheduledActivityId) {
        if (accountRoleId < 0 || scheduledActivityId < 0) {
            throw new IllegalArgumentException("Id not valid!");
        }
        for (Registration registration : registrationRepository.findAll()) {
            if (registration.getCustomer().getAccountRoleId() == accountRoleId
                    && registration.getScheduledActivity().getScheduledActivityId() == scheduledActivityId) {
                return registration;
            }
        }
        return null;
    }

    /**
     * Delete a registration by its registrationId (primary key)
     * 
     * @param registrationId
     */
    @Transactional
    public void deleteRegistration(int registrationId) {
        if (registrationId < 0) {
            throw new IllegalArgumentException("Id not valid!");
        }
        if (registrationRepository.findRegistrationByRegId(registrationId) == null) {
            throw new IllegalArgumentException("Registration does not exist");
        }
        registrationRepository.deleteById(registrationId);
    }

    /**
     * Delete all registrations
     * 
     * @return void
     */
    @Transactional
    public void deleteAllRegistrations() {
        registrationRepository.deleteAll();
    }
}
