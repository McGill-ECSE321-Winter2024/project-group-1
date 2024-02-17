package ca.mcgill.ecse321.sportcenter.model;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

//import java.sql.Date;
//import java.sql.Time;

@Entity
public class SportCenter
{

  //all associations. Comments are made in their own java class

  @OneToMany
  private List<User> users; 
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
    users = new ArrayList<User>();
    if (aOwner == null || aOwner.getSportCenter() != null)
    {
      throw new RuntimeException("Unable to create SportCenter due to aOwner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    owner = aOwner;
    instructors = new ArrayList<Instructor>();
    customers = new ArrayList<Customer>();
    registrations = new ArrayList<Registration>();
    scheduledActivities = new ArrayList<ScheduledActivity>();
    activities = new ArrayList<Activity>();
  }

  public SportCenter(int aAccountIdForOwner, User aUserForOwner)
  {
    users = new ArrayList<User>();
    owner = new Owner(aAccountIdForOwner, this, aUserForOwner);
    instructors = new ArrayList<Instructor>();
    customers = new ArrayList<Customer>();
    registrations = new ArrayList<Registration>();
    scheduledActivities = new ArrayList<ScheduledActivity>();
    activities = new ArrayList<Activity>();
  }


  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_GetOne */
  public Owner getOwner()
  {
    return owner;
  }
  /* Code from template association_GetMany */
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

  public int numberOfInstructors()
  {
    int number = instructors.size();
    return number;
  }

  public boolean hasInstructors()
  {
    boolean has = instructors.size() > 0;
    return has;
  }

  public int indexOfInstructor(Instructor aInstructor)
  {
    int index = instructors.indexOf(aInstructor);
    return index;
  }
  /* Code from template association_GetMany */
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

  public int numberOfCustomers()
  {
    int number = customers.size();
    return number;
  }

  public boolean hasCustomers()
  {
    boolean has = customers.size() > 0;
    return has;
  }

  public int indexOfCustomer(Customer aCustomer)
  {
    int index = customers.indexOf(aCustomer);
    return index;
  }
  /* Code from template association_GetMany */
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

  public int numberOfRegistrations()
  {
    int number = registrations.size();
    return number;
  }

  public boolean hasRegistrations()
  {
    boolean has = registrations.size() > 0;
    return has;
  }

  public int indexOfRegistration(Registration aRegistration)
  {
    int index = registrations.indexOf(aRegistration);
    return index;
  }
  /* Code from template association_GetMany */
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