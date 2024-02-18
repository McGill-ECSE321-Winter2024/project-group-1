package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Customer extends AccountRole
{

  @ManyToOne(optional = false) //many customers in a sport center
  private SportCenter sportCenter;
  
  @OneToOne(optional = true) //an account can at most have 1 customer account role
  @JoinColumn(name = "account_id") //account_id is a FK
  private Account account;

  
  //CONSTRUCTORS

  public Customer() {
    
  }

  public Customer(int aAccountRoleId, SportCenter aSportCenter, Account aAccount)
  {
    super(aAccountRoleId);
    
    if (!setAccount(aAccount))
    {
      throw new RuntimeException("Unable to create Customer due to aAccount.");
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