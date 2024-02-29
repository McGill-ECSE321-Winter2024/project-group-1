package ca.mcgill.ecse321.sportcenter.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.model.Owner;

import ca.mcgill.ecse321.sportcenter.model.Activity;

public class OwnerService {
    @Autowired
    OwnerRepository ownerRepository;
    
}
