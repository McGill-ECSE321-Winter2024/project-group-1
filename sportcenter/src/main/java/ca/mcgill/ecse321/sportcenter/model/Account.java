package ca.mcgill.ecse321.sportcenter.model;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account
{


  private static Map<String, Account> accountsByUsername = new HashMap<String, Account>();

  //User Attributes
  private String username; // unique turn into primary key? -Patrick
  private String password;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO) 
  private int accountId;

  //CONSTRUCTORS

  public Account() {

  }

  public Account(String aUsername, String aPassword)
  {
    password = aPassword;
    if (!setUsername(aUsername))
    {
      throw new RuntimeException("Cannot create due to duplicate username.");
    }
    
  }

  //SETTERS

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    String anOldUsername = getUsername();
    if (anOldUsername != null && anOldUsername.equals(aUsername)) {
      return true;
    }
    if (hasWithUsername(aUsername)) {
      return wasSet;
    }
    username = aUsername;
    wasSet = true;
    if (anOldUsername != null) {
      accountsByUsername.remove(anOldUsername);
    }
    accountsByUsername.put(aUsername, this);
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setAccountId(int aAccountId)
  {
    boolean wasSet = false;
    accountId = aAccountId;
    wasSet = true;
    return wasSet;
  }

  //GETTERS

  public String getUsername()
  {
    return username;
  }
  
  public static Account getWithUsername(String aUsername)
  {
    return accountsByUsername.get(aUsername);
  }
  
  public static boolean hasWithUsername(String aUsername)
  {
    return getWithUsername(aUsername) != null;
  }

  public String getPassword()
  {
    return password;
  }

  public int getAccountId()
  {
    return accountId;
  }

}