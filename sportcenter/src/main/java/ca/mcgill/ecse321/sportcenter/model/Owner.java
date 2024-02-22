package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Owner extends AccountRole
{

  @OneToOne(optional = false) //sport center can only have one owner
  private SportCenter sportCenter;
  
  @OneToOne(optional = true) //an account can have at most 1 owner account role
  @JoinColumn(name = "account_id") //account_id is a FK
  private Account account;

  
  //CONSTRUCTORS

  public Owner() {
    
  }

  public Owner(int aAccountRoleId, SportCenter aSportCenter, Account aAccount)
  {
    super(aAccountRoleId);
    if (aSportCenter == null || aSportCenter.getOwner() != null)
    {
      throw new RuntimeException("Unable to create Owner due to aSportCenter.");
    }
    sportCenter = aSportCenter;
    if (!setAccount(aAccount))
    {
      throw new RuntimeException("Unable to create Owner due to aAccount.");
    }
  }

  public Owner(int aAccountRoleId, Account aAccount)
  {
    super(aAccountRoleId);
    sportCenter = new SportCenter(this);
    boolean didAddUser = setAccount(aAccount);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create owner due to aAccount.");
    }
  }

  //GETTERS

  public SportCenter getSportCenter()
  {
    return sportCenter;
  }


  public Account getAccount()
  {
    return account;
  }

  //SETTERS

  public boolean setSportCenter(SportCenter aSportCenter)
  {
    boolean wasSet = false;
    sportCenter = aSportCenter;
    wasSet = true;
    return wasSet;
  }

  public boolean setAccount(Account aNewAccount)
  {
    boolean wasSet = false;
    if (aNewAccount != null)
    {
      account = aNewAccount;
      wasSet = true;
    }
    return wasSet;
  }


}