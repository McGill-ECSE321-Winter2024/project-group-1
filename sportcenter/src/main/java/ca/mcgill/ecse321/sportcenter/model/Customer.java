package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Customer extends AccountRole {

  @OneToOne(optional = true) // an account can at most have 1 customer account role
  @JoinColumn(name = "accountId") // account_id is a FK
  private Account account;

  // CONSTRUCTORS

  public Customer() {

  }

  public Customer(Account aAccount) {

    if (!setAccount(aAccount)) {
      throw new RuntimeException("Unable to create Customer due to aAccount.");
    }
  }

  // GETTERS

  public Account getAccount() {
    return account;
  }

  // SETTERS

  public boolean setAccount(Account aNewAccount) {
    boolean wasSet = false;
    if (aNewAccount != null) {
      account = aNewAccount;
      wasSet = true;
    }
    return wasSet;
  }

}