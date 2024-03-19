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
     * @param accountRoleId
     * @param scheduledActivityId
     * @return Regitration
     * @author Emilie Ruel
     */
    @Transactional
	public Registration register(int accountRoleId, int scheduledActivityId) {
        Registration registration = new Registration();
        ScheduledActivity scheduledActivity = scheduledActivityRepository.findScheduledActivityByScheduledActivityId(scheduledActivityId);
        if (scheduledActivity == null) {
            throw new IllegalArgumentException("Scheduled activity does not exist");
        }
		registration.setRegistrationId(accountRoleId * scheduledActivityId);
		//registration.setCustomer(customer);
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
     * Get all registrations of a costumer bu its accountRoleId
     * @param accountRoleId
     * @return List<Registration>
     * @author Emilie Ruel
     */
	@Transactional
	public List<Registration> getRegistrationByCostumerId(int accountRoleId) {
		List<Registration> scheduledActivitiesAttendedByCustomer = new ArrayList<>();
		for (Registration r : registrationRepository.findAll()) {
            if (r.getCustomer().getAccountRoleId()==accountRoleId) {
                scheduledActivitiesAttendedByCustomer.add(r);
            }			
		}
		return scheduledActivitiesAttendedByCustomer;
	}

	/**
     * Get all registrations of a scheduled activity by scheduled activity Id
     * @param scheduledActivityId
     * @return List<Registration>
     * @author Emilie Ruel
     */
	@Transactional
	public List<Registration> getRegistrationByScheduledActivityId(int scheduledActivityId) {
		List<Registration> customersAttendingScheduledActivity = new ArrayList<>();
		for (Registration r : registrationRepository.findAll()) {
            if (r.getScheduledActivity().getScheduledActivityId()==scheduledActivityId) {
                customersAttendingScheduledActivity.add(r);
            }
			
		}
		return customersAttendingScheduledActivity;
	}

    /**
     * Get a registration by its scheduled activity and customer
     * @param accountRoleId
     * @param scheduledActivityId
     * @return Registration
     */
    @Transactional
    public Registration getRegistrationByCustomerAndScheduledActivity(int accountRoleId, int scheduledActivityId) {
        Registration registration;
        for (Registration r : registrationRepository.findAll()) {
            if (r.getCustomer().getAccountRoleId()==accountRoleId) {
                if (r.getScheduledActivity().getScheduledActivityId()==scheduledActivityId) {
                    registration=r;
                    return registration;
                }
            }
        }
        throw new IllegalArgumentException("Registartion does not exist");
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
     * @Author Emilie Ruel
     */
    @Transactional
    public void deleteAllRegistrations() {
        registrationRepository.deleteAll();
    }
}
