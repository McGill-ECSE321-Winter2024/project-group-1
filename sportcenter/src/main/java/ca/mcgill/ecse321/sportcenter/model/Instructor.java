package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Instructor extends Account
{

  public enum InstructorStatus { Active, Inactive, Fired, Suspended }

  //attributes
  private InstructorStatus status; 
  private String description;
  private String profilePicURL;

  @ManyToOne(optional = false) //many instructors in a sport center
  private SportCenter sportCenter;
  
  @OneToOne(optional = true) //a user can at most have 1 instructor account
  @JoinColumn(name = "user_id") //user_id is a FK
  private User user;

  
  //CONSTRUCTORS
  
  public Instructor() {

  }

  public Instructor(int aAccountId, InstructorStatus aStatus, String aDescription, String aProfilePicURL, SportCenter aSportCenter, User aUser)
  {
    super(aAccountId);
    status = aStatus;
    description = aDescription;
    profilePicURL = aProfilePicURL;
    
 
    if (!setUser(aUser))
    {
      throw new RuntimeException("Unable to create Instructor due to aUser.");
    }
  }

  //SETTERS

  public boolean setStatus(InstructorStatus aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setProfilePicURL(String aProfilePicURL)
  {
    boolean wasSet = false;
    profilePicURL = aProfilePicURL;
    wasSet = true;
    return wasSet;
  }

  public boolean setUser(User aNewUser)
  {
    boolean wasSet = false;
    if (aNewUser != null)
    {
      user = aNewUser;
      wasSet = true;
    }
    return wasSet;
  }

  //GETTERS

  public InstructorStatus getStatus()
  {
    return status;
  }

  public String getDescription()
  {
    return description;
  }

  public String getProfilePicURL()
  {
    return profilePicURL;
  }

  public SportCenter getSportCenter()
  {
    return sportCenter;
  }

  public User getUser()
  {
    return user;
  }
 


}