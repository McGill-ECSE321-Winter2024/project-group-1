
package ca.mcgill.ecse321.sportcenter.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Activity
{

  public enum ClassCategory { Strength, Cardio, Stretch }

 
  private ClassCategory subcategory;

  @Id //name is the PM
  private String name;
  private boolean isApproved;
  private String description;

  @ManyToOne(optional = false) //many activities in SportCenter
  private SportCenter sportCenter;


  //CONSTRUCTORS

  public Activity() {
    
  }

  public Activity(ClassCategory aSubcategory, String aName, boolean aIsApproved, String aDescription, SportCenter aSportCenter)
  {
    subcategory = aSubcategory;
    name = aName;
    isApproved = aIsApproved;
    description = aDescription;
  
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
  /* Code from template association_GetOne */
  public SportCenter getSportCenter()
  {
    return sportCenter;
  }

  public String getDescription() {

    return description;
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