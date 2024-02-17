package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass //abstract class
public abstract class Account
{

  @Id //accountId is the PM
  @GeneratedValue(strategy = GenerationType.AUTO) 
  private int accountId;

  public Account(int aAccountId)
  {
    accountId = aAccountId;
  }

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

  public String toString()
  {
    return super.toString() + "["+
            "accountId" + ":" + getAccountId()+ "]";
  }
}