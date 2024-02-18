package ca.mcgill.ecse321.sportcenter.model;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class SportCenter
{

  //all associations. Comments are made in their own java class

  @OneToMany
  private List<Account> users; 
  @OneToOne
  private Owner owner;
  @OneToMany
  private List<Instructor> instructors;
  @OneToMany
  private List<Customer> customers;
  @OneToMany
  private List<Registration> registrations;
  @OneToMany
  private List<ScheduledActivity> scheduledActivities;
  @OneToMany
  private List<Activity> activities;

  //CONSTRUCTORS

  public SportCenter() {

  }

  public SportCenter(Owner aOwner)
  {
    users = new ArrayList<Account>();
    if (aOwner == null || aOwner.getSportCenter() != null)
    {
      throw new RuntimeException("Unable to create SportCenter due to aOwner.");
    }
    owner = aOwner;
    instructors = new ArrayList<Instructor>();
    customers = new ArrayList<Customer>();
    registrations = new ArrayList<Registration>();
    scheduledActivities = new ArrayList<ScheduledActivity>();
    activities = new ArrayList<Activity>();
  }

  public SportCenter(int aAccountIdForOwner, Account aUserForOwner)
  {
    users = new ArrayList<Account>();
    owner = new Owner(aAccountIdForOwner, this, aUserForOwner);
    instructors = new ArrayList<Instructor>();
    customers = new ArrayList<Customer>();
    registrations = new ArrayList<Registration>();
    scheduledActivities = new ArrayList<ScheduledActivity>();
    activities = new ArrayList<Activity>();
  }

  //GETTERS

  public Account getUser(int index)
  {
    Account aUser = users.get(index);
    return aUser;
  }

  public List<Account> getUsers()
  {
    List<Account> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public Owner getOwner()
  {
    return owner;
  }

  public Instructor getInstructor(int index)
  {
    Instructor aInstructor = instructors.get(index);
    return aInstructor;
  }

  public List<Instructor> getInstructors()
  {
    List<Instructor> newInstructors = Collections.unmodifiableList(instructors);
    return newInstructors;
  }

  public Customer getCustomer(int index)
  {
    Customer aCustomer = customers.get(index);
    return aCustomer;
  }

  public List<Customer> getCustomers()
  {
    List<Customer> newCustomers = Collections.unmodifiableList(customers);
    return newCustomers;
  }

  public Registration getRegistration(int index)
  {
    Registration aRegistration = registrations.get(index);
    return aRegistration;
  }

  public List<Registration> getRegistrations()
  {
    List<Registration> newRegistrations = Collections.unmodifiableList(registrations);
    return newRegistrations;
  }

  public ScheduledActivity getScheduledActivity(int index)
  {
    ScheduledActivity aScheduledActivity = scheduledActivities.get(index);
    return aScheduledActivity;
  }

  public List<ScheduledActivity> getScheduledActivities()
  {
    List<ScheduledActivity> newScheduledActivities = Collections.unmodifiableList(scheduledActivities);
    return newScheduledActivities;
  }


}