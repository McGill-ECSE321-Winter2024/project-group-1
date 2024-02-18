
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

  //SETTERS

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

  //GETTERS

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
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

  public String getDescription() {

    return description;
  }


}