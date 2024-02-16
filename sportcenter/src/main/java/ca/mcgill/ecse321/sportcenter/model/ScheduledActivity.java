/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

package ca.mcgill.ecse321.sportcenter.model;
import java.sql.Date;
import java.sql.Time;

// line 59 "../../../../../../model.ump"
// line 137 "../../../../../../model.ump"
public class ScheduledActivity
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ScheduledActivity Attributes
  private int scheduledActivityId;
  private Date date;
  private Time startTime;
  private Time endTime;

  //ScheduledActivity Associations
  private SportCenter sportCenter;
  private Instructor accounts;
  private Activity activity;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ScheduledActivity(int aScheduledActivityId, Date aDate, Time aStartTime, Time aEndTime, SportCenter aSportCenter, Instructor aAccounts, Activity aActivity)
  {
    scheduledActivityId = aScheduledActivityId;
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;
    boolean didAddSportCenter = setSportCenter(aSportCenter);
    if (!didAddSportCenter)
    {
      throw new RuntimeException("Unable to create scheduledActivity due to sportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setAccounts(aAccounts))
    {
      throw new RuntimeException("Unable to create ScheduledActivity due to aAccounts. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setActivity(aActivity))
    {
      throw new RuntimeException("Unable to create ScheduledActivity due to aActivity. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  /* Code from template association_SetOneToMany */
  public boolean setSportCenter(SportCenter aSportCenter)
  {
    boolean wasSet = false;
    if (aSportCenter == null)
    {
      return wasSet;
    }

    SportCenter existingSportCenter = sportCenter;
    sportCenter = aSportCenter;
    if (existingSportCenter != null && !existingSportCenter.equals(aSportCenter))
    {
      existingSportCenter.removeScheduledActivity(this);
    }
    sportCenter.addScheduledActivity(this);
    wasSet = true;
    return wasSet;
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

  public void delete()
  {
    SportCenter placeholderSportCenter = sportCenter;
    this.sportCenter = null;
    if(placeholderSportCenter != null)
    {
      placeholderSportCenter.removeScheduledActivity(this);
    }
    accounts = null;
    activity = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "scheduledActivityId" + ":" + getScheduledActivityId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "sportCenter = "+(getSportCenter()!=null?Integer.toHexString(System.identityHashCode(getSportCenter())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "accounts = "+(getAccounts()!=null?Integer.toHexString(System.identityHashCode(getAccounts())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "activity = "+(getActivity()!=null?Integer.toHexString(System.identityHashCode(getActivity())):"null");
  }
}