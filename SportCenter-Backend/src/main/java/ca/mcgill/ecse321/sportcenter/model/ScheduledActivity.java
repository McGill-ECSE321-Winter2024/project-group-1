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

  @ManyToOne(optional = false) //there are many scheduled activities for a sport center
  private SportCenter sportCenter;
 
  @ManyToOne(optional = false) //an instructor can teach many scheduled activities
  private Instructor supervisor;
 
  @ManyToOne(optional = false) //a scheduled activity consists of one activity, yet activities can have many Sched. activities
  @JoinColumn(name = "activity_name") //activity is a FK
  private Activity activity;

  
  //CONSTRUCTORS

  public ScheduledActivity() {
    
  }

  public ScheduledActivity(int aScheduledActivityId, LocalDate aDate, LocalTime aStartTime, LocalTime aEndTime, SportCenter aSportCenter, Instructor aAccounts, Activity aActivity)
  {
    scheduledActivityId = aScheduledActivityId;
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;

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

  public SportCenter getSportCenter()
  {
    return sportCenter;
  }

  public Instructor getSupervisor()
  {
    return supervisor;
  }

  public Activity getActivity()
  {
    return activity;
  }


}