package ca.mcgill.ecse321.sportcenter.model;

public class Instructor extends Account
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum InstructorStatus { Active, Inactive, Fired, Suspended }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Instructor Attributes
  private InstructorStatus status;
  private String description;
  private String profilePicURL;

  //Instructor Associations
  private SportCenter sportCenter;
  private User user;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Instructor(int aAccountId, InstructorStatus aStatus, String aDescription, String aProfilePicURL, SportCenter aSportCenter, User aUser)
  {
    super(aAccountId);
    status = aStatus;
    description = aDescription;
    profilePicURL = aProfilePicURL;
    boolean didAddSportCenter = setSportCenter(aSportCenter);
    if (!didAddSportCenter)
    {
      throw new RuntimeException("Unable to create instructor due to sportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setUser(aUser))
    {
      throw new RuntimeException("Unable to create Instructor due to aUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  /* Code from template association_GetOne */
  public SportCenter getSportCenter()
  {
    return sportCenter;
  }
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
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
      existingSportCenter.removeInstructor(this);
    }
    sportCenter.addInstructor(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setUser(User aNewUser)
  {
    boolean wasSet = false;
    if (aNewUser != null)
    {
      user = aNewUser;
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
      placeholderSportCenter.removeInstructor(this);
    }
    user = null;
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "profilePicURL" + ":" + getProfilePicURL()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "sportCenter = "+(getSportCenter()!=null?Integer.toHexString(System.identityHashCode(getSportCenter())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null");
  }
}