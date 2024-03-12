package ca.mcgill.ecse321.sportcenter.dto;
import ca.mcgill.ecse321.sportcenter.model.*;
import java.util.*;

import org.checkerframework.checker.units.qual.s;

public class RegistrationDto{
    private int registrationId;
    private CustomerDto customer;
    private ScheduledActivityDto scheduledActivity;

    public RegistrationDto(){}

    public RegistrationDto(CustomerDto customer, ScheduledActivityDto scheduledActivity, int registrationId) {
        this.customer=customer;
        this.scheduledActivity=scheduledActivity;
        this.registrationId=registrationId;
    }

    
    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer=customer;
    }

    public ScheduledActivityDto getScheduledActivity() {
        return scheduledActivity;
    }


    public void setScheduledActivity(ScheduledActivityDto scheduledActivity) {
        this.scheduledActivity=scheduledActivity;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId=registrationId;
    } 
}