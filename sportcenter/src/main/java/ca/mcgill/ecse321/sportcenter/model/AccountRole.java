package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass // abstract class
public abstract class AccountRole {

  @Id // accountId is the PM
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int accountRoleId;

  // CONSTRUCTOR

  public AccountRole() {

  }

  // SETTERS

  public boolean setAccountRoleId(int aAccountRoleId) {
    accountRoleId = aAccountRoleId;
    boolean wasSet = true;
    return wasSet;
  }

  // GETTERS

  public int getAccountRoleId() {
    return accountRoleId;
  }

}