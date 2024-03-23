package ca.mcgill.ecse321.sportcenter.service;

import org.springframework.stereotype.Service;
import java.sql.Date;
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
public class RegistrationManagementService {

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
     * @return Registration
     */
    @Transactional
    public Registration createRegistration(int accountRoleId, int scheduledActivityId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("Account role id not valid!");
        }
        if (scheduledActivityId < 0) {
            throw new IllegalArgumentException("Scheduled activity id not valid!");
        }

        Customer customer = customerRepository.findCustomerByAccountRoleId(accountRoleId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist");
        }
        ScheduledActivity scheduledActivity = scheduledActivityRepository
                .findScheduledActivityByScheduledActivityId(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled activity does not exist");
        }
        Registration existingRegistration = getRegistrationByCustomerIdAndScheduledActivityId(accountRoleId,
                scheduledActivityId);
        if (existingRegistration != null) {
            throw new IllegalArgumentException("Registration already exists for this customer and scheduled activity");
        }

        // Check if scheduled activity is in the past
        Date date = new Date(System.currentTimeMillis());
        if (scheduledActivity.getDate().isBefore(date.toLocalDate())) {
            throw new IllegalArgumentException("Scheduled activity is in the past");
        }

        // Check if scheduled activity is full
        int size = getRegistrationByScheduledActivityId(scheduledActivityId).size();
        if (size >= scheduledActivity.getCapacity()) {
            throw new IllegalArgumentException("Scheduled activity is full");
        }

        Registration registration = new Registration();
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
        if (registrationId < 0) {
            throw new IllegalArgumentException("Registration Id not valid!");
        }
        Registration registration = registrationRepository.findRegistrationByRegId(registrationId);
        if (registration == null) {
            throw new IllegalArgumentException("Registration does not exist");
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
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("Account role id not valid!");
        }
        if (customerRepository.findCustomerByAccountRoleId(accountRoleId) == null) {
            throw new IllegalArgumentException("Customer does not exist");
        }
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
        if (scheduledActivityId < 0) {
            throw new IllegalArgumentException("Id not valid!");
        }
        if (scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId) == null) {
            throw new IllegalArgumentException("Scheduled activity does not exist");
        }
        List<Registration> customersAttendingScheduledActivity = new ArrayList<>();
        for (Registration registration : registrationRepository.findAll()) {
            if (registration.getScheduledActivity().getScheduledActivityId() == scheduledActivityId) {
                customersAttendingScheduledActivity.add(registration);
            }
        }
        return customersAttendingScheduledActivity;
    }

    /**
     * Get a registration by its customer and scheduled activity
     * 
     * @param accountRoleId
     * @param scheduledActivityId
     * @return Registration
     */
    @Transactional
    public Registration getRegistrationByCustomerIdAndScheduledActivityId(int accountRoleId,
            int scheduledActivityId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("Account role id not valid!");
        }
        if (scheduledActivityId < 0) {
            throw new IllegalArgumentException("Scheduled activity id not valid!");
        }
        if (customerRepository.findCustomerByAccountRoleId(accountRoleId) == null) {
            throw new IllegalArgumentException("Customer does not exist");
        }
        if (scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId) == null) {
            throw new IllegalArgumentException("Scheduled activity does not exist");
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
     * Get all costumers attending a scheduled activity by its scheduledActivityId
     * 
     * @param scheduledActivityId
     */
    @Transactional
    public List<Customer> getCustomersByScheduledActivityId(int scheduledActivityId) {
        List<Customer> customers = new ArrayList<>();
        if (scheduledActivityId < 0) {
            throw new IllegalArgumentException("Scheduled activity Id not valid!");
        }
        if (scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId) == null) {
            throw new IllegalArgumentException("Scheduled activity does not exist");
        }
        for (Registration registration : registrationRepository.findAll()) {
            if (registration.getScheduledActivity().getScheduledActivityId() == scheduledActivityId) {
                customers.add(registration.getCustomer());
            }
        }
        return customers;
    }

    /**
     * Get all scheduled activities attended by a customer by its accountRoleId
     * 
     * @param registrationId
     */
    @Transactional
    public List<ScheduledActivity> getScheduledActivitiesByCustomerId(int accountRoleId) {
        List<ScheduledActivity> scheduledActivities = new ArrayList<>();
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("Account role id not valid!");
        }
        if (customerRepository.findCustomerByAccountRoleId(accountRoleId) == null) {
            throw new IllegalArgumentException("Customer does not exist");
        }
        for (Registration registration : registrationRepository.findAll()) {
            if (registration.getCustomer().getAccountRoleId() == accountRoleId) {
                scheduledActivities.add(registration.getScheduledActivity());
            }
        }
        return scheduledActivities;
    }

    /**
     * Get all scheduled activities attended by an instructor by its accountRoleId
     */
    @Transactional
    public List<ScheduledActivity> getScheduledActivitiesByInstructorId(int accountRoleId) {
        List<ScheduledActivity> scheduledActivities = new ArrayList<>();
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("Account role id not valid!");
        }
        if (customerRepository.findCustomerByAccountRoleId(accountRoleId) == null) {
            throw new IllegalArgumentException("Instructor does not exist");
        }
        for (Registration registration : registrationRepository.findAll()) {
            if (registration.getScheduledActivity().getSupervisor().getAccountRoleId() == accountRoleId) {
                scheduledActivities.add(registration.getScheduledActivity());
            }
        }
        return scheduledActivities;
    }

    /**
     * Delete a registration by its registrationId (primary key)
     * 
     * @param registrationId
     */
    @Transactional
    public void deleteRegistration(int registrationId) {
        if (registrationId < 0) {
            throw new IllegalArgumentException("Registration id not valid!");
        }
        if (registrationRepository.findRegistrationByRegId(registrationId) == null) {
            throw new IllegalArgumentException("Registration does not exist");
        }
        registrationRepository.deleteById(registrationId);
    }

    /**
     * Delete all registrations
     */
    @Transactional
    public void deleteAllRegistrations() {
        registrationRepository.deleteAll();
    }

    /**
     * Delete all registrations of a customer by its accountRoleId
     */
    @Transactional
    public void deleteRegistrationsByAccountRoleId(int accountRoleId) {
        if (accountRoleId < 0) {
            throw new IllegalArgumentException("Account role id not valid!");
        }
        if (customerRepository.findCustomerByAccountRoleId(accountRoleId) == null) {
            throw new IllegalArgumentException("Customer does not exist");
        }
        for (Registration registration : registrationRepository.findAll()) {
            if (registration.getCustomer().getAccountRoleId() == accountRoleId) {
                registrationRepository.deleteById(registration.getRegistrationId());
            }
        }
    }

    /**
     * Delete all registrations of a scheduled activity by its scheduledActivityId
     */
    @Transactional
    public void deleteRegistrationsByScheduledActivityId(int scheduledActivityId) {
        if (scheduledActivityId < 0) {
            throw new IllegalArgumentException("Scheduled activity id not valid!");
        }
        if (scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId) == null) {
            throw new IllegalArgumentException("Scheduled activity does not exist");
        }
        for (Registration registration : registrationRepository.findAll()) {
            if (registration.getScheduledActivity().getScheduledActivityId() == scheduledActivityId) {
                registrationRepository.deleteById(registration.getRegistrationId());
            }
        }
    }
}
