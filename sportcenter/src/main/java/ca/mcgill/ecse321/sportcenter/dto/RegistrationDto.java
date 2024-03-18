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

     //converts an Registration to an RegistrationDto
     public static RegistrationDto convertToDto(Registration registration) {
        if (registration == null){
            throw new IllegalArgumentException("There is no registration to convert");
        }
        return new RegistrationDto(CustomerDto.convertToDto(registration.getCustomer()), ScheduledActivityDto.convertToDto(registration.getScheduledActivity()), registration.getRegistrationId());
    }
    public static List<RegistrationDto> convertToDto(List<Registration> registrations) {
        List<RegistrationDto> registrationDto = new ArrayList<RegistrationDto>(registrations.size());

        for (Registration registration : registrations) {
            registrationDto.add(RegistrationDto.convertToDto(registration));
        }
        return registrationDto;
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