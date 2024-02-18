package ca.mcgill.ecse321.sportcenter.model;


//import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Registration
{

  @Id //regId will be PM
  @GeneratedValue(strategy = GenerationType.AUTO) 
  private int regId;

  @ManyToOne(optional = false) //there are many registration for a schedule activity
  @JoinColumn(name = "schedule_activity_id") //schedule_activity_id is a FK
  private ScheduledActivity scheduledActivity;

  @ManyToOne(optional = false) //there are many customer performing a registration
  @JoinColumn(name = "customer_id") //customer_id is a FK
  private Customer customer;

  @ManyToOne(optional = false) //there are many registration for a sport center
  private SportCenter sportCenter;

  
  //CONSTRUCTORS

  public Registration() {
    
  }

  public Registration(int aRegId, ScheduledActivity aScheduledActivity, Customer aCustomer, SportCenter aSportCenter)
  {
    regId = aRegId;
    if (!setScheduledActivity(aScheduledActivity))
    {
      throw new RuntimeException("Unable to create Registration due to aScheduledActivity.");
    }
    if (!setCustomer(aCustomer))
    {
      throw new RuntimeException("Unable to create Registration due to aCustomer.");
    }

  }

  //GETTERS

  public boolean setRegId(int aRegId)
  {
    boolean wasSet = false;
    regId = aRegId;
    wasSet = true;
    return wasSet;
  }

  public int getRegId()
  {
    return regId;
  }

  public ScheduledActivity getScheduledActivity()
  {
    return scheduledActivity;
  }
 
  public Customer getCustomer()
  {
    return customer;
  }

  public SportCenter getSportCenter()
  {
    return sportCenter;
  }

  //SETTERS

  public boolean setScheduledActivity(ScheduledActivity aNewScheduledActivity)
  {
    boolean wasSet = false;
    if (aNewScheduledActivity != null)
    {
      scheduledActivity = aNewScheduledActivity;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setCustomer(Customer aNewCustomer)
  {
    boolean wasSet = false;
    if (aNewCustomer != null)
    {
      customer = aNewCustomer;
      wasSet = true;
    }
    return wasSet;
  }


}