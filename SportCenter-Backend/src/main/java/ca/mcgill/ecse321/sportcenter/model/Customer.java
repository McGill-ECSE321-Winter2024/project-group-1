package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Customer extends Account
{

  @ManyToOne(optional = false) //many customers in a sport center
  private SportCenter sportCenter;
  
  @OneToOne(optional = true) //a user can at most have 1 customer account
  @JoinColumn(name = "user_id") //user_id is a FK
  private User user;

  
  //CONSTRUCTORS

  public Customer() {
    
  }

  public Customer(int aAccountId, SportCenter aSportCenter, User aUser)
  {
    super(aAccountId);
    

    if (!setUser(aUser))
    {
      throw new RuntimeException("Unable to create Customer due to aUser.");
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