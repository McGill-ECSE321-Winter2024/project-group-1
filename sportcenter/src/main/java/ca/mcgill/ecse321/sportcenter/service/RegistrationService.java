package ca.mcgill.ecse321.sportcenter.service;

import org.springframework.stereotype.Service;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.dao.ScheduledActivityRepository;
import ca.mcgill.ecse321.sportcenter.dao.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.model.Account;
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

    private <T> List<T> toList(Iterable<T> iterable){
            List<T> resultList = new ArrayList<T>();
            for (T t : iterable) {
                resultList.add(t);
            }
            return resultList;
        }

    /**
     * Create an registration
     * @param Customer
     * @param ScheduledActivity
     * @return Regitration
     * @author Emilie Ruel
     */
    @Transactional
	public Registration register(Customer customer, ScheduledActivity scheduledActivity) {
		Registration registration = new Registration();
		registration.setRegId(customer.getAccountRoleId() * scheduledActivity.getScheduledActivityId());
		registration.setCustomer(customer);
		registration.setScheduledActivity(scheduledActivity);

		registrationRepository.save(registration);

		return registration;
	}

    /**
     * Get a registration by a registration ID
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
     * @return List<Registration>
     * @author Emilie Ruel
     */
    @Transactional
	public List<Registration> getAllRegistrations(){
		return toList(registrationRepository.findAll());
	}

    /**
     * Get acheduled activities attended by a costumer
     * @param Customer
     * @return List<ScheduledActiviy>
     * @author Emilie Ruel
     */
	@Transactional
	public List<ScheduledActivity> getScheduledActivitiesAttendedByCostumer(Customer customer) {
		List<ScheduledActivity> scheduledActivitiesAttendedByCustomer = new ArrayList<>();
		for (Registration r : registrationRepository.findByCustomer(customer)) {
			scheduledActivitiesAttendedByCustomer.add(r.getScheduledActivity());
		}
		return scheduledActivitiesAttendedByCustomer;
	}

	/**
     * Get customers attending a scheduled activity
     * @param scheduledActivity
     * @return List<customer>
     * @author Emilie Ruel
     */
	@Transactional
	public List<Customer> getCustomersAttendingScheduledActivity(ScheduledActivity scheduledActivity) {
		List<Customer> customersAttendingScheduledActivity = new ArrayList<>();
		for (Registration r : registrationRepository.findBySchuledActivity(scheduledActivity)) {
			customersAttendingScheduledActivity.add(r.getCustomer());
		}
		return customersAttendingScheduledActivity;
	}

    /**
     * Get a registration by its scheduled activity and customer
     * @param customer
     * @param scheduledActivity
     * @return Registration
     */
    @Transactional
    public Registration getRegistrationByCustomerAndScheduledActivity(Customer customer, ScheduledActivity scheduledActivity) {
        Registration registration = registrationRepository.findRegistrationByCustomerAndScheduledActivity(customer, scheduledActivity);
        if (registration == null) {
            throw new IllegalArgumentException("No corresponding registration");
        }
        return registration;
    }
    
    /**
     * Delete a registration by its registration ID (primary key)
     * @param registrationId
     * @autor Emilie Ruel
     */
    @Transactional
    public void deleteRegistration(int registrationId) {
        registrationRepository.deleteById(registrationId);
    }

     /**
     * Delete all registrations
     * @return void
     */
    @Transactional
    public void deleteAllAccounts() {
        registrationRepository.deleteAll();
    }
}

