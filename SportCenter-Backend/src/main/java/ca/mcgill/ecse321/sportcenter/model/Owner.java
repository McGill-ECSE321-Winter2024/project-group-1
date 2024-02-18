package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Owner extends Account
{

  @OneToOne(optional = false) //sport center can only have one owner
  private SportCenter sportCenter;
  
  @OneToOne(optional = true) //a user can at most have 1 account
  @JoinColumn(name = "user_id") //user_id is a FK
  private User user;

  
  //CONSTRUCTORS

  public Owner() {
    
  }

  public Owner(int aAccountId, SportCenter aSportCenter, User aUser)
  {
    super(aAccountId);
    if (aSportCenter == null || aSportCenter.getOwner() != null)
    {
      throw new RuntimeException("Unable to create Owner due to aSportCenter.");
    }
    sportCenter = aSportCenter;
    if (!setUser(aUser))
    {
      throw new RuntimeException("Unable to create Owner due to aUser.");
    }
  }

  public Owner(int aAccountId, User aUser)
  {
    super(aAccountId);
    sportCenter = new SportCenter(this);
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create owner due to user.");
    }
  }

  //GETTERS

  public SportCenter getSportCenter()
  {
    return sportCenter;
  }


  public User getUser()
  {
    return user;
  }

  //SETTERS

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


}