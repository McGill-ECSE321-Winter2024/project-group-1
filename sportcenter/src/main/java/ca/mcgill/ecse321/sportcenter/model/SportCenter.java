package ca.mcgill.ecse321.sportcenter.model;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

public class SportCenter
{
  private List<User> users;
  private Owner owner;
  private List<Instructor> instructors;
  private List<Customer> customers;
  private List<Registration> registrations;
  private List<ScheduledActivity> scheduledActivities;
  private List<Activity> activities;

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

  public SportCenter(User aUserForOwner)
  {
    users = new ArrayList<User>();
    owner = new Owner(aUserForOwner, this);
    instructors = new ArrayList<Instructor>();
    customers = new ArrayList<Customer>();
    registrations = new ArrayList<Registration>();
    scheduledActivities = new ArrayList<ScheduledActivity>();
    activities = new ArrayList<Activity>();
  }

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

  public int numberOfScheduledActivities()
  {
    int number = scheduledActivities.size();
    return number;
  }

  public boolean hasScheduledActivities()
  {
    boolean has = scheduledActivities.size() > 0;
    return has;
  }

  public int indexOfScheduledActivity(ScheduledActivity aScheduledActivity)
  {
    int index = scheduledActivities.indexOf(aScheduledActivity);
    return index;
  }

  public Activity getActivity(int index)
  {
    Activity aActivity = activities.get(index);
    return aActivity;
  }

  public List<Activity> getActivities()
  {
    List<Activity> newActivities = Collections.unmodifiableList(activities);
    return newActivities;
  }

  public int numberOfActivities()
  {
    int number = activities.size();
    return number;
  }

  public boolean hasActivities()
  {
    boolean has = activities.size() > 0;
    return has;
  }

  public int indexOfActivity(Activity aActivity)
  {
    int index = activities.indexOf(aActivity);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }

