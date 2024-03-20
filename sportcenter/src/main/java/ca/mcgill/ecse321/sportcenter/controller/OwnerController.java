package ca.mcgill.ecse321.sportcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.sportcenter.dto.OwnerDto;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    /**
     * Get owner by accountRoleId
     * 
     * @param accountRoleId
     * @return
     */
    @GetMapping(value = { "/owner/{accountRoleId}", "/owner/{accountRoleId}/" })
    public OwnerDto getOwner(@PathVariable("accountRoleId") int accountRoleId) {
        Owner owner = ownerService.getOwnerByAccountRoleId(accountRoleId);
        return convertToDto(owner);
    }

    /**
     * Approve Activity
     * 
     * @return
     */
    @PutMapping(value = { "/owner/aprooveActivity", "/owner/aprooveActivity/" })
    public ResponseEntity<?> aprooveActivity(@RequestParam("activityName") String activityName) {
        try {
            ownerService.approveActivity(activityName);
            return ResponseEntity.ok("Activity aprooved");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Disapprove Activity
     * 
     * @return
     */
    @PutMapping(value = { "/owner/disaproveActivity", "/owner/disaproveActivity/" })
    public ResponseEntity<?> disaproveActivity(@RequestParam("activityName") String activityName) {
        try {
            ownerService.disapproveActivity(activityName);
            return ResponseEntity.ok("Activity disaproved");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public static OwnerDto convertToDto(Owner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("There is no owner!");
        }
        return new OwnerDto(owner.getAccountRoleId());
    }
}
