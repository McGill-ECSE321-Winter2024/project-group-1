package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Instructor extends AccountRole {

  public enum InstructorStatus {
    Active, Inactive, Fired, Suspended, Pending
  }

  // attributes
  private InstructorStatus status;
  private String description;
  private String profilePicURL;

  @OneToOne(optional = true) // an account can at most have 1 instructor account role
  @JoinColumn(name = "accountId") // account_id is a FK
  private Account account;

  // CONSTRUCTORS

  public Instructor() {

  }

  public Instructor(InstructorStatus aStatus, String aDescription, String aProfilePicURL, Account aAccount) {
    status = aStatus;
    description = aDescription;
    profilePicURL = aProfilePicURL;

    if (!setAccount(aAccount)) {
      throw new RuntimeException("Unable to create Instructor due to aAccount.");
    }
  }

  // SETTERS

  public boolean setStatus(InstructorStatus aStatus) {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription) {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setProfilePicURL(String aProfilePicURL) {
    boolean wasSet = false;
    profilePicURL = aProfilePicURL;
    wasSet = true;
    return wasSet;
  }

  public boolean setAccount(Account aNewAccount) {
    boolean wasSet = false;
    if (aNewAccount != null) {
      account = aNewAccount;
      wasSet = true;
    }
    return wasSet;
  }

  // GETTERS

  public InstructorStatus getStatus() {
    return status;
  }

  public String getDescription() {
    return description;
  }

  public String getProfilePicURL() {
    return profilePicURL;
  }

  public Account getAccount() {
    return account;
  }

}