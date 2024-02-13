package ca.mcgill.ecse321.sportcenter.model;

public abstract class Account
{
  private User user;

  public Account(User aUser)
  {
    if (!setUser(aUser))
    {
      throw new RuntimeException("Unable to create Account due to aUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public User getUser()
  {
    return user;
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

}