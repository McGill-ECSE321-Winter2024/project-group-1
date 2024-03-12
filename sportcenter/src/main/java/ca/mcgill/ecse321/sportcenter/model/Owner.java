package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Owner extends AccountRole
{
  
  @OneToOne(optional = true) //an account can have at most 1 owner account role
  @JoinColumn(name = "accountId") //account_id is a FK
  private Account account;

  
  //CONSTRUCTORS

  public Owner() {
    
  }

  public Owner(Account aAccount)
  {
    boolean didAddUser = setAccount(aAccount);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create owner due to aAccount.");
    }
  }

  //GETTERS

  public Account getAccount()
  {
    return account;
  }

  //SETTERS

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