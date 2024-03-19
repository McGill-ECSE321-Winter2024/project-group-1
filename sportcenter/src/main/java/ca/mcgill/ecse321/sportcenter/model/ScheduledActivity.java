package ca.mcgill.ecse321.sportcenter.model;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ScheduledActivity
{

  @Id //scheduledActivityId will be PM
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int scheduledActivityId;
  
  //attributes
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private int capacity;
 
  @ManyToOne(optional = false) //an instructor can teach many scheduled activities
  private Instructor supervisor;
 
  @ManyToOne(optional = false) //a scheduled activity consists of one activity, yet activities can have many Sched. activities
  @JoinColumn(name = "name") //activity is a FK
  private Activity activity;

  
  //CONSTRUCTORS

  public ScheduledActivity() {
    
  }

  public ScheduledActivity(LocalDate aDate, LocalTime aStartTime, LocalTime aEndTime, Instructor aAccounts, Activity aActivity, int aCapacity)
  {
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;
    capacity = aCapacity;

    if (!setSupervisor(aAccounts))
    {
      throw new RuntimeException("Unable to create ScheduledActivity due to aAccounts.");
    }
    if (!setActivity(aActivity))
    {
      throw new RuntimeException("Unable to create ScheduledActivity due to aActivity.");
    }
  }

  //SETTERS

  public boolean setScheduledActivityId(int aScheduledActivityId)
  {
    boolean wasSet = false;
    scheduledActivityId = aScheduledActivityId;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(LocalDate aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(LocalTime aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(LocalTime aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setSupervisor(Instructor aNewAccounts)
  {
    boolean wasSet = false;
    if (aNewAccounts != null)
    {
      supervisor = aNewAccounts;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setActivity(Activity aNewActivity)
  {
    boolean wasSet = false;
    if (aNewActivity != null)
    {
      activity = aNewActivity;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setCapacity(int aCapacity)
  {
    boolean wasSet = false;
    capacity = aCapacity;
    wasSet = true;
    return wasSet;
  }

  //GETTERS

  public int getScheduledActivityId()
  {
    return scheduledActivityId;
  }

  public LocalDate getDate()
  {
    return date;
  }

  public LocalTime getStartTime()
  {
    return startTime;
  }

  public LocalTime getEndTime()
  {
    return endTime;
  }
  
  public Instructor getSupervisor()
  {
    return supervisor;
  }

  public Activity getActivity()
  {
    return activity;
  }

  public int getCapacity()
  {
    return capacity;
  }

}