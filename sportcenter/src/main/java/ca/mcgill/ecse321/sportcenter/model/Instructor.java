package ca.mcgill.ecse321.sportcenter.model;

public class Instructor extends Account
{

  public enum InstructorStatus { Active, Inactive, Fired, Suspended }

  private InstructorStatus status;
  private String description;
  private String profilePicURL;

  private SportCenter sportCenter;

  public Instructor(User aUser, InstructorStatus aStatus, String aDescription, String aProfilePicURL, SportCenter aSportCenter)
  {
    super(aUser);
    status = aStatus;
    description = aDescription;
    profilePicURL = aProfilePicURL;
    boolean didAddSportCenter = setSportCenter(aSportCenter);
    if (!didAddSportCenter)
    {
      throw new RuntimeException("Unable to create instructor due to sportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public boolean setStatus(InstructorStatus aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setProfilePicURL(String aProfilePicURL)
  {
    boolean wasSet = false;
    profilePicURL = aProfilePicURL;
    wasSet = true;
    return wasSet;
  }

  public InstructorStatus getStatus()
  {
    return status;
  }

  public String getDescription()
  {
    return description;
  }

  public String getProfilePicURL()
  {
    return profilePicURL;
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
      existingSportCenter.removeInstructor(this);
    }
    sportCenter.addInstructor(this);
    wasSet = true;
    return wasSet;
  }
  
  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "profilePicURL" + ":" + getProfilePicURL()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "sportCenter = "+(getSportCenter()!=null?Integer.toHexString(System.identityHashCode(getSportCenter())):"null");
  }
}