package ca.mcgill.ecse321.sportcenter.model;

public class Activity
{

  public enum ClassCategory { Strength, Cardio, Stretch }

  private ClassCategory subcategory;
  private String name;
  private boolean isApproved;

  private SportCenter sportCenter;

  public Activity(ClassCategory aSubcategory, String aName, boolean aIsApproved, SportCenter aSportCenter)
  {
    subcategory = aSubcategory;
    name = aName;
    isApproved = aIsApproved;
    boolean didAddSportCenter = setSportCenter(aSportCenter);
    if (!didAddSportCenter)
    {
      throw new RuntimeException("Unable to create activity due to sportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public boolean setSubcategory(ClassCategory aSubcategory)
  {
    boolean wasSet = false;
    subcategory = aSubcategory;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsApproved(boolean aIsApproved)
  {
    boolean wasSet = false;
    isApproved = aIsApproved;
    wasSet = true;
    return wasSet;
  }

  public ClassCategory getSubcategory()
  {
    return subcategory;
  }

  public String getName()
  {
    return name;
  }

  public boolean getIsApproved()
  {
    return isApproved;
  }

  public SportCenter getSportCenter()
  {
    return sportCenter;
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
      existingSportCenter.removeActivity(this);
    }
    sportCenter.addActivity(this);
    wasSet = true;
    return wasSet;
  }

  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "isApproved" + ":" + getIsApproved()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "subcategory" + "=" + (getSubcategory() != null ? !getSubcategory().equals(this)  ? getSubcategory().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "sportCenter = "+(getSportCenter()!=null?Integer.toHexString(System.identityHashCode(getSportCenter())):"null");
  }
}