  public User addUser(String aUsername, String aPassword)
  {
    return new User(aUsername, aPassword, this);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    SportCenter existingSportCenter = aUser.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter)
    {
      aUser.setSportCenter(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;

    if (!this.equals(aUser.getSportCenter()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfInstructors()
  {
    return 0;
  }

  public Instructor addInstructor(User aUser, Instructor.InstructorStatus aStatus, String aDescription, String aProfilePicURL)
  {
    return new Instructor(aUser, aStatus, aDescription, aProfilePicURL, this);
  }

  public boolean addInstructor(Instructor aInstructor)
  {
    boolean wasAdded = false;
    if (instructors.contains(aInstructor)) { return false; }
    SportCenter existingSportCenter = aInstructor.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter)
    {
      aInstructor.setSportCenter(this);
    }
    else
    {
      instructors.add(aInstructor);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeInstructor(Instructor aInstructor)
  {
    boolean wasRemoved = false;

    if (!this.equals(aInstructor.getSportCenter()))
    {
      instructors.remove(aInstructor);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addInstructorAt(Instructor aInstructor, int index)
  {  
    boolean wasAdded = false;
    if(addInstructor(aInstructor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInstructors()) { index = numberOfInstructors() - 1; }
      instructors.remove(aInstructor);
      instructors.add(index, aInstructor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveInstructorAt(Instructor aInstructor, int index)
  {
    boolean wasAdded = false;
    if(instructors.contains(aInstructor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInstructors()) { index = numberOfInstructors() - 1; }
      instructors.remove(aInstructor);
      instructors.add(index, aInstructor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addInstructorAt(aInstructor, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfCustomers()
  {
    return 0;
  }

  public Customer addCustomer(User aUser)
  {
    return new Customer(aUser, this);
  }

  public boolean addCustomer(Customer aCustomer)
  {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) { return false; }
    SportCenter existingSportCenter = aCustomer.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter)
    {
      aCustomer.setSportCenter(this);
    }
    else
    {
      customers.add(aCustomer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomer(Customer aCustomer)
  {
    boolean wasRemoved = false;
    if (!this.equals(aCustomer.getSportCenter()))
    {
      customers.remove(aCustomer);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addCustomerAt(Customer aCustomer, int index)
  {  
    boolean wasAdded = false;
    if(addCustomer(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerAt(Customer aCustomer, int index)
  {
    boolean wasAdded = false;
    if(customers.contains(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCustomerAt(aCustomer, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfRegistrations()
  {
    return 0;
  }

  public Registration addRegistration(ScheduledActivity aScheduledActivity, Customer aCustomer)
  {
    return new Registration(aScheduledActivity, aCustomer, this);
  }

  public boolean addRegistration(Registration aRegistration)
  {
    boolean wasAdded = false;
    if (registrations.contains(aRegistration)) { return false; }
    SportCenter existingSportCenter = aRegistration.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter)
    {
      aRegistration.setSportCenter(this);
    }
    else
    {
      registrations.add(aRegistration);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRegistration(Registration aRegistration)
  {
    boolean wasRemoved = false;
    if (!this.equals(aRegistration.getSportCenter()))
    {
      registrations.remove(aRegistration);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addRegistrationAt(Registration aRegistration, int index)
  {  
    boolean wasAdded = false;
    if(addRegistration(aRegistration))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRegistrations()) { index = numberOfRegistrations() - 1; }
      registrations.remove(aRegistration);
      registrations.add(index, aRegistration);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRegistrationAt(Registration aRegistration, int index)
  {
    boolean wasAdded = false;
    if(registrations.contains(aRegistration))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRegistrations()) { index = numberOfRegistrations() - 1; }
      registrations.remove(aRegistration);
      registrations.add(index, aRegistration);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRegistrationAt(aRegistration, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfScheduledActivities()
  {
    return 0;
  }

  public ScheduledActivity addScheduledActivity(Date aDate, Time aStartTime, Time aEndTime, Instructor aAccounts, Activity aActivity)
  {
    return new ScheduledActivity(aDate, aStartTime, aEndTime, this, aAccounts, aActivity);
  }

  public boolean addScheduledActivity(ScheduledActivity aScheduledActivity)
  {
    boolean wasAdded = false;
    if (scheduledActivities.contains(aScheduledActivity)) { return false; }
    SportCenter existingSportCenter = aScheduledActivity.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter)
    {
      aScheduledActivity.setSportCenter(this);
    }
    else
    {
      scheduledActivities.add(aScheduledActivity);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeScheduledActivity(ScheduledActivity aScheduledActivity)
  {
    boolean wasRemoved = false;
    if (!this.equals(aScheduledActivity.getSportCenter()))
    {
      scheduledActivities.remove(aScheduledActivity);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addScheduledActivityAt(ScheduledActivity aScheduledActivity, int index)
  {  
    boolean wasAdded = false;
    if(addScheduledActivity(aScheduledActivity))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfScheduledActivities()) { index = numberOfScheduledActivities() - 1; }
      scheduledActivities.remove(aScheduledActivity);
      scheduledActivities.add(index, aScheduledActivity);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveScheduledActivityAt(ScheduledActivity aScheduledActivity, int index)
  {
    boolean wasAdded = false;
    if(scheduledActivities.contains(aScheduledActivity))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfScheduledActivities()) { index = numberOfScheduledActivities() - 1; }
      scheduledActivities.remove(aScheduledActivity);
      scheduledActivities.add(index, aScheduledActivity);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addScheduledActivityAt(aScheduledActivity, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfActivities()
  {
    return 0;
  }

  public Activity addActivity(Activity.ClassCategory aSubcategory, String aName, boolean aIsApproved)
  {
    return new Activity(aSubcategory, aName, aIsApproved, this);
  }

  public boolean addActivity(Activity aActivity)
  {
    boolean wasAdded = false;
    if (activities.contains(aActivity)) { return false; }
    SportCenter existingSportCenter = aActivity.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter)
    {
      aActivity.setSportCenter(this);
    }
    else
    {
      activities.add(aActivity);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeActivity(Activity aActivity)
  {
    boolean wasRemoved = false;
    if (!this.equals(aActivity.getSportCenter()))
    {
      activities.remove(aActivity);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addActivityAt(Activity aActivity, int index)
  {  
    boolean wasAdded = false;
    if(addActivity(aActivity))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfActivities()) { index = numberOfActivities() - 1; }
      activities.remove(aActivity);
      activities.add(index, aActivity);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveActivityAt(Activity aActivity, int index)
  {
    boolean wasAdded = false;
    if(activities.contains(aActivity))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfActivities()) { index = numberOfActivities() - 1; }
      activities.remove(aActivity);
      activities.add(index, aActivity);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addActivityAt(aActivity, index);
    }
    return wasAdded;
  }

}