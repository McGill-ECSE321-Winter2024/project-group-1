package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.checkerframework.checker.units.qual.Acceleration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;

import ca.mcgill.ecse321.sportcenter.dao.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.dto.OwnerDto;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import net.bytebuddy.pool.TypePool.AbstractBase.Hierarchical;

/**
 * @author Mathias Pacheco Lemina
 */
@SpringBootTest
public class TestOwnerRepository {
    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        ownerRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadOwner() {
        String name = "testName";
        String email = "testEmail";
        String phoneNumber = "testPhoneNumber";
        Owner owner = new Owner();
        owner.setName(name);
        owner.setEmail(email);
        owner.setPhoneNumber(phoneNumber);
        ownerRepository.save(owner);

        owner = ownerRepository.findOwnerByName(name);
        assertNotNull(owner);
        assertEquals(name, owner.getName());
        assertEquals(email, owner.getEmail());
        assertEquals(phoneNumber, owner.getPhoneNumber());
    }

    @Test
    public void testPersistAndLoadOwnerDto() {
        String name = "ownerDTO";
        String password = "password";
        String email = "testEmail";
        String phoneNumber = "testPhoneNumber";
        Account a = new Account(name, password);
        int i = OwnerRepository.save(a);

        OwnerDto ownerDto = new OwnerDto(name, email, phoneNumber);
        ownerDto.setUserame(name);
        ownerDto.setEmail(email);
        ownerDto.setPhoneNumber(phoneNumber);

        - Hierarchically 
        ownerRepository.getAccount().setOwner(ownerDto);
        assertEquals(phoneNumber, ownerDto);
    }
}
