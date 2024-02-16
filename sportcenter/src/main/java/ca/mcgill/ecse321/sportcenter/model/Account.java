package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public abstract class Account
{

  @Id
  @GeneratedValue
  private int accountId;

  public Account(int aAccountId)
  {
    accountId = aAccountId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAccountId(int aAccountId)
  {
    boolean wasSet = false;
    accountId = aAccountId;
    wasSet = true;
    return wasSet;
  }

  public int getAccountId()
  {
    return accountId;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "accountId" + ":" + getAccountId()+ "]";
  }
}