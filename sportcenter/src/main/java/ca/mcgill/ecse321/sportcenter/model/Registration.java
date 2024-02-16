/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

package ca.mcgill.ecse321.sportcenter.model;

// line 51 "../../../../../../model.ump"
// line 128 "../../../../../../model.ump"
public class Registration
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Registration Attributes
  private int regId;

  //Registration Associations
  private ScheduledActivity scheduledActivity;
  private Customer customer;
  private SportCenter sportCenter;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Registration(int aRegId, ScheduledActivity aScheduledActivity, Customer aCustomer, SportCenter aSportCenter)
  {
    regId = aRegId;
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

  //------------------------
  // INTERFACE
  //------------------------

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
  /* Code from template association_GetOne */
  public ScheduledActivity getScheduledActivity()
  {
    return scheduledActivity;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public SportCenter getSportCenter()
  {
    return sportCenter;
  }
  /* Code from template association_SetUnidirectionalOne */
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
  /* Code from template association_SetUnidirectionalOne */
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
      existingSportCenter.removeRegistration(this);
    }
    sportCenter.addRegistration(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    scheduledActivity = null;
    customer = null;
    SportCenter placeholderSportCenter = sportCenter;
    this.sportCenter = null;
    if(placeholderSportCenter != null)
    {
      placeholderSportCenter.removeRegistration(this);
    }
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