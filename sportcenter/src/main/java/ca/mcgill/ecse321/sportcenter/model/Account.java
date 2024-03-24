package ca.mcgill.ecse321.sportcenter.model;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {

  // User Attributes
  private String username;
  private String password;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int accountId;

  // CONSTRUCTORS

  public Account() {

  }

  public Account(String aUsername, String aPassword) {
    password = aPassword;
    if (!setUsername(aUsername)) {
      throw new RuntimeException("Cannot create due to duplicate username.");
    }

  }

  // SETTERS

  public boolean setAccountId(int aAccountId) {
    boolean wasSet = false;
    accountId = aAccountId;
    wasSet = true;
    return wasSet;
  }

  public boolean setUsername(String aUsername) {
    boolean wasSet = false;
    if (aUsername == null) {
      return wasSet;
    }
    username = aUsername;

    return !wasSet;
  }

  public boolean setPassword(String aPassword) {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  // GETTERS

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public int getAccountId() {
    return accountId;
  }

}