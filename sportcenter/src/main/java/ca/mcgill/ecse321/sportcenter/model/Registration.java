package ca.mcgill.ecse321.sportcenter.model;

public class Registration
{

  private static int nextRegId = 1;

  private int regId;

  private ScheduledActivity scheduledActivity;
  private Customer customer;
  private SportCenter sportCenter;

  public Registration(ScheduledActivity aScheduledActivity, Customer aCustomer, SportCenter aSportCenter)
  {
    regId = nextRegId++;
    if (!setScheduledActivity(aScheduledActivity))
    {
      throw new RuntimeException("Unable to create Registration due to aScheduledActivity. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCustomer(aCustomer))
    {
      throw new RuntimeException("Unable to create Registration due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddSportCenter = setSportCenter(aSportCenter);
    if (!didAddSportCenter)
    {
      throw new RuntimeException("Unable to create registration due to sportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
      existingSportCenter.removeRegistration(this);
    }
    sportCenter.addRegistration(this);
    wasSet = true;
    return wasSet;
  }

  public String toString()
  {
    return super.toString() + "["+
            "regId" + ":" + getRegId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "scheduledActivity = "+(getScheduledActivity()!=null?Integer.toHexString(System.identityHashCode(getScheduledActivity())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "sportCenter = "+(getSportCenter()!=null?Integer.toHexString(System.identityHashCode(getSportCenter())):"null");
  }
}