package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Owner extends Account
{

  @OneToOne(optional = false) //sport center can only have one owner
  private SportCenter sportCenter;
  @OneToOne(optional = true) //a user can at most have 1 account
  private User user;


  public Owner(int aAccountId, SportCenter aSportCenter, User aUser)
  {
    super(aAccountId);
    if (aSportCenter == null || aSportCenter.getOwner() != null)
    {
      throw new RuntimeException("Unable to create Owner due to aSportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    sportCenter = aSportCenter;
    if (!setUser(aUser))
    {
      throw new RuntimeException("Unable to create Owner due to aUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Owner(int aAccountId, User aUser)
  {
    super(aAccountId);
    sportCenter = new SportCenter(this);
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create owner due to user. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public SportCenter getSportCenter()
  {
    return sportCenter;
  }
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }
  /* Code from template association_SetUnidirectionalOne */
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