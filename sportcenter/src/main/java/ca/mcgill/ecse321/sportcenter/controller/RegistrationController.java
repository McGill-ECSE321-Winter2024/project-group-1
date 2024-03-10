package ca.mcgill.ecse321.sportcenter.controller;
import java.util.stream.Collectors;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.sportcenter.dto.*;

import ca.mcgill.ecse321.sportcenter.service.RegistrationService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = {"/registrations", "/registrations/"})
public class RegistrationController {
    
    @Autowired
	private RegistrationService service;


    //get all registrations

    //get registration by registration id
    @GetMapping(value = { "/{registrationId}" })
    public List<RegistrationDto> getRegistrationDtos() {
	    return convertRegistrationDto(service.getAllRegistrations()) ;
    }

    //get registrations by costumer


    //get registrations by scheduled activity


    //get registration by scheduled activity and costumer


    //create new registration


    //delete registration


    //delete all registration

}
