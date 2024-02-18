package ca.mcgill.ecse321.sportcenter.model;
import java.sql.Date;
import java.sql.Time;

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
  private Date date;
  private Time startTime;
  private Time endTime;

  @ManyToOne(optional = false) //there are many scheduled activities for a sport center
  private SportCenter sportCenter;
  @ManyToOne(optional = false) //an instructor can teach many scheduled activities
  private Instructor accounts;
  @ManyToOne(optional = false) //a scheduled activity consists of one activity, yet activities can have many Sched. activities
  @JoinColumn(name = "activity_name") //activity is a FK
  private Activity activity;

  
  //CONSTRUCTORS

  public ScheduledActivity() {
    
  }

  public ScheduledActivity(int aScheduledActivityId, Date aDate, Time aStartTime, Time aEndTime, SportCenter aSportCenter, Instructor aAccounts, Activity aActivity)
  {
    scheduledActivityId = aScheduledActivityId;
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;

    if (!setAccounts(aAccounts))
    {
      throw new RuntimeException("Unable to create ScheduledActivity due to aAccounts. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setActivity(aActivity))
    {
      throw new RuntimeException("Unable to create ScheduledActivity due to aActivity. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public boolean setScheduledActivityId(int aScheduledActivityId)
  {
    boolean wasSet = false;
    scheduledActivityId = aScheduledActivityId;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public int getScheduledActivityId()
  {
    return scheduledActivityId;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }
  /* Code from template association_GetOne */
  public SportCenter getSportCenter()
  {
    return sportCenter;
  }
  /* Code from template association_GetOne */
  public Instructor getAccounts()
  {
    return accounts;
  }
  /* Code from template association_GetOne */
  public Activity getActivity()
  {
    return activity;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setAccounts(Instructor aNewAccounts)
  {
    boolean wasSet = false;
    if (aNewAccounts != null)
    {
      accounts = aNewAccounts;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
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

